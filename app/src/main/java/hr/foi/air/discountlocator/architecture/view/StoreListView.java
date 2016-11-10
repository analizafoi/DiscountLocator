package hr.foi.air.discountlocator.architecture.view;

import java.util.List;

import hr.foi.air.discountlocator.data.entities.Store;

/**
 * Created by Ivan on 18.10.2016..
 */

public interface StoreListView extends BaseView {

    void showStoreList(List<Store> stores);


}
