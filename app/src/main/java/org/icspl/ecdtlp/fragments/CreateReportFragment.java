package org.icspl.ecdtlp.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.icspl.ecdtlp.BuildConfig;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.icspl.ecdtlp.Adapter.DeviceListAdapter;
import org.icspl.ecdtlp.DbHelper.DbHelper;
import org.icspl.ecdtlp.DbHelper.DbTables;
import org.icspl.ecdtlp.MainActivity;
import org.icspl.ecdtlp.R;
import org.icspl.ecdtlp.services.BluetoothConnectionService;
import org.icspl.ecdtlp.utils.Common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class CreateReportFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, DatePickerDialog.OnDateSetListener, BluetoothConnectionService.IClickListener {

    private static final String TAG = "CreateReportFragment";
    boolean isValueChecked1 = false;
    boolean isValueChecked2 = false;
    boolean isValueChecked3 = false;
    boolean isValueChecked4 = false;
    boolean isValueChecked5 = false;
    public String location = "";
    public String str_power_ON_AC = "";
    public String str_power_OFF_AC = "";
    public String str_power_ON_DC = "";
    public String str_power_OFF_DC = "";
    public String str_data_loging_data = "";
    private Spinner sp_show_ts_no;
    private SearchableSpinner sp_show_manual_remark;
    private SQLiteDatabase mDatabase;
    private LocationManager locationManager;
    private Context mContext;
    private DbHelper mHelper;
    private TextView tv_show_type;
    private TextView tv_show_ts_ch;
    private TextView tv_show_location;
    private TextView tv_show_date;
    private TextView tv_show_d_cp_min;
    private TextView tv_show_d_cp_max;
    private TextView tv_show_d_cpu_min;
    private TextView tv_show_d_cpu_max;
    private TextView tv_show_d_velve;
    private TextView tv_show_d_pot_1;
    private TextView tv_show_d_ac_psp;
    private TextView tv_show_d_zinc_value;
    private static TextView tv_show_time, tv_show_Cordinates;

    private EditText et_show_carrier_pro_min, et_show_carrier_pro_max, et_show_cpu_min, et_show_cpu_max,
            et_show_value, et_show_p1, et_show_p2, et_show_ac_psp, et_show_z_value, et_driver_name, et_driver_mobile,
            et_start_km, et_end_km;

    private android.support.v7.widget.AppCompatAutoCompleteTextView et_vehical_no;
    private LinearLayout tl_show_5;
    private LinearLayout tl_show_6;
    private LinearLayout tl_show_7;
    private LinearLayout tl_show_8;
    private LinearLayout tl_show_9;
    private LinearLayout tl_show_10;
    private LinearLayout tl_show_11;
    private LinearLayout tl_show_12;
    private LinearLayout tl_show_13;
    private LinearLayout tl_show_16;
    LinearLayout linearLayout, tlpLinearLayout;

    private Button btn_show_photo_1, btn_show_photo_2, btn_show_photo_3,
            btn_start_km, btn_end_km, btn_vehical_no, tlpButton, dataloggingbtn, btn_show_save;
    boolean dataLoggingIsWhat = false;

    private String section, quarter, user_id;
    private SharedPreferences mLoginPreferences, _prefs;

    private ArrayList<String> permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    Button newBTConnectBtn;

    private final static int ALL_PERMISSIONS_RESULT = 101;
    private Uri tlpImageToUri;
    private final int IMAGE_CAPTURE_REQUEST_1 = 123;
    private final int IMAGE_CAPTURE_REQUEST_2 = 456;
    private final int IMAGE_CAPTURE_REQUEST_3 = 789;
    private final int REQUEST_CHECK_SETTINGS = 100;
    private final int END_KM_PHOTO_REQUEST = 185; // end_km_file
    private final int IMAGE_CAPTURE_REQUEST_4 = 363;
    private final int IMAGE_CAPTURE_REQUEST_5 = 258;
    private final int IMAGE_CAPTURE_REQUEST_6 = 147;
    private AlertDialog mDialog;
    private File imageBtnFile;
    private View mView;
    private Button btn_elert_file_end_km;


    ImageButton carrierProOn, carrierProOff, casingProOn, casingProOff, ac_input_btn;
    Button btnFindUnpairedDevices;

    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
//            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    RelativeLayout relativeLayout;
    ScrollView scrollView;

    int clickIncrement = 0;

    private BluetoothSocket mBTSocket;

    TextView timer, codeResend;
    EditText onTimer, offTimer;
    Button btn, btnStop;
    DbHelper dbHelper;
    long i = 0;
    int count = 0;
    boolean isType = true;
    public static int tempVal = 0;

    int onTimerInt, offTimerInt;
    RecyclerView recyclerView;
    ProgressDialog pd;

    ProgressDialog progressDialog;


    String newLati = "";
    String newLongi = "";
    private static long delay;

    public final static String PREFS = "PrefsFile";

    private SharedPreferences settings = null;
    private SharedPreferences.Editor editor = null;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {

            count += 1;
            long millis = System.currentTimeMillis() - i;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timer.setText(String.format("%d:%02d", minutes, seconds));
            //Log.d(TAG, "run: " + count);
            timerHandler.postDelayed(this, 1000);

            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault());
            String formattedDate = df.format(c);

            String onStrId1 = dbHelper.getDateON(formattedDate);
            String onStrId2 = dbHelper.getDateOFF(formattedDate);


            if (isType) {
                if (!onStrId1.equals("")) {
                    String[] newStringData2 = str_data_loging_data.split(",");
                    str_power_ON_AC = newStringData2[0];
                    str_power_ON_DC = newStringData2[1];

                    Log.d(TAG, "run: True Value ID1: " + onStrId1 + ", Date: " + formattedDate);
                    Log.d(TAG, "run: True Value ID2: " + onStrId2 + ", Date: " + formattedDate);
//                    str_power_OFF_AC = onStrId2;

                }
                isType = false;
            }
            else
            {
                if (!onStrId2.equals(""))
                {
                    String[] newStringData2 = str_data_loging_data.split(",");
                   try {
                       str_power_OFF_AC = newStringData2[0];
                       str_power_OFF_DC = newStringData2[1];
                   } catch (Exception e) {
                       e.printStackTrace();
                   }

                    Log.d(TAG, "run: False Value ID1: " + onStrId1 + ", Date: " + formattedDate);
//                    str_power_ON_DC = onStrId1;
                    Log.d(TAG, "run: False Value ID2: " + onStrId2 + ", Date: " + formattedDate);
//                    str_power_OFF_DC = onStrId2;

                }

                isType = true;
            }

        }
    };

    int newSelectedHour = 0;
    int newSelectedMin = 0;
    int newSelectedHour1 = 0;
    int newSelectedMin1 = 0;
    EditText End_Time, Edit_Time;
    Timer newTimer;
    Button dataLoggingBtn;
    long newDiff;
    private String Vehicle_Number,Driver_Name,Mobile_Number,Start_Km,End_Km;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_create_report, container, false);
        Calendar mcurrentTime = Calendar.getInstance();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);
        int date = mcurrentTime.get(Calendar.DATE);
        int month = mcurrentTime.get(Calendar.MONTH);
        int year = mcurrentTime.get(Calendar.YEAR);
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        int second = mcurrentTime.get(Calendar.SECOND);

        Bundle b = getArguments();
        mContext = mView.getContext();

        mLoginPreferences = mContext.getSharedPreferences(getString(R.string.login_pref), MODE_PRIVATE);
        _prefs = mContext.getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        editor = _prefs.edit();
        if (b != null) {

            quarter = b.getString("quarter", null);
            user_id = mLoginPreferences.getString("UserName", null);
            section = b.getString("section", null);
            Log.i(TAG, "onCreateView: Q: " + quarter + " s: " + section + " u:" + user_id);
        } else {
            Log.i(TAG, "onCreateView: bundle is null");
        }


        carrierProOn = mView.findViewById(R.id.carrierProOnBT);
        carrierProOff = mView.findViewById(R.id.carrierProOffBT);
        casingProOn = mView.findViewById(R.id.casingProOnBT);
        casingProOff = mView.findViewById(R.id.casingProOffBT);
        ac_input_btn = mView.findViewById(R.id.ac_input);
        linearLayout = mView.findViewById(R.id.dataLoggingpart);
        tlpLinearLayout = mView.findViewById(R.id.tlpMonitoringPart);

        init(mView);
        String previousDate = new DbHelper(mContext).fetchLastDate(section, quarter);
        if (previousDate != null) {
            if (mLoginPreferences.getString("previousDate", null) != null)
                tv_show_date.setText(mLoginPreferences.getString("previousDate", null));
            else
                tv_show_date.setText(previousDate);
        } else tv_show_date.setText(new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(new Date()));

        //checkEndKm();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        // tv_show_date.setOnClickListener(v -> datePickerDialog.show());


        tv_show_time.setText(new SimpleDateFormat("hh:mm", Locale.ENGLISH).format(new Date()));


        Button btnONOFF = (Button) mView.findViewById(R.id.btnONOFF);
        btnEnableDisable_Discoverable = (Button) mView.findViewById(R.id.btnDiscoverable_on_off);
        lvNewDevices = (ListView) mView.findViewById(R.id.lvNewDevices);
        mBTDevices = new ArrayList<>();


        btnStartConnection = (Button) mView.findViewById(R.id.btnStartConnection);
        btnSend = (Button) mView.findViewById(R.id.btnSend);
        etSend = (EditText) mView.findViewById(R.id.editText);
        msgReceived = (TextView) mView.findViewById(R.id.msgReceived);

        //Broadcasts when bond state changes (ie:pairing)
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        mContext.registerReceiver(mBroadcastReceiver4, filter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        lvNewDevices.setOnItemClickListener(CreateReportFragment.this);


        btnStartConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startConnection();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] bytes = etSend.getText().toString().getBytes();
                mBluetoothConnection.write(bytes);
            }
        });
        carrierProOn.setOnClickListener(this);
        carrierProOff.setOnClickListener(this);
        casingProOn.setOnClickListener(this);
        casingProOff.setOnClickListener(this);
        ac_input_btn.setOnClickListener(this);

        ///automatically start bluetooth
        startBTAuto();
        End_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        End_Time.setText(formattedDate + "_" + selectedHour + ":" + selectedMinute + ":00");
                        newSelectedHour1 = selectedHour;
                        newSelectedMin1 = selectedMinute;
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        Edit_Time.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Edit_Time.setText(formattedDate + "_" + selectedHour + ":" + selectedMinute + ":00");
                        newSelectedHour = selectedHour;
                        newSelectedMin = selectedMinute;
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        btnStop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Edit_Time.setText("");
                End_Time.setText("");
                onTimer.setText("");
                offTimer.setText("");
                timer.setText("00:00");
                codeResend.setText("No Value");
                newTimer.cancel();
            }
        });
        dataLoggingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!Edit_Time.getText().toString().isEmpty()) {
                        pd = new ProgressDialog(mContext);
                        pd.setMessage("Please wait while monitoring starts");
                        pd.setCancelable(false);
                        pd.show();
                        java.text.DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                        Date date = null;
                        try {
                            date = dateFormatter.parse(Edit_Time.getText().toString());
                            Log.d(TAG, "onClick: " + date.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        newTimer.schedule(new CreateReportFragment.MyTimeTask(), date);
                    } else {
                        Edit_Time.setError("Please enter date");
                        Edit_Time.setFocusable(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault());
                    Date newDate = Calendar.getInstance().getTime();
                    String newDateValue = df.format(newDate);
                    String tempDateValue = df.format(newDate);


                    String newDateValue1 = Edit_Time.getText().toString();
                    String newDateValue2 = End_Time.getText().toString();
//                     location = tv_show_location.getText().toString();

                    Date date1 = null, date2 = null;
                    try {
                        date1 = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").parse(newDateValue1);
                        date2 = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").parse(newDateValue2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String strPrintDiff = printDifference(date1, date2);
                    Log.d(TAG, "onClick: " + strPrintDiff);

                    onTimerInt = Integer.parseInt(onTimer.getText().toString());
                    offTimerInt = Integer.parseInt(offTimer.getText().toString());
                    long calVal1 = newDiff / (onTimerInt + offTimerInt);
                    long calVal2 = calVal1 * 2;
                    Log.d(TAG, "onClick: " + calVal2);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault());
                    String currentDateandTime = sdf.format(new Date());

                    for (int h = 0; h <= calVal2; h++) {
                        if (isType) {
//                        if (count == onTimerInt) {
//                            count = 0;
//                        }
                            isType = false;
                            tempVal = onTimerInt;
                            Log.d(TAG, "onClick: DateValue: " + newDateValue + "\nTempValue: " + tempVal + "\nBooleanValue: " + isType);
                            newDateValue = parseDate(newDateValue, "yyyy-MM-dd_HH:mm:ss", "yyyy-MM-dd_HH:mm:ss");
                            tempDateValue = parseDate1(newDateValue, "yyyy-MM-dd_HH:mm:ss", "yyyy-MM-dd_HH:mm:ss", offTimerInt);
                            new DbHelper(mContext).insertDataLoggingValue(str_power_ON_AC, str_power_OFF_DC, str_power_ON_DC, str_power_OFF_AC, newDateValue, tempDateValue, newLongi, newLati);
                            codeResend.setText(currentDateandTime + ":" + "OFF");
                        } else {
//                        if (count == offTimerInt) {
//                            count = 0;
//                        }
                            isType = true;
                            tempVal = offTimerInt;
                            codeResend.setText(currentDateandTime + ":" + "ON");
                            Log.d(TAG, "onClick: DateValue: " + newDateValue + "\nTempValue: " + tempVal + "\nBooleanValue: " + isType);
                            newDateValue = parseDate(newDateValue, "yyyy-MM-dd_HH:mm:ss", "yyyy-MM-dd_HH:mm:ss");
                            //new DbHelper(TestingActivity.this).insertDataLoggingValue("", "", "", "", "", newDateValue, "","");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        btnFindUnpairedDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFindUnpairedDevices.setEnabled(false);
                btnFindUnpairedDevices.setBackgroundResource(R.drawable.selector_xml_btn_yellow);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnFindUnpairedDevices.setBackgroundResource(R.drawable.btn_red);
                        btnFindUnpairedDevices.setEnabled(true);
                    }
                }, 10000);
                Log.d(TAG, "btnDiscover: Looking for unpaired devices.");

                mBTDevices.clear();
                mDeviceListAdapter = new DeviceListAdapter(mContext, R.layout.device_adapter_view, mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);
                mDeviceListAdapter.notifyDataSetChanged();


                if (mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.cancelDiscovery();
                    Log.d(TAG, "btnDiscover: Canceling discovery.");

                    //check BT permissions in manifest
                    checkBTPermissions();

                    mBluetoothAdapter.startDiscovery();
                    IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    mContext.registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
                }
                if (!mBluetoothAdapter.isDiscovering()) {

                    //check BT permissions in manifest
                    checkBTPermissions();

                    mBluetoothAdapter.startDiscovery();
                    IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    mContext.registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
                }
            }
        });
        return mView;
    }
    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            newLati = String.valueOf(latitude);
            newLongi = String.valueOf(longitude);
            Log.d(TAG, "onLocationChanged: Latitude: "+latitude+", Longitude: "+longitude);
            tv_show_Cordinates.setText("Longitude :"+longitude+"\n"+"Latitude :"+latitude);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 41001);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    3000, 0, locationListenerGPS);
            Log.d(TAG, "onCreateView: " + locationManager);
            isLocationEnabled();

        }
    }

    private void isLocationEnabled() {

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
            alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
        }
    }

    //
    private void checkEndKm() {

        ArrayList<String> tsNoList = new DbHelper(mContext).checkEndKm();
        if (tsNoList != null && tsNoList.size() > 0) {
            final Dialog mDialogAlert = new Dialog(mContext);
            mDialogAlert.setContentView(R.layout.alert_layout_end_km);
            final TextView tv_alert_end_km_title = mDialogAlert.findViewById(R.id.tv_alert_end_km_title);
            final EditText et_alert_manual = mDialogAlert.findViewById(R.id.et_alert_end_km);
            btn_elert_file_end_km = mDialogAlert.findViewById(R.id.btn_elert_file_end_km);
            final Button btn_alert_save_km = mDialogAlert.findViewById(R.id.btn_alert_save_km);
            tv_alert_end_km_title.setText(String.format("%s%s", getString(R.string.enter_end_for_tsno), tsNoList.get(0)));
            mDialogAlert.setCancelable(true);

            btn_alert_save_km.setOnClickListener(v -> {
                if (!et_alert_manual.getText().toString().equalsIgnoreCase("")) {

                    new DbHelper(mContext).updateEndKm(tsNoList.get(0), et_alert_manual.getText().toString(),
                             tsNoList.get(1),
                            tsNoList.get(2), tsNoList.get(3));
                    mDialogAlert.dismiss();
                    Toasty.success(mContext, "TLP Saved Successfully", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    mContext.startActivity(new Intent(mContext, MainActivity.class));

                } else {
                    msg("Please enter End Km fields");
                    return;
                }
            });
            btn_elert_file_end_km.setOnClickListener(v -> {
                startPhotoActivity(END_KM_PHOTO_REQUEST);
            });
            mDialogAlert.show();
        }
    }

    // dexter permission
    private void checkLocationPermission() {

        // Requesting ACCESS_FINE_LOCATION using Dexter library

        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted())
                        {
                            Log.i(TAG, "onPermissionsChecked: Permission Granted");
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied())
                        {
                            // show alert dialog navigating to Settings
                            openSettings();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(mContext, "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void init(View mView) {

        mContext = mView.getContext();
        FragmentActivity mActivity = getActivity();
        mHelper = new DbHelper(mContext);


        mDialog = new SpotsDialog(mContext, "Please Wait...");
        mDialog.setCancelable(false);
        mLoginPreferences = mContext.getSharedPreferences(getString(R.string.login_pref), MODE_PRIVATE);

        relativeLayout = mView.findViewById(R.id.relativeLayoutHide);
        scrollView = mView.findViewById(R.id.scrollViewData);

        //table row
        LinearLayout tl_show_1 = mView.findViewById(R.id.tl_show_1);
        LinearLayout tl_show_2 = mView.findViewById(R.id.tl_show_2);
        LinearLayout tl_show_3 = mView.findViewById(R.id.tl_show_3);
        LinearLayout tl_show_4 = mView.findViewById(R.id.tl_show_4);
        tl_show_5 = mView.findViewById(R.id.tl_show_5);
        tl_show_6 = mView.findViewById(R.id.tl_show_6);
        tl_show_7 = mView.findViewById(R.id.tl_show_7);
        tl_show_8 = mView.findViewById(R.id.tl_show_8);
        tl_show_9 = mView.findViewById(R.id.tl_show_9);
        tl_show_10 = mView.findViewById(R.id.tl_show_10);
        tl_show_11 = mView.findViewById(R.id.tl_show_11);
        tl_show_12 = mView.findViewById(R.id.tl_show_12);
        tl_show_13 = mView.findViewById(R.id.tl_show_13);
        tl_show_16 = mView.findViewById(R.id.tl_show_16);


        // sp
        sp_show_ts_no = mView.findViewById(R.id.sp_show_ts_no);
        sp_show_manual_remark = mView.findViewById(R.id.sp_show_manual_remark);
        tv_show_time = mView.findViewById(R.id.tv_show_time);
        tv_show_Cordinates = mView.findViewById(R.id.tv_show_Cordinates);

        //TV
        tv_show_type = mView.findViewById(R.id.tv_show_type);
        tv_show_ts_ch = mView.findViewById(R.id.tv_show_ts_ch);
        tv_show_location = mView.findViewById(R.id.tv_show_location);

        // dummy TV
        tv_show_d_cp_min = mView.findViewById(R.id.tv_show_d_cp_min);
        tv_show_d_cp_max = mView.findViewById(R.id.tv_show_d_cp_max);
        tv_show_d_cpu_min = mView.findViewById(R.id.tv_show_d_cpu_min);
        tv_show_d_cpu_max = mView.findViewById(R.id.tv_show_d_cpu_max);
        tv_show_d_cpu_max = mView.findViewById(R.id.tv_show_d_cpu_max);

        tv_show_d_velve = mView.findViewById(R.id.tv_show_d_velve);
        tv_show_d_pot_1 = mView.findViewById(R.id.tv_show_d_pot_1);
        TextView tv_show_d_pot_2 = mView.findViewById(R.id.tv_show_d_pot_2);
        tv_show_d_ac_psp = mView.findViewById(R.id.tv_show_d_ac_psp);
        tv_show_d_zinc_value = mView.findViewById(R.id.tv_show_d_zinc_value);

        //ET
        et_show_carrier_pro_min = mView.findViewById(R.id.et_show_carrier_pro_min);
        et_show_carrier_pro_max = mView.findViewById(R.id.et_show_carrier_pro_max);
        et_show_cpu_min = mView.findViewById(R.id.et_show_cpu_min);
        et_show_cpu_max = mView.findViewById(R.id.et_show_cpu_max);
        et_show_value = mView.findViewById(R.id.et_show_value);
        et_show_p1 = mView.findViewById(R.id.et_show_p1);
        et_show_p2 = mView.findViewById(R.id.et_show_p2);
        et_show_ac_psp = mView.findViewById(R.id.et_show_ac_psp);
        et_show_z_value = mView.findViewById(R.id.et_show_z_value);
        tv_show_date = mView.findViewById(R.id.tv_show_date);
        et_driver_name = mView.findViewById(R.id.et_driver_name);
        et_driver_mobile = mView.findViewById(R.id.et_driver_mobile);
        et_start_km = mView.findViewById(R.id.et_start_km);
        et_end_km = mView.findViewById(R.id.et_end_km);
        et_vehical_no = mView.findViewById(R.id.et_vehical_no);

        //Btn
        btn_show_photo_1 = mView.findViewById(R.id.btn_show_photo_1);
        btn_show_photo_2 = mView.findViewById(R.id.btn_show_photo_2);
        btn_show_photo_3 = mView.findViewById(R.id.btn_show_photo_3);

        btn_start_km = mView.findViewById(R.id.btn_start_km);
        btn_end_km = mView.findViewById(R.id.btn_end_km);
        btn_vehical_no = mView.findViewById(R.id.btn_vehical_no);
        tlpButton = mView.findViewById(R.id.tlpbtn);
        codeResend = mView.findViewById(R.id.resend);

        timer = mView.findViewById(R.id.timer);
        newTimer = new Timer();
        dbHelper = new DbHelper(mContext);

        onTimer = mView.findViewById(R.id.onTimer);
        offTimer = mView.findViewById(R.id.offTimer);
        btn = mView.findViewById(R.id.clickHere);
        btnStop = mView.findViewById(R.id.clickStop);
        recyclerView = mView.findViewById(R.id.testingRv);
        dataLoggingBtn = mView.findViewById(R.id.dataLoggingBtn);

        Edit_Time = (EditText) mView.findViewById(R.id.time_view_edit);
        End_Time = (EditText) mView.findViewById(R.id.time_view_end);

        dataloggingbtn = mView.findViewById(R.id.dataloggingbtn);
        btn_show_save = mView.findViewById(R.id.btn_show_save);
        tlpButton.setBackgroundResource(R.drawable.selector_xml_btn_yellow);
        dataloggingbtn.setBackgroundResource(R.drawable.selector_xml_btn_yellow);
        tlpButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                dataLoggingIsWhat = false;
                tlpButton.setBackgroundResource(R.drawable.btn_red);
                dataloggingbtn.setBackgroundResource(R.drawable.selector_xml_btn_yellow);
                try {
                    String sendSignalVal = "1";
                    byte[] bytes = sendSignalVal.getBytes();
                    mBluetoothConnection.write(bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                linearLayout.setVisibility(View.GONE);
                tlpLinearLayout.setVisibility(View.VISIBLE);
            }
        });

        newBTConnectBtn = mView.findViewById(R.id.newBTConnectBtn);
        newBTConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                progressDialog = new ProgressDialog(mContext);
//                progressDialog.setMessage("Please Wait");
//                progressDialog.setCancelable(false);
//                progressDialog.show();
//                startBTAuto();
            }
        });

        dataloggingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataLoggingIsWhat = true;
                tlpButton.setBackgroundResource(R.drawable.selector_xml_btn_yellow);
                dataloggingbtn.setBackgroundResource(R.drawable.btn_red);
                try {
                    String sendSignalVal = "2";
                    byte[] bytes = sendSignalVal.toString().getBytes();
                    mBluetoothConnection.write(bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                linearLayout.setVisibility(View.VISIBLE);
                tlpLinearLayout.setVisibility(View.GONE);
            }
        });
        btnFindUnpairedDevices = mView.findViewById(R.id.btnFindUnpairedDevices);

        btn_show_photo_1.setOnClickListener(this);
        btn_show_photo_2.setOnClickListener(this);
        btn_show_photo_3.setOnClickListener(this);

        btn_start_km.setOnClickListener(this);
        btn_end_km.setOnClickListener(this);
        btn_vehical_no.setOnClickListener(this);
        btn_show_save.setOnClickListener(this);

        sp_show_ts_no.setOnItemSelectedListener(this);
        sp_show_manual_remark.setOnItemSelectedListener(this);

        String vehicals[] = new DbHelper(mContext).retrieveVehicleNoList();
        if (vehicals != null) {
            Log.i(TAG, "init: vehical not null:");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (mContext, android.R.layout.select_dialog_item, vehicals);

            et_vehical_no.setThreshold(1);
            et_vehical_no.setAdapter(adapter);
        }


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        checkLocationPermission();

        Common.showDialog(mDialog);

        setupSpinner();
        Common.disableDialog(mDialog);
    }
    private void setupSpinner() {

        Log.i(TAG, "setupSpinner: in spinner");
        ArrayList<String> mTsNoList = new ArrayList<>();


        String sql_query = "Select TLP_TSNo  from Tbl_TRUnit left outer join (select * from TblMonitoring_TLP" +
                " where  Quaters = '" + quarter + "' ) as TblMonitoring_TLP " +
                "on Tbl_TRUnit.ContractorID = TblMonitoring_TLP.ContractorID and " +
                "Tbl_TRUnit.TLP_TSNo = TblMonitoring_TLP.TS_No and Tbl_TRUnit.Section = TblMonitoring_TLP.Section " +
                "Where Tbl_TRUnit.ContractorID ='" + user_id + "'" +
                "and (Tbl_TRUnit.Nallah_Crossing is null or Tbl_TRUnit.Nallah_Crossing <> 'Yes') and  " +
                "Tbl_TRUnit.Section = '" + section + "' and (Quaters is null OR Quaters <> '" + quarter + "')";


        mDatabase = mHelper.getReadableDatabase();
        Log.i(TAG, "setupSpinner: Section: " + section.trim() + " \n Quarters: " + quarter + " \n UserId: " + user_id);
        Cursor mCursor = mDatabase.rawQuery(sql_query, null);
        if (mCursor != null && mCursor.getCount() > 0) {

            Log.i(TAG, "setupSpinner: Count: " + mCursor.getCount());
            while (mCursor.moveToNext()) {
                mTsNoList.add(mCursor.getString(0));
                Log.i(TAG, "setupSpinner: " + mCursor.getString(0));
            }
            Collections.sort(mTsNoList, new Comparator<String>() {
                @Override
                public int compare(String s, String t1) {
                    return extractInt(s) - extractInt(t1);
                }

                int extractInt(String s) {
                    String num = s.replaceAll("\\D", "");
                    // return 0 if no digits found
                    return num.isEmpty() ? 0 : Integer.parseInt(num);
                }
            });
            mTsNoList.add(0, "Select");
            sp_show_ts_no.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, mTsNoList));
        } else
            Toast.makeText(mContext, "No Data Found", Toast.LENGTH_SHORT).show();
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity mainActivity = (MainActivity) getActivity();
        Toolbar sToolbar = null;
        if (mainActivity != null) {
            sToolbar = mainActivity.findViewById(R.id.toolbar);
            sToolbar.setTitle("Create Report");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show_photo_1:
                startPhotoActivity(IMAGE_CAPTURE_REQUEST_1);  // take 1st photo
                break;
            case R.id.btn_show_photo_2:
                startPhotoActivity(IMAGE_CAPTURE_REQUEST_2); // take 2nd photo
                break;
            case R.id.btn_show_photo_3:
                startPhotoActivity(IMAGE_CAPTURE_REQUEST_3); // take 3rs Front  photo
                break;
            case R.id.btn_show_save:
                saveButtonClickHandler();  // submit data in table
                break;
            case R.id.btn_start_km:
                startPhotoActivity(IMAGE_CAPTURE_REQUEST_4);  // submit data in table
                break;
            case R.id.btn_end_km:
                startPhotoActivity(IMAGE_CAPTURE_REQUEST_5);  // submit data in table
                break;
            case R.id.btn_vehical_no:
                startPhotoActivity(IMAGE_CAPTURE_REQUEST_6);  // submit data in table
                break;
            case R.id.carrierProOnBT:
                if (isValueChecked1) {
                    isValueChecked1 = false;
                    carrierProOn.setBackgroundColor(Color.RED);
                } else {
                    isValueChecked1 = true;
                    carrierProOn.setBackgroundColor(Color.GREEN);
                }
                clickIncrement = 1;
                Log.d(TAG, "onClick: " + clickIncrement);
                break;
            case R.id.carrierProOffBT:
                if (isValueChecked2) {
                    isValueChecked2 = false;
                    carrierProOff.setBackgroundColor(Color.RED);
                } else {
                    isValueChecked2 = true;
                    carrierProOff.setBackgroundColor(Color.GREEN);
                }
                clickIncrement = 2;
                Log.d(TAG, "onClick: " + clickIncrement);
                break;
            case R.id.casingProOnBT:
                if (isValueChecked3) {
                    isValueChecked3 = false;
                    casingProOn.setBackgroundColor(Color.RED);
                } else {
                    isValueChecked3 = true;
                    casingProOn.setBackgroundColor(Color.GREEN);
                }
                clickIncrement = 3;
                Log.d(TAG, "onClick: " + clickIncrement);
                break;
            case R.id.casingProOffBT:
                if (isValueChecked4) {
                    isValueChecked4 = false;
                    casingProOff.setBackgroundColor(Color.RED);
                } else {
                    isValueChecked4 = true;
                    casingProOff.setBackgroundColor(Color.GREEN);
                }
                clickIncrement = 4;
                Log.d(TAG, "onClick: " + clickIncrement);
                break;
            case R.id.ac_input:
                if (isValueChecked5) {
                    isValueChecked5 = false;
                    ac_input_btn.setBackgroundColor(Color.RED);
                } else {
                    isValueChecked5 = true;
                    ac_input_btn.setBackgroundColor(Color.GREEN);
                }
                clickIncrement = 5;
                Log.d(TAG, "onClick: " + clickIncrement);
                break;
        }
    }

    private void startPhotoActivity(int request) {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/TLP Monitoring/";
            File newdir = new File(dir);
            if (!newdir.exists()) {
                newdir.mkdirs();
            }
            String file = dir + "TLP_" + DateFormat.format("yyyyMMdd_hhmmss", new Date()).toString() + ".jpg";

            imageBtnFile = new File(file);
            try {
                imageBtnFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            tlpImageToUri = FileProvider.getUriForFile(mContext,
                    BuildConfig.APPLICATION_ID + ".provider", imageBtnFile);


            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tlpImageToUri);
            startActivityForResult(cameraIntent, request);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 500);
        }

    }

    // submit button handler
    private void saveButtonClickHandler() {

        Vehicle_Number = et_vehical_no.getText().toString();
        Driver_Name = et_driver_name.getText().toString();
        Mobile_Number = et_driver_mobile.getText().toString();
        Start_Km = et_start_km.getText().toString();
        End_Km = et_end_km.getText().toString();
        new DbHelper(mContext).insertTbl_Vehicles_details(Vehicle_Number,Driver_Name,Mobile_Number,Start_Km,End_Km);

        String tag_1 = (String) btn_show_photo_1.getTag();
        String tag_2 = (String) btn_show_photo_2.getTag();
        String tag_3 = (String) btn_show_photo_3.getTag();
        String tag_4 = (String) btn_vehical_no.getTag();
        String tag_5 = (String) btn_start_km.getTag();
        Log.i(TAG, "saveButtonClickHandler: Tag1:" + tag_1 + " \n" + "Tag_2" + tag_2 + "\n"
                + "Tag_3" + tag_3 + "\n");

        if (tag_1 == null) {
            msg("Please Upload TLp Photo");
            return;
        } else if (tag_2 == null) {
            msg("Please Upload Reading Photo");
            return;
        } else if (tag_3 == null) {
            msg("Please Upload your Selfie Photo");
            return;
        } else if (tv_show_date.getText().toString().equals("Date")) {
            msg("Please choose Date");
            return;
        } else{

                Common.showDialog(mDialog);
                java.util.Date date = Calendar.getInstance().getTime();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                // set 0  if et is null
                Double sSV = Double.valueOf(et_show_value.getText().toString().equals("") ? "0" : et_show_value.getText().toString());
                Double sP1 = Double.valueOf(et_show_p1.getText().toString().equals("") ? "0" : et_show_p1.getText().toString());
                Double sP2 = Double.valueOf(et_show_p2.getText().toString().equals("") ? "0" : et_show_p2.getText().toString());
                Double sZV = Double.valueOf(et_show_z_value.getText().toString().equals("") ? "0" : et_show_z_value.getText().toString());
                Double sAC_PSP = Double.valueOf(et_show_ac_psp.getText().toString().equals("") ? "0" : et_show_ac_psp.getText().toString());

                if (sSV.isNaN()) {
                    sSV = 0.0;
                }
                if (sP1.isNaN()) {
                    sP1 = 0.0;
                }
                if (sP2.isNaN()) {
                    sP2 = 0.0;
                }
                if (sZV.isNaN()) {
                    sZV = 0.0;
                }
                if (sAC_PSP.isNaN()) {
                    sAC_PSP = 0.0;
                }
                ContentValues mValues = new ContentValues();
                mDatabase.beginTransaction();
                Common.showDialog(mDialog);
                boolean askForEndKm = false;
                try {
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.ClientID, mLoginPreferences.getString(DbTables.UserList_Entry.client_id, "test"));
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.ContractorID, user_id);
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.Section, section);
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.PIPEType, "TLP");
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.TS_No, (String) sp_show_ts_no.getSelectedItem());
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.TS_Type, tv_show_type.getText().toString());
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.TS_Km, tv_show_ts_ch.getText().toString());
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.TS_Location, tv_show_location.getText().toString());
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.PSP_Min, Double.parseDouble(et_show_carrier_pro_min.getText().toString().equals("") ? "0" : et_show_carrier_pro_min.getText().toString()));
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.PSP_Max, Double.parseDouble(et_show_carrier_pro_max.getText().toString().equals("") ? "0" : et_show_carrier_pro_max.getText().toString()));
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.Casing_Min, Double.parseDouble(et_show_cpu_min.getText().toString().equals("") ? "0" : et_show_cpu_min.getText().toString()));
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.Casing_Max, Double.parseDouble(et_show_cpu_max.getText().toString().equals("") ? "0" : et_show_cpu_max.getText().toString()));
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.ZINC_Value, sZV);
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.AC_PSP, sAC_PSP);
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.Status, getString(R.string.pending));
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.S_DATE, tv_show_date.getText().toString());
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.S_Time, tv_show_time.getText().toString());
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.DateofEntry, sqlDate.toString());
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.Quaters, quarter);
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.Valve, sSV);
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.Potential_1, sP1);
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.Potential_2, sP2);
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.NCordinate, newLati.equals("") ? "" : newLati);
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.ECordinate, newLongi.equals("") ? "" : newLongi);
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.ManualRemarks, Common.MANNUAL_REMARK);
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.TLPFile, tag_1);
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.Reading_File, tag_2);
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.Selfie_File, tag_3);
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.Driver_Name, et_driver_name.getText().toString().equals("") ? "-" : et_driver_name.getText().toString());
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.Driver_Mob_Number, et_driver_mobile.getText().toString().equals("") ? "-" : et_driver_mobile.getText().toString());
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.Vehicle_Number, et_vehical_no.getText().toString().equals("") ? "-" : et_vehical_no.getText().toString());
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.Start_Km, et_start_km.getText().toString().equals("") ? "-" : et_start_km.getText().toString());
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.Vehicle_No_File, tag_4);
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.Start_Km_File, tag_5);
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.type_client, Common.TYPE_CLIENT);
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.tsno_client, Common.TSNo_Client);
                    mValues.put(DbTables.Monnitoring_Tlp_Entry.server_status, getString(R.string.pending));

                    long id = mDatabase.insert(DbTables.TbL_Mentoring_Tip, null, mValues);
                    if (id > 0) {
                        askForEndKm = true;

                        mDatabase.setTransactionSuccessful();
                        Toasty.success(mContext, "TLP Inserted Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toasty.error(mContext, "Error while Inserting Data", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mDatabase.endTransaction();
                    Common.disableDialog(mDialog);
                }
                if (askForEndKm) checkEndKm();

            }

    }

    // messages
    private void msg(String msgs) {
        Toasty.warning(mContext, msgs, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        TextView mt = (TextView) view;
        if (position > 0) {
            switch (parent.getId()) {
                case R.id.sp_show_ts_no:
                    Cursor mCursor = new DbHelper(mContext).getTsNo(user_id, section, sp_show_ts_no.getSelectedItem().toString());
                    if (mCursor != null && mCursor.getCount() > 0) {

                        Log.i(TAG, "setupSpinner: Count: " + mCursor.getCount());

                        while (mCursor.moveToNext()) {
                            Log.i(TAG, "Type: " + mCursor.getString(0));
                            Log.i(TAG, "Location: " + mCursor.getString(2));
                            Common.TYPE_CLIENT = mCursor.getString(3);
                            Common.TSNo_Client = mCursor.getString(4);
                            Common.TS_Chainage = mCursor.getString(1);
                            setupViewType(mCursor.getString(0), mCursor.getString(2), mCursor.getString(1));
                        }
                    } else
                        Toast.makeText(mContext, "No Data Found", Toast.LENGTH_SHORT).show();
                    if (mCursor != null) mCursor.close();
                    Cursor mCursor2 = new DbHelper(mContext).getVehicleDetails();
                    if (mCursor2 != null && mCursor2.getCount() > 0)
                    {
                        while (mCursor2.moveToNext())
                        {
                            Vehicle_Number = mCursor2.getString(1);
                            Driver_Name = mCursor2.getString(2);
                            Mobile_Number = mCursor2.getString(3);
                            Start_Km = mCursor2.getString(4);
                            End_Km = mCursor2.getString(5);
                            Log.d(TAG, "onItemSelectedsssssss: "+Vehicle_Number);
                            Log.d(TAG, "onItemSelectedsssssss: "+Driver_Name);
                            Log.d(TAG, "onItemSelectedsssssss: "+Mobile_Number);
                            Log.d(TAG, "onItemSelectedsssssss: "+Start_Km);
                            Log.d(TAG, "onItemSelectedsssssss: "+End_Km);
                            et_vehical_no.setText(Vehicle_Number);
                            et_driver_name.setText(Driver_Name);
                            et_driver_mobile.setText(Mobile_Number);
                            et_start_km.setText(Start_Km);
                            et_end_km.setText(End_Km);
                        }
                    }

                case R.id.sp_show_manual_remark:

                    Log.i(TAG, "onItemSelected: Manual Remark " + mt.getText() + " manRemark: ");
                    if (mt.getText().toString().equalsIgnoreCase("Other")) {
                        Log.i(TAG, "onItemSelected: in other");

                        final Dialog mDialogAlert = new Dialog(mContext);
                        mDialogAlert.setContentView(R.layout.alert_layout_manual_remark);
                        final EditText et_alert_manual = mDialogAlert.findViewById(R.id.et_alert_manual);
                        Button btn_manual_save = mDialogAlert.findViewById(R.id.btn_manual_save);
                        Button btn_manual_cancel = mDialogAlert.findViewById(R.id.btn_manual_cancel);

                        mDialogAlert.setTitle("Other Remark");
                        mDialogAlert.setCancelable(false);

                        btn_manual_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sp_show_manual_remark.setSelection(0);
                                mDialogAlert.dismiss();
                            }
                        });

                        btn_manual_save.setOnClickListener(v -> {
                            if (!et_alert_manual.getText().toString().equalsIgnoreCase("")) {
                                Common.MANNUAL_REMARK = et_alert_manual.getText().toString();
                                Toasty.info(mContext, "Entry Saved: " + et_alert_manual.getText(),
                                        Toast.LENGTH_LONG).show();

                                mDialogAlert.dismiss();
                            } else {
                                Toasty.info(mContext, "Please enter other remark", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                        mDialogAlert.show();
                    } else if (mt.getText().toString().equalsIgnoreCase("Select")) {
                        Common.MANNUAL_REMARK = "-";
                    } else
                        Common.MANNUAL_REMARK = sp_show_manual_remark.getSelectedItem().toString();
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // show/hide view based on type
    private void setupViewType(String type, String location, String chainage) {

//        tv_show_location.setText(location);
        if (chainage != null) {
            tv_show_ts_ch.setText(chainage);
        } else {
            tv_show_ts_ch.setText("Chainage not found");
        }
        if (location != null) {
            tv_show_location.setText(location);
        } else {
            tv_show_location.setText("Location not found");
        }
        if (type != null) {
            tv_show_type.setText(type);
            if (type.equalsIgnoreCase("A") || type.equalsIgnoreCase("B")) {

                tl_show_5.setVisibility(View.VISIBLE);
                tl_show_6.setVisibility(View.VISIBLE);
                tl_show_7.setVisibility(View.GONE);
                tl_show_8.setVisibility(View.GONE);
                tl_show_9.setVisibility(View.GONE);
                tl_show_10.setVisibility(View.GONE);
                tl_show_11.setVisibility(View.GONE);
                tl_show_12.setVisibility(View.VISIBLE);
                tl_show_13.setVisibility(View.GONE);

                tv_show_d_cp_min.setText(R.string.cp_min);
                tv_show_d_cp_max.setText(R.string.cp_max);
                //tv_show_d_cpu_min.setText("Carrrier/Pro MIN");
                //  tv_show_d_cpu_max.setText("Carrrier/Pro MAX");
                tv_show_d_ac_psp.setText(R.string.ac_psp);

            } else if (type.equalsIgnoreCase("C")) {
                tl_show_5.setVisibility(View.VISIBLE);
                tl_show_6.setVisibility(View.VISIBLE);
                tl_show_7.setVisibility(View.VISIBLE);
                tl_show_8.setVisibility(View.VISIBLE);
                tl_show_9.setVisibility(View.GONE);
                tl_show_10.setVisibility(View.GONE);
                tl_show_11.setVisibility(View.GONE);
                tl_show_12.setVisibility(View.VISIBLE);
                tl_show_13.setVisibility(View.GONE);


                tv_show_d_cp_min.setText(R.string.cp_min);
                tv_show_d_cp_max.setText(R.string.cp_max);
                tv_show_d_cpu_min.setText(R.string.casing_min);
                tv_show_d_cpu_max.setText(R.string.casing_max);
                tv_show_d_ac_psp.setText(R.string.ac_psp);
            } else if (type.equalsIgnoreCase("IJ")) {


                tl_show_5.setVisibility(View.VISIBLE);
                tl_show_6.setVisibility(View.VISIBLE);
                tl_show_7.setVisibility(View.VISIBLE);
                tl_show_8.setVisibility(View.VISIBLE);
                tl_show_9.setVisibility(View.GONE);
                tl_show_10.setVisibility(View.GONE);
                tl_show_11.setVisibility(View.GONE);
                tl_show_12.setVisibility(View.VISIBLE);
                tl_show_13.setVisibility(View.GONE);

                tv_show_d_cp_min.setText("Protected Side MIN");
                tv_show_d_cp_max.setText("Protected Side MAX");
                tv_show_d_cpu_min.setText("Unprotected Side MIN");
                tv_show_d_cpu_max.setText("Unprotected Side MAX");
                tv_show_d_ac_psp.setText(R.string.ac_psp);

            } else if (type.equalsIgnoreCase("M")) {
                tl_show_5.setVisibility(View.VISIBLE);
                tl_show_6.setVisibility(View.VISIBLE);
                tl_show_7.setVisibility(View.GONE);
                tl_show_8.setVisibility(View.GONE);
                tl_show_9.setVisibility(View.GONE);
                tl_show_10.setVisibility(View.GONE);
                tl_show_11.setVisibility(View.GONE);
                tl_show_12.setVisibility(View.VISIBLE);
                tl_show_13.setVisibility(View.VISIBLE);
                tv_show_d_cp_min.setText(R.string.cp_min);
                tv_show_d_cp_max.setText(R.string.cp_max);
                tv_show_d_ac_psp.setText(R.string.ac_psp);
                tv_show_d_zinc_value.setText("Zinc Value");
            } else if (type.equalsIgnoreCase("E")) {
                tl_show_5.setVisibility(View.VISIBLE);
                tl_show_6.setVisibility(View.VISIBLE);
                tl_show_7.setVisibility(View.VISIBLE);
                tl_show_8.setVisibility(View.VISIBLE);
                tl_show_9.setVisibility(View.GONE);
                tl_show_10.setVisibility(View.GONE);
                tl_show_11.setVisibility(View.GONE);
                tl_show_12.setVisibility(View.VISIBLE);
                tl_show_13.setVisibility(View.GONE);


                tv_show_d_cp_min.setText(R.string.cp_min);
                tv_show_d_cp_max.setText(R.string.cp_max);
                tv_show_d_cpu_min.setText(R.string.foreign_p_min);
                tv_show_d_cpu_max.setText(R.string.foreign_p_max);
                tv_show_d_ac_psp.setText(R.string.ac_psp);
                tv_show_d_zinc_value.setText("Zinc Value");
            } else if (type.equalsIgnoreCase("VC")) {
                tl_show_5.setVisibility(View.VISIBLE);
                tl_show_6.setVisibility(View.VISIBLE);
                tl_show_7.setVisibility(View.VISIBLE);
                tl_show_8.setVisibility(View.VISIBLE);
                tl_show_9.setVisibility(View.VISIBLE);
                tl_show_10.setVisibility(View.VISIBLE);
                tl_show_11.setVisibility(View.VISIBLE);
                tl_show_12.setVisibility(View.VISIBLE);
                tl_show_13.setVisibility(View.VISIBLE);


                tv_show_d_cp_min.setText("UP MIN");
                tv_show_d_cp_min.setText("UP MAX");
                tv_show_d_cpu_min.setText("DN MIN");
                tv_show_d_cpu_max.setText("DN MAX");
                tv_show_d_velve.setText("Valve");
                tv_show_d_pot_1.setText("Anode Potential 1");
                tv_show_d_pot_1.setText("Anode Potential 2");
                tv_show_d_ac_psp.setText("Anode Current in mA");
                tv_show_d_zinc_value.setText("Anode Current in mA");

            }
        } else Toasty.error(mContext, "No Type Found ):", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int months, int date) {
        int month = months + 1;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, months, date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String formatedDate = sdf.format(calendar.getTime());
        Log.i(TAG, ": picker date format " + formatedDate);

        // tv_show_date.setText(String.format("%d/%d/%d", date, month, year));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case IMAGE_CAPTURE_REQUEST_1:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "onActivityResult: Result:1 " + imageBtnFile.toString());
                    uploadTagPhotos(imageBtnFile.toString(), btn_show_photo_1);
                    deleteRecursive(new File(tlpImageToUri.toString()));
                }
                break;
            case IMAGE_CAPTURE_REQUEST_2:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "onActivityResult: Result:2 " + imageBtnFile.toString());
                    uploadTagPhotos(imageBtnFile.toString(), btn_show_photo_2);
                    deleteRecursive(new File(tlpImageToUri.toString()));
                }
                break;
            case IMAGE_CAPTURE_REQUEST_3:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "onActivityResult: Result:3 " + imageBtnFile.toString());
                    uploadTagPhotos(imageBtnFile.toString(), btn_show_photo_3);
                    deleteRecursive(new File(tlpImageToUri.toString()));
                }
                break;
            case IMAGE_CAPTURE_REQUEST_4:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "onActivityResult: Result:3 " + imageBtnFile.toString());
                    uploadTagPhotos(imageBtnFile.toString(), btn_start_km);
                    deleteRecursive(new File(tlpImageToUri.toString()));
                }
                break;
            case IMAGE_CAPTURE_REQUEST_5:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "onActivityResult: Result:3 " + imageBtnFile.toString());
                    uploadTagPhotos(imageBtnFile.toString(), btn_end_km);
                    deleteRecursive(new File(tlpImageToUri.toString()));
                }
                break;
            case IMAGE_CAPTURE_REQUEST_6:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "onActivityResult: Result:3 " + imageBtnFile.toString());
                    uploadTagPhotos(imageBtnFile.toString(), btn_vehical_no);
                    deleteRecursive(new File(tlpImageToUri.toString()));
                }
                break;
            case END_KM_PHOTO_REQUEST:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "onActivityResult: Result:3 " + imageBtnFile.toString());
                    uploadTagPhotos(imageBtnFile.toString(), btn_elert_file_end_km);
                    deleteRecursive(new File(tlpImageToUri.toString()));
                }
                break;

        }
    }

    // minimize photos  in background
    private void uploadTagPhotos(final String imageBtnFile, final Button btn) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(final Void... params) {
                // something you know that will take a few seconds
                String path = compressImage(imageBtnFile);

                Log.i(TAG, "doInBackground: path: " + path);
                return path;
            }

            @Override
            protected void onPostExecute(String path) {
                btn.setText(R.string.uploaded);
                btn.setTag(path);
                btn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.md_green_200));
            }
        }.execute();

    }

    //delete photo
    public void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
        }

        fileOrDirectory.delete();
        Log.i(TAG, "deleteRecursive: File deleted");
    }


    //******************************************************************Convert image to String***********************************
    public static String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.NO_WRAP);
    }

    //******************************************************************Cropping Photo ***********************************
    public String compressImage(String imageUri) {
        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;
    }

    public String getFilename() {
        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/TLP Monitoring/";
        File newdir = new File(dir);
        if (!newdir.exists()) {
            newdir.mkdirs();
        }
        String file = dir + "TLP_" + DateFormat.format("yyyyMMdd_hhmmss", new Date()).toString() + ".jpg";

        return file;
    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Log.i(TAG, "getRealPathFromURI: " + contentUri.toString());
        Cursor cursor = mContext.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            cursor.close();
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    @Override
    public void msgSent(String data) {
        try {
            if (clickIncrement == 1 && isValueChecked1) {
                try {
                    String[] newStringData = data.split(",");
                    String strValDC = newStringData[0];
                    String strValAC = newStringData[1];
                    Log.d(TAG, "run: " + strValDC);
                    strValAC = strValAC.replace(";", "");
                    et_show_carrier_pro_min.setText(strValDC);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (clickIncrement == 2 && isValueChecked2) {
                try {
                    String[] newStringData = data.split(",");
                    String strValDC = newStringData[0];
                    String strValAC = newStringData[1];
                    Log.d(TAG, "run: " + strValDC);
                    strValAC = strValAC.replace(";", "");
                    et_show_carrier_pro_max.setText(strValDC);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (clickIncrement == 3 && isValueChecked3) {
                try {
                    String[] newStringData = data.split(",");
                    String strValDC = newStringData[0];
                    String strValAC = newStringData[1];
                    Log.d(TAG, "run: " + strValDC);
                    strValAC = strValAC.replace(";", "");
                    et_show_cpu_min.setText(strValDC);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (clickIncrement == 4 && isValueChecked4)
            {
                try {
                    String[] newStringData = data.split(",");
                    String strValDC = newStringData[0];
                    String strValAC = newStringData[1];
                    Log.d(TAG, "run: " + strValDC);
                    strValAC = strValAC.replace(";", "");
                    et_show_cpu_max.setText(strValDC);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (clickIncrement == 5 && isValueChecked5)
            {
                try {
                    String[] newStringData = data.split(",");
                    String strValDC = newStringData[0];
                    String strValAC = newStringData[1];
                    Log.d(TAG, "run: " + strValAC);
                    strValAC = strValAC.replace(";", "");
                    et_show_ac_psp.setText(strValAC);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            str_data_loging_data = data;
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
////                    Toast.makeText(mContext, data, Toast.LENGTH_SHORT).show();
//
//
//                }
//            });
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }


    //bluetooth data is starting from here

    BluetoothAdapter mBluetoothAdapter;
    Button btnEnableDisable_Discoverable;

    BluetoothConnectionService mBluetoothConnection;

    Button btnStartConnection;
    TextView msgReceived;
    Button btnSend;

    EditText etSend;

    BluetoothDevice mBTDevice;

    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();

    public DeviceListAdapter mDeviceListAdapter;

    ListView lvNewDevices;


    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING ON");
                        break;
                }
            }
        }
    };

    /**
     * Broadcast Receiver for changes made to bluetooth states such as:
     * 1) Discoverability mode on/off or expire.
     */
    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {

                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode) {
                    //Device is in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled.");
                        break;
                    //Device not in discoverable mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Able to receive connections.");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections.");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "mBroadcastReceiver2: Connecting....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "mBroadcastReceiver2: Connected.");
                        break;
                }

            }
        }
    };


    /**
     * Broadcast Receiver for listing devices that are not yet paired
     * -Executed by btnDiscover() method.
     */
    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND.");

            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);
            }
        }
    };

    /**
     * Broadcast Receiver that detects bond state changes (Pairing status changes)
     */
    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //3 cases:
                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDED.");
                    //inside BroadcastReceiver4
                    mBTDevice = mDevice;
                }
                //case2: creating a bone
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDING.");
                }
                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    Log.d(TAG, "BroadcastReceiver: BOND_NONE.");
                }
            }
        }
    };

    private void startBTAuto() {
        Log.d(TAG, "onClick: enabling/disabling bluetooth.");
        enableDisableBT();

        //Discoverable
        Log.d(TAG, "btnEnableDisable_Discoverable: Making device discoverable for 3000 seconds.");

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3000);
        startActivity(discoverableIntent);

        IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        mContext.registerReceiver(mBroadcastReceiver2, intentFilter);

