package org.icspl.ecdtlp.activity


import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log.i
import android.widget.Toast
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_sync.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.icspl.ecdtlp.Adapter.SyncAdapter
import org.icspl.ecdtlp.DbHelper.DbHelper
import org.icspl.ecdtlp.DbHelper.DbTables
import org.icspl.ecdtlp.Interfaces.CallbackListener
import org.icspl.ecdtlp.MainActivity
import org.icspl.ecdtlp.R
import org.icspl.ecdtlp.models.InsertSectionModel
import org.icspl.ecdtlp.models.UserDetailModel
import org.icspl.ecdtlp.utils.Common
import org.icspl.ecdtlp.utils.DelayedProgressDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.ParseException
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SyncActivity : AppCompatActivity(), CallbackListener, SyncAdapter.IServerData {


    companion object {
        private val TAG = SyncActivity::class.java.simpleName
    }

    private val mService by lazy { Common.getAPI() }
    private lateinit var loginPref: SharedPreferences
    private val callbackListener: CallbackListener by lazy { this@SyncActivity }
    private lateinit var date: String
    private lateinit var section: String
    private lateinit var quarter: String

    var sEmail: String? = ""
    var sPassword: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync)



        loginPref = getSharedPreferences(getString(R.string.login_pref), AppCompatActivity.MODE_PRIVATE)

        rv_sync.setHasFixedSize(true)
        rv_sync.layoutManager = LinearLayoutManager(this@SyncActivity)

        rv_sync.adapter = SyncAdapter(DbHelper(this@SyncActivity).syncRow(),
                this@SyncActivity, this@SyncActivity)


    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this)
                .setTitle(R.string.sync_before_title)
                .setMessage(R.string.sync_before_message)
                .setPositiveButton("Sync Now") { dialogInterface, _ ->

                    try {
                        sendTlpAndSectionData()


                        val mProgressDialog = DelayedProgressDialog()
                        mProgressDialog.isCancelable = false
                        mProgressDialog.show(supportFragmentManager, "ir")
                        loginPref.getString(DbTables.UserList_Entry.UserName, "-")?.let {
                            mService!!.loginDetails(it,
                                    loginPref.getString(DbTables.UserList_Entry.Password, "-")!!)
                                    .enqueue(object : Callback<UserDetailModel> {
                                        override fun onResponse(call: Call<UserDetailModel>, response: Response<UserDetailModel>) {
                                            if (response.isSuccessful && response.body()?.userDetails != null) {

                                                // insert users sectionDetails details in DB
                                                if (response.body()!!.sections != null) DbHelper(this@SyncActivity).fetchSection(response.body()!!.sections as java.util.ArrayList<UserDetailModel.Section>?)
                                                // insert users quarter details in DB
                                                if (response.body()!!.quarter != null) DbHelper(this@SyncActivity).fetchQuarters(response.body()!!.quarter as java.util.ArrayList<UserDetailModel.Quarter>?)
                                                if (response.body()!!.sectionDetails != null) DbHelper(this@SyncActivity).fetchAllSQDetails(response.body()!!.sectionDetails as java.util.ArrayList<UserDetailModel.SectionDetail>?)
                                                if (response.body()!!.monitoringTLP != null) DbHelper(this@SyncActivity).fetchTblMonitoringDetails(response.body()!!.monitoringTLP as java.util.ArrayList<UserDetailModel.MonitoringTLP>?)

                                            } else
                                                Toasty.error(this@SyncActivity, "No User Found", Toast.LENGTH_LONG).show()
                                            mProgressDialog.cancel()

                                        }

                                        override fun onFailure(call: Call<UserDetailModel>, t: Throwable) {
                                            Toasty.error(this@SyncActivity, "Please try again after 5 sec: \n " +
                                                    "Error: " + t.message, Toast.LENGTH_SHORT).show()
                                            mProgressDialog.cancel()
                                        }

                                    })
                        }
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }

                    dialogInterface.dismiss()
                }.setNegativeButton("Cancel Sync") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    startActivity(Intent(this@SyncActivity, MainActivity::class.java))
                    finish()
                }.show()
        if (Common.isNetworkAvailable(this@SyncActivity)) {

        } else {
            startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
            Toasty.error(this@SyncActivity, "No Internet to sync your data", Toast.LENGTH_LONG).show()
        }
    }

    // Sync Tlp and Section data
    private fun sendTlpAndSectionData() {
        val cursor = DbHelper(this@SyncActivity).sendSectionsDetailsToServer()
        if (cursor.count > 0) {
            while (cursor.moveToNext()) {

                val mProgressDialog = DelayedProgressDialog()
                mProgressDialog.isCancelable = false
                mProgressDialog.show(supportFragmentManager, "ir")
                val id = cursor.getString(cursor.getColumnIndex(DbTables.SectionDetails_Entry._ID))


                loginPref.getString("UserName", "client")?.let {
                    mService.insertSection(
                            it,
                            cursor.getString(cursor.getColumnIndex(DbTables.SectionDetails_Entry.Section)),
                            cursor.getString(cursor.getColumnIndex(DbTables.SectionDetails_Entry.Ip_Voltg)),
                            cursor.getString(cursor.getColumnIndex(DbTables.SectionDetails_Entry.Op_Voltg)),
                            cursor.getString(cursor.getColumnIndex(DbTables.SectionDetails_Entry.Ext_Refvoltg)),
                            cursor.getString(cursor.getColumnIndex(DbTables.SectionDetails_Entry.Op_Curnt)),
                            cursor.getString(cursor.getColumnIndex(DbTables.SectionDetails_Entry.Intrnl_Refvoltg)),
                            cursor.getString(cursor.getColumnIndex(DbTables.SectionDetails_Entry.Ref_Sectr)),
                            cursor.getString(cursor.getColumnIndex(DbTables.SectionDetails_Entry.Coarse_Cntrl)),
                            cursor.getString(cursor.getColumnIndex(DbTables.SectionDetails_Entry.Fine_Cntrl)),
                            cursor.getString(cursor.getColumnIndex(DbTables.SectionDetails_Entry.PSP_Reqrd)),
                            cursor.getString(cursor.getColumnIndex(DbTables.SectionDetails_Entry.DateofEntry)),
                            cursor.getString(cursor.getColumnIndex(DbTables.SectionDetails_Entry.Quaters)))
                            .enqueue(object : Callback<List<InsertSectionModel>> {

                                override fun onResponse(call: Call<List<InsertSectionModel>>, response: Response<List<InsertSectionModel>>) {
                                    if (response.isSuccessful) {
                                        i(TAG, "Sts called: ${response.body()!![0].sts}")
                                        if (response.body()?.get(0)?.sts!! > 0) {
                                            DbHelper(this@SyncActivity).doneSectionStatus(id)
                                            callbackListener.onCompleteListener()
                                        }
                                    }

                                    mProgressDialog.cancel()
                                }

                                override fun onFailure(call: Call<List<InsertSectionModel>>, t: Throwable) {
                                    mProgressDialog.cancel()
                                }
                            })
                }

            }
            cursor.close()
        } else {
            i(TAG, "Sync")
            syncTlp()
        }

    }

    private fun syncTlp() {

        val cursor = DbHelper(this@SyncActivity).sendTlpDetails(date, section, quarter)
        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {

                val mProgressDialog = DelayedProgressDialog()
                mProgressDialog.isCancelable = false
                mProgressDialog.show(supportFragmentManager, "tlp")

                val doneId = cursor.getInt(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry._ID))

                val tlpFile = File(cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.TLPFile)))
                val readingFile = File(cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Reading_File)))
                val selfieFile = File(cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Selfie_File)))

                val requesttlp = RequestBody.create(MediaType.parse("multipart/form-data"), tlpFile)
                val requestreading = RequestBody.create(MediaType.parse("multipart/form-data"), readingFile)
                val requestSelfie = RequestBody.create(MediaType.parse("multipart/form-data"), selfieFile)

                val tlpBody = MultipartBody.Part.createFormData("tlp_file", tlpFile.name, requesttlp)
                val readingBody = MultipartBody.Part.createFormData("reading_file", readingFile.name, requestreading)
                val selfieBody = MultipartBody.Part.createFormData("selfie", selfieFile.name, requestSelfie)

