package org.icspl.ecdtlp.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.InputFilter
import android.text.TextUtils
import android.util.Log
import android.util.Log.i
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import dmax.dialog.SpotsDialog
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*
import org.icspl.ecdtlp.DbHelper.DbHelper
import org.icspl.ecdtlp.DbHelper.DbTables
import org.icspl.ecdtlp.Helper.APIHelper
import org.icspl.ecdtlp.Interfaces.ApiService
import org.icspl.ecdtlp.MainActivity
import org.icspl.ecdtlp.R
import org.icspl.ecdtlp.models.*
import org.icspl.ecdtlp.utils.Common
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

class LoginActivity : AppCompatActivity() {
    internal var mLoginPreferences: SharedPreferences? = null
    private var defaultPreferences: SharedPreferences? = null
    private lateinit var name: String
    private lateinit var pass: String
    private var mService: ApiService? = null
    private var mDialog: AlertDialog? = null
    private var backPressed: Long = 0
    private lateinit var analytics: FirebaseAnalytics

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }


    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun init() {

        mService = Common.getAPI()
        et_username!!.filters = arrayOf<InputFilter>(InputFilter.AllCaps())
        mLoginPreferences =
            getSharedPreferences(getString(R.string.login_pref), Context.MODE_PRIVATE)
        defaultPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        mDialog = SpotsDialog(this, "Please Wait...")
        mDialog!!.setCancelable(false)
        analytics = FirebaseAnalytics.getInstance(this@LoginActivity)
        btn_sigin.setOnClickListener {
//            /*Crashlytics.getInstance().crash();} // Force a crash*/
            if (ActivityCompat.checkSelfPermission(
                    this@LoginActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this@LoginActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this@LoginActivity,
                    Manifest.permission.CAMERA
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@LoginActivity,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    ),
                    1599
                )
            } else {
                val path1 =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .toString() + "/TLP Monitoring"
                        .toString()
                val file1 = File(path1)
                if (!file1.exists()) {
                    file1.mkdirs()
                } else {
                    login()
                }
            }
        }
    }

    fun msg(text: String) {
        Toast.makeText(this@LoginActivity, text, Toast.LENGTH_SHORT).show()
    }

    // Login Button
    fun login() {
        name = et_username!!.text.toString()
        pass = et_password!!.text.toString()


        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pass)) {
            if (mLoginPreferences != null) { // something is there
                val un = mLoginPreferences!!.getString(getString(R.string.login_name), null)
                val ps = mLoginPreferences!!.getString(getString(R.string.login_pass), null)

                if (ps == null && un == null) { //get UN,Pass from SP andput it in ET

                    if (Common.isNetworkAvailable(this)) {

                        createDB()
                        closeFirstTimeLogin()

                    } else
                        Toasty.error(this, "Internet Required First Time", Toast.LENGTH_SHORT)
                            .show()
                }
            }
        } else
            Toasty.error(this, "Field Cannot be Blank", Toast.LENGTH_SHORT).show()


    }


    private fun createDB() {


        Common.showDialog(mDialog!!)

        mDialog!!.setMessage("getting Login Detail")
        mService!!.loginDetails(name, pass)
            .enqueue(object : Callback<UserDetailModel> {
                override fun onResponse(
                    call: Call<UserDetailModel>,
                    response: Response<UserDetailModel>
                ) {
                    if (response.isSuccessful && response.body()?.userDetails != null) {

                        val model = response.body()!!.sections

                        model?.forEach {
                            i(TAG, "Section: ${it.section}")
                        }

                        // insert users client id details inSP
                        if (response.body() != null) insertDataInLoginDb(response.body()!!.userDetails)
                        /* // insert users sections details inSP
                         if (response.body()!!.sections != null) DbHelper(this@LoginActivity).insertSection(response.body()!!.sections as java.util.ArrayList<UserDetailModel.Section>?)
                         // insert users quarter details in DB
                         if (response.body()!!.quarter != null) DbHelper(this@LoginActivity).insertQuarters(response.body()!!.quarter as java.util.ArrayList<UserDetailModel.Quarter>?)
                         // insert users sectionDetails details in DB
                         if (response.body()!!.sectionDetails != null) DbHelper(this@LoginActivity).
                                 getAllSQDetails(response.body()!!.sectionDetails as java.util.ArrayList<UserDetailModel.SectionDetail>?)
                         // insert users Monitoring TLP details in DB
                         if (response.body()!!.monitoringTLP != null) DbHelper(this@LoginActivity).getTblMonitoringDetails(response.body()!!.monitoringTLP as java.util.ArrayList<UserDetailModel.MonitoringTLP>?)
                         // insert users Monitoring TLP details in DB
                         */

                        if (response.body()!!.sections != null) DbHelper(this@LoginActivity).fetchSection(
                            response.body()!!.sections as java.util.ArrayList<UserDetailModel.Section>?
                        )
                        if (response.body()!!.quarter != null) DbHelper(this@LoginActivity).fetchQuarters(
                            response.body()!!.quarter as java.util.ArrayList<UserDetailModel.Quarter>?
                        )
                        if (response.body()!!.sectionDetails != null) DbHelper(this@LoginActivity).fetchAllSQDetails(
                            response.body()!!.sectionDetails as java.util.ArrayList<UserDetailModel.SectionDetail>?
                        )
                        if (response.body()!!.monitoringTLP != null) DbHelper(this@LoginActivity).fetchTblMonitoringDetails(
                            response.body()!!.monitoringTLP as java.util.ArrayList<UserDetailModel.MonitoringTLP>?
                        )
                        if (response.body()!!.trUnit != null) DbHelper(this@LoginActivity).insertTbl_TrUnit(
                            response.body()!!.trUnit as java.util.ArrayList<UserDetailModel.TrUnit>?
                        )

                        Log.d(TAG, "onResponse: " + response.body()!!.sectionDetails)

                        /* AsyncTask.execute {

                         }*/
                        Common.disableDialog(mDialog)
                        navigateUserHome()
                    } else {
                        Toasty.error(this@LoginActivity, "No User Found", Toast.LENGTH_LONG).show()
                        Common.disableDialog(mDialog)
                    }

                }

                override fun onFailure(call: Call<UserDetailModel>, t: Throwable) {
                    Toasty.error(
                        this@LoginActivity, "Please try again after 5 sec: \n " +
                                "Error: " + t.message, Toast.LENGTH_SHORT
                    ).show()
                    Common.disableDialog(mDialog)
                }

            })

        /*  val mCall = mService !!. checkLogin (name, pass)


    APIHelper.enqueueWithRetry(mCall, 2, object : Callback<UserLogin> {
      override fun onResponse(call: Call<UserLogin>, response: Response<UserLogin>) {

          if (response.isSuccessful) {

              if (response.body() != null) {
                  if (response.body()!!.isExist!!) {
                      // user exist in Server DB
                      getUserDetails()
                      mLoginPreferences!!.edit()
                              .putString(getString(R.string.login_name), name)
                              .putString(getString(R.string.login_pass), pass)
                              .apply()
                      Toasty.success(this@LoginActivity, "Login Successfully", Toast.LENGTH_SHORT).show()

                  } else {
                      openFirstTimeLogin()
                      Common.disableDialog(mDialog!!)
                      Toasty.warning(this@LoginActivity, "Incorrect Username of Password", Toast.LENGTH_SHORT).show()
                  }
              }

          } else {
              Log.i(TAG, "onResponse: error: " + response.errorBody()!!)
          }


      }

      override fun onFailure(call: Call<UserLogin>, t: Throwable) {

          Toasty.error(this@LoginActivity, "Please try again after 5 sec: \n " +
                  "Error: " + t.message, Toast.LENGTH_SHORT).show()
      }
    })*/

    }

    // After login Success
    private fun getUserDetails() {
        mDialog!!.setMessage("Creating Login Detail in Local")
        val mCall = mService!!.userDetails(name, pass)
        APIHelper.enqueueWithRetry(mCall, 3, object : Callback<UserLoginDetail> {
            override fun onResponse(
                call: Call<UserLoginDetail>,
                response: Response<UserLoginDetail>
            ) {

                if (response.isSuccessful) {

                    if (response.body() != null) {
                        val mDetail = response.body()
                        // insertDataInLoginDb(mDetail!!)
                        //  Log.i(TAG, "onResponse: " + mDetail.displayName)
                    }
                } else {
                    i(TAG, "onResponse: error: " + response.errorBody()!!)
                    Toasty.warning(
                        this@LoginActivity,
                        "Error: " + response.errorBody()!!,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<UserLoginDetail>, t: Throwable) {
                Toasty.error(
                    this@LoginActivity, "Please try again after 5 sec: \n " +
                            "Error: " + t.message, Toast.LENGTH_SHORT
                ).show()
            }
        })

    }

    // @setup:- fill user login table
    private fun insertDataInLoginDb(mList: List<UserDetailModel.UserDetail>?) {
        // Save Client Login Details id to SP
        if (mList?.size!! > 0)
            mLoginPreferences!!.edit()
                .putString(DbTables.UserList_Entry.client_id, mList[0].clientID)
                .putString(DbTables.UserList_Entry.DisplayName, mList[0].displayName)
                .putString(DbTables.UserList_Entry.Status, mList[0].status)
                .putString(DbTables.UserList_Entry.UserName, mList[0].userName)
                .putString(DbTables.UserList_Entry.Password, mList[0].password)
                .apply()
        // insertSection()

    }

    // Insert Section in Db Tbl_Section
    private fun insertSection(sectionsList: List<UserDetailModel.Section>?) {
        mDialog!!.setMessage("getting Section Detail")
        val mCall = mService!!.getSection(name)
        APIHelper.enqueueWithRetry(mCall, 3, object : Callback<GetSectionModel> {
            override fun onResponse(
                call: Call<GetSectionModel>,
                response: Response<GetSectionModel>
            ) {
                if (response.isSuccessful) {

                    if (response.body() != null) {
                        val mList =
                            response.body()!!.getTrue() as ArrayList<GetSectionModel.SectionTrue>
                        if (mList.size > 0) {
                            //DbHelper(this@LoginActivity).insertSection(mList)
                            // insertQuarters()

                        } else {
                            i(TAG, "onResponse: No data:")
                        }
                    }
                } else {
                    i(TAG, "onResponse: error: " + response.errorBody()!!)
                    Toasty.warning(
                        this@LoginActivity,
                        "Error: " + response.errorBody()!!,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<GetSectionModel>, t: Throwable) {
                Toasty.error(
                    this@LoginActivity, "Please try again after 5 sec: \n " +
                            "Error: " + t.message, Toast.LENGTH_SHORT
                ).show()
            }
        })

    }

    //  @setup: Fill Quarters Details Table
    private fun insertQuarters() {

        mDialog!!.setMessage("getting Quarters Detail")

        val modelCall = mService!!.getQuarters(name)
        APIHelper.enqueueWithRetry(modelCall, 3, object : Callback<GetQuartersModel> {
            override fun onResponse(
                call: Call<GetQuartersModel>,
                response: Response<GetQuartersModel>
            ) {

                if (response.body() != null) {
                    val mList =
                        response.body()!!.getTrue() as ArrayList<GetQuartersModel.QuartersTrue>
                    if (mList.size > 0) {
                        //DbHelper(this@LoginActivity).insertQuarters(mList)
                        //  getAllSQDetails()
                    } else {
                        i(TAG, "onResponse: No data:")
                    }
                }

            }

            override fun onFailure(call: Call<GetQuartersModel>, t: Throwable) {
                Toasty.error(
                    this@LoginActivity, "Please try again after 5 sec: \n " +
                            "Error: " + t.message, Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    //@Setup: all Sections, Quarters Details to show after Spinner Selected
    private fun getAllSQDetails() {

        val mCall = mService!!.getAllSQDetails(name)
        APIHelper.enqueueWithRetry(mCall, 3, object : Callback<GetAllSQModel> {
            override fun onResponse(call: Call<GetAllSQModel>, response: Response<GetAllSQModel>) {

                if (response.body() != null) {
                    val mList = response.body()!!.getTrue() as ArrayList<GetAllSQModel.SQTrue>
                    if (mList.size > 0) {

                        //DbHelper(this@LoginActivity).getAllSQDetails(mList)
                        insertTblTrUnit()
                    } else {
                        i(TAG, "onResponse: No data:")
                    }
                }
            }

            override fun onFailure(call: Call<GetAllSQModel>, t: Throwable) {
                Toasty.error(
                    this@LoginActivity, "Please try again after 5 min: \n " +
                            "Error: " + t.message, Toast.LENGTH_SHORT
                ).show()
            }
        }
        )
    }

    // Inserting In table TbL_TRUnit_Entry
    private fun insertTblTrUnit() {

        mDialog!!.setMessage("Getting Tr Unit Details.... ")
        val mCall = mService!!.getAllTbl_Tr_Unit(name)
        APIHelper.enqueueWithRetry(mCall, 3, object : Callback<GetAllTblTrUnit> {
            override fun onResponse(
                call: Call<GetAllTblTrUnit>,
                response: Response<GetAllTblTrUnit>
            ) {

                if (response.body() != null) {
                    val mList =
                        response.body()!!.`true` as ArrayList<GetAllTblTrUnit.TblTrUnit_True>?
                    if (mList != null && mList.size > 0) {

                        //DbHelper(this@LoginActivity).insertTbl_TrUnit(mList)
                        getTblMonitoringDetails()

                    } else {
                        i(TAG, "onResponse: No data:")
                    }
                }
            }

            override fun onFailure(call: Call<GetAllTblTrUnit>, t: Throwable) {
                Toasty.error(
                    this@LoginActivity, "Please try again after 5 min: \n " +
                            "Error: " + t.message, Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    // @setup:- Creating Tbl_Monitoring table
    private fun getTblMonitoringDetails() {
        mDialog!!.setMessage("Getting Tlp Monitoring Details.... ")
        val mCall = mService!!.getAllTblMonitoringDetails(name)
        APIHelper.enqueueWithRetry(mCall, 4, object : Callback<GetAllTlpMonitoringDetailsModel> {
            override fun onResponse(
                call: Call<GetAllTlpMonitoringDetailsModel>,
                response: Response<GetAllTlpMonitoringDetailsModel>
            ) {


                if (response.body() != null) {
                    val mList =
                        response.body()!!.`true` as ArrayList<GetAllTlpMonitoringDetailsModel.True>?
                    if (mList != null && mList.size > 0) {

                        //DbHelper(this@LoginActivity).getTblMonitoringDetails(mList)
                        navigateUserHome()
                    } else
                        i(TAG, "onResponse: No data:")
                }
            }

            override fun onFailure(call: Call<GetAllTlpMonitoringDetailsModel>, t: Throwable) {
                Common.disableDialog(mDialog!!)
                Toasty.error(
                    this@LoginActivity, "Please try again after 5 min: \n " +
                            "Error: " + t.message, Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    //  user login success go to home
    private fun navigateUserHome() {
        val i = Intent(this, MainActivity::class.java)

        val bundle = Bundle()
        bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, 2)
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "In LoginScreen")

        analytics.setUserId(UUID.randomUUID().toString())
        analytics.setUserProperty("Property", "Going MainaActivity")

        //Logs an app event.
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)


        i.putExtra(getString(R.string.intent_home), 1)
        startActivity(i)
        finish()
    }


    // login first time
    private fun openFirstTimeLogin() {
        defaultPreferences!!.edit()
            .putBoolean(getString(R.string.first_time), false)
            .apply()
    }

    // login first time
    private fun closeFirstTimeLogin() {

        defaultPreferences!!.edit()
            .putBoolean(getString(R.string.first_time), true)
            .apply()
    }

    override fun onBackPressed() {
        val timeDelay = 2000
        if (backPressed + timeDelay > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            Toast.makeText(baseContext, "Press once again to exit!", Toast.LENGTH_SHORT).show()
        }
        backPressed = System.currentTimeMillis()
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}
