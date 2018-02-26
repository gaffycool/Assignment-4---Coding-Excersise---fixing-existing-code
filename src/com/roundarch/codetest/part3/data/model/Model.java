package com.roundarch.codetest.part3.data.model;

import java.util.ArrayList;

/**
 * Created by TAE on 25-Feb-18.
 */

public class Model {

    public ArrayList<Location> getMyLocations() {
        return result;
    }

    public void setMyLocations(ArrayList<Location> locations) {
        this.result = locations;
    }

    ArrayList<Location> result;
}
