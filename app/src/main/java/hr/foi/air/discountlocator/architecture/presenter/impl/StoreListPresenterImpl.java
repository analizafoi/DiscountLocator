package hr.foi.air.discountlocator.architecture.presenter.impl;

import java.util.List;

import hr.foi.air.discountlocator.architecture.interactor.StoreListInteractor;
import hr.foi.air.discountlocator.architecture.listeners.StoresListener;
import hr.foi.air.discountlocator.architecture.presenter.StoreListPresenter;
import hr.foi.air.discountlocator.architecture.view.StoreListView;
import hr.foi.air.discountlocator.data.entities.Store;

/**
 * Created by Ivan on 18.10.2016..
 */

public class StoreListPresenterImpl implements StoreListPresenter, StoresListener {

    private StoreListView mStoreListView;
    private StoreListInteractor mStoreListInteractor;

    public StoreListPresenterImpl(StoreListView storeListView, StoreListInteractor storeListInteractor) {
        mStoreListView = storeListView;
        mStoreListInteractor = storeListInteractor;
    }

    @Override
    public void loadList() {
        mStoreListInteractor.loadStoreList(this);
    }

    @Override
    public void onStoresAvailable(List<Store> stores) {
        mStoreListView.showStoreList(stores);
    }
}
