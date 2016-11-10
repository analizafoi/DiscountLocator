package hr.foi.air.discountlocator.architecture.view;

/**
 * Created by Ivan on 18.10.2016..
 */

public interface DiscountDetailsView extends BaseView{
    void showName(String name);
    void showDescription(String details);
    void showStartDate(String startDate);
    void showEndDate(String endDate);
}
