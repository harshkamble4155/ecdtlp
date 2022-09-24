package org.icspl.ecdtlp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserDetailModel {

    @SerializedName("userDetails")
    @Expose
    var userDetails: List<UserDetail>? = null

    @SerializedName("sections")
    @Expose
    var sections: List<Section>? = null

    @SerializedName("quarter")
    @Expose
    var quarter: List<Quarter>? = null

    @SerializedName("SectionDetails")
    @Expose
    var sectionDetails: List<SectionDetail>? = null

    @SerializedName("Monitoring_TLP")
    @Expose
    var monitoringTLP: List<MonitoringTLP>? = null

    @SerializedName("TrUnit")
    @Expose
    var trUnit: List<TrUnit>? = null


    inner class MonitoringTLP {

        @SerializedName("ClientID")
        @Expose
        var clientID: String? = null
        @SerializedName("ContractorID")
        @Expose
        var contractorID: String? = null
        @SerializedName("Section")
        @Expose
        var section: String? = null
        @SerializedName("Quaters")
        @Expose
        var quaters: String? = null
        @SerializedName("TS_No")
        @Expose
        var tsNo: String? = null
        @SerializedName("TS_Type")
        @Expose
        var tsType: String? = null
        @SerializedName("Type_Client")
        @Expose
        var typeClient: String? = null

    }

    inner class Quarter {

        @SerializedName("Quaters")
        @Expose
        var quaters: String? = null

    }


    inner class Section {

        @SerializedName("Section")
        @Expose
        var section: String? = null

    }

    inner class SectionDetail {

        @SerializedName("ClientID")
        @Expose
        var clientID: String? = null
        @SerializedName("ContractorID")
        @Expose
        var contractorID: String? = null
        @SerializedName("PipeType")
        @Expose
        var pipeType: String? = null
        @SerializedName("Section")
        @Expose
        var section: String? = null
        @SerializedName("Ip_Voltg")
        @Expose
        var ipVoltg: String? = null
        @SerializedName("Op_Voltg")
        @Expose
        var opVoltg: String? = null
        @SerializedName("Ext_Refvoltg")
        @Expose
        var extRefvoltg: String? = null
        @SerializedName("Op_Curnt")
        @Expose
        var opCurnt: String? = null
        @SerializedName("Intrnl_Refvoltg")
        @Expose
        var intrnlRefvoltg: String? = null
        @SerializedName("Ref_Sectr")
        @Expose
        var refSectr: String? = null
        @SerializedName("Coarse_Cntrl")
        @Expose
        var coarseCntrl: String? = null
        @SerializedName("Fine_Cntrl")
        @Expose
        var fineCntrl: String? = null
        @SerializedName("PSP_Reqrd")
        @Expose
        var pspReqrd: String? = null
        @SerializedName("Status")
        @Expose
        var status: String? = null
        @SerializedName("DateofEntry")
        @Expose
        var dateofEntry: String? = null
        @SerializedName("Quaters")
        @Expose
        var quaters: String? = null
        var server_status: String? = null

    }

    inner class TrUnit {

        @SerializedName("ClientID")
        @Expose
        var clientID: String? = null
        @SerializedName("ContractorID")
        @Expose
        var contractorID: String? = null
        @SerializedName("Section")
        @Expose
        var section: String? = null
        @SerializedName("TLP_TSNO")
        @Expose
        var tlptsNo: Double? = null
        @SerializedName("Type")
        @Expose
        var type: String? = null
        @SerializedName("TLP_LOC")
        @Expose
        var tlpLoc: String? = null
        @SerializedName("TLP_Chainage")
        @Expose
        var tlpChainage: Double? = null
        @SerializedName("Nallah_Crossing")
        @Expose
        var nallahCrossing: String? = null
        @SerializedName("Type_Client")
        @Expose
        var typeClient: String? = null
        @SerializedName("TsNo_Client")
        @Expose
        var tsNoClient: String? = null

    }


    inner class UserDetail {

        @SerializedName("Srno")
        @Expose
        var srno: Int? = null
        @SerializedName("DisplayName")
        @Expose
        var displayName: String? = null
        @SerializedName("UserName")
        @Expose
        var userName: String? = null
        @SerializedName("Password")
        @Expose
        var password: String? = null
        @SerializedName("UserType")
        @Expose
        var userType: String? = null
        @SerializedName("DateofRegister")
        @Expose
        var dateofRegister: String? = null
        @SerializedName("Status")
        @Expose
        var status: String? = null
        @SerializedName("ClientID")
        @Expose
        var clientID: String? = null

    }

}






    
