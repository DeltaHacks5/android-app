package me.susieson.sportscanner;

import java.util.ArrayList;

class Group {
    private String name;
    private int count;
    private ArrayList<String> members;
    private String time;
    private boolean registered;

    Group(String name, int count, ArrayList<String> members, String time) {
        this.name = name;
        this.count = count;
        this.members = members;
        this.time = time;
        registered = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
