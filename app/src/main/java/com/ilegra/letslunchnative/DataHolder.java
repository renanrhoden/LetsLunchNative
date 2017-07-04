package com.ilegra.letslunchnative;

import java.util.ArrayList;

public class DataHolder {

    private ArrayList<Restaurant> data;
    public ArrayList<Restaurant> getData() {return data;}

    public void setData(ArrayList<Restaurant> data) {
        this.data = data;
    }

    private static final DataHolder holder = new DataHolder();

    public static DataHolder getInstance() {
        return holder;
    }
}
