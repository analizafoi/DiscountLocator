package hr.foi.air.discountlocator.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air.core.DataLoadedListener;
import hr.foi.air.core.DataLoader;
import hr.foi.air.database.entities.Discount;
import hr.foi.air.database.entities.Store;
import hr.foi.air.discountlocator.R;
import hr.foi.air.discountlocator.adapters.ExpandableStoreItem;
import hr.foi.air.discountlocator.adapters.StoreRecyclerAdapter;
import hr.foi.air.discountlocator.loaders.DbDataLoader;
import hr.foi.air.discountlocator.loaders.WsDataLoader;

/**
 * Created by Zlatko on 1.11.2016..
 */

public class DiscountListFragment extends Fragment implements DataLoadedListener {
    private StoreRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.discount_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    public void loadData(){

        DataLoader dataLoader;
        if(Store.getAll().isEmpty()){
            System.out.println("Loading web data");
            dataLoader = new WsDataLoader();
        } else {
            System.out.println("Loading local data");
            dataLoader = new DbDataLoader();
        }
        dataLoader.loadData(this);
    }

    @Override
    public void onDataLoaded(ArrayList<Store> stores, ArrayList<Discount> discounts) {
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
