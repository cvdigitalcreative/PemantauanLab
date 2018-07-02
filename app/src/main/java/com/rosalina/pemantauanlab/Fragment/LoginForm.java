package com.rosalina.pemantauanlab.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.rosalina.pemantauanlab.Menuactivity;
import com.rosalina.pemantauanlab.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginForm extends Fragment {


    public LoginForm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_form, container, false);
        Spinner loginspin = view.findViewById(R.id.spinner_login);

        //Spinner
        ArrayAdapter<String> spinadapter = new ArrayAdapter<>(this. getActivity(), android.R.layout.simple_dropdown_item_1line, getResources()
                                                .getStringArray(R.array.status_login));
        loginspin.setAdapter(spinadapter);
        loginspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Button Login
        Button btn_login = view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Menuactivity.class);
                startActivity(i);
            }
        });
        return view;
    }

}
