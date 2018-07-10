package com.rosalina.pemantauanlab.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rosalina.pemantauanlab.Model.Model;
import com.rosalina.pemantauanlab.R;

import java.util.ArrayList;
import java.util.List;

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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_frag, container, false);
        final TextView new_laporanread = view.findViewById(R.id.new_laporanread);
        final TextView new_laporanongoing = view.findViewById(R.id.new_laporanongoing);
        final TextView new_laporandone = view.findViewById(R.id.new_laporandone);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Laporan");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int laporanread = 0;
                int laporanongoing = 0;
                int laporandone = 0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    if (dataSnapshot1.child("status_read").getValue().equals("unread")) {
                        laporanread += 1;
                    }
                    if (dataSnapshot1.child("status_ongoing").getValue().equals("ongoing")) {
                        laporanongoing += 1;
                    }
                    if (dataSnapshot1.child("status_done").getValue().equals("done")) {
                        laporandone += 1;
                    } else {
                        laporanread += 0;laporanongoing += 0;laporanongoing += 0;
                    }
                    new_laporanread.setText(String.valueOf(laporanread));
                    new_laporanongoing.setText(String.valueOf(laporanongoing));
                    new_laporandone.setText(String.valueOf(laporandone));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

}

