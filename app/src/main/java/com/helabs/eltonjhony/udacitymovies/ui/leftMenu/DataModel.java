package com.helabs.eltonjhony.udacitymovies.ui.leftMenu;

/**
 * Created by eltonjhony on 13/09/17.
 */
public class DataModel {

    private int icon;
    private String name;

    public DataModel(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }
}
