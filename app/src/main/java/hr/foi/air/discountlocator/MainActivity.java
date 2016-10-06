package hr.foi.air.discountlocator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.database.entities.Discount;
import hr.foi.air.database.entities.Discount_Table;
import hr.foi.air.database.entities.Store;
import hr.foi.air.discountlocator.helper.MockData;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.discount_list)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        FlowManager.init(new FlowConfig.Builder(this).build());

    }

    @OnClick(R.id.test_button)
    public void buttonClicked(View view){
        if(SQLite.select().from(Store.class).queryList().isEmpty()){
            MockData.writeAll();
        }

        final List<Discount> discounts = SQLite.select().from(Discount.class).where(Discount_Table.discount.greaterThan(5)).queryList();
        String[] listItems = new String[discounts.size()];
        for(int i = 0; i < discounts.size(); i++){
            listItems[i] = discounts.get(i).getName();
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);
    }
}
