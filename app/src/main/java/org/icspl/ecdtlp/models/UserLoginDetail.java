
package org.icspl.ecdtlp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLoginDetail {

    @SerializedName("Srno")
    @Expose
    private Integer srno;
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("UserType")
    @Expose
    private String userType;
    @SerializedName("DateofRegister")
    @Expose
    private DateofRegister dateofRegister;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("client_id")
    @Expose
    private String clientId;

    public Integer getSrno() {
        return srno;
    }

    public void setSrno(Integer srno) {
        this.srno = srno;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public DateofRegister getDateofRegister() {
        return dateofRegister;
    }

    public void setDateofRegister(DateofRegister dateofRegister) {
        this.dateofRegister = dateofRegister;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

}