//        //discover other devices here
//        Log.d(TAG, "btnDiscover: Looking for unpaired devices.");
//
//        if (mBluetoothAdapter.isDiscovering()) {
//            mBluetoothAdapter.cancelDiscovery();
//            Log.d(TAG, "btnDiscover: Canceling discovery.");
//
//            //check BT permissions in manifest
//            checkBTPermissions();
//
//            mBluetoothAdapter.startDiscovery();
//            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
//        }
//        if (!mBluetoothAdapter.isDiscovering()) {
//
//            //check BT permissions in manifest
//            checkBTPermissions();
//
//            mBluetoothAdapter.startDiscovery();
//            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
//        }
    }


    //create method for starting connection
//***remember the conncction will fail and app will crash if you haven't paired first
    public void startConnection() {
        startBTConnection(mBTDevice, MY_UUID_INSECURE);
    }

    /**
     * starting chat service method
     */
    public void startBTConnection(BluetoothDevice device, UUID uuid) {
        Log.d(TAG, "startBTConnection: Initializing RFCOM Bluetooth Connection.");

        mBluetoothConnection.startClient(device, uuid);
    }
    public void enableDisableBT() {
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "enableDisableBT: Does not have BT capabilities.");
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Log.d(TAG, "enableDisableBT: enabling BT.");
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            mContext.registerReceiver(mBroadcastReceiver1, BTIntent);

