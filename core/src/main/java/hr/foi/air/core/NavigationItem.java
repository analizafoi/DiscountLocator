package hr.foi.air.core;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import java.util.ArrayList;
import hr.foi.air.database.entities.Discount;
import hr.foi.air.database.entities.Store;

public interface NavigationItem {
    public String getItemName();
    public int getPosition();
    public void setPosition(int position);
    public Fragment getFragment();
    public Drawable getIcon(Context context);
    public void setReadyForDataListener(ReadyForDataListener readyForDataListener);
    public void loadData(ArrayList<Store> stores, ArrayList<Discount> discounts);
};
