package org.icspl.ecdtlp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllSQModel {

    @SerializedName("true")
    @Expose
    private List<SQTrue> _true = null;

    public List<SQTrue> getTrue() {
        return _true;
    }

    public void setTrue(List<SQTrue> _true) {
        this._true = _true;
    }

    @Override
    public String toString() {
        return "data \n"+ getTrue();
    }


    public class SQTrue {


        @SerializedName("ClientID")
        @Expose
        private String clientID;
        @SerializedName("ContractorID")
        @Expose
        private String contractorID;
        @SerializedName("PipeType")
        @Expose
        private String pipeType;
        @SerializedName("Section")
        @Expose
        private String section;
        @SerializedName("Ip_Voltg")
        @Expose
        private String ipVoltg;
        @SerializedName("Op_Voltg")
        @Expose
        private String opVoltg;
        @SerializedName("Ext_Refvoltg")
        @Expose
        private String extRefvoltg;
        @SerializedName("Op_Curnt")
        @Expose
        private String opCurnt;
        @SerializedName("Intrnl_Refvoltg")
        @Expose
        private String intrnlRefvoltg;
        @SerializedName("Ref_Sectr")
        @Expose
        private String refSectr;
        @SerializedName("Coarse_Cntrl")
        @Expose
        private String coarseCntrl;
        @SerializedName("Fine_Cntrl")
        @Expose
        private String fineCntrl;
        @SerializedName("PSP_Reqrd")
        @Expose
        private String pSPReqrd;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("DateofEntry")
        @Expose
        private String dateofEntry;
        @SerializedName("Quaters")
        @Expose
        private String quaters;

        public String getClientID() {
            return clientID;
        }

        public String getContractorID() {
            return contractorID;
        }


        public String getPipeType() {
            return pipeType;
        }

        public String getSection() {
            return section;
        }


        public String getIpVoltg() {
            return ipVoltg;
        }


        public String getOpVoltg() {
            return opVoltg;
        }


        public String getExtRefvoltg() {
            return extRefvoltg;
        }


        public String getOpCurnt() {
            return opCurnt;
        }


        public String getIntrnlRefvoltg() {
            return intrnlRefvoltg;
        }



        public String getRefSectr() {
            return refSectr;
        }


        public String getCoarseCntrl() {
            return coarseCntrl;
        }

        public String getFineCntrl() {
            return fineCntrl;
        }


        public String getPSPReqrd() {
            return pSPReqrd;
        }


        public String getStatus() {
            return status;
        }


        public String getDateofEntry() {
            return dateofEntry;
        }


        public String getQuaters() {
            return quaters;
        }


    }
}
