package hr.foi.air.discountlocator.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air.core.NavigationItem;
import hr.foi.air.core.ReadyForDataListener;
import hr.foi.air.database.entities.Discount;
import hr.foi.air.database.entities.Store;
import hr.foi.air.discountlocator.R;
import hr.foi.air.discountlocator.adapters.ExpandableStoreItem;
import hr.foi.air.discountlocator.adapters.StoreRecyclerAdapter;

/**
 * Created by Zlatko on 1.11.2016..
 */

public class DiscountListFragment extends Fragment implements NavigationItem {
    private StoreRecyclerAdapter adapter;
    private int position;
    private String name = "List view";
    private ReadyForDataListener readyForDataListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.discount_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        return context.getResources().getDrawable(android.R.drawable.ic_menu_agenda);
    }

    @Override
    public void setReadyForDataListener(ReadyForDataListener readyForDataListener) {
        this.readyForDataListener = readyForDataListener;
    }

    @Override
    public void loadData(ArrayList<Store> stores, ArrayList<Discount> discounts) {
        List<ExpandableStoreItem> storeItemList = new ArrayList<ExpandableStoreItem>();

        if(stores != null) {
            for (Store store : stores) {
                storeItemList.add(new ExpandableStoreItem(store));
            }
            RecyclerView mRecycler = (RecyclerView) getView().findViewById(R.id.main_recycler);
            if(mRecycler != null) {
                adapter = new StoreRecyclerAdapter(getActivity(), storeItemList);
                mRecycler.setAdapter(adapter);
                mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

                // https://github.com/bignerdranch/expandable-recycler-view/blob/master/expandablerecyclerview/src/main/java/com/bignerdranch/expandablerecyclerview/Adapter/ExpandableRecyclerAdapter.java
                // store states and reload states
                adapter.expandParent(0);
            }
        }
    }
}
