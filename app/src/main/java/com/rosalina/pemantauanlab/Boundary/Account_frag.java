package com.rosalina.pemantauanlab.Boundary;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rosalina.pemantauanlab.LoginActivity;
import com.rosalina.pemantauanlab.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Account_frag extends Fragment {
    FirebaseAuth firebaseAuth;
    DatabaseReference myStatus;
    FirebaseDatabase database2;

    public Account_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_account_frag, container, false);
        //Toolbar
        android.support.v7.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity action = (AppCompatActivity)getActivity();
        action.setSupportActionBar(toolbar);
        action.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        action.setTitle("Akun");

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        database2 = FirebaseDatabase.getInstance();
        myStatus = database2.getReference("Users").child(user.getUid());

        myStatus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                TextView usernameq = view.findViewById(R.id.your_name);
                TextView statusq = view.findViewById(R.id.status);

                String nama = dataSnapshot.child("username").getValue().toString();
                usernameq.setText(nama);

                String status = dataSnapshot.child("status").getValue().toString();
                if (status.equals("0")){
                    statusq.setText("Kelapa Laboratorium");
                } else if (status.equals("1")){
                    statusq.setText("Kasi Laboratorium");
                } else {
                    statusq.setText("Mahasiswa");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Button btn_signout = view.findViewById(R.id.btn_logout);

        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();

                Intent in = new Intent(getActivity(), LoginActivity.class);
                startActivity(in);
            }
         });
        return view;
    }
}
