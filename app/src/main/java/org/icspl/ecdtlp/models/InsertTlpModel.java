package org.icspl.ecdtlp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertTlpModel {

    @SerializedName("true")
    @Expose
    private String _true;

    public String getTrue() {
        return _true;
    }

    public void setTrue(String _true) {
        this._true = _true;
    }

}
