package com.firebase.ontapckandoid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.ontapckandoid.database.EnDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListEn extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ListView idListView;
    private java.util.List<En> list;
    static int id;
    static EditText txtname;
    static EditText txtdep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //Khoi tao Ref FireBase
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference("ToDo");
        mDatabase.keepSynced(true);

        idListView = (ListView) findViewById(R.id.list);
        txtname = findViewById(R.id.edtTop);
        txtdep = findViewById(R.id.edtBottom);
        Button btn = findViewById(R.id.btnAdd);
        Button btnu = findViewById(R.id.btnUpdate);

        list = new ArrayList<>();

        try {
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        En c = postSnapshot.getValue(En.class);
                        EnDB.getInstance(getBaseContext()).enDao().insertEn(c);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception ex){

        }

        list = EnDB.getInstance(getBaseContext()).enDao().findAllEnSync();
        Adap adapter = new Adap(getBaseContext(), R.layout.item, list);
        idListView.setAdapter(adapter);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                En e = new En();
                e.setName(txtname.getText().toString());
                e.setDep(txtdep.getText().toString());
                EnDB.getInstance(getBaseContext()).enDao().insertEn(e);
                mDatabase.setValue(EnDB.getInstance(getBaseContext()).enDao().findAllEnSync());
                loadlist();

            }
        });

        btnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (true) {
                    En en = new En();
                    en.setId(id);
                    en.setName(txtname.getText().toString());
                    en.setDep(txtdep.getText().toString());
                    EnDB.getInstance(getBaseContext()).enDao().updateEn(en);
                    mDatabase.setValue(EnDB.getInstance(view.getContext()).enDao().findAllEnSync());
                    loadlist();


                }
            }
        });

    }

    private void loadlist() {
        list = new ArrayList<>();

        try {
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        En c = postSnapshot.getValue(En.class);
                        EnDB.getInstance(getBaseContext()).enDao().insertEn(c);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception ex){

        }

        list = EnDB.getInstance(getBaseContext()).enDao().findAllEnSync();
        Adap adapter = new Adap(getBaseContext(), R.layout.item, list);
        idListView.setAdapter(adapter);
    }


}