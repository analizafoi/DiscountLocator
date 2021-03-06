package hr.foi.air.discountlocator.map;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import hr.foi.air.core.NavigationItem;
import hr.foi.air.core.ReadyForDataListener;
import hr.foi.air.database.entities.Discount;
import hr.foi.air.database.entities.Store;

public class MapFragment extends Fragment implements NavigationItem, OnMapReadyCallback {

    private int position;
    private String name = "Map view";
    private ReadyForDataListener readyForDataListener;
    private com.google.android.gms.maps.MapFragment mapFragment;
    private GoogleMap map = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_fragment, container, false);
        mapFragment = new com.google.android.gms.maps.MapFragment();
        getFragmentManager().beginTransaction().add(R.id.frame, mapFragment).commit();
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (!(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            map.setMyLocationEnabled(true);
        }
        else
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        if (readyForDataListener != null)
            readyForDataListener.onReadyForData(this);
    }

    @Override
    public String getItemName() {
        return name;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public Drawable getIcon(Context context) {
        return context.getResources().getDrawable(android.R.drawable.ic_menu_mylocation);
    }

    @Override
    public void setReadyForDataListener(ReadyForDataListener readyForDataListener) {
        this.readyForDataListener = readyForDataListener;
    }

    @Override
    public void loadData(ArrayList<Store> stores, ArrayList<Discount> discounts) {

        if (map != null)
        {
            LatLng position = null;
            for (Store s : stores)
            {
                position = new LatLng(
                        (double)s.getLatitude() / 1000000,
                        (double)s.getLongitude() / 1000000);
                map.addMarker(new MarkerOptions()
                    .position(position)
                    .title(s.getName()));
            }
            if (position != null) {
                map.moveCamera(CameraUpdateFactory.newLatLng(position));
                map.moveCamera(CameraUpdateFactory.zoomTo(12));
            }
        }
    }
}
