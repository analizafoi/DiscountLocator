package hr.foi.air.discountlocator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air.discountlocator.adapters.StoreAdapter;
import hr.foi.air.discountlocator.architecture.interactor.impl.StoreListInteractorImpl;
import hr.foi.air.discountlocator.architecture.presenter.StoreListPresenter;
import hr.foi.air.discountlocator.architecture.presenter.impl.StoreListPresenterImpl;
import hr.foi.air.discountlocator.architecture.view.StoreListView;
import hr.foi.air.discountlocator.data.entities.Store;
import hr.foi.air.discountlocator.helper.ExpandableStoreItem;


public class MainActivity extends AppCompatActivity implements StoreListView {
    private StoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlowManager.init(new FlowConfig.Builder(this).build());

        StoreListPresenter storeListPresenter = new StoreListPresenterImpl(this, new StoreListInteractorImpl());
        storeListPresenter.loadList();

    }

    @Override
    public void showStoreList(List<Store> stores) {
        List<ExpandableStoreItem> storeItemList = new ArrayList<ExpandableStoreItem>();
        if(stores != null) {
            for (Store store : stores) {
                storeItemList.add(new ExpandableStoreItem(store));
            }
            RecyclerView mRecycler = (RecyclerView) findViewById(R.id.main_recycler);
            if(mRecycler != null) {
                adapter = new StoreAdapter(this, storeItemList);
                mRecycler.setAdapter(adapter);
                mRecycler.setLayoutManager(new LinearLayoutManager(this));
                adapter.expandParent(0);
            }
        }
    }
}

