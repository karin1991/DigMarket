package com.karin.digmarket;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScanShopActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {


    private TextView formatTxt;
    private TextView contentTxt;
    private ZXingScannerView zXingScannerView;
    private String shopName;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("shopCodes");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_shop);
        formatTxt = findViewById(R.id.scan_format);
        contentTxt = findViewById(R.id.scan_content);

    }


    public void scanShop(View view) {
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            setContentView(R.layout.activity_scan_shop); // it will use .xml from /res/layout
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            setContentView(R.layout.activity_scan_shop); // it will use xml from /res/layout-land
        }
    }


    @Override
    public void handleResult(com.google.zxing.Result result) {

        final String code = result.getText();


        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent intent = new Intent();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if(ds.child("code").getValue(String.class).equals(code))
                    {
                        shopName = ds.child("name").getValue(String.class);

                        intent.putExtra("shopName", shopName);

                    }

                }

                setResult(RESULT_OK, intent);
                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
