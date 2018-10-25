package com.karin.digmarket;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private Button start;
    private Button continueCart;
    private static final int FIRST_ACTIVITY_REQUEST_CODE = 1;
    String shop_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);
        start = findViewById(R.id.startButton1);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();

            }
        });
        continueCart = findViewById(R.id.continueButton);
        continueCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueFunc();
            }
        });
    }

    public void start() {
        Intent intent = new Intent(this, ScanShopActivity.class);
        startActivityForResult(intent, 1);

    }

    private void continueFunc() {
        Intent i = getIntent();
        Intent intent = new Intent(this, BaseActivity.class);

        shop_name = i.getStringExtra("shopname");
        String total = getIntent().getStringExtra("total");
        List<Item> list = (ArrayList<Item>) i.getSerializableExtra("LIST");

        intent.putExtra("shopName", shop_name);
        intent.putExtra("LIST", (Serializable)list);
        intent.putExtra("total", total);
        startActivity(intent);

    }

        public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_first); // it will use .xml from /res/layout
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_first); // it will use xml from /res/layout-land
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

                    final Intent intent = new Intent(this, BaseActivity.class);
                    intent.putExtra("shopName", shopName);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }


            }
        }

        if (requestCode == 4) {
            if (resultCode == RESULT_OK) {

                shop_name = data.getStringExtra("shopname");

            }
        }
    }
}
