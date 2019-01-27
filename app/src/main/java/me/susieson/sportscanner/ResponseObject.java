package me.susieson.sportscanner;

import java.util.ArrayList;

class ResponseObject {

    private ArrayList<Park> parks;

    ResponseObject(ArrayList<Park> parks) {
        this.parks = parks;
    }

    public ArrayList<Park> getParks() {
        return parks;
    }

    public void setPark(ArrayList<Park> parks) {
        this.parks = parks;
    }
}
