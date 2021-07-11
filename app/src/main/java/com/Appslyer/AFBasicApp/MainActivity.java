package com.Appslyer.AFBasicApp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {
    Map<String, Object> conversionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button showConversionDataButton = findViewById(R.id.showConversionDataButton);
        TextView conversionDataTextView = findViewById(R.id.conversionDataTextView);

        showConversionDataButton.setOnClickListener(v ->{
            this.conversionData = ((AppsFlyerBasicApp) getApplication()).conversionData;
            if (this.conversionData == null) {
                conversionDataTextView.setText("Conversion data not available.\nPlease try again");
            }
            else{
                String conversionDataString = mapToSortedString(conversionData);
                conversionDataTextView.setMovementMethod(new ScrollingMovementMethod());
                conversionDataTextView.setText(conversionDataString);
                showConversionDataButton.setVisibility(View.GONE);

            }
        });
    }

    public String mapToSortedString(Map<String, Object> map){
        if (map == null){
            return "Conversion data not available";
        }
        String result = "";
        SortedSet<String> keys = new TreeSet<>(map.keySet());
        Object value;
        for (String key: keys){
            value = map.get(key);
            if (value == null) {
                value = "null";
            }
            result += String.format("%s : %s\n", key, value);
        }
        return result;
    }
}