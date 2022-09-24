package org.icspl.ecdtlp.fragments


import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.util.Log.i
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_view_report.view.*
import org.icspl.ecdtlp.Adapter.PreviewAdapter
import org.icspl.ecdtlp.DbHelper.DbHelper
import org.icspl.ecdtlp.DbHelper.DbTables
import org.icspl.ecdtlp.MainActivity
import org.icspl.ecdtlp.R
import org.icspl.ecdtlp.models.PreviewModel

class ViewReportFragment : Fragment() {
    private lateinit var mContext: Context
    private var section: String? = null
    private var quarter: String? = null
    private var userId: String? = null
    private var mDialog: AlertDialog? = null
    private var mDatabase: SQLiteDatabase? = null
    private lateinit var prewiewList: ArrayList<PreviewModel>
    private lateinit var previewAdapter: PreviewAdapter
    private lateinit var mView: View

    private val mHelper: DbHelper by lazy {
        DbHelper(mContext)
    }

    companion object {

        private const val TAG = "ViewReportFragment"
    }

    private lateinit var loginPref: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_view_report, container, false)


        val b = arguments
        mContext = mView.context

        if (b != null) {
            loginPref = mContext.getSharedPreferences(getString(R.string.login_pref), AppCompatActivity.MODE_PRIVATE)

            quarter = b.getString("quarter", null)
            userId = loginPref.getString("UserName", null)
            section = b.getString("section", null)
            Log.i(TAG, "onCreateView USER ID: $userId : Q: $quarter s: $section u:$userId")
        }

        init(mView)
        return mView
    }

    private fun init(mView: View) {
        mContext = mView.context
        mDialog = SpotsDialog(mContext, "Please Wait...")
        mDatabase = mHelper.writableDatabase
        prewiewList = ArrayList()

        initRV()

    }

    // preview RV by the help of Cursor
    private fun initRV() {
        //RV
        mView.recycler_preview.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(mContext.applicationContext, LinearLayoutManager.VERTICAL, false)
        mView.recycler_preview.layoutManager = layoutManager
        mView.recycler_preview.itemAnimator = DefaultItemAnimator()
        val mCursor = DbHelper(mContext).previewListData(userId, section, quarter)

        if (mCursor != null && mCursor.count > 0) {
            while (mCursor.moveToNext()) {

                prewiewList.add(PreviewModel(mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.TS_No)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.TS_Type)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.TS_Type)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.TS_Km)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.TS_Km)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.TS_Location)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.TS_Location)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.PSP_Min)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.PSP_Min)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.PSP_Max)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.PSP_Max)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Casing_Min)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Casing_Min)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Casing_Max)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Casing_Max)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Valve)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Valve)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Potential_1)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Potential_1)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Potential_2)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Potential_2)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.AC_PSP)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.AC_PSP)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.ZINC_Value)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.ZINC_Value)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.S_DATE)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.S_DATE)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.S_Time)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.S_Time)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.ManualRemarks)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.ManualRemarks)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.TLPFile)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.TLPFile)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Reading_File)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Reading_File)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Selfie_File)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Selfie_File)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Start_Km_File)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Start_Km_File)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.End_Km_file)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.End_Km_file)),
                        if (mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Vehicle_No_File)) == null) "" else mCursor.getString(mCursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Vehicle_No_File)))
                )
            }

            previewAdapter = PreviewAdapter(prewiewList, mContext)
            mView.recycler_preview.adapter = previewAdapter


            i(TAG, "List Size: " + prewiewList.size)
            previewAdapter.notifyDataSetChanged()
        }
    }

    /*private fun decodeBaseImage(tag: String) {

        val decodedString = Base64.decode(tag, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

        val dialogView = LayoutInflater.from(activity).inflate(R.layout.image_view_alert, null)

        val img = dialogView.img_view_photos
        img.setImageBitmap(decodedByte)
        val alertDialog = AlertDialog.Builder(mContext)
        alertDialog.setTitle("View Image")
        alertDialog.setView(dialogView)
        alertDialog.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }*/

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val mainActivity = activity as MainActivity?
        val sToolbar: Toolbar?
        sToolbar = mainActivity?.findViewById(R.id.toolbar)
        sToolbar?.title = "View Report"

    }
}