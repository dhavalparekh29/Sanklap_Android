package com.universalapp.sankalp.learningapp.model.membershipPack;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MembershipSubmitResponse {

    @SerializedName("MID")
    @Expose
    private String mID;
    @SerializedName("ORDER_ID")
    @Expose
    private String oRDERID;
    @SerializedName("CUST_ID")
    @Expose
    private String cUSTID;
    @SerializedName("INDUSTRY_TYPE_ID")
    @Expose
    private String iNDUSTRYTYPEID;
    @SerializedName("CHANNEL_ID")
    @Expose
    private String cHANNELID;
    @SerializedName("TXN_AMOUNT")
    @Expose
    private String tXNAMOUNT;
    @SerializedName("WEBSITE")
    @Expose
    private String wEBSITE;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("checksumhash")
    @Expose
    private String checksumhash;
    @SerializedName("CALLBACK_URL")
    @Expose
    private String callbackUrl;

    public String getMID() {
        return mID;
    }

    public void setMID(String mID) {
        this.mID = mID;
    }

    public String getORDERID() {
        return oRDERID;
    }

    public void setORDERID(String oRDERID) {
        this.oRDERID = oRDERID;
    }

    public String getCUSTID() {
        return cUSTID;
    }

    public void setCUSTID(String cUSTID) {
        this.cUSTID = cUSTID;
    }

    public String getINDUSTRYTYPEID() {
        return iNDUSTRYTYPEID;
    }

    public void setINDUSTRYTYPEID(String iNDUSTRYTYPEID) {
        this.iNDUSTRYTYPEID = iNDUSTRYTYPEID;
    }

    public String getCHANNELID() {
        return cHANNELID;
    }

    public void setCHANNELID(String cHANNELID) {
        this.cHANNELID = cHANNELID;
    }

    public String getTXNAMOUNT() {
        return tXNAMOUNT;
    }

    public void setTXNAMOUNT(String tXNAMOUNT) {
        this.tXNAMOUNT = tXNAMOUNT;
    }

    public String getWEBSITE() {
        return wEBSITE;
    }

    public void setWEBSITE(String wEBSITE) {
        this.wEBSITE = wEBSITE;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getChecksumhash() {
        return checksumhash;
    }

    public void setChecksumhash(String checksumhash) {
        this.checksumhash = checksumhash;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
