package hr.foi.air.discountlocator.architecture.listeners;

import hr.foi.air.discountlocator.data.entities.Discount;

/**
 * Created by Ivan on 24.10.2016..
 */

public interface DiscountListener {
    void onDiscountLoaded(Discount discount);
}