//                val startKmFile = File(cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Start_Km_File)))
//                val endKmfile = File(cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.End_Km_file)))
//                val vehicleNoile = File(cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Vehicle_No_File)))

//                val requestStart = RequestBody.create(MediaType.parse("multipart/form-data"), startKmFile)
//                val requestEnd = RequestBody.create(MediaType.parse("multipart/form-data"), endKmfile)
//                val requestVehicleNo = RequestBody.create(MediaType.parse("multipart/form-data"), vehicleNoile)
//
//                val strtKmBody = MultipartBody.Part.createFormData("start_km_file", tlpFile.name, requestStart)
//                val endKmBody = MultipartBody.Part.createFormData("end_km_file", readingFile.name, requestEnd)
//                val vehicalNoBody = MultipartBody.Part.createFormData("vehicle_no_file", selfieFile.name, requestVehicleNo)

                i(TAG, "Data count: " + DbHelper(this@SyncActivity).countMaxSending(
                        cursor.getString(cursor.getColumnIndex(DbTables.SectionDetails_Entry.Section)),
                        cursor.getString(cursor.getColumnIndex(DbTables.SectionDetails_Entry.Quaters))))
                val max_num = DbHelper(this@SyncActivity).countMaxSending(cursor.getString(cursor.getColumnIndex(DbTables.SectionDetails_Entry.Section)),
                        cursor.getString(cursor.getColumnIndex(DbTables.SectionDetails_Entry.Quaters)))

                try {
                    mService.insertTlpNew(
                        RequestBody.create(MediaType.parse("text/plain"), loginPref.getString("client_id", "-")),
                        RequestBody.create(MediaType.parse("text/plain"), loginPref.getString("UserName", "-")),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Section))),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.TS_No))),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.TS_Type))),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.TS_Km))),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.TS_Location))),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getDouble(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.PSP_Min)).toString()),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getDouble(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.PSP_Max)).toString()),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getDouble(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Casing_Min)).toString()),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getDouble(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Casing_Max)).toString()),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Valve))),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getDouble(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Potential_1)).toString()),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getDouble(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Potential_2)).toString()),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getDouble(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.ZINC_Value)).toString()),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getDouble(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.AC_PSP)).toString()),
                        RequestBody.create(MediaType.parse("text/plain"), if (cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.S_DATE)) == null) "-" else cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.S_DATE))),
                        RequestBody.create(MediaType.parse("text/plain"), if (cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.S_Time)) == null) "-" else cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.S_Time))),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.NCordinate))),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.ECordinate))),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.DateofEntry))),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Quaters))),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.ManualRemarks))),
                        tlpBody,
                        readingBody,
                        selfieBody,
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.type_client))),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.tsno_client))),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Driver_Name))),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Driver_Mob_Number))),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Vehicle_Number))),
                        RequestBody.create(MediaType.parse("text/plain"), cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.Start_Km))),
                        RequestBody.create(MediaType.parse("text/plain"), if (cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.End_Km)) == null) "-" else cursor.getString(cursor.getColumnIndex(DbTables.Monnitoring_Tlp_Entry.End_Km))),
                        RequestBody.create(MediaType.parse("text/plain"), "${cursor.position + 1}"),
                        RequestBody.create(MediaType.parse("text/plain"), "${max_num}")
                    )
                        .enqueue(object : Callback<List<InsertSectionModel>> {

                            override fun onResponse(call: Call<List<InsertSectionModel>>, response: Response<List<InsertSectionModel>>) {
                                if (response.isSuccessful) {
//                                    strtKmBody,
//                                    endKmBody,
//                                    vehicalNoBody,
                                    if (response.body() != null) {
                                        if (response.body()!![0].sts > 0) {
                                            i(TAG, "Done ID: $doneId")
                                            i(TAG, "Data count: ${response.body()!![0].sts}")
                                            sEmail = "intechsoftcell@gmail.com"
                                            sPassword = "master@008"
                                            val properties = Properties()
                                            properties["mail.smtp.auth"] = "true"
                                            properties["mail.smtp.starttls.enable"] = "true"
                                            properties["mail.smtp.host"] = "smtp.gmail.com"
                                            properties["mail.smtp.port"] = "587"
                                            val session = Session.getInstance(
                                                properties,
                                                object : Authenticator() {
                                                    override fun getPasswordAuthentication(): PasswordAuthentication {
                                                        return PasswordAuthentication(
                                                            sEmail,
                                                            sPassword
                                                        )
                                                    }
                                                })

                                            val message: Message = MimeMessage(session)
                                            try {
                                                message.setFrom(InternetAddress(sEmail))
                                                message.setRecipients(
                                                    Message.RecipientType.TO,
                                                    InternetAddress.parse("harshkamble4155@gmail.com")
                                                )
                                                message.subject = "TLP Monitoring details"
                                                message.setText("This is just message")

                                                SendMail().execute(message)

                                            } catch (e1: MessagingException) {
                                                e1.printStackTrace()
                                            }
                                            
                                            DbHelper(this@SyncActivity).updateDoneMonitoringStage(doneId)
                                            callbackListener.onCompleteListenerTlp()
                                        }
                                    }
                                }

                                mProgressDialog.cancel()
                            }

                            override fun onFailure(call: Call<List<InsertSectionModel>>, t: Throwable) {
                                mProgressDialog.cancel()
                                startActivity(Intent(this@SyncActivity, MainActivity::class.java))
                                Toasty.error(this@SyncActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                            }
                        })
                } catch (e : Exception) {
                    e.printStackTrace()
                    Toasty.error(this@SyncActivity, "Some error has occured please sync after some time...")
                }

            }
            cursor.close()
        } else {
            startActivity(Intent(this@SyncActivity, MainActivity::class.java))
            Toasty.success(this@SyncActivity, "", Toast.LENGTH_LONG).show()
        }
    }

    class SendMail() : AsyncTask<Message, String, String>() {
        override fun doInBackground(vararg messages: Message?): String {
            return try {
                Transport.send(messages.get(0))
                "Success"
            } catch (e: MessagingException) {
                e.printStackTrace()
                "Error"
            }
        }

        override fun onPostExecute(s: String?) {
            super.onPostExecute(s)

        }

    }

    override fun onCompleteListener() {
        i(TAG, "Interface called")
        sendTlpAndSectionData()
    }

    override fun onCompleteListenerTlp() {
        syncTlp()
    }


    override fun sendingDataCallback(date: String, section: String, quarter: String) {
        i(TAG, "Data: $date")
        this.date = date
        this.section = section
        this.quarter = quarter
        showAlertDialog()
    }

}
