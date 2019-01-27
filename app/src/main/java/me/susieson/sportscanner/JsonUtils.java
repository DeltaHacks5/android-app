package me.susieson.sportscanner;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    static ResponseObject parseObjectJson(String json) {
        if (json == null) {
            return null;
        }

        ResponseObject responseObject = null;

        try {
            JSONObject object = new JSONObject(json);

            ArrayList<Group> groups = new ArrayList<>();
            JSONObject groupObject = object.getJSONObject("Group");

            Iterator groupIter = groupObject.keys();
            JSONArray groupsArray = new JSONArray();

            while (groupIter.hasNext()) {
                String key = (String) groupIter.next();
                groupsArray.put(groupObject.get(key));
            }
            for (int i = 0; i < groupsArray.length(); i++) {
                JSONObject groupObj = groupsArray.getJSONObject(i);
                String name = groupObj.getString("Name");
                int count = groupObj.getInt("Count");
                ArrayList<String> members = new ArrayList<>();
                JSONArray membersArray = groupObj.getJSONArray("Member");
                for (int j = 0; j < membersArray.length(); j++) {
                    String member = membersArray.getString(j);
                    members.add(member);
                }
                String time = groupObj.getString("Time");
                Group group = new Group(name, count, members, time);
                groups.add(group);
            }

            ArrayList<Park> parks = new ArrayList<>();
            JSONObject parkObject = object.getJSONObject("Park");
            Iterator parkIter = parkObject.keys();
            JSONArray parksArray = new JSONArray();

            while (parkIter.hasNext()) {
                String key = (String) parkIter.next();
                parksArray.put(parkObject.get(key));
            }
            for (int i = 0; i < parksArray.length(); i++) {
                JSONObject parkObj = parksArray.getJSONObject(i);
                String name = parkObj.getString("Name");
                Double latitude = parkObj.getDouble("Lat");
                Double longitude = parkObj.getDouble("Lon");
                Park park = new Park(name, latitude, longitude, groups);
                parks.add(park);
            }

            responseObject = new ResponseObject(parks);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error parsing JSON.");
        }

        return responseObject;
    }

}
