package org.icspl.ecdtlp.DbHelper;

import android.provider.BaseColumns;

public class DbTables {

    // DB Tables
    public static final String TbL_UserList = "TbL_UserList";
    public static final String TbL_Section = "TbL_Section";
    public static final String TbL_Mentoring_Tip = "TblMonitoring_TLP";
    public static final String TbL_Quarters = "TbL_Quarters";
    public static final String TbL_SectionDetails = "TbL_SectionDetails";
    public static final String TbL_TRUnit = "TbL_TRUnit";
    public static final String TbL_DataLogging = "TbL_DataLogging";
    public static final String Tbl_Vehicle_details = "Tbl_Vehicle_details ";

    public static class Datalogging implements BaseColumns {

        public static final String _ID = BaseColumns._ID;

        public static final String powerOnAc = "PowerON_AC";
        public static final String powerOffAc = "PowerOFF_AC";
        public static final String powerOnDc = "PowerON_DC";
        public static final String powerOffDc = "PowerOFF_DC";
        public static final String timeOn = "TimeON";
        public static final String timeOff = "TimeOFF";
        public static final String longi = "Longitude";
        public static final String lati = "Latitude";
    }

    public static class Monnitoring_Tlp_Entry implements BaseColumns {

        public static final String _ID = BaseColumns._ID;

        public static final String ClientID = "ClientID";
        public static final String ContractorID = "ContractorID";
        public static final String Section = "Section";
        public static final String PIPEType = "PIPEType";
        public static final String TS_No = "TS_No";
        public static final String TS_Type = "TS_Type";
        public static final String TS_Km = "TS_Km";
        public static final String TS_Location = "TS_Location";
        public static final String PSP_Min = "PSP_Min";
        public static final String PSP_Max = "PSP_Max";
        public static final String Casing_Min = "Casing_Min";
        public static final String Casing_Max = "Casing_Max";
        public static final String ZINC_Value = "ZINC_Value";
        public static final String AC_PSP = "AC_PSP";
        public static final String S_DATE = "S_DATE";
        public static final String S_Time = "S_Time";
        public static final String NCordinate = "NCordinate";
        public static final String ECordinate = "ECordinate";
        public static final String Remark = "Remark";
        public static final String Status = "Status";
        public static final String DateofEntry = "DateofEntry";
        public static final String Quaters = "Quaters";
        public static final String ManualRemarks = "ManualRemarks";
        public static final String Valve = "Valve";
        public static final String Potential_1 = "Potential_1";
        public static final String Potential_2 = "Potential_2";
        public static final String TR_Location = "TR_Location";
        public static final String RejectDate = "RejectDate";
        public static final String Potential_3 = "Potential_3";
        public static final String Potential_4 = "Potential_4";
        public static final String RCPortable = "RCPortable"; // type_client columns in server
        public static final String anodeCurrent = "anodeCurrent";
        public static final String Driver_Name = "driver_name";
        public static final String Driver_Mob_Number = "driver_mob_number";
        public static final String Vehicle_Number = "vehicle_number";
        public static final String Start_Km = "start_km";
        public static final String End_Km = "end_km";
        public static final String Vehicle_No_File = "Vehicle_No_File";
        public static final String Start_Km_File = "start_km_file";
        public static final String End_Km_file = "end_km_file";
        public static final String TLPFile = "TLPFile";
        public static final String Reading_File = "Reading_File";
        public static final String Selfie_File = "Selfie_File";
        public static final String tsno_client = "tsno_client";
        public static final String type_client = "type_client";
        public static final String filled_date = "filled_date";
        public static final String server_status = "server_status";
    }

    public static class UserList_Entry implements BaseColumns {
        public static final String _ID = BaseColumns._ID;

        public static final String DisplayName = "DisplayName";
        public static final String UserName = "UserName";
        public static final String Password = "Password";
        public static final String UserType = "UserType";
        public static final String Status = "Status";
        public static final String client_id = "client_id";
    }

