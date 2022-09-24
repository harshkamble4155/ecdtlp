package org.icspl.ecdtlp.fragments


import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.util.Log.i
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_user_home.*
import kotlinx.android.synthetic.main.fragment_user_home.view.*
import org.icspl.ecdtlp.DbHelper.DbHelper
import org.icspl.ecdtlp.DbHelper.DbTables
import org.icspl.ecdtlp.MainActivity
import org.icspl.ecdtlp.R
import java.text.SimpleDateFormat
import java.util.*


class UserHomeFragment : android.app.Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {


    companion object {
        private const val TAG: String = "UserHomeFragment"
    }

    private var mCursor: Cursor? = null
    private var mDatabase: SQLiteDatabase? = null
    private lateinit var mContext: Context
    private val mHelper: DbHelper by lazy {
        DbHelper(mContext)
    }

    private lateinit var loginPref: SharedPreferences

    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_user_home, container, false)
        init()
        return mView
    }

    private fun init() {
        mContext = mView.context
        mDatabase = mHelper.writableDatabase
        loginPref = mContext.getSharedPreferences(getString(R.string.login_pref), AppCompatActivity.MODE_PRIVATE)

        mView.btn_user_next?.setOnClickListener(this)
        mView.btn_user_submit?.setOnClickListener(this)
        mView.sp_sections?.onItemSelectedListener = this
        mView.sp_quarter.onItemSelectedListener = this

        //checkPreviousDateData(loginPref.getString("previousDate", null))
        loadSectionSpinner()

    }

    //check previous date
    public fun checkPreviousDateData(prefDate: String?) {
        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val dbDate = DbHelper(mContext).compareDates(sp_sections.selectedItem.toString(),
                sp_quarter.selectedItem.toString())
        val dFormater = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

        if (dbDate != null) { // previous Date  available in preference
            val cDate = dFormater.parse(currentDate)// current Date
            val pDate = dFormater.parse(dbDate) // Preference Date
            i(TAG, "Data avalable in preference $prefDate")
            if (pDate < cDate) { // 20/11/2018 < 28//11/2018

                AlertDialog.Builder(mContext)
                        .setTitle("Please Note")
                        .setMessage("Do you Wish to Add Any More TLP Of Previous Date:-$dbDate?")
                        .setCancelable(false)
                        .setPositiveButton("Yes") { dialogInterface, _ ->
                            dialogInterface.dismiss()
                            navFragment()
                        }.setNegativeButton("No") { dialogInterface, _ ->
                            dialogInterface.dismiss()
                            val calender = Calendar.getInstance()
                            calender.time = pDate;
                            calender.add(Calendar.DATE, 1)

                            DbHelper(mContext).updatePreviousDate(dFormater.format(calender.time).toString(),
                                    sp_sections.selectedItem.toString(), sp_quarter.selectedItem.toString())

                            loginPref.edit().putString("previousDate", dFormater.format(calender.time).toString()).apply()

                            i(TAG, "Current date $currentDate  Chenged Date:${dFormater.format(calender.time)}}}")

                            checkPreviousDateData(loginPref.getString("previousDate", null))
                        }.show()
            } else if (pDate == cDate) {
                navFragment()
            }
        } else { // previous Date  not available in preference
            navFragment()
        }
    }

    private fun navFragment() {
        val mFragment = ShowRecordFragment()
        val mBundle = Bundle()
        mBundle.putString("section", mView.sp_sections?.selectedItem.toString())
        mBundle.putString("quarter", mView.sp_quarter?.selectedItem.toString())

        mFragment.arguments = mBundle

        fragmentManager
                .beginTransaction()
                .replace(R.id.main_container, mFragment, getString(R.string.frag_user_record))
                .addToBackStack(getString(R.string.frag_user_home))
                .commit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            val mainActivity = activity as MainActivity
            val sToolbar: Toolbar = mainActivity.findViewById(R.id.toolbar)
            sToolbar.title = "User Home"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //setup section Spinner
    private fun loadSectionSpinner() {
        mView.sp_sections?.adapter = ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, DbHelper(mContext).sectionsList)
        mView.sp_quarter?.adapter = ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, DbHelper(mContext).quartersList)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position > 0) when (parent!!.id) {
            R.id.sp_sections -> {

            }
            R.id.sp_quarter -> {
                if (position > 0 && mView.sp_sections.selectedItemPosition > 0) getEdittextData()
                else Toasty.error(mContext, "Choose Appropriate data", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun getEdittextData() {
//        mCursor = DbHelper(mContext).getSQDetails(mView.sp_sections.selectedItem.toString())
        mCursor = DbHelper(mContext).getSQDetails(mView.sp_sections.selectedItem.toString(),
                mView.sp_quarter.selectedItem.toString())
        if (mCursor != null && mCursor!!.count > 0) {
            showTexttViewVisiblity()
            disableEditTexttVisiblity()
            while (mCursor!!.moveToNext()) {

                mView.tv_ip_voltage.text = mCursor!!.getString(5)
                mView.tv_op_voltage.text = mCursor!!.getString(6)
                mView.tv_ext_voltage.text = mCursor!!.getString(7)
                mView.tv_op_current.text = mCursor!!.getString(8)
                mView.tv_internal_voltage_ref.text = mCursor!!.getString(9)
                mView.tv_ref_selector.text = mCursor!!.getString(10)
                mView.tv_coarse_control.text = mCursor!!.getString(11)
                mView.tv_fine_ctrl.text = mCursor!!.getString(12)
                mView.tv_psp_req.text = mCursor!!.getString(13)

            }
            mView.btn_user_submit.isEnabled = false
            mView.btn_user_next.isEnabled = true


        } else {
            showEditTexttVisiblity()
            disableTexttViewVisiblity()
            mView.btn_user_submit.isEnabled = true
            mView.btn_user_next.isEnabled = true
            Toasty.info(mContext, "No data found", Toast.LENGTH_LONG).show()
        }

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_user_next -> nextButtonClicked()
            R.id.btn_user_submit -> submitButtonClicked()
        }
    }


    // Submit button handle
    private fun submitButtonClicked() {

        val sdf = SimpleDateFormat("E dd yyy hh:mm:ss", Locale.ENGLISH)
        val currentDate = sdf.format(Date())

        System.out.println(" C DATE is  $currentDate")
        if (isEditTextValid(mView.et_ip_voltage) && isEditTextValid(mView.et_op_voltage) && isEditTextValid(mView.et_ext_voltage)
                && isEditTextValid(mView.et_op_current) && isEditTextValid(mView.et_internal_voltage_ref) && isEditTextValid(mView.et_ref_selector)
                && isEditTextValid(mView.et_coarse_control) && isEditTextValid(mView.et_coarse_control) && isEditTextValid(mView.et_fine_ctrl)
                && mView.sp_quarter.selectedItem != "Select" && mView.sp_sections.selectedItem != "Select") {

            val mValues = ContentValues()
            mValues.put(DbTables.SectionDetails_Entry.ClientID, loginPref.getString("client_id", null))
            mValues.put(DbTables.SectionDetails_Entry.ContractorID, loginPref.getString("UserName", null))
            mValues.put(DbTables.SectionDetails_Entry.PipeType, getString(R.string.tlp) as String)
            mValues.put(DbTables.SectionDetails_Entry.Section, sp_sections.selectedItem.toString())
            mValues.put(DbTables.SectionDetails_Entry.Ip_Voltg, mView.et_ip_voltage.text.toString())
            mValues.put(DbTables.SectionDetails_Entry.Op_Voltg, mView.et_op_voltage.text.toString())
            mValues.put(DbTables.SectionDetails_Entry.Ext_Refvoltg, mView.et_ext_voltage.text.toString())
            mValues.put(DbTables.SectionDetails_Entry.Op_Curnt, mView.et_op_current.text.toString())
            mValues.put(DbTables.SectionDetails_Entry.Intrnl_Refvoltg, mView.et_internal_voltage_ref.text.toString())
            mValues.put(DbTables.SectionDetails_Entry.Ref_Sectr, mView.et_ref_selector.text.toString())
            mValues.put(DbTables.SectionDetails_Entry.Coarse_Cntrl, mView.et_coarse_control.text.toString())
            mValues.put(DbTables.SectionDetails_Entry.Fine_Cntrl, mView.et_fine_ctrl.text.toString())
            mValues.put(DbTables.SectionDetails_Entry.PSP_Reqrd, mView.et_psp_req.text.toString())
            mValues.put(DbTables.SectionDetails_Entry.DateofEntry, currentDate)
            mValues.put(DbTables.SectionDetails_Entry.Quaters, mView.sp_quarter.selectedItem.toString())
            mValues.put(DbTables.SectionDetails_Entry.Server_Status, getString(R.string.pending))

            val id = mDatabase?.insert(DbTables.TbL_SectionDetails, null, mValues)

            if (id != null) {
                if (id > 0) {
                    Toasty.success(mContext, "Data Inserted Successfully", Toast.LENGTH_SHORT).show()
                    clearText()
                } else Toasty.error(mContext, "Data Insertion Failed", Toast.LENGTH_SHORT).show()
            }
        } else Toasty.error(mContext, "Aww! Please read once again what you are Submitting", Toast.LENGTH_LONG).show()

    }

    private fun isEditTextValid(e: EditText?): Boolean {
        return !TextUtils.isEmpty(e?.text.toString())
    }

    // Next button handle
    private fun nextButtonClicked() {

        if (sp_quarter.selectedItem.toString() == getString(R.string.select) && sp_sections.selectedItem.toString() == getString(R.string.select)) {
            Toasty.info(mContext, "Please Select some Sections & Quarters", Toast.LENGTH_LONG).show()
            return
        }

        if (sp_quarter.selectedItem.toString() == getString(R.string.select)) {
            Toasty.info(mContext, "Please Select some Quarters", Toast.LENGTH_LONG).show()
            return
        }

        if (sp_sections.selectedItem.toString() == getString(R.string.select)) {
            Toasty.info(mContext, "Please Select some Sections", Toast.LENGTH_LONG).show()
            return
        }


        if (sp_quarter.selectedItem.toString() != getString(R.string.select) &&
                sp_sections.selectedItem.toString() != getString(R.string.select)) {


            checkPreviousDateData(loginPref.getString("previousDate", null))


        } else run { Toasty.info(mContext, "Please Select some Sections and Quarters", Toast.LENGTH_LONG).show() }

    }


    // clear all Edit Text
    private fun clearText() {
        mView.et_ip_voltage.setText("")
        mView.et_op_voltage.setText("")
        mView.et_ext_voltage.setText("")
        mView.et_op_current.setText("")
        mView.et_internal_voltage_ref.setText("")
        mView.et_ref_selector.setText("")
        mView.et_coarse_control.setText("")
        mView.et_fine_ctrl.setText("")
        mView.et_psp_req.setText("")
        mView.sp_quarter.setSelection(1)
        mView.sp_sections.setSelection(1)
    }


    // disable  sp_section_text view visiblity
    private fun disableTexttViewVisiblity() {
        mView.tv_ip_voltage.visibility = View.GONE
        mView.tv_op_voltage.visibility = View.GONE
        mView.tv_ext_voltage.visibility = View.GONE
        mView.tv_op_current.visibility = View.GONE
        mView.tv_internal_voltage_ref.visibility = View.GONE
        mView.tv_ref_selector.visibility = View.GONE
        mView.tv_coarse_control.visibility = View.GONE
        mView.tv_fine_ctrl.visibility = View.GONE
        mView.tv_psp_req.visibility = View.GONE

    }

    // disable  edittext view visiblity
    private fun disableEditTexttVisiblity() {
        mView.et_ip_voltage.visibility = View.GONE
        mView.et_op_voltage.visibility = View.GONE
        mView.et_ext_voltage.visibility = View.GONE
        mView.et_op_current.visibility = View.GONE
        mView.et_internal_voltage_ref.visibility = View.GONE
        mView.et_ref_selector.visibility = View.GONE
        mView.et_coarse_control.visibility = View.GONE
        mView.et_fine_ctrl.visibility = View.GONE
        mView.et_psp_req.visibility = View.GONE

    }

    // show  sp_section_text view visiblity
    private fun showTexttViewVisiblity() {
        mView.tv_ip_voltage.visibility = View.VISIBLE
        mView.tv_op_voltage.visibility = View.VISIBLE
        mView.tv_ext_voltage.visibility = View.VISIBLE
        mView.tv_op_current.visibility = View.VISIBLE
        mView.tv_internal_voltage_ref.visibility = View.VISIBLE
        mView.tv_ref_selector.visibility = View.VISIBLE
        mView.tv_coarse_control.visibility = View.VISIBLE
        mView.tv_fine_ctrl.visibility = View.VISIBLE
        mView.tv_psp_req.visibility = View.VISIBLE
    }

    // show  edittext view visiblity
    private fun showEditTexttVisiblity() {
        mView.et_ip_voltage.visibility = View.VISIBLE
        mView.et_op_voltage.visibility = View.VISIBLE
        mView.et_ext_voltage.visibility = View.VISIBLE
        mView.et_op_current.visibility = View.VISIBLE
        mView.et_internal_voltage_ref.visibility = View.VISIBLE
        mView.et_ref_selector.visibility = View.VISIBLE
        mView.et_coarse_control.visibility = View.VISIBLE
        mView.et_fine_ctrl.visibility = View.VISIBLE
        mView.et_psp_req.visibility = View.VISIBLE

    }

}