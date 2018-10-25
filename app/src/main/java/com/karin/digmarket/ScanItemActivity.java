package com.karin.digmarket;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ScanItemActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{


    private TextView formatTxt;
    private TextView contentTxt;
    static String description;
    static String price;
    private ZXingScannerView zXingScannerView;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("items");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        formatTxt = findViewById(R.id.scan_format);
        contentTxt = findViewById(R.id.scan_content);
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();

    }


    @Override
    public void handleResult(com.google.zxing.Result result)
    {
        final String code =  result.getText();


       mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent intent = new Intent();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if(ds.child("code").getValue().toString().equals(code))
                    {
                        description = ds.child("description").getValue(String.class);
                        price = ds.child("price").getValue(String.class);
                        intent.putExtra("description", description);
                        intent.putExtra("price", price);

                    }
                }
                intent.putExtra("code", code);
                setResult(RESULT_OK, intent);
                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


}
