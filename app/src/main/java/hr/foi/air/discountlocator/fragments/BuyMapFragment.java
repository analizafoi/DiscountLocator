package hr.foi.air.discountlocator.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hr.foi.air.core.ReadyForDataListener;
import hr.foi.air.discountlocator.R;
import hr.foi.air.core.NavigationItem;
import hr.foi.air.database.entities.Discount;
import hr.foi.air.database.entities.Store;

public class BuyMapFragment extends Fragment implements NavigationItem {
    int position = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.buy_map_fragment, container, false);
    }

    @Override
    public String getItemName() {
        return "Buy Map View";
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
        return context.getResources().getDrawable(android.R.drawable.ic_menu_more);
    }

    @Override
    public void setReadyForDataListener(ReadyForDataListener readyForDataListener) {
        //Nothing for this fragment
    }

    @Override
    public void loadData(ArrayList<Store> stores, ArrayList<Discount> discounts) {
        //Nothing for this fragment.
    }
}
