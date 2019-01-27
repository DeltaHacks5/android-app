package me.susieson.sportscanner;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements ExploreFragment.OnFragmentInteractionListener,
        ChatFragment.OnFragmentInteractionListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String EXPLORE_FRAGMENT_TAG = "explore-fragment";
    private static final String CHAT_FRAGMENT_TAG = "chat-fragment";
    private ExploreFragment mExploreFragment;
    private ChatFragment mChatFragment;
    private FusedLocationProviderClient mFusedLocationClient;
    private FragmentManager mFragmentManager;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sport Scanner");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);

        mBottomNavigationView = findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager();
        mExploreFragment = new ExploreFragment();
        mFragmentManager.beginTransaction().replace(R.id.main_container, mExploreFragment, EXPLORE_FRAGMENT_TAG).addToBackStack(null).commit();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void getLocation(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(hamilton, 10f);
            //mMap.animateCamera(cameraUpdate);
            requestLocationPermission(map);
        } else {
            mExploreFragment.getMap().setMyLocationEnabled(true);
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                LatLng currLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currLatLng, 10f);
                                mExploreFragment.getMap().animateCamera(cameraUpdate);
                            }
                        }
                    });
        }
    }

    private void requestLocationPermission(final GoogleMap map) {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle(getString(R.string.location_permission));
        Permissions.check(this, permissions, getString(R.string.location_permission_rationale), options,
                new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        getLocation(map);
                    }

                    @Override
                    public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                        Toast.makeText(context, getString(R.string.location_permission_denied), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_explore:
                Fragment exploreFragment = mFragmentManager.findFragmentByTag(EXPLORE_FRAGMENT_TAG);
                if (exploreFragment == null) {
                    exploreFragment = new ExploreFragment();
                }
                mFragmentManager.beginTransaction().replace(R.id.main_container, exploreFragment, EXPLORE_FRAGMENT_TAG).addToBackStack(null).commit();
                mExploreFragment = (ExploreFragment) exploreFragment;
                return true;
            case R.id.action_chat:
                Fragment chatFragment = mFragmentManager.findFragmentByTag(CHAT_FRAGMENT_TAG);
                if (chatFragment == null) {
                    chatFragment = new ChatFragment();
                }
                mFragmentManager.beginTransaction().replace(R.id.main_container, chatFragment, CHAT_FRAGMENT_TAG).addToBackStack(null).commit();
                mChatFragment = (ChatFragment) chatFragment;
                return true;
        }
        return false;
    }
}
