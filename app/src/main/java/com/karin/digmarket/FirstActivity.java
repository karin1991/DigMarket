package com.karin.digmarket;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;

public class FirstActivity extends AppCompatActivity {

    private static final int FIRST_ACTIVITY_REQUEST_CODE = 1;
    private Button startFirst;
    private Button startSecond;
    private Button continueButton;
    View view1 ;
    View view2;
    public static int continueScan;
    private int changeOriantaion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view1 = getLayoutInflater().inflate(R.layout.activity_first, null);
        view2 = getLayoutInflater().inflate(R.layout.activity_second, null);
        setContentView(view1);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);

        startFirst = findViewById(R.id.startButton);
        continueButton = view2.findViewById(R.id.continueButton);
        startSecond = view2.findViewById(R.id.startButton1);
        startFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();

            }
        });
        startSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();

            }
        });

    }


    private void start()
    {
        continueScan = 0;
        Intent intent = new Intent(this, ScanShopActivity.class);
        startActivityForResult(intent, 1);

    }

    private void verifyPermissions()
    {
       String permissions = Manifest.permission.CAMERA;
       if(ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions)== PackageManager.PERMISSION_GRANTED)
       {

       }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(changeOriantaion == 0)
        {
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
            {
                setContentView(R.layout.activity_first); // it will use .xml from /res/layout
            }
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                setContentView(R.layout.activity_first); // it will use xml from /res/layout-land
            }

        }
        else if(changeOriantaion == 1)
        {
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
            {
                setContentView(R.layout.activity_second); // it will use .xml from /res/layout
            }
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                setContentView(R.layout.activity_second); // it will use xml from /res/layout-land
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == FIRST_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {


                // get String data from Intent
                String shopName = data.getStringExtra("shopName");

                if (shopName == null) {
                    Toast.makeText(this, "קוד החנות אינו מזוהה, אנא נסה שוב", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, ScanShopActivity.class);
                    startActivityForResult(intent, 1);

                } else {

                    Intent intent = new Intent(this, BaseActivity.class);
                    intent.putExtra("shopName", shopName);

                    startActivity(intent);
                }

                changeOriantaion = 1;

            }
        }
    }
}
