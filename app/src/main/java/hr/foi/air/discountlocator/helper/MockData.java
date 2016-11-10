package hr.foi.air.discountlocator.helper;

import hr.foi.air.discountlocator.data.entities.Discount;
import hr.foi.air.discountlocator.data.entities.Store;

/**
 * Created by Ivan on 6.10.2016..
 */

public class MockData {

    public static void writeAll() {
        Store acmeStore = new Store();
        acmeStore.setName("ACME Store");
        acmeStore.save();

        Discount bananas = new Discount();
        bananas.setName("Bananas off!");
        bananas.setDiscount(5);
        bananas.setStore(acmeStore);
        bananas.save();

        Discount tuna = new Discount();
        tuna.setName("Three for two!");
        tuna.setDiscount(33);
        tuna.setStore(acmeStore);
        tuna.save();
    }

}
