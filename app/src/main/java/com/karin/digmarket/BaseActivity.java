package com.karin.digmarket;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseActivity extends AppCompatActivity implements ItemDetailsAdapter.OnClickListener, ItemFragment.ItemDeletedListener,
        OnItemClick , Serializable{


    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;



    private List<Item> itemsList;
    private List<Item> items;
    private List<String> codes;
    private RecyclerView recyclerView;
    private ItemDetailsAdapter itemDetailsAdapter;
    private FloatingActionButton addButton;
    private Button buyingButton;
    private TextView total;
    private TextView shopName;
    private String sName;
    static double totalPrice = 0;
    private ArrayList<String> itemsToSave;
    private Bundle myBundle;
    private Intent intent;

    private Parcelable recyclerViewState;


    @Override
    public void onSaveInstanceState(Bundle outState)
    {

        super.onSaveInstanceState(outState);

    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();//save
//    }
//
//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//        if(recyclerViewState!=null)
//            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);//restore
//    }


    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState)
    {
        super.onRestoreInstanceState(saveInstanceState);

    }



    @Override
    public void onClick (String value){

        char sign = value.charAt(value.length() - 1);
        if(sign == '1')
        {
            totalPrice += Double.parseDouble(value.substring(0, value.length()));
        }

        else if(sign == '0')
        {
            totalPrice -= Double.parseDouble(value.substring(0, value.length()));
        }

        total.setText(Double.toString(Double.parseDouble(new DecimalFormat("##.##").format(totalPrice))));

// value this data you receive when increment() / decrement() called
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        itemsList = new ArrayList<>();
        codes = new ArrayList<>();
        items = new ArrayList<>();

        recyclerView = findViewById(R.id.recycle_view);
        itemDetailsAdapter = new ItemDetailsAdapter(itemsList, BaseActivity.this, this);
        itemsToSave = new ArrayList<>(itemDetailsAdapter.itemsList.size());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(itemDetailsAdapter);
        total = findViewById(R.id.totalPrice);
        shopName = findViewById(R.id.shopName);
        buyingButton = findViewById(R.id.buyingButton);
        Intent myIntent = getIntent();
        sName = myIntent.getStringExtra("shopName");

        List<Item> list = (ArrayList<Item>) myIntent.getSerializableExtra("LIST");

        if(list != null)
        {
            for(int i = 0; i < list.size(); i++)
            {
                itemDetailsAdapter.addItem(list.get(i));
                items.add(list.get(i));
                codes.add(list.get(i).getCode());
            }

            String total_sum = myIntent.getStringExtra("total");
            totalPrice = Double.parseDouble(total_sum);
            total.setText(total_sum);

        }
        else
        {
            total.setText("0.0");
            totalPrice = 0;
        }

        shopName.setText(sName);

        addButton = findViewById(R.id.floatingActionButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();

            }
        });

        buyingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buying();


            }
        });


    }

    private void start()
    {
        Intent intent = new Intent(this, ScanItemActivity.class);

        startActivityForResult(intent, 0); }

    private void buying()
    {
        Intent intent = new Intent(this, EndActivity.class);
        intent.putExtra("total", total.getText().toString());
        intent.putExtra("LIST", (Serializable) itemDetailsAdapter.itemsList);
        intent.putExtra("shopName", shopName.getText().toString());

        startActivity(intent);
    }



    public void onBackPressed() {


        intent = new Intent(this,SecondActivity.class);
        intent.putExtra("shopname", shopName.getText().toString());
        intent.putExtra("LIST", (Serializable) itemDetailsAdapter.itemsList);
        intent.putExtra("total", total.getText().toString());
        startActivity(intent);
        super.onBackPressed();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // get String data from Intent
                String itemDescription = data.getStringExtra("description");
                String itemPrice = data.getStringExtra("price");
                String code = data.getStringExtra("code");

                if(itemDescription ==  null && itemPrice == null)
                {
                    Toast.makeText(this, "לא ניתן להוסיף את המוצר", Toast.LENGTH_SHORT).show();
                }
                if(codes.contains(code)) {
                    totalPrice += Double.parseDouble(itemPrice);
                    int position = this.findMyItem(code);//TODO WHEN RETURN -1
                    Item item = this.items.get(position);
                    itemDetailsAdapter.addItem(item);
                    itemDetailsAdapter.notifyItemChanged(position);
                }
                else if(!codes.contains(code) && itemDescription!=null && itemPrice!=null)
                {
                    codes.add(code);
                    Item item = new Item(code, itemDescription, itemPrice, "1");
                    itemDetailsAdapter.addItem(item);
                    items.add(item);
                    totalPrice += Double.parseDouble(itemPrice);

                }
                total.setText(Double.toString(Double.parseDouble(new DecimalFormat("##.##").format(totalPrice))));

            }
        }
    }


    private int findMyItem(String code)
    {
        for(int i = 0; i < items.size(); i++)
        {
            if(this.items.get(i).getCode().equals(code))
            {
                return i;
            }

        }
        return -1;
    }



    @Override
    public void onClick(Item item)
    {
        ItemFragment fragment = ItemFragment.newInstance(item);
        getFragmentManager().beginTransaction().add(R.id.activity_base_frame, fragment)
                .addToBackStack(null).commit();
    }

    @Override
    public void onItemDeleted(Item item)
    {
        itemDetailsAdapter.removeItem(item);
        getSupportFragmentManager().popBackStack();
    }



}
