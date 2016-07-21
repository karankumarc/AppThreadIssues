package com.pluralsight.karan.appthreadissues;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by ADMIN on 7/21/2016.
 */
public class MyLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        long threadId = Thread.currentThread().getId();
        Log.d("appthreadissues", String.format("Provider:%s [running on thread %d] - Location:%.6f/%.6f",
                location.getProvider(), threadId, location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle bundle) {
        String statusMessage = "unknown";
        switch (status) {
            case LocationProvider.AVAILABLE:
                statusMessage = "Available";
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                statusMessage = "Temporarily Unavailable";
                break;
            case LocationProvider.OUT_OF_SERVICE:
                statusMessage = "Out of Service";
                break;
        }

        long threadId = Thread.currentThread().getId();
        Log.d("appthreadissues", String.format("Provider:%s Status changed [running on thread %d] - new status:%s", provider, threadId, statusMessage));
    }

    @Override
    public void onProviderEnabled(String provider) {
        long threadId = Thread.currentThread().getId();
        Log.d("apppthreadissues", String.format("Provider:%s [running on thread %d] ENABLED", provider, threadId));
    }

    @Override
    public void onProviderDisabled(String provider) {
        long threadId = Thread.currentThread().getId();
        Log.d("apppthreadissues", String.format("Provider:%s [running on thread %d] DISABLED", provider, threadId));
    }
}
