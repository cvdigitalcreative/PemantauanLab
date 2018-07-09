package com.rosalina.pemantauanlab.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rosalina.pemantauanlab.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home_frag extends Fragment {
    FirebaseDatabase database;
    DatabaseReference myRef;

    public Home_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_frag, container, false);
        final TextView new_laporan = view.findViewById(R.id.new_laporan);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Laporan");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    if (dataSnapshot1.child("status_laporan").getValue().equals("unread")){
                        count += 1;
                    } else {
                        count += 0;
                    }
                } System.out.println(count);
                new_laporan.setText(String.valueOf(count));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

}

