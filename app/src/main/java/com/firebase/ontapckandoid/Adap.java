package com.firebase.ontapckandoid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.room.util.DBUtil;

import com.firebase.ontapckandoid.database.EnDB;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Adap extends BaseAdapter {

    private Context context;
    private int layout;
    private List<En> arrayList;
    int positionSelect = -1;

    public Adap(Context context, int layout, List<En> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        }

        TextView txt1 = view.findViewById(R.id.textView5);
        TextView txt2 = view.findViewById(R.id.textView6);
        View view2 = view.findViewById(R.id.view);


        txt1.setText(arrayList.get(i).getName());
        txt2.setText(arrayList.get(i).getDep());

        Button btndelete= view.findViewById(R.id.button5);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase;

                EnDB.getInstance(view.getContext()).enDao().delete(arrayList.get(i).getId());
                arrayList = EnDB.getInstance(view.getContext()).enDao().findAllEnSync();

                mDatabase = FirebaseDatabase.getInstance().getReference("ToDo");


                mDatabase.setValue(arrayList);

                notifyDataSetChanged();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListEn.id = arrayList.get(i).getId();
                ListEn.txtname.setText(arrayList.get(i).getName());
                ListEn.txtdep.setText(arrayList.get(i).getDep());
                positionSelect =i;
                notifyDataSetChanged();
            }
        });

        if (positionSelect == i) {
            view2.setBackgroundColor(Color.GRAY);
        } else {
            view2.setBackgroundColor(Color.WHITE);
        }

        return  view;
    }
}
