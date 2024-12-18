package com.puce.retrofit;

public class User {
    String usuario;
    String record;
    String producto;
    String precio;
    String cantidad;

    public String getName() {return usuario;}

    public void setName(String usuario) {this.usuario = usuario;}

    public String getRecord() {return record;}

    public void setRecord(String record) {
        this.record = record;
    }

    public String getProduct() {
        return producto;
    }

    public void setProduct(String producto) {
        this.producto = producto;
    }

    public String getPrize() {
        return precio;
    }

    public void setPrize(String precio) {
        this.precio = precio;
    }

    public String getCant() {
        return cantidad;
    }

    public void setCant(String cantidad) {
        this.cantidad = cantidad;
    }

}