    public static class Section_Entry implements BaseColumns {
        public static final String _ID = BaseColumns._ID;

        public static final String Section = "Section";
    }

    public static class Quarters_Entry implements BaseColumns {
        public static final String _ID = BaseColumns._ID;
        public static final String Quarters = "Quarters";

    }

    public static class SectionDetails_Entry implements BaseColumns {
        public static final String _ID = BaseColumns._ID;

        public static final String ClientID = "ClientID";
        public static final String ContractorID = "ContractorID";
        public static final String PipeType = "PipeType";
        public static final String Section = "Section";
        public static final String Ip_Voltg = "Ip_Voltg";
        public static final String Op_Voltg = "Op_Voltg";
        public static final String Ext_Refvoltg = "Ext_Refvoltg";
        public static final String Op_Curnt = "Op_Curnt";
        public static final String Intrnl_Refvoltg = "Intrnl_Refvoltg";
        public static final String Ref_Sectr = "Ref_Sectr";
        public static final String Coarse_Cntrl = "Coarse_Cntrl";
        public static final String Fine_Cntrl = "Fine_Cntrl";
        public static final String PSP_Reqrd = "PSP_Reqrd";
        public static final String Status = "Status";
        public static final String DateofEntry = "DateofEntry";
        public static final String Quaters = "Quaters";
        public static final String previousDate = "Previous_Date";
        public static final String Server_Status = "Server_Status";
    }

    public static class TRUnit_Entry implements BaseColumns {
        public static final String _ID = BaseColumns._ID;

