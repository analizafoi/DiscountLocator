package hr.foi.air.discountlocator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.air.discountlocator.architecture.interactor.impl.DiscountDetailsInteractorImpl;
import hr.foi.air.discountlocator.architecture.presenter.DiscountDetailsPresenter;
import hr.foi.air.discountlocator.architecture.presenter.impl.DiscountDetailsPresenterImpl;
import hr.foi.air.discountlocator.architecture.view.DiscountDetailsView;

public class DiscountDetailsActivity extends AppCompatActivity implements DiscountDetailsView {

    @BindView(R.id.discount_details_name)
    TextView txtName;

    @BindView(R.id.discount_details_description)
    TextView txtDescription;

    @BindView(R.id.discount_details_start)
    TextView txtStartDate;

    @BindView(R.id.discount_details_end)
    TextView txtEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_details);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        int discountId = intent.getIntExtra("id", -1);

        DiscountDetailsPresenter discountDetailsPresenter = new DiscountDetailsPresenterImpl(this,
                new DiscountDetailsInteractorImpl());
        discountDetailsPresenter.loadDetails(discountId);
    }

    @Override
    public void showName(String name) {
        txtName.setText(name);
    }

    @Override
    public void showDescription(String details) {
        txtDescription.setText(details);
    }

    @Override
    public void showStartDate(String startDate) {
        txtStartDate.setText(startDate);
    }

    @Override
    public void showEndDate(String endDate) {
        txtEndDate.setText(endDate);
    }
}
