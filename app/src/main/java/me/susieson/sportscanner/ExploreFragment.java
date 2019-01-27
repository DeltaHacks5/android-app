package me.susieson.sportscanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ExploreFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowCloseListener {

    private GoogleMap mMap;

    private OnFragmentInteractionListener mListener;
    private FragmentManager mFragmentManager;
    private ConstraintLayout mBottomSheet;
    private BottomSheetBehavior mBottomSheetBehavior;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public ExploreFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentManager = getChildFragmentManager();
        SupportMapFragment supportMapFragment = new SupportMapFragment();
        mFragmentManager.beginTransaction().replace(R.id.map_fragment, supportMapFragment).commit();
        supportMapFragment.getMapAsync(this);
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBottomSheet = view.findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);

        mRecyclerView = mBottomSheet.findViewById(R.id.amenities_rv);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AmenityAdapter(getContext(), MainActivity.mResponseObject.getParks().get(0).getGroups());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (Park park : MainActivity.mResponseObject.getParks()) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(park.getLatitude(), park.getLongitude()))
                    .title(park.getName())
                    .icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.ic_location_48dp))));
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MainActivity.hamilton, MainActivity.zoomOut));
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowCloseListener(this);

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

    public void showSlidingLayout() {
        mListener.hideBottomNav();
        mBottomSheet.setVisibility(View.VISIBLE);
    }

    public void hideSlidingLayout() {
        mBottomSheet.setVisibility(View.GONE);
        mListener.showBottomNav();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        showSlidingLayout();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), MainActivity.zoomIn));
        return true;
    }

    @Override
    public void onInfoWindowClose(Marker marker) {
        hideSlidingLayout();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), MainActivity.zoomOut));
    }

    interface OnFragmentInteractionListener {
        void getLocation(GoogleMap map);

        void showBottomNav();

        void hideBottomNav();
    }

}
