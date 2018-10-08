package com.biginsect.easyhub.util;

import android.os.Bundle;
import android.os.Parcel;

import java.io.Serializable;

/**
 * Copied from Copyright (C) 2017 ThirtyDegreesRay.
 * @author biginsect
 * @date 2018/10/8.
 */


public class BundleHelper {

    private final static int BUNDLE_MAX_SIZE = 512 * 1024;

    private Bundle bundle;

    private BundleHelper(){
        bundle = new Bundle();
    }

    public static BundleHelper builder(){
        return new BundleHelper();
    }

    public BundleHelper put(String key, Serializable value){
        bundle.putSerializable(key, value);
        return this;
    }

    public BundleHelper put(String key, String value){
        bundle.putString(key, value);
        return this;
    }

    public Bundle build(){
        Parcel parcel = Parcel.obtain();
        bundle.writeToParcel(parcel, 0);
        if (parcel.dataSize() > BUNDLE_MAX_SIZE){
            bundle.clear();
            throw new IllegalArgumentException("bundle data is too large");
        }

        return bundle;
    }
}
