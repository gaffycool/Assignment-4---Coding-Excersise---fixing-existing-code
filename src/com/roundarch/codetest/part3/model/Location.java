package com.roundarch.codetest.part3.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TAE on 25-Feb-18.
 */

class Location implements Parcelable {
    private String Longitude;
    private String Zipcode;
    private String ZipClass;
    private String County;
    private String City;
    private String State;
    private String Latitude;

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getZipcode() {
        return Zipcode;
    }

    public void setZipcode(String zipcode) {
        Zipcode = zipcode;
    }



    public String getZipClass() {
        return ZipClass;
    }

    public void setZipClass(String zipClass) {
        ZipClass = zipClass;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String county) {
        County = county;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }


    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel w, int flags) {
        w.writeString(City);
        w.writeString(State);
        w.writeString(Longitude);
        w.writeString(Zipcode);
        w.writeString(Latitude);
        w.writeString(ZipClass);
        w.writeString(County);

    }

    public static final Parcelable.Creator<Location> CREATOR
            = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    private Location(Parcel in) {
        City = in.readString();
        State = in.readString();
        Longitude = in.readString();
        Zipcode = in.readString();
        Latitude = in.readString();
        ZipClass = in.readString();
        County= in.readString();



    }
}