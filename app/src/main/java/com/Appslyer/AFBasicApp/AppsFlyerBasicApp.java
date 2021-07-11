package com.Appslyer.AFBasicApp;

import com.appsflyer.AppsFlyerLib;
import com.appsflyer.AppsFlyerConversionListener;
import android.app.Application;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import java.util.Map;
import java.util.Objects;

public class AppsFlyerBasicApp extends Application {
    public static final String LOG_TAG = "AppsFlyerBasicApp";
    Map<String, Object> conversionData;
    @Override
    public void onCreate() {
        super.onCreate();
        String afDevKey = AppsFlyerConstants.afDevKey;
        AppsFlyerLib appsflyer = AppsFlyerLib.getInstance();
        // The following line is for debug. Consider removing in production
        // appsflyer.setDebugLog(true);
        AppsFlyerConversionListener conversionListener =  new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionDataMap) {
                String status = Objects.requireNonNull(conversionDataMap.get("af_status")).toString();
                if(status.equals("Non-organic")){
                    if( Objects.requireNonNull(conversionDataMap.get("is_first_launch")).toString().equals("true")){
                        Log.d(LOG_TAG,"Conversion: First Launch");
                    } else {
                        Log.d(LOG_TAG,"Conversion: Not First Launch");
                    }
                } else {
                    Log.d(LOG_TAG, "Conversion: This is an organic install.");
                }
                conversionData = conversionDataMap;
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d(LOG_TAG, "error getting conversion data: " + errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> attributionData) {
                Log.d(LOG_TAG, "onAppOpenAttribution: This is fake call.");
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d(LOG_TAG, "error onAttributionFailure : " + errorMessage);
            }
        };
        appsflyer.init(afDevKey, conversionListener, this);
        appsflyer.start(this);
    }
}
