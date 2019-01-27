package me.susieson.sportscanner;

import java.util.ArrayList;

class Park {

    private String name;
    private double latitude;
    private double longitude;
    private ArrayList<Group> groups;

    Park(String name, double latitude, double longitude, ArrayList<Group> groups) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.groups = groups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }
}
