package hr.foi.air.discountlocator;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import java.util.ArrayList;
import hr.foi.air.core.NavigationItem;

public class NavigationManager {

    private static NavigationManager instance;
    public ArrayList<NavigationItem> navigationItems;
    private Activity mHandlerActivity;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private int mItemGroupId;

    // private constructor
    private NavigationManager(){
        navigationItems = new ArrayList<NavigationItem>();
    }

    public static NavigationManager getInstance(){
        if(instance == null)
            instance = new NavigationManager();
        return instance;
    }

    public void setDependencies(Activity handlerActivity, DrawerLayout drawerLayout, NavigationView navigationView, int itemGroupId){
        this.mHandlerActivity = handlerActivity;
        this.mNavigationView = navigationView;
        this.mDrawerLayout = drawerLayout;
        this.mItemGroupId = itemGroupId;
    }

    public void addItem(NavigationItem newItem){
        newItem.setPosition(navigationItems.size());
        navigationItems.add(newItem);
        mNavigationView.getMenu().add(mItemGroupId, newItem.getPosition(), newItem.getPosition() + 1, newItem.getItemName())
                .setIcon(newItem.getIcon(mHandlerActivity));
    }
}
