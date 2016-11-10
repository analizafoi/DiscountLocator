package hr.foi.air.discountlocator.architecture.interactor;

import hr.foi.air.discountlocator.architecture.listeners.DiscountListener;

/**
 * Created by Ivan on 18.10.2016..
 */

public interface DiscountDetailsInteractor  {
    void loadDiscountDetails(DiscountListener discountListener, int discountId);
}
