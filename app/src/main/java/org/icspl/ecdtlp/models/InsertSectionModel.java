package org.icspl.ecdtlp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertSectionModel {


    @SerializedName("true")
    @Expose
    private String _true;

    public String getTrue() {
        return _true;
    }

    public void setTrue(String _true) {
        this._true = _true;
    }

    @SerializedName("sts")
    @Expose
    public Integer sts;

    public Integer getSts() {
        return sts;
    }

}
