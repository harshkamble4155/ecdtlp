package org.icspl.ecdtlp.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.icspl.ecdtlp.models.SynNowMode;
import org.icspl.ecdtlp.models.UserDetailModel;
import org.icspl.ecdtlp.utils.Common;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";

    public DbHelper(Context context) {
        super(context, Common.DB_NAME, null, Common.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        // USER Login Details
        db.execSQL("CREATE TABLE " + DbTables.TbL_UserList + "("
                + DbTables.UserList_Entry._ID + " integer primary key autoincrement" + " , "
                + DbTables.UserList_Entry.client_id + " VARCHAR(250) " + " , "
                + DbTables.UserList_Entry.DisplayName + " VARCHAR(250) " + " , "
                + DbTables.UserList_Entry.UserName + " VARCHAR(250) " + " , "
                + DbTables.UserList_Entry.Password + " VARCHAR(250) " + " , "
                + DbTables.UserList_Entry.Status + " VARCHAR(250)   " + "); "
        );

        db.execSQL("CREATE TABLE " + DbTables.TbL_DataLogging + "("
                + DbTables.Datalogging._ID + " integer primary key autoincrement" + " , "
                + DbTables.Datalogging.powerOffAc + " VARCHAR(250) " + " , "
                + DbTables.Datalogging.powerOnAc + " VARCHAR(250) " + " , "
                + DbTables.Datalogging.powerOffDc + " VARCHAR(250) " + " , "
                + DbTables.Datalogging.powerOnDc + " VARCHAR(250) " + " , "
                + DbTables.Datalogging.timeOff + " VARCHAR(250) " + " , "
                + DbTables.Datalogging.timeOn + " VARCHAR(250) " + " , "
                + DbTables.Datalogging.longi + " VARCHAR(250) " + " , "
                + DbTables.Datalogging.lati + " VARCHAR(250) " + " ); "
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS " + DbTables.TbL_DataLogging + "("
                + DbTables.Datalogging._ID + " integer primary key autoincrement" + " , "
                + DbTables.Datalogging.powerOffAc + " VARCHAR(250) " + " , "
                + DbTables.Datalogging.powerOnAc + " VARCHAR(250) " + " , "
                + DbTables.Datalogging.powerOffDc + " VARCHAR(250) " + " , "
                + DbTables.Datalogging.powerOnDc + " VARCHAR(250) " + " , "
                + DbTables.Datalogging.timeOff + " VARCHAR(250) " + " , "
                + DbTables.Datalogging.timeOn + " VARCHAR(250) " + " , "
                + DbTables.Datalogging.longi + " VARCHAR(250) " + " , "
                + DbTables.Datalogging.lati + " VARCHAR(250) " + " ); "
        );


        // Spinner Section Details
        db.execSQL("CREATE TABLE " + DbTables.TbL_Section + "("
                + DbTables.Section_Entry._ID + " integer primary key autoincrement" + " , "
                + DbTables.Section_Entry.Section + " VARCHAR(250)   " + "); "
        );

        // Spinner Quarter Details
        db.execSQL("CREATE TABLE " + DbTables.TbL_Quarters + "("
                + DbTables.Quarters_Entry._ID + " integer primary key autoincrement" + " , "
                + DbTables.Quarters_Entry.Quarters + " VARCHAR(80)   " + "); "
        );

        // All SQ Details
        db.execSQL("CREATE TABLE " + DbTables.TbL_SectionDetails + "("
                + DbTables.SectionDetails_Entry._ID + " integer primary key autoincrement" + " , "
                + DbTables.SectionDetails_Entry.ClientID + " VARCHAR(250) " + " , "
                + DbTables.SectionDetails_Entry.ContractorID + " VARCHAR(250) " + " , "
                + DbTables.SectionDetails_Entry.PipeType + " VARCHAR(250) " + " , "
                + DbTables.SectionDetails_Entry.Section + " VARCHAR(250) " + " , "
                + DbTables.SectionDetails_Entry.Ip_Voltg + " VARCHAR(250) " + " , "
                + DbTables.SectionDetails_Entry.Op_Voltg + " VARCHAR(250) " + " , "
                + DbTables.SectionDetails_Entry.Ext_Refvoltg + " VARCHAR(250) " + " , "
                + DbTables.SectionDetails_Entry.Op_Curnt + " VARCHAR(250) " + " , "
                + DbTables.SectionDetails_Entry.Intrnl_Refvoltg + " VARCHAR(250) " + " , "
                + DbTables.SectionDetails_Entry.Ref_Sectr + " VARCHAR(250) " + " , "
                + DbTables.SectionDetails_Entry.Coarse_Cntrl + " VARCHAR(250) " + " , "
                + DbTables.SectionDetails_Entry.Fine_Cntrl + " VARCHAR(250) " + " , "
                + DbTables.SectionDetails_Entry.PSP_Reqrd + " VARCHAR(250) " + " , "
                + DbTables.SectionDetails_Entry.DateofEntry + " VARCHAR(250) " + " , "
                + DbTables.SectionDetails_Entry.Quaters + " VARCHAR(250) " + " , "
                + DbTables.SectionDetails_Entry.Status + " VARCHAR(250) " + " , "
                + DbTables.SectionDetails_Entry.previousDate + " VARCHAR(250) " + " , "
                + DbTables.SectionDetails_Entry.Server_Status + " VARCHAR(250)   " + "); "
        );


        // All TbL_TRUnit_Entry
        db.execSQL("CREATE TABLE " + DbTables.TbL_TRUnit + "("
                + DbTables.TRUnit_Entry._ID + " integer primary key autoincrement" + " , "

                + DbTables.TRUnit_Entry.ClientID + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.ContractorID + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.Section + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.PipeType + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TR_Chainage + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TR_Loc + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TR_Add + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TR_Make + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TR_Supplier + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TR_SupplierNo + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TR_SupplierEmail + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TR_RefCalNo + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TR_RefCalMake + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TR_RefCalType + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TR_RangeVltg + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TR_RangeAmpx + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TR_Lat + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TR_Long + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.CorCoupon_Loc + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.Coupan_SrNo + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.Thikness + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.OD + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.IDs + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.Surface_Area + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.Weight_Initial + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.DiodeStn_Chainage + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.DiodeStn_Loc + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.DiodeStn_Add + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.DiodeStn_Make + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.DiodeStn_Supplier + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.DiodeStn_SupplierNo + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.DiodeStn_SupplierEmail + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.DiodeStn_RefCalNo + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.DiodeStn_RefCalMake + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.DiodeStn_RefCalType + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.DiodeStn_RangeVltg + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.DiodeStn_RangeAmpx + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.DiodeStn_Lat + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.DiodeStn_Long + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.AJB_Chainage + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.AJB_Loc + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.AJB_Add + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.AJB_Make + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.AJB_Supplier + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.AJB_SupplierNo + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.AJB_SupplierEmail + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.AJB_AnodeNo + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.AJB_AnodeRtng + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.AJB_ShuntNo + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.AJB_ShuntRtng + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.AJB_RegiNo + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.AJB_Lat + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.AJB_Long + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.CJB_Chainage + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.CJB_Loc + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.CJB_Add + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.CJB_Make + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.CJB_Supplier + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.CJB_SupplierNo + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.CJB_SupplierEmail + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.CJB_RefCalNo + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.CJB_RefCalMake + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.CJB_RefCalType + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.CJB_Lat + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.CJB_Long + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TLP_TSNo + " REAL  " + " , "
                + DbTables.TRUnit_Entry.Type + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TLP_Chainage + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TLP_Loc + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TLP_Add + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TLP_Make + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TLP_Supplier + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TLP_SupplierNo + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TLP_SupplierEmail + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TLP_Lat + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TLP_Long + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.Status + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.SUP_POL_CELL + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.MAKE_OF_POL_CELL + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.MODEL_TLP + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.SIZE_TLP + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.DRAWING_TLP + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.F85 + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.DoneStatus + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.DoneDate + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.AJB_ShuntRtng_Amp + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.AJB_No + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.CJB_NO + " VARCHAR(250) " + " , " // type_client columns in server
                + DbTables.TRUnit_Entry.TR_NO + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.AJB_RegiRtng + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.DateofEntry + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.Nallah_Crossing + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.TsNo_Client + " VARCHAR(250) " + " , "
                + DbTables.TRUnit_Entry.Type_Client + " VARCHAR(250)   " + "); "
        );


        // All TbL_TRUnit_Entry
        db.execSQL("CREATE TABLE " + DbTables.TbL_Mentoring_Tip + "("
                + DbTables.TRUnit_Entry._ID + " integer primary key autoincrement" + " , "

                + DbTables.Monnitoring_Tlp_Entry.ClientID + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.ContractorID + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.Section + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.PIPEType + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.TS_No + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.TS_Type + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.TS_Km + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.TS_Location + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.PSP_Min + " REAL " + " , "
                + DbTables.Monnitoring_Tlp_Entry.PSP_Max + " REAL " + " , "
                + DbTables.Monnitoring_Tlp_Entry.Casing_Min + " REAL " + " , "
                + DbTables.Monnitoring_Tlp_Entry.Casing_Max + " REAL " + " , "
                + DbTables.Monnitoring_Tlp_Entry.ZINC_Value + " REAL " + " , "
                + DbTables.Monnitoring_Tlp_Entry.AC_PSP + " REAL " + " , "
                + DbTables.Monnitoring_Tlp_Entry.S_DATE + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.S_Time + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.NCordinate + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.ECordinate + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.Remark + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.Status + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.DateofEntry + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.Quaters + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.ManualRemarks + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.Valve + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.Potential_1 + " REAL " + " , "
                + DbTables.Monnitoring_Tlp_Entry.Potential_2 + " REAL " + " , "
                + DbTables.Monnitoring_Tlp_Entry.TR_Location + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.RejectDate + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.Potential_3 + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.Potential_4 + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.RCPortable + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.anodeCurrent + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.type_client + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.tsno_client + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.Driver_Name + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.Driver_Mob_Number + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.Vehicle_Number + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.Start_Km + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.End_Km + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.TLPFile + " VARCHAR(250)" + " , "
                + DbTables.Monnitoring_Tlp_Entry.Start_Km_File + " VARCHAR(250)" + " , "
                + DbTables.Monnitoring_Tlp_Entry.End_Km_file + " VARCHAR(250)" + " , "
                + DbTables.Monnitoring_Tlp_Entry.Vehicle_No_File + " VARCHAR(250)" + " , "
                + DbTables.Monnitoring_Tlp_Entry.Selfie_File + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.Reading_File + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.filled_date + " VARCHAR(250) " + " , "
                + DbTables.Monnitoring_Tlp_Entry.server_status + " VARCHAR(250) " + "); "
        );

        //vehicle_details
        db.execSQL("CREATE TABLE " + DbTables.Tbl_Vehicle_details + "("

                + DbTables.Vehicle_details._ID + " integer primary key autoincrement" + " , "
                + DbTables.Vehicle_details.Vehicle_No + " VARCHAR(250) " + " , "
                + DbTables.Vehicle_details.Driver_Name + " VARCHAR(250) " + " , "
                + DbTables.Vehicle_details.Mobile_Number + " VARCHAR(250) " + " , "
                + DbTables.Vehicle_details.Start_Km + " VARCHAR(250) " + " , "
                + DbTables.Vehicle_details.End_Km + " VARCHAR(250) " + ");"

        );
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DbTables.Tbl_Vehicle_details + "("
                + DbTables.Vehicle_details._ID + " integer primary key autoincrement" + " , "
                + DbTables.Vehicle_details.Vehicle_No + " VARCHAR(250) " + " , "
                + DbTables.Vehicle_details.Driver_Name + " VARCHAR(250) " + " , "
                + DbTables.Vehicle_details.Mobile_Number + " VARCHAR(250) " + " , "
                + DbTables.Vehicle_details.Start_Km + " VARCHAR(250) " + " , "
                + DbTables.Vehicle_details.End_Km + " VARCHAR(250) " + ");"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbTables.TbL_UserList);
    }

    // insert Sections Details
    public void insertSection(ArrayList<UserDetailModel.Section> sectionList) {

        SQLiteDatabase mDatabase = this.getWritableDatabase();


        for (int i = 0; i < sectionList.size(); i++) {
            try {
                mDatabase.beginTransaction();
                Log.i(TAG, "insertSection: " + sectionList.get(i).getSection());
                ContentValues mValues = new ContentValues();
                mValues.put(DbTables.Section_Entry.Section, String.valueOf(sectionList.get(i).getSection()));
                mDatabase.insert(DbTables.TbL_Section, null, mValues);
                mDatabase.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mDatabase.endTransaction();
            }
        }

    }

    // insert Quarters Details
    public void insertQuarters(ArrayList<UserDetailModel.Quarter> quarterList) {

        SQLiteDatabase mDatabase = this.getWritableDatabase();
        mDatabase.beginTransaction();
        try {
            for (int i = 0; i < quarterList.size(); i++) {
                ContentValues mValues = new ContentValues();
                mValues.put(DbTables.Quarters_Entry.Quarters, String.valueOf(quarterList.get(i).getQuaters()));
                mDatabase.insert(DbTables.TbL_Quarters, null, mValues);
            }
            mDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDatabase.endTransaction();
        }
    }

    // insert All SQ Details
    public void getAllSQDetails(ArrayList<UserDetailModel.SectionDetail> mList) {
        SQLiteDatabase mDatabase = this.getWritableDatabase();

        for (int i = 0; i < mList.size(); i++) {
            ContentValues mValues = new ContentValues();
            try {
                mDatabase.beginTransaction();
                mValues.put(DbTables.SectionDetails_Entry.ClientID, mList.get(i).getClientID());
                mValues.put(DbTables.SectionDetails_Entry.ContractorID, mList.get(i).getContractorID());
                mValues.put(DbTables.SectionDetails_Entry.PipeType, mList.get(i).getPipeType());
                mValues.put(DbTables.SectionDetails_Entry.Coarse_Cntrl, mList.get(i).getCoarseCntrl());
                mValues.put(DbTables.SectionDetails_Entry.DateofEntry, mList.get(i).getDateofEntry());
                mValues.put(DbTables.SectionDetails_Entry.Ext_Refvoltg, mList.get(i).getExtRefvoltg());
                mValues.put(DbTables.SectionDetails_Entry.Fine_Cntrl, mList.get(i).getFineCntrl());
                mValues.put(DbTables.SectionDetails_Entry.Ip_Voltg, mList.get(i).getIpVoltg());
                mValues.put(DbTables.SectionDetails_Entry.Op_Voltg, mList.get(i).getOpVoltg());
                mValues.put(DbTables.SectionDetails_Entry.Op_Curnt, mList.get(i).getOpCurnt());
                mValues.put(DbTables.SectionDetails_Entry.Ref_Sectr, mList.get(i).getRefSectr());
                mValues.put(DbTables.SectionDetails_Entry.Intrnl_Refvoltg, mList.get(i).getIntrnlRefvoltg());
                mValues.put(DbTables.SectionDetails_Entry.PSP_Reqrd, mList.get(i).getPspReqrd());
                mValues.put(DbTables.SectionDetails_Entry.Status, mList.get(i).getStatus());
                mValues.put(DbTables.SectionDetails_Entry.Quaters, mList.get(i).getQuaters());
                mValues.put(DbTables.SectionDetails_Entry.Section, mList.get(i).getSection());
                mValues.put(DbTables.SectionDetails_Entry.Server_Status, "Fetched");

                mDatabase.insert(DbTables.TbL_SectionDetails, null, mValues);
                mDatabase.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mDatabase.endTransaction();
            }
        }

    }

    // inserting TrUnit Details
    public void insertTbl_TrUnit(ArrayList<UserDetailModel.TrUnit> mList) {
        SQLiteDatabase mDatabase = this.getWritableDatabase();


        for (int i = 0; i < mList.size(); i++) {
            ContentValues mValues = new ContentValues();
            try {
                mDatabase.beginTransaction();
                mValues.put(DbTables.TRUnit_Entry.ClientID, mList.get(i).getClientID());
                mValues.put(DbTables.TRUnit_Entry.ContractorID, mList.get(i).getContractorID());
                mValues.put(DbTables.TRUnit_Entry.Section, mList.get(i).getSection());
                mValues.put(DbTables.TRUnit_Entry.TLP_TSNo, mList.get(i).getTlptsNo());
                mValues.put(DbTables.TRUnit_Entry.TLP_Loc, mList.get(i).getTlpLoc());
                mValues.put(DbTables.TRUnit_Entry.Type, mList.get(i).getType());
                mValues.put(DbTables.TRUnit_Entry.TLP_Chainage, mList.get(i).getTlpChainage());
                mValues.put(DbTables.TRUnit_Entry.Nallah_Crossing, mList.get(i).getNallahCrossing());
                mValues.put(DbTables.TRUnit_Entry.Type_Client, mList.get(i).getTypeClient());
                mValues.put(DbTables.TRUnit_Entry.TsNo_Client, mList.get(i).getTsNoClient());
                mDatabase.insert(DbTables.TbL_TRUnit, null, mValues);

                mDatabase.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mDatabase.endTransaction();
            }
        }

    }

    //inserting Tbl Vehicles details
    public void insertTbl_Vehicles_details(String Vehicle_Number, String Driver_Name, String Mobile_Number, String Start_Km, String End_Km) {
        SQLiteDatabase mDatabase = this.getWritableDatabase();
        try {
            ContentValues mValues = new ContentValues();
            mDatabase.beginTransaction();
            mValues.put(DbTables.Vehicle_details.Vehicle_No, Vehicle_Number);
            mValues.put(DbTables.Vehicle_details.Driver_Name, Driver_Name);
            mValues.put(DbTables.Vehicle_details.Mobile_Number, Mobile_Number);
            mValues.put(DbTables.Vehicle_details.Start_Km, Start_Km);
            mValues.put(DbTables.Vehicle_details.End_Km, End_Km);
            mDatabase.insert(DbTables.Tbl_Vehicle_details, null, mValues);
            mDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDatabase.endTransaction();
        }
    };

    // inserting Tbl Monitoring Details Details
    public void getTblMonitoringDetails(ArrayList<UserDetailModel.MonitoringTLP> mList) {
        SQLiteDatabase mDatabase = this.getWritableDatabase();

        for (int i = 0; i < mList.size(); i++) {
            try {
                ContentValues mValues = new ContentValues();
                mDatabase.beginTransaction();
                mValues.put(DbTables.TRUnit_Entry.ClientID, mList.get(i).getClientID());
                mValues.put(DbTables.Monnitoring_Tlp_Entry.ContractorID, mList.get(i).getContractorID());
                mValues.put(DbTables.Monnitoring_Tlp_Entry.Section, mList.get(i).getSection());
                mValues.put(DbTables.Monnitoring_Tlp_Entry.TS_No, mList.get(i).getTsNo());
                mValues.put(DbTables.Monnitoring_Tlp_Entry.TS_Type, mList.get(i).getTsType());
                mValues.put(DbTables.Monnitoring_Tlp_Entry.Quaters, mList.get(i).getQuaters());
                mValues.put(DbTables.Monnitoring_Tlp_Entry.Status, "Active");
                mDatabase.insert(DbTables.TbL_Mentoring_Tip, null, mValues);
                mDatabase.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mDatabase.endTransaction();
            }
        }

    }


    public Cursor getTsNo(String user_id, String section, String tsno) {

        SQLiteDatabase mDatabase = this.getReadableDatabase();
        String sql = "SELECT Type, TLP_Chainage, TLP_Loc,Type_Client,TsNo_Client FROM Tbl_TRUnit " +
                "WHERE ContractorID = '" + user_id + "' and Section = '" + section + "' AND " +
                "TLP_TSNo = '" + tsno + "' ORDER BY Section ";
        return mDatabase.rawQuery(sql, null);
    }

    public Cursor getVehicleDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DbTables.Tbl_Vehicle_details;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    // init Sections list Spinner
    public ArrayList<String> getSectionsList() {
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        ArrayList<String> sectionList = new ArrayList<>();
        sectionList.add("Select");
        Cursor mCursor = mDatabase.query(DbTables.TbL_Section, null, null, null, null, null, null, null);

        if (mCursor != null && mCursor.getCount() > 0) {
            while (mCursor.moveToNext()) {
                sectionList.add(mCursor.getString(1));
            }
        }
        if (mCursor != null) mCursor.close();
        return sectionList;
    }

    // init Quarters list Spinner
    public ArrayList<String> getQuartersList() {
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        ArrayList<String> quartersList = new ArrayList<>();
        quartersList.add("Select");
        Cursor mCursor = mDatabase.query(DbTables.TbL_Quarters, null, null, null, null, null, null, null);

        if (mCursor != null && mCursor.getCount() > 0) {
            while (mCursor.moveToNext()) {
                quartersList.add(mCursor.getString(1));
            }
        }
        if (mCursor != null) mCursor.close();
        return quartersList;
    }

    // ---------------------         send data to server ------------------- \\
    public Cursor sendSectionsDetailsToServer() {
        SQLiteDatabase mDatabase = this.getReadableDatabase();

        return mDatabase.query(DbTables.TbL_SectionDetails, null,
                DbTables.SectionDetails_Entry.Server_Status + "=?",
                new String[]{"pending"}, null, null, null, "1");

    }

    public Cursor sendTlpDetails(String date, String section, String quarter) {
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        Log.i(TAG, "sendTlpDetails: Came here");
        return mDatabase.query(DbTables.TbL_Mentoring_Tip, null,
                DbTables.Monnitoring_Tlp_Entry.S_DATE + "=? AND " +
                        DbTables.Monnitoring_Tlp_Entry.Section + "=? AND " +
                        DbTables.Monnitoring_Tlp_Entry.Quaters + "=? AND " +
                        DbTables.Monnitoring_Tlp_Entry.server_status + "=? ",
                new String[]{date, section, quarter, "pending"}, null, null, null, "1");
    }

    // check is user filled end KM with that
    public ArrayList<String> checkEndKm() {
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        ArrayList<String> result = new ArrayList<>();
        Cursor mCursor = mDatabase.query(DbTables.TbL_Mentoring_Tip,
                new String[]{DbTables.Monnitoring_Tlp_Entry.TS_No, DbTables.Monnitoring_Tlp_Entry.Section,
                        DbTables.Monnitoring_Tlp_Entry.Quaters, DbTables.Monnitoring_Tlp_Entry.S_DATE},
                " end_km_file  IS NULL AND " + DbTables.Monnitoring_Tlp_Entry.server_status + "=? ",
                new String[]{"pending"}, null, null, null, "1");
        if (mCursor != null && mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            Log.i(TAG, "Tlp Monitoring Details Cursor Count:" + mCursor.getInt(0));
            result.add(0, mCursor.getString(0));
            result.add(1, mCursor.getString(1));
            result.add(2, mCursor.getString(2));
            result.add(3, mCursor.getString(3));
            mCursor.close();
            return result;
        } else {
            Log.i(TAG, "Tlp Monitoring Details Cursor Count:" + null);
            return null;
        }
    }

    // Update user filled end KM with file
    public void updateEndKm(String tsNo, String end_km, String section,
                            String quarters, String previousDate) {
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        ContentValues mValues = new ContentValues();
        mValues.put(DbTables.Monnitoring_Tlp_Entry.End_Km, end_km);
//        mValues.put(DbTables.Monnitoring_Tlp_Entry.End_Km_file, endKm_File);
        mValues.put(DbTables.Monnitoring_Tlp_Entry.filled_date, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
        long id = mDatabase.update(DbTables.TbL_Mentoring_Tip, mValues,
                DbTables.Monnitoring_Tlp_Entry.TS_No + "=?  AND end_km_file IS NULL AND "
                        + DbTables.Monnitoring_Tlp_Entry.server_status + "=? ",
                new String[]{tsNo, "pending"});
        if (id > 0) {
            ContentValues mVal = new ContentValues();
            mVal.put(DbTables.SectionDetails_Entry.previousDate, previousDate);
            mDatabase.update(DbTables.TbL_SectionDetails, mVal,
                    DbTables.SectionDetails_Entry.Section + "=?  AND  "
                            + DbTables.SectionDetails_Entry.Quaters + "=? ",
                    new String[]{section, quarters});
        }

    }

    // getLatestDate(
    public String fetchLastDate(String section, String quater) {
        String lastDate = null;
        SQLiteDatabase mDatabase = this.getReadableDatabase();

        Cursor mCursor = mDatabase.query(DbTables.TbL_SectionDetails,
                new String[]{DbTables.SectionDetails_Entry.previousDate},
                "Section=? AND Quaters=? ", new String[]{section, quater},
                null, null, DbTables.SectionDetails_Entry.previousDate + " desc");

        if (mCursor != null && mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            Log.i(TAG, "Previous Date:" + mCursor.getString(0));
            lastDate = mCursor.getString(0);
            mCursor.close();
            return lastDate;
        } else {
            Log.i(TAG, "Previous Date:" + null);
            return null;
        }
    }


    // ---------------------  Done Record------------------- \\
    public void doneSectionStatus(@Nullable String id) {
        Log.i(TAG, "doneInspectionID: Done  id: " + id);
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        ContentValues mContentValues = new ContentValues();
        mContentValues.put(DbTables.SectionDetails_Entry.Server_Status, "done");

        mDatabase.update(DbTables.TbL_SectionDetails, mContentValues, "_id=?",
                new String[]{id});
    }

    // init Quarters list Spinner
    public Cursor getSQDetails(String section) {
        SQLiteDatabase mDatabase = this.getReadableDatabase();

        return mDatabase.query(DbTables.TbL_SectionDetails, null,
                DbTables.SectionDetails_Entry.Section + "=?",
                new String[]{section}, null, null, null, null);


    }

    // init Quarters list Spinner
    public Cursor getSQDetails(String section, String quarter) {
        SQLiteDatabase mDatabase = this.getReadableDatabase();

        return mDatabase.query(DbTables.TbL_SectionDetails, null,
                DbTables.SectionDetails_Entry.Section + "=? AND " + DbTables.SectionDetails_Entry.Quaters + "=?",
                new String[]{section, quarter}, null, null, null, null);


    }

    // ---------------------        Fetch Data ------------------- \\
    // fetch and insert new SQ Details again
    public long fetchAllSQDetails(ArrayList<UserDetailModel.SectionDetail> mList) {

        SQLiteDatabase mDatabase = this.getWritableDatabase();
        long id = 0;

        for (int i = 0; i < mList.size(); i++) {
            ContentValues mValues = new ContentValues();
            try {
                mDatabase.beginTransaction();
                mValues.put(DbTables.SectionDetails_Entry.ClientID, mList.get(i).getClientID());
                mValues.put(DbTables.SectionDetails_Entry.ContractorID, mList.get(i).getContractorID());
                mValues.put(DbTables.SectionDetails_Entry.PipeType, mList.get(i).getPipeType());
                mValues.put(DbTables.SectionDetails_Entry.Coarse_Cntrl, mList.get(i).getCoarseCntrl());
                mValues.put(DbTables.SectionDetails_Entry.DateofEntry, mList.get(i).getDateofEntry());
                mValues.put(DbTables.SectionDetails_Entry.Ext_Refvoltg, mList.get(i).getExtRefvoltg());
                mValues.put(DbTables.SectionDetails_Entry.Fine_Cntrl, mList.get(i).getFineCntrl());
                mValues.put(DbTables.SectionDetails_Entry.Ip_Voltg, mList.get(i).getIpVoltg());
                mValues.put(DbTables.SectionDetails_Entry.Op_Voltg, mList.get(i).getOpVoltg());
                mValues.put(DbTables.SectionDetails_Entry.Op_Curnt, mList.get(i).getOpCurnt());
                mValues.put(DbTables.SectionDetails_Entry.Ref_Sectr, mList.get(i).getRefSectr());
                mValues.put(DbTables.SectionDetails_Entry.Intrnl_Refvoltg, mList.get(i).getIntrnlRefvoltg());
                mValues.put(DbTables.SectionDetails_Entry.PSP_Reqrd, mList.get(i).getPspReqrd());
                mValues.put(DbTables.SectionDetails_Entry.Status, mList.get(i).getStatus());
                mValues.put(DbTables.SectionDetails_Entry.Quaters, mList.get(i).getQuaters());
                mValues.put(DbTables.SectionDetails_Entry.Section, mList.get(i).getSection());
                mValues.putNull(DbTables.SectionDetails_Entry.previousDate);
                mValues.put(DbTables.SectionDetails_Entry.Server_Status, "Fetched");

                id = mDatabase.insert(DbTables.TbL_SectionDetails, null, mValues);
                if (id == -1) {
                    Log.d(TAG, "fetchAllSQDetails: Failed to insert Section Details");
                } else {
                    Log.d(TAG, "fetchAllSQDetails: Success in inserting Section Details");
                }
                mDatabase.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mDatabase.endTransaction();
            }
        }
        return id;
    }

    // fetch Sections Details again
    public void fetchSection(ArrayList<UserDetailModel.Section> sectionList) {

        SQLiteDatabase mDatabase = this.getWritableDatabase();


        for (int i = 0; i < sectionList.size(); i++) {
            try {
                mDatabase.beginTransaction();
                ContentValues mValues = new ContentValues();
                mValues.put(DbTables.Section_Entry.Section, sectionList.get(i).getSection());

                Cursor mCursor = mDatabase.rawQuery("SELECT count( * ) as count from " +
                                DbTables.TbL_Section + " where " + DbTables.Section_Entry.Section + "=?",
                        new String[]{sectionList.get(i).getSection()});

                if (mCursor != null && mCursor.getCount() > 0) {
                    mCursor.moveToFirst();
                    //  Log.i(TAG, "Tlp Sections Details Cursor Count:" + mCursor.getInt(0));
                    if (mCursor.getInt(0) == 0)
                        mDatabase.insert(DbTables.TbL_Section, null, mValues);
                    else Log.i(TAG, "Sections Details Rows Exist already");
                    mCursor.close();
                } else Log.i(TAG, "Null section cursor");


                mDatabase.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mDatabase.endTransaction();
            }
        }

    }

    // fetch Quarters Details again
    public void fetchQuarters(ArrayList<UserDetailModel.Quarter> quarterList) {

        SQLiteDatabase mDatabase = this.getWritableDatabase();
        for (int i = 0; i < quarterList.size(); i++) {
            try {
                ContentValues mValues = new ContentValues();
                mDatabase.beginTransaction();
                mValues.put(DbTables.Quarters_Entry.Quarters, quarterList.get(i).getQuaters());
                // Log.e("where Section Details", "-->>" + whereClause);
                Cursor mCursor = mDatabase.rawQuery("SELECT count( * ) as count from " +
                                DbTables.TbL_Quarters + " where " + DbTables.Quarters_Entry.Quarters + "=?",
                        new String[]{String.valueOf(quarterList.get(i).getQuaters())});

                if (mCursor != null && mCursor.getCount() > 0) {
                    mCursor.moveToFirst();
                    //  Log.i(TAG, "Tlp Quarters Details Cursor Count:" + mCursor.getInt(0));
                    if (mCursor.getInt(0) == 0)
                        mDatabase.insert(DbTables.TbL_Quarters, null, mValues);
                    else Log.i(TAG, "Quarters Details Rows Exist already");
                    mCursor.close();
                }
                mDatabase.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mDatabase.endTransaction();
            }
        }
    }

    // Fetch Tbl Monitoring Details again
    public void fetchTblMonitoringDetails(ArrayList<UserDetailModel.MonitoringTLP> mList) {
        SQLiteDatabase mDatabase = this.getWritableDatabase();

        for (int i = 0; i < mList.size(); i++) {
            try {
                ContentValues mValues = new ContentValues();
                mDatabase.beginTransaction();
                mValues.put(DbTables.TRUnit_Entry.ClientID, mList.get(i).getClientID());
                mValues.put(DbTables.Monnitoring_Tlp_Entry.ContractorID, mList.get(i).getContractorID());
                mValues.put(DbTables.Monnitoring_Tlp_Entry.Section, mList.get(i).getSection());
                mValues.put(DbTables.Monnitoring_Tlp_Entry.TS_No, mList.get(i).getTsNo());
                mValues.put(DbTables.Monnitoring_Tlp_Entry.TS_Type, mList.get(i).getTsType());
                mValues.put(DbTables.Monnitoring_Tlp_Entry.Quaters, mList.get(i).getQuaters());
                mValues.put(DbTables.Monnitoring_Tlp_Entry.Status, "Active");

                Cursor mCursor = mDatabase.rawQuery("SELECT count( * ) as count from " +
                                DbTables.TbL_Mentoring_Tip + " where " +
                                DbTables.Monnitoring_Tlp_Entry.ClientID + "=?"
                                + " AND " + DbTables.Monnitoring_Tlp_Entry.ContractorID + "=?"
                                + " AND " + DbTables.Monnitoring_Tlp_Entry.Section + "=?"
                                + " AND " + DbTables.Monnitoring_Tlp_Entry.TS_No + "=?"
                                + " AND " + DbTables.Monnitoring_Tlp_Entry.TS_Type + "=?"
                                + " AND " + DbTables.Monnitoring_Tlp_Entry.Quaters + "=?"
                                + " AND " + DbTables.Monnitoring_Tlp_Entry.Status + "=?",
                        new String[]{
                                mList.get(i).getClientID(),
                                mList.get(i).getContractorID(),
                                mList.get(i).getSection(),
                                mList.get(i).getTsNo(),
                                mList.get(i).getTsType(),
                                mList.get(i).getQuaters(),
                                "Active"
                        });

                if (mCursor != null && mCursor.getCount() > 0) {
                    mCursor.moveToFirst();
                    // Log.i(TAG, "Tlp Monitoring Details Cursor Count:" + mCursor.getInt(0));
                    if (mCursor.getInt(0) == 0)
                        mDatabase.insert(DbTables.TbL_Mentoring_Tip, null, mValues);
                    else Log.i(TAG, "Tlp Monitoring Details Rows Exist already");
                    mCursor.close();
                }
                mDatabase.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mDatabase.endTransaction();
            }
        }

    }

    public void updateDoneMonitoringStage(int doneId) {
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        ContentValues mContentValues = new ContentValues();
        mContentValues.put(DbTables.Monnitoring_Tlp_Entry.server_status, "Done");

        mDatabase.update(DbTables.TbL_Mentoring_Tip, mContentValues, "_id=?",
                new String[]{String.valueOf(doneId)});
    }

    // preview list of data filled by user
    public Cursor previewListData(String user_id, String section, String quarter) {
        SQLiteDatabase mDatabase = this.getReadableDatabase();


        String sql_query = "Select * from TblMonitoring_TLP Where ContractorID= '" + user_id + "' and Section='"
                + section + "' and Quaters = '" + quarter + "' and server_status='pending' or server_status='Done'" +
                "order by TS_No";

        return mDatabase.rawQuery(sql_query, null);
       /* Cursor mCursor = mDatabase.query(DbTables.TbL_Mentoring_Tip, null,
                " end_km_file  IS NOT NULL AND " + DbTables.Monnitoring_Tlp_Entry.server_status + "=? ",
                new String[]{"pending"}, null, null, null, "1");*/

    }


    // fetch all vehical numbers hint
    public String[] retrieveVehicleNoList() {
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        String[] vehical;

        Cursor mCursor = mDatabase.query(DbTables.TbL_Mentoring_Tip, new String[]{DbTables.Monnitoring_Tlp_Entry.Vehicle_Number},
                "vehicle_number not null", null, null, null, null);

        if (mCursor != null && mCursor.getCount() > 0) {
            vehical = new String[mCursor.getCount()];
            while (mCursor.moveToNext()) {

                vehical[mCursor.getPosition()] = mCursor.getString(0);
                Log.i(TAG, "retrieveVehicalNoList: " + Arrays.toString(vehical));
            }

            mCursor.close();
            return vehical;
        }
        return null;
    }


    public String compareDates(String section, String quater) {
        String sql_query = "Select Previous_Date from TbL_SectionDetails Where Section='" +
                section + "' AND " + "Quaters='" + quater + "' AND Previous_Date IS NOT NULL";
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        Cursor cursor = mDatabase.rawQuery(sql_query, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Log.i(TAG, "Previous_Date" + cursor.getString(0));
            String s = cursor.getString(0);
            cursor.close();
            return s;
        }
        return null;
    }

    // count no. of rows sending to server
    public int countMaxSending(String section, String quater) {
        String sql_query = "Select count(*) as count from TblMonitoring_TLP Where Section='" +
                section + "' AND " + "Quaters='" + quater + "' AND server_status='pending'";
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        Cursor cursor = mDatabase.rawQuery(sql_query, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Log.i(TAG, "Previous_Date" + cursor.getInt(0));
            int s = cursor.getInt(0);
            cursor.close();
            return s;
        }
        return 0;
    }

    public void insertDataLoggingValue(String powerOnAC, String powerOffDC, String powerOnDC, String powerOffAC, String timeON, String timeOFF, String longi, String lati) {
        SQLiteDatabase mDatabase = this.getWritableDatabase();

        try {
            ContentValues mValues = new ContentValues();
            mDatabase.beginTransaction();
            mValues.put(DbTables.Datalogging.powerOffAc, powerOffAC);
            mValues.put(DbTables.Datalogging.powerOnAc, powerOnAC);
            mValues.put(DbTables.Datalogging.powerOffDc, powerOffDC);
            mValues.put(DbTables.Datalogging.powerOnDc, powerOnDC);
            mValues.put(DbTables.Datalogging.timeOff, timeOFF);
            mValues.put(DbTables.Datalogging.timeOn, timeON);
            mValues.put(DbTables.Datalogging.longi, longi);
            mValues.put(DbTables.Datalogging.lati, lati);
            mDatabase.insert(DbTables.TbL_DataLogging, null, mValues);
            mDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDatabase.endTransaction();
        }
    }

    ;

    public void updatePreviousDate(@NotNull String updatedDate, @NotNull String section, @NotNull String quarters) {
        SQLiteDatabase mDatabase = this.getWritableDatabase();
        ContentValues mVal = new ContentValues();
        mVal.put(DbTables.SectionDetails_Entry.previousDate, updatedDate);
        mDatabase.update(DbTables.TbL_SectionDetails, mVal,
                DbTables.SectionDetails_Entry.Section + "=?  AND  "
                        + DbTables.SectionDetails_Entry.Quaters + "=? ",
                new String[]{section, quarters});
    }

    public String getDateON(String dateOn) {
        String id = "";
        String sql_query = "select " + DbTables.Datalogging._ID + " from " + DbTables.TbL_DataLogging + " where TimeON=?";
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        Cursor cursor = mDatabase.rawQuery(sql_query, new String[]{dateOn});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getString(cursor.getColumnIndex(DbTables.Datalogging._ID));
        }
        return id;
    }

    public String getDateOFF(String dateOn) {
        String id = "";
        String sql_query = "select " + DbTables.Datalogging._ID + " from " + DbTables.TbL_DataLogging + " where TimeOFF=?";
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        Cursor cursor = mDatabase.rawQuery(sql_query, new String[]{dateOn});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getString(cursor.getColumnIndex(DbTables.Datalogging._ID));
        }
        return id;
    }

    public ArrayList<SynNowMode> syncRow() {
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        ArrayList<SynNowMode> mList = new ArrayList<>();
        /*Cursor cursor = mDatabase.query(DbTables.TbL_Mentoring_Tip, new String[]{
                        DbTables.Monnitoring_Tlp_Entry.S_DATE,
                        DbTables.Monnitoring_Tlp_Entry.Section,
                        DbTables.Monnitoring_Tlp_Entry.Quaters,
                }, DbTables.Monnitoring_Tlp_Entry.server_status + "=?", new String[]{"pending"},
                null, null, DbTables.Monnitoring_Tlp_Entry.S_DATE + " ASC");*/

        Cursor cursor = mDatabase.rawQuery("select distinct(S_DATE), Section,Quaters from TblMonitoring_TLP" +
                " where server_status='pending' order by S_DATE ASC", null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                mList.add(new SynNowMode(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
            cursor.close();
            return mList;
        } else return mList;
    }
}
