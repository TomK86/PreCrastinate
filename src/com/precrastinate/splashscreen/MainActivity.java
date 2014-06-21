package com.precrastinate.splashscreen;
import android.os.Bundle;
import android.app.Activity;
import com.csci3308.precrastinate.R;
 
public class MainActivity extends Activity {
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_main.xml
        setContentView(R.layout.activity_main);
    }
 
}