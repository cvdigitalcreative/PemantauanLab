package com.rosalina.pemantauanlab.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.rosalina.pemantauanlab.LoginActivity;
import com.rosalina.pemantauanlab.Menuactivity;
import com.rosalina.pemantauanlab.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Account_frag extends Fragment {
    FirebaseAuth firebaseAuth;

    public Account_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_frag, container, false);
        Button btn_signout = view.findViewById(R.id.btn_logout);
        firebaseAuth = FirebaseAuth.getInstance();

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
