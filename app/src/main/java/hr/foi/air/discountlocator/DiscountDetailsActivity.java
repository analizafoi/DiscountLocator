package hr.foi.air.discountlocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.air.database.entities.Discount;

public class DiscountDetailsActivity extends AppCompatActivity {

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

        if(discountId != -1){
            Discount discount = Discount.getDiscountById(discountId);

            txtName.setText(discount.getName());
            txtDescription.setText(discount.getDescription());

            // import java.text.SimpleDateFormat;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            txtStartDate.setText(sdf.format(discount.getStartDate()));
            txtEndDate.setText(" -- " + sdf.format(discount.getEndDate()));
        }
    }
}
