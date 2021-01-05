package com.courier.courierapp.model;

import com.google.gson.annotations.SerializedName;

public class PDFGenerationDTO {

    @SerializedName("Username")
    private String pdfUserName;
    @SerializedName("Useremail")
    private String pdfUserEmail;
    @SerializedName("Usermobileno")
    private String pdfUserMobile;
    @SerializedName("ServiceName")
    private String pdfServiceName;
    @SerializedName("ServiceItem")
    private String pdfServiceItem;
    @SerializedName("ServiceWeight")
    private String pdfServiceWeight;
    @SerializedName("ServiceDeliverydate")
    private String pdfServiceDeliveryDate;
    @SerializedName("ServiceAmountType")
    private String pdfServiceAmtType;
    @SerializedName("ReceiverCompanyName")
    private String pdfReceiverCompany;
    @SerializedName("ReceiverAddress")
    private String pdfReceiverAddress;
    @SerializedName("Receiveremail")
    private String pdfReceiverEmail;
    @SerializedName("ShippersCompanyName")
    private String pdfShipperCompanyName;
    @SerializedName("ShippersAddress")
    private String pdfShipperAddress;
    @SerializedName("ShipperscontactName")
    private String pdfShipperContactName;

    @SerializedName("ShipperscontactNumber")
    private String pdfShipperContactNumber;
    @SerializedName("Shippersemail")
    private String pdfShipperEmail;
    @SerializedName("trackno")
    private String pdfTrackNumber;
    @SerializedName("price")
    private int pdfPrice;
    @SerializedName("TransactionStatus")
    private String pdfTransactionStatus;


     /*@SerializedName("")
    private String pdfShipperLat;
    @SerializedName("")
    private String pdfShipperLongi;

    @SerializedName("")
    private String pdfReceiverLat;
    @SerializedName("")
    private String pdfReceiverLongi;


    public String getPdfShipperLat() {
        return pdfShipperLat;
    }

    public void setPdfShipperLat(String pdfShipperLat) {
        this.pdfShipperLat = pdfShipperLat;
    }

    public String getPdfShipperLongi() {
        return pdfShipperLongi;
    }

    public void setPdfShipperLongi(String pdfShipperLongi) {
        this.pdfShipperLongi = pdfShipperLongi;
    }

    public String getPdfReceiverLat() {
        return pdfReceiverLat;
    }

    public void setPdfReceiverLat(String pdfReceiverLat) {
        this.pdfReceiverLat = pdfReceiverLat;
    }

    public String getPdfReceiverLongi() {
        return pdfReceiverLongi;
    }

    public void setPdfReceiverLongi(String pdfReceiverLongi) {
        this.pdfReceiverLongi = pdfReceiverLongi;
    }
*/
    public String getPdfUserName() {
        return pdfUserName;
    }

    public void setPdfUserName(String pdfUserName) {
        this.pdfUserName = pdfUserName;
    }

    public String getPdfUserEmail() {
        return pdfUserEmail;
    }

    public void setPdfUserEmail(String pdfUserEmail) {
        this.pdfUserEmail = pdfUserEmail;
    }

    public String getPdfUserMobile() {
        return pdfUserMobile;
    }

    public void setPdfUserMobile(String pdfUserMobile) {
        this.pdfUserMobile = pdfUserMobile;
    }

    public String getPdfServiceName() {
        return pdfServiceName;
    }

    public void setPdfServiceName(String pdfServiceName) {
        this.pdfServiceName = pdfServiceName;
    }

    public String getPdfServiceItem() {
        return pdfServiceItem;
    }

    public void setPdfServiceItem(String pdfServiceItem) {
        this.pdfServiceItem = pdfServiceItem;
    }

    public String getPdfServiceWeight() {
        return pdfServiceWeight;
    }

    public void setPdfServiceWeight(String pdfServiceWeight) {
        this.pdfServiceWeight = pdfServiceWeight;
    }

    public String getPdfServiceDeliveryDate() {
        return pdfServiceDeliveryDate;
    }

    public void setPdfServiceDeliveryDate(String pdfServiceDeliveryDate) {
        this.pdfServiceDeliveryDate = pdfServiceDeliveryDate;
    }

    public String getPdfServiceAmtType() {
        return pdfServiceAmtType;
    }

    public void setPdfServiceAmtType(String pdfServiceAmtType) {
        this.pdfServiceAmtType = pdfServiceAmtType;
    }

    public String getPdfReceiverCompany() {
        return pdfReceiverCompany;
    }

    public void setPdfReceiverCompany(String pdfReceiverCompany) {
        this.pdfReceiverCompany = pdfReceiverCompany;
    }

    public String getPdfReceiverAddress() {
        return pdfReceiverAddress;
    }

    public void setPdfReceiverAddress(String pdfReceiverAddress) {
        this.pdfReceiverAddress = pdfReceiverAddress;
    }

    public String getPdfReceiverEmail() {
        return pdfReceiverEmail;
    }

    public void setPdfReceiverEmail(String pdfReceiverEmail) {
        this.pdfReceiverEmail = pdfReceiverEmail;
    }

    public String getPdfShipperCompanyName() {
        return pdfShipperCompanyName;
    }

    public void setPdfShipperCompanyName(String pdfShipperCompanyName) {
        this.pdfShipperCompanyName = pdfShipperCompanyName;
    }

    public String getPdfShipperAddress() {
        return pdfShipperAddress;
    }

    public void setPdfShipperAddress(String pdfShipperAddress) {
        this.pdfShipperAddress = pdfShipperAddress;
    }

    public String getPdfShipperContactName() {
        return pdfShipperContactName;
    }

    public void setPdfShipperContactName(String pdfShipperContactName) {
        this.pdfShipperContactName = pdfShipperContactName;
    }

    public String getPdfShipperContactNumber() {
        return pdfShipperContactNumber;
    }

    public void setPdfShipperContactNumber(String pdfShipperContactNumber) {
        this.pdfShipperContactNumber = pdfShipperContactNumber;
    }

    public String getPdfShipperEmail() {
        return pdfShipperEmail;
    }

    public void setPdfShipperEmail(String pdfShipperEmail) {
        this.pdfShipperEmail = pdfShipperEmail;
    }

    public String getPdfTrackNumber() {
        return pdfTrackNumber;
    }

    public void setPdfTrackNumber(String pdfTrackNumber) {
        this.pdfTrackNumber = pdfTrackNumber;
    }

    public int getPdfPrice() {
        return pdfPrice;
    }

    public void setPdfPrice(int pdfPrice) {
        this.pdfPrice = pdfPrice;
    }

    public String getPdfTransactionStatus() {
        return pdfTransactionStatus;
    }

    public void setPdfTransactionStatus(String pdfTransactionStatus) {
        this.pdfTransactionStatus = pdfTransactionStatus;
    }
}
