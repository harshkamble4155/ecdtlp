package org.icspl.ecdtlp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetAllTblTrUnit {

    @SerializedName("true")
    @Expose
    var `true`: List<TblTrUnit_True>? = null

    inner class TblTrUnit_True {


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
        var tlptsNo: Int? = null
        @SerializedName("Type")
        @Expose
        var type: String? = null
        @SerializedName("TLP_LOC")
        @Expose
        var tlpLoc: String? = null
        @SerializedName("TLP_Chainage")
        @Expose
        var tlpChainage: Int? = null
        @SerializedName("Nallah_Crossing")
        @Expose
        var nallah_Crossing: Int? = null
        @SerializedName("Type_Client")
        @Expose
        var type_client: String? = null

    }

}
