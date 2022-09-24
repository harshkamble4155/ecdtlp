package org.icspl.ecdtlp

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log.i
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*
import org.icspl.ecdtlp.activity.LoginActivity
import org.icspl.ecdtlp.activity.SyncActivity
import org.icspl.ecdtlp.fragments.DeafultMainPage
import org.icspl.ecdtlp.fragments.UserHomeFragment
import org.icspl.ecdtlp.utils.Common
import org.icspl.ecdtlp.utils.UpdateHelper


class MainActivity : AppCompatActivity(), UpdateHelper.OnUpdateCheckListener {

    companion object {
        //private  val TAG = MainActivity::class.java.simpleName
    }

    private val delay = 2000
    //private var isloggedIn = false
    private lateinit var loginPref: SharedPreferences
    //private var drawer: Drawer? = null
    private var backPressed: Long = 0
    private var updateAvailable: Boolean = false
    private var googlePlayLink: String? = null
    private var hideToolbarItems: Boolean = false
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginPref = getSharedPreferences(getString(R.string.login_pref), MODE_PRIVATE)
        setSupportActionBar(toolbar)
        checkUpdate()

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this@MainActivity, { instanceIdResult ->
            val newToken = instanceIdResult.token
            i("newToken", newToken)
        })

        if (loginPref.contains("UserName") && loginPref.contains("Password")) {
            if (updateAvailable) {
                showUpdateALert()
            } else {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_container, UserHomeFragment(), getString(R.string.frag_user_home))
                        .commit()
                showlogoutMenuToolbar()
            }

        } else {
            if (updateAvailable) {
                showUpdateALert()
            } else {
                hideToolbarItems = true
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_container, DeafultMainPage(), getString(R.string.deafult_main_frag))
                        .commit()
            }
        }

        // get intent and change fragment
        val navigator = this.intent.getIntExtra(getString(R.string.intent_home), 0)

        if (navigator == 1 && !updateAvailable) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, UserHomeFragment(), getString(R.string.frag_user_home))
                    .commit()
        }


    }

    private fun checkUpdate() {
        UpdateHelper.with(this)
                .onUpdateCheck(this)
                .check()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_item, menu)
        this@MainActivity.menu = menu
        if (hideToolbarItems) showLiginMenuToolbar() else showlogoutMenuToolbar()
        return super.onCreateOptionsMenu(menu)
    }

    private fun showLiginMenuToolbar() {
        menu?.findItem(R.id.tb_logout)!!.isVisible = false
        menu?.findItem(R.id.tb_sync)!!.isVisible = false
    }

    private fun showlogoutMenuToolbar() {
        if (menu != null) {
            menu!!.findItem(R.id.tb_login).isVisible = false
            menu!!.findItem(R.id.tb_logout).isVisible = true
            menu!!.findItem(R.id.tb_sync).isVisible = true
        }

    }

    // logout button handler
    private fun showLogoutAlert() {
        val mAlertDialog = AlertDialog.Builder(this)
                .setTitle("Please Note:-")
                .setMessage("After logging out your filled data will be deleted.\n\nPLEASE SYNC BEFORE LOGGING OUT ")
                .setPositiveButton("Sync") { _, _ ->
                    startActivity(Intent(this, SyncActivity::class.java))
                }.setNegativeButton("CANCEL") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }.setNeutralButton("Logout") { _, _ ->
                    loginPref.edit().clear().apply()
                    deleteDatabase(Common.DB_NAME)
                    val defaultPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                    defaultPreferences.edit().putBoolean(getString(R.string.first_time), false).apply()
                    finish()
                    startActivity(Intent(this, LoginActivity::class.java))

                }.create()
        mAlertDialog.setCancelable(false)
        mAlertDialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.tb_login -> {
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            }
            R.id.tb_logout -> showLogoutAlert()
            R.id.tb_sync -> {
                // SyncHandler()
                startActivity(Intent(this@MainActivity, SyncActivity::class.java))

            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onUpdateCheckListener(uriApp: String?) {
        Toasty.info(this, "update Available", Toast.LENGTH_LONG).show()
        updateAvailable = true
        googlePlayLink = uriApp
       // showUpdateALert()
    }

    private fun showUpdateALert() {
        val mAlertDialog = AlertDialog.Builder(this)
                .setTitle("New Version Available")
                .setMessage("Please update to new version to continue use")
                .setPositiveButton("UPDATE") { _, _ ->
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("market://$googlePlayLink")
                    startActivity(intent)
                    finish()
                }.create()

        mAlertDialog.setCancelable(false)
        mAlertDialog.show()
    }

    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            if (backPressed + delay > System.currentTimeMillis()) {
                super.onBackPressed()
            } else {
                Toast.makeText(baseContext, "Press once again to exit!", Toast.LENGTH_SHORT).show()
            }
            backPressed = System.currentTimeMillis()
        }


    }
}
