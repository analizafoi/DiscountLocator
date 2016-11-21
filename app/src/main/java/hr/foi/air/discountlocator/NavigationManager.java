package hr.foi.air.discountlocator;

import java.util.ArrayList;
import hr.foi.air.core.NavigationItem;

public class NavigationManager {

    private static NavigationManager instance;
    public ArrayList<NavigationItem> navigationItems;

    // private constructor
    private NavigationManager(){
        navigationItems = new ArrayList<NavigationItem>();
    }

    public static NavigationManager getInstance(){
        if(instance == null)
            instance = new NavigationManager();
        return instance;
    }

    public void addItem(NavigationItem newItem){
        newItem.setPosition(navigationItems.size());
        navigationItems.add(newItem);
    }
}
