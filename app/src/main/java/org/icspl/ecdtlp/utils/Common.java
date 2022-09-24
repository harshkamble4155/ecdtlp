package org.icspl.ecdtlp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import org.icspl.ecdtlp.Interfaces.ApiService;

public class Common {

    public static final String GET_MATERIALS_URL = "http://192.168.0.243:80/api/Getmaterial";
//    private static final String BASE_URL = "http://192.168.0.243:80/Home/";
      private static final String BASE_URL = "http://icspl.org:82/Home/";
//    private static final String BASE_URL = "http://192.168.0.243:82/Home/";  //this one
    //private static final String BASE_URL = "http://192.168.0.247/tlp_monitoring/";
   // private static final String BASE_URL = "http://10.0.3.2/offline_tlp_monitoring/";
   //private static final String BASE_URL = "http://icspl.org:82/offline_tlp_monitoring/";

    public static String TLP_FILE_DATA = "";
    public static String READING_FILE_DATA = "";
    public static String TYPE_CLIENT = "";
    public static String MANNUAL_REMARK = null;
    public static String TSNo_Client = null;
    public static String TS_Chainage = null;
    // DB Info
    public static final String DB_NAME = "ecd.db";
    public static final int DB_VERSION = 4;

    public static ApiService getAPI() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }

    // checking network available or not
    public static boolean isNetworkAvailable(FragmentActivity mActivity) {
        ConnectivityManager manager = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {


            NetworkInfo mrActiveNetworkInfo = manager.getActiveNetworkInfo();
            return (mrActiveNetworkInfo != null && mrActiveNetworkInfo.isConnected());
        } else {
            Toast.makeText(mActivity, "Connection not Available", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // Show Alert Dialog
    public static void showDialog(android.app.AlertDialog mDialog) {
        if (!mDialog.isShowing())
            mDialog.show();
    }

    // cancel Alert Dialog
    public static void disableDialog(android.app.AlertDialog mDialog) {
        if (mDialog.isShowing())
            mDialog.cancel();
    }

}
