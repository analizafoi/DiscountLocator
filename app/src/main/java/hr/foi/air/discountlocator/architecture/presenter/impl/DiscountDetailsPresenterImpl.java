package hr.foi.air.discountlocator.architecture.presenter.impl;

import java.text.SimpleDateFormat;

import hr.foi.air.discountlocator.architecture.interactor.DiscountDetailsInteractor;
import hr.foi.air.discountlocator.architecture.listeners.DiscountListener;
import hr.foi.air.discountlocator.architecture.presenter.DiscountDetailsPresenter;
import hr.foi.air.discountlocator.architecture.view.DiscountDetailsView;
import hr.foi.air.discountlocator.data.entities.Discount;

/**
 * Created by Ivan on 18.10.2016..
 */

public class DiscountDetailsPresenterImpl implements DiscountDetailsPresenter, DiscountListener {

    private DiscountDetailsView mDiscountDetaisView;
    private DiscountDetailsInteractor mDiscountDetailsInteractor;
    private int discountId;

    public DiscountDetailsPresenterImpl(DiscountDetailsView discountDetailsView,
                                        DiscountDetailsInteractor discountDetailsInteractor){
        mDiscountDetaisView = discountDetailsView;
        mDiscountDetailsInteractor = discountDetailsInteractor;
    }

    @Override
    public void loadDetails(int discountId) {
        this.discountId = discountId;
        mDiscountDetailsInteractor.loadDiscountDetails(this, discountId);
    }

    @Override
    public void onDiscountLoaded(Discount discount) {
        mDiscountDetaisView.showDescription(discount.getDescription());
        mDiscountDetaisView.showName(discount.getName());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        mDiscountDetaisView.showStartDate(sdf.format(discount.getStartDate()));
        mDiscountDetaisView.showEndDate(sdf.format(discount.getEndDate()));
    }
}
