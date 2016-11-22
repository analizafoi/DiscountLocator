package hr.foi.air.discountlocator;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

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

        /*
        //Analizirajte ponašanje aplikacije ako se prijava na click događaj napravi ovdje
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectNavigationItem(menuItem);
                return false;
            }
        });
        */
    }

    public void selectNavigationItem(MenuItem menuItem){
        if (!menuItem.isChecked()) {
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawer(GravityCompat.START, true);

            // uses the menu item to find the NavigationItem (interface implementator)
            NavigationItem clickedItem = navigationItems.get(menuItem.getItemId());

            FragmentManager fragmentManager = mHandlerActivity.getFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, clickedItem.getFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
    }

    public void addItem(NavigationItem newItem){
        newItem.setPosition(navigationItems.size());
        navigationItems.add(newItem);
        mNavigationView.getMenu().add(mItemGroupId, newItem.getPosition(), newItem.getPosition() + 1, newItem.getItemName())
                .setIcon(newItem.getIcon(mHandlerActivity))
                .setCheckable(true);
    }
}
