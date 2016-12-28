package com.example.erik.videogamesproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.erik.videogamesproject.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Marco on 25/11/2016.
 */

public class MapAdapter extends BaseAdapter {

    private final ArrayList mData;
    private Map<String, Integer> mapPlatforms;


    public MapAdapter ( Map<String, Integer> map ) {

        mData = new ArrayList();
        mData.addAll(map.entrySet());
        mapPlatforms = map;
    }

    @Override
    public int getCount () {
        return mData.size();
    }

    @Override
    public Map.Entry<String, Integer> getItem ( int position ) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId ( int position ) {
        return 0;
    }

    @Override
    public View getView ( final int position, View convertView, ViewGroup parent ) {

        final View result;
        final TextView textPlatform;
        final ElegantNumberButton quantity;
        Button button;

        if (convertView == null) {

            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_alertdialog_videogame, parent, false);

        } else {

            result = convertView;
        }

        Map.Entry<String, Integer> item = getItem(position);

        textPlatform = (TextView) result.findViewById(R.id.namePlatform);
        quantity = (ElegantNumberButton) result.findViewById(R.id.quantityToDelete);
        button = (Button) result.findViewById(R.id.buttonAdapterConfirm);

        textPlatform.setText(item.getKey());
        quantity.setNumber(String.valueOf(item.getValue()));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {

                mapPlatforms.put(textPlatform.getText().toString(), Integer.valueOf(quantity.getNumber()));
            }
        });

        return result;
    }

    public Map<String, Integer> getMap () {

        return mapPlatforms;

    }

    public int getCountQuantity () {

        Iterator iterator = mapPlatforms.entrySet().iterator();
        int countQuantity = 0;
        while (iterator.hasNext()) {

            Map.Entry entry = (Map.Entry) iterator.next();

            countQuantity = countQuantity + Integer.parseInt(entry.getValue().toString());
        }

        return countQuantity;
    }

}
