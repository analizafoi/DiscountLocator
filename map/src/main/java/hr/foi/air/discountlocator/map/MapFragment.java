package hr.foi.air.discountlocator.map;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    }
}
