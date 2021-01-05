package com.courier.courierapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PDFGenerationResponse {

    @SerializedName("data")
    List<PDFGenerationDTO> pdfGenerationResponse;

    public PDFGenerationResponse(List<PDFGenerationDTO> pdfGenerationResponse) {
        this.pdfGenerationResponse = pdfGenerationResponse;
    }

    public List<PDFGenerationDTO> getPdfGenerationResponse() {
        return pdfGenerationResponse;
    }

    public void setPdfGenerationResponse(List<PDFGenerationDTO> pdfGenerationResponse) {
        this.pdfGenerationResponse = pdfGenerationResponse;
    }
}
