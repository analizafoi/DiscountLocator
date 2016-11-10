package hr.foi.air.discountlocator.architecture.interactor;

import hr.foi.air.discountlocator.architecture.listeners.StoresListener;

/**
 * Created by Ivan on 18.10.2016..
 */

public interface StoreListInteractor {
    void loadStoreList(StoresListener storeListener);
}