//            Toast.makeText(mContext, "Bluetooth Connected", Toast.LENGTH_LONG).show();
        }

//        if (mBluetoothAdapter.isEnabled()) {
//            Log.d(TAG, "enableDisableBT: disabling BT.");
//            mBluetoothAdapter.disable();
//
//            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
//            registerReceiver(mBroadcastReceiver1, BTIntent);
//            Toast.makeText(this, "Bluetooth Disconnected", Toast.LENGTH_LONG).show();
//        }

    }
    public void btnEnableDisable_Discoverable(View view) {
        Log.d(TAG, "btnEnableDisable_Discoverable: Making device discoverable for 300 seconds.");

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        mContext.registerReceiver(mBroadcastReceiver2, intentFilter);

    }
    public void btnDiscoverNew(View view)
    {


    }
    /**
     * This method is required for all devices running API23+
     * Android must programmatically check the permissions for bluetooth. Putting the proper permissions
     * in the manifest is not enough.
     * <p>
     * NOTE: This will only execute on versions > LOLLIPOP because it is not needed otherwise.
     */
    private void checkBTPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            int permissionCheck = mContext.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += mContext.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        } else {
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //first cancel discovery because its very memory intensive.
        mBluetoothAdapter.cancelDiscovery();

        Log.d(TAG, "onItemClick: You Clicked on a device.");
        String deviceName = mBTDevices.get(i).getName();
        String deviceAddress = mBTDevices.get(i).getAddress();
        int position = i;

        Log.d(TAG, "onItemClick: deviceName = " + deviceName);
        Log.d(TAG, "onItemClick: deviceAddress = " + deviceAddress);

        //create the bond.
        //NOTE: Requires API 17+? I think this is JellyBean


        android.support.v7.app.AlertDialog.Builder newAlertDialog = new android.support.v7.app.AlertDialog.Builder(mContext)
                .setCancelable(false)
                .setTitle("Connect Device")
                .setMessage("Device Name: " + deviceName + "\nDevice Address: " + deviceAddress)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                            Log.d(TAG, "Trying to pair with " + deviceName);
                            mBTDevices.get(position).createBond();

                            mBTDevice = mBTDevices.get(position);
                            Log.d(TAG, "onClick: " + mBTDevice);
                            mBluetoothConnection = new BluetoothConnectionService(mContext, CreateReportFragment.this);
                            startConnection();
                            relativeLayout.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        newAlertDialog.show();
        newAlertDialog.create();
    }
    public class MyTimeTask extends TimerTask {

        public void run()
        {
            pd.dismiss();
            i = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);
        }
    }
    public static String parseDate(String Date, String CurrentPattern, String OutputPattern)
    {

        SimpleDateFormat sdf = new SimpleDateFormat(CurrentPattern, Locale.getDefault());

        try {
            Date startDate = increaseDate(sdf.parse(Date));
            sdf.applyPattern(OutputPattern);
            return sdf.format(startDate);
        } catch (Exception e) {
            return "";
        }
    }
    public static Date increaseDate(Date origDate) {
        final Calendar calendar = Calendar.getInstance() ;
        calendar.setTime(origDate);
//        calendar.add(Calendar.SECOND, tempVal);
        calendar.add(Calendar.SECOND, tempVal);
        Date newDate = calendar.getTime();
        return newDate;
    }
    public static String parseDate1(String Date, String CurrentPattern, String OutputPattern, int inc)
    {

        SimpleDateFormat sdf = new SimpleDateFormat(CurrentPattern, Locale.getDefault());
        try {
            Date startDate = increaseDate1(sdf.parse(Date), inc);
            sdf.applyPattern(OutputPattern);
            return sdf.format(startDate);
        } catch (Exception e) {
            return "";
        }
    }
    public static Date increaseDate1(Date origDate, int inc) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(origDate);
//        calendar.add(Calendar.SECOND, tempVal);
        calendar.add(Calendar.SECOND, inc);
        Date newDate = calendar.getTime();
        return newDate;
    }
    public String printDifference(Date startDate, Date endDate)
    {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();
        newDiff = different / 1000;

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);
        System.out.println("New Difference : " + newDiff);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        return elapsedHours + ":" + elapsedMinutes + ":" + elapsedSeconds;
    }


}