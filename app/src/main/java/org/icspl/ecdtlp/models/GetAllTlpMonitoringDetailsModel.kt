package org.icspl.ecdtlp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetAllTlpMonitoringDetailsModel {

    @SerializedName("true")
    @Expose
    var `true`: List<True>? = null

    inner class DateofEntry {

        @SerializedName("date")
        @Expose
        var date: String? = null
        @SerializedName("timezone_type")
        @Expose
        var timezoneType: Int? = null
        @SerializedName("timezone")
        @Expose
        var timezone: String? = null

    }


    inner class True {

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
        var tSNo: String? = null
        @SerializedName("TS_Type")
        @Expose
        var tSType: String? = null

    }


}
