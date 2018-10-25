package com.karin.digmarket;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;




public class ItemDetailsAdapter extends RecyclerView.Adapter<ItemDetailsAdapter.MyViewHolder> {



    List<Item> itemsList;
    private Context context;
    int value = -1;
    static int specialVal = 0;
    static double totalSum = 0;
    public static boolean add = true;
    private OnItemClick mCallback;

    public void addItem(Item item)
    {
        if(!itemsList.contains(item))
        {
            itemsList.add(item);
        }
        else
        {
            specialVal = 1;
            item.setAmount(Integer.toString(item.getIntAmount() + 1));
        }

        notifyItemChanged(itemsList.size() - 1);
    }



    public void removeItem(Item item)
    {
        for(int i = 0 , size = itemsList.size(); i < size; i++)
        {
            if(item.equals(itemsList.get(i)))
            {
                item.setAmount("1");
                itemsList.remove(i);
                notifyItemRemoved(i);
                return;
            }
        }
        throw new IllegalArgumentException("item is not in database");
    }



    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView description;
        TextView amount;
        TextView price;
        TextView totalPrice;
        int counter = 0;
        Button addButton;
        Button subButton;
        View.OnClickListener listener;

        public MyViewHolder(View view)
        {
            super(view);
            description = view.findViewById(R.id.description);
            amount = view.findViewById(R.id.amount);
            price = view.findViewById(R.id.price);

            totalPrice = view.findViewById(R.id.totalPrice);

            addButton = view.findViewById(R.id.addButton);
            subButton = view.findViewById(R.id.subButton);
        }

    }

    interface OnClickListener
    {
        void onClick(Item item);
    }



    public ItemDetailsAdapter(List<Item> itemsList, Context context, OnItemClick listener)
    {
        this.itemsList = itemsList;
        this.context = context;
        this.mCallback = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
        {
            final Item item = itemsList.get(position);

            holder.counter = Integer.parseInt(item.getAmount());
            if(specialVal == 1)
            {

                holder.amount.setText(item.getAmount() );
                specialVal = 0 ;
            }

            else {

                holder.amount.setText(Integer.toString(holder.counter));
            }

        holder.description.setText(item.getDescription());
        holder.price.setText(item.getPrice());
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addUp(holder, item);

            }
        });

        holder.subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(holder.amount.getText().toString()) ==  1)
                {
                    removeItem(itemsList.get(holder.getAdapterPosition()));
                }
                else {
                    holder.counter -= 1;
                    holder.amount.setText(Integer.toString(Integer.parseInt(((holder.amount

                    ).getText().toString())) - 1));
                    item.setAmount(holder.amount.getText().toString());

                }

                String currentNos = holder.price.getText().toString() ;

                mCallback.onClick(currentNos + "0");

            }
        });

    }

    private void addUp(final MyViewHolder holder, Item item)
    {

        String currentNos = holder.price.getText().toString() ;

        mCallback.onClick(currentNos + "1");

        holder.counter += 1;

        holder.amount.setText(Integer.toString(Integer.parseInt(((holder.amount

        ).getText().toString())) + 1));

        item.setAmount(holder.amount.getText().toString());

    }

    @Override
    public int getItemCount()
    {
        return this.itemsList.size();
    }






}
