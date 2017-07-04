package com.ilegra.letslunchnative;

import java.io.Serializable;

public class Restaurant implements Serializable{

    private String name;
    private String address;
    private String photoReference;

    public Restaurant(String name, String address, String photoReference) {
        this.name = name;
        this.address = address;
        this.photoReference = photoReference;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhotoReference() {
        return photoReference;
    }
}
