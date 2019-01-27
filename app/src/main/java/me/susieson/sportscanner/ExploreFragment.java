package me.susieson.sportscanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExploreFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    private LatLng hamilton = new LatLng(43.255722, -79.871101);
    private OnFragmentInteractionListener mListener;
    private FragmentManager mFragmentManager;

    public ExploreFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentManager = getChildFragmentManager();
        SupportMapFragment supportMapFragment = new SupportMapFragment();
        mFragmentManager.beginTransaction().replace(R.id.map_fragment, supportMapFragment).commit();
        supportMapFragment.getMapAsync(this);

        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        ArrayList<String> Addresses = new ArrayList<>();
//        ArrayList<String> Ratings = new ArrayList<>();
//        ArrayList<String> Names = new ArrayList<>();
//        ArrayList<String> IDs = new ArrayList<>();
//
//        RequestQueue requestQueue = (RequestQueue) Volley.newRequestQueue(this);
//
//        String JsonURL = "http://sportscanner.net:8080/";
//
//        JsonArrayRequest obreq = new JsonArrayRequest(Request.Method.GET, JsonURL, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        try {
//                            for(int i=0; i < response.length(); i++){
//                                JSONObject obj = response.getJSONObject(i);
//                                Names.add(obj.getString("placeName"));
//                                Addresses.add(obj.getString("placeAddress"));
//                                Ratings.add(obj.getString("placeRating"));
//                                IDs.add(obj.getString("_id"));
//                                String placeLatitude = obj.getString("placeLatitude");
//                                String placeLongitude = obj.getString("placeLongitude");
//                                mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(placeLatitude),Double.parseDouble(placeLongitude))).title(Names.get(Names.size() - 1)).snippet(Addresses.get(Addresses.size() - 1)));
//                            }
//                        }
//                        catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("Volley", error.toString());
//                    }
//                }
//
//        );

        mMap.addMarker(new MarkerOptions()
                .position(hamilton)
                .title("Hamilton")
                .icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.ic_location_48dp))));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hamilton, 10f));

        mListener.getLocation(googleMap);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public GoogleMap getMap() {
        return mMap;
    }

    interface OnFragmentInteractionListener {
        void getLocation(GoogleMap map);
    }


}
