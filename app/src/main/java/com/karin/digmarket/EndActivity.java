package com.karin.digmarket;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EndActivity extends AppCompatActivity {

    private TextView total_sum;
    private Button ok;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("items");
    private String currentShop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        total_sum = findViewById(R.id.total);
        ok = findViewById(R.id.ok_button);


        Intent myIntent = getIntent();
        total_sum.setText(myIntent.getStringExtra("total")+ "₪");
        final List<Item> list = (ArrayList<Item>) myIntent.getSerializableExtra("LIST");

        currentShop = myIntent.getStringExtra("shopName");




        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (Item item : list) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                if(ds.child("code").getValue().toString().equals(item.getCode()))
                                {

                                    ds.child("amount").getRef().setValue(item.getAmount());
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





                Intent i = new Intent(EndActivity.this, FirstActivity.class);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                moveTaskToBack(true);

            }
        });




    }



}
