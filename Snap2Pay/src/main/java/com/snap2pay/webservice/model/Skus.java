package com.snap2pay.webservice.model;

/**
 * Created by sachin on 10/31/15.
 */
public class Skus {
    String upc;
    String pog;
    String osa;
    String facings;
    String priceLabel;

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getPog() {
        return pog;
    }

    public void setPog(String pog) {
        this.pog = pog;
    }

    public String getOsa() {
        return osa;
    }

    public void setOsa(String osa) {
        this.osa = osa;
    }

    public String getFacings() {
        return facings;
    }

    public void setFacings(String facings) {
        this.facings = facings;
    }

    public String getPriceLabel() {
        return priceLabel;
    }

    public void setPriceLabel(String priceLabel) {
        this.priceLabel = priceLabel;
    }
}
