
package org.icspl.ecdtlp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetQuartersModel {

    @SerializedName("true")
    @Expose
    private List<QuartersTrue> _true = null;

    public List<QuartersTrue> getTrue() {
        return _true;
    }

    public void setTrue(List<QuartersTrue> _true) {
        this._true = _true;
    }


    public class QuartersTrue {

        @SerializedName("Quaters")
        @Expose
        private String quaters;

        public String getQuaters() {
            return quaters;
        }


    }


}
