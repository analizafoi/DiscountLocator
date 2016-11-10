package hr.foi.air.discountlocator.architecture.listeners;

import java.util.List;

import hr.foi.air.discountlocator.data.entities.Store;

/**
 * Created by Ivan on 18.10.2016..
 */

public interface StoresListener {
    void onStoresAvailable(List<Store> stores);
}
