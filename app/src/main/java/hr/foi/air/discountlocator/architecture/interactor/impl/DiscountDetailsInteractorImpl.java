package hr.foi.air.discountlocator.architecture.interactor.impl;

import hr.foi.air.discountlocator.architecture.interactor.DiscountDetailsInteractor;
import hr.foi.air.discountlocator.architecture.listeners.DiscountListener;
import hr.foi.air.discountlocator.data.entities.Discount;

/**
 * Created by Ivan on 24.10.2016..
 */

public class DiscountDetailsInteractorImpl implements DiscountDetailsInteractor {
    @Override
    public void loadDiscountDetails(DiscountListener discountListener, int discountId) {
        discountListener.onDiscountLoaded(Discount.getDiscountById(discountId));
    }
}
