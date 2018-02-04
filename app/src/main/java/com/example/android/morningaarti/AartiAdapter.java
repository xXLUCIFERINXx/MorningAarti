package com.example.android.morningaarti;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class AartiAdapter extends ArrayAdapter<Aarti> {

    AartiAdapter(@NonNull Context context, ArrayList<Aarti> aartis) {
        super(context, 0, aartis);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }

        Aarti aarti = getItem(position);
        TextView aartiName = convertView.findViewById(R.id.name);
        ImageView pic = convertView.findViewById(R.id.pic);

        if (aarti != null) {
            aartiName.setText(aarti.getAartiName());
            pic.setImageResource(aarti.getImageRes());
        }
        return convertView;
    }

}