        public static final String ClientID = "ClientID";
        public static final String ContractorID = "ContractorID";
        public static final String Section = "Section";
        public static final String PipeType = "PipeType";
        public static final String TR_Chainage = "TR_Chainage";
        public static final String TR_Loc = "TR_Loc";
        public static final String TR_Add = "TR_Add";
        public static final String TR_Make = "TR_Make";
        public static final String TR_Supplier = "TR_Supplier";
        public static final String TR_SupplierNo = "TR_SupplierNo";
        public static final String TR_SupplierEmail = "TR_SupplierEmail";
        public static final String TR_RefCalNo = "TR_RefCalNo";
        public static final String TR_RefCalMake = "TR_RefCalMake";
        public static final String TR_RefCalType = "TR_RefCalType";
        public static final String TR_RangeVltg = "TR_RangeVltg";
        public static final String TR_RangeAmpx = "TR_RangeAmpx";
        public static final String TR_Lat = "TR_Lat";
        public static final String TR_Long = "TR_Long";
        public static final String CorCoupon_Loc = "CorCoupon_Loc";
        public static final String Coupan_SrNo = "Coupan_SrNo";
        public static final String Thikness = "Thikness";
        public static final String OD = "OD";
        public static final String IDs = "IDs";
        public static final String Surface_Area = "Surface_Area";
        public static final String Weight_Initial = "Weight_Initial";
        public static final String DiodeStn_Chainage = "DiodeStn_Chainage";
        public static final String DiodeStn_Loc = "DiodeStn_Loc";
        public static final String DiodeStn_Add = "DiodeStn_Add";
        public static final String DiodeStn_Make = "DiodeStn_Make";
        public static final String DiodeStn_Supplier = "DiodeStn_Supplier";
        public static final String DiodeStn_SupplierNo = "DiodeStn_SupplierNo";
        public static final String DiodeStn_SupplierEmail = "DiodeStn_SupplierEmail";
        public static final String DiodeStn_RefCalNo = "DiodeStn_RefCalNo";
        public static final String DiodeStn_RefCalMake = "DiodeStn_RefCalMake";
        public static final String DiodeStn_RefCalType = "DiodeStn_RefCalType";
        public static final String DiodeStn_RangeVltg = "DiodeStn_RangeVltg";
        public static final String DiodeStn_RangeAmpx = "DiodeStn_RangeAmpx";
        public static final String DiodeStn_Lat = "DiodeStn_Lat";
        public static final String DiodeStn_Long = "DiodeStn_Long";
        public static final String AJB_Chainage = "AJB_Chainage          ";
        public static final String AJB_Loc = "AJB_Loc";
        public static final String AJB_Add = "AJB_Add";
        public static final String AJB_Make = "AJB_Make";
        public static final String AJB_Supplier = "AJB_Supplier";
        public static final String AJB_SupplierNo = "AJB_SupplierNo";
        public static final String AJB_SupplierEmail = "AJB_SupplierEmail";
        public static final String AJB_AnodeNo = "AJB_AnodeNo";
        public static final String AJB_AnodeRtng = "AJB_AnodeRtng";
        public static final String AJB_ShuntNo = "AJB_ShuntNo";
        public static final String AJB_ShuntRtng = "AJB_ShuntRtng";
        public static final String AJB_RegiNo = "AJB_RegiNo";
        public static final String AJB_RegiRtng = "AJB_RegiRtng";
        public static final String AJB_Lat = "AJB_Lat";
        public static final String AJB_Long = "AJB_Long";
        public static final String CJB_Chainage = "CJB_Chainage";
        public static final String CJB_Loc = "CJB_Loc";
        public static final String CJB_Add = "CJB_Add";
        public static final String CJB_Make = "CJB_Make";
        public static final String CJB_Supplier = "CJB_Supplier";
        public static final String CJB_SupplierNo = "CJB_SupplierNo";
        public static final String CJB_SupplierEmail = "CJB_SupplierEmail";
        public static final String CJB_RefCalNo = "CJB_RefCalNo";
        public static final String CJB_RefCalMake = "CJB_RefCalMake";
        public static final String CJB_RefCalType = "CJB_RefCalType";
        public static final String CJB_Lat = "CJB_Lat";
        public static final String CJB_Long = "CJB_Long";
        public static final String TLP_TSNo = "TLP_TSNo";
        public static final String Type = "Type";
        public static final String TLP_Chainage = "TLP_Chainage";
        public static final String TLP_Loc = "TLP_Loc";
        public static final String TLP_Add = "TLP_Add";
        public static final String TLP_Make = "TLP_Make";
        public static final String TLP_Supplier = "TLP_Supplier";
        public static final String TLP_SupplierNo = "TLP_SupplierNo";
        public static final String TLP_SupplierEmail = "TLP_SupplierEmail";
        public static final String TLP_Lat = "TLP_Lat";
        public static final String TLP_Long = "TLP_Long";
        public static final String Status = "Status";
        public static final String DateofEntry = "DateofEntry";
        public static final String SUP_POL_CELL = "SUP_POL_CELL";
        public static final String MAKE_OF_POL_CELL = "MAKE_OF_POL_CELL";
        public static final String MODEL_TLP = "MODEL_TLP";
        public static final String SIZE_TLP = "SIZE_TLP";
        public static final String DRAWING_TLP = "DRAWING_TLP";
        public static final String F85 = "F85";
        public static final String DoneStatus = "DoneStatus";
        public static final String DoneDate = "DoneDate";
        public static final String AJB_ShuntRtng_Amp = "AJB_ShuntRtng_Amp";
        public static final String AJB_No = "AJB_No";
        public static final String CJB_NO = "CJB_NO"; // type_client columns in server
        public static final String TR_NO = "TR_NO";
        public static final String UploadFile = "UploadFile";
        public static final String Nallah_Crossing = "Nallah_Crossing";
        public static final String Type_Client = "Type_Client";
        public static final String TsNo_Client = "TsNo_Client";

    }


    public static class Vehicle_details implements BaseColumns
    {
        public static final String _ID = BaseColumns._ID;
        public static final String Vehicle_No = "Vechicle_No";
        public static final String Driver_Name = "Driver_Name";
        public static final String Mobile_Number = "Mobile_Number";
        public static final String Start_Km = "Start_Km";
        public static final String End_Km = "End_Km";

    }


}
