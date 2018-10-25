package com.karin.digmarket;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemFragment extends Fragment{

    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.amount)
    TextView amount;
    ItemDeletedListener listener;

   @Override
   public void onSaveInstanceState(Bundle outState) {
             super.onSaveInstanceState(outState);

            outState.putString("description", description.toString());
            outState.putString("amount", amount.toString());
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        ButterKnife.bind(this, view);
        if ((saveInstanceState != null)
                   && (saveInstanceState.getString("description") != null) && (saveInstanceState.getString("amount") != null))
        {
            description.setText(saveInstanceState
                    .getString("description"));

            amount.setText(saveInstanceState
                    .getString("amount"));
        }

        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if(context instanceof  ItemDeletedListener)
        {
            this.listener = (ItemDeletedListener) context;
        }
    }

    public static ItemFragment newInstance(Item item)
    {
        ItemFragment itemFragment = new ItemFragment();
        Bundle bundle = new Bundle();

        bundle.putString("item", item.toString());
        itemFragment.setArguments(bundle);

        return itemFragment;
    }



    public interface ItemDeletedListener
    {
        void onItemDeleted(Item item);
    }

}
