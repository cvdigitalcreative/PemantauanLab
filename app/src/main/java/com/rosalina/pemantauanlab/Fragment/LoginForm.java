package com.rosalina.pemantauanlab.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rosalina.pemantauanlab.Menuactivity;
import com.rosalina.pemantauanlab.R;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginForm extends Fragment {
    FirebaseAuth firebaseAuth;
    private EditText email, pass;
    private DatabaseReference mDatabase;
    String userid;

    public LoginForm() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mDatabase = FirebaseDatabase.getInstance().getReference();

        View view = inflater.inflate(R.layout.fragment_login_form, container, false);
        email = view.findViewById(R.id.mail_user);
        pass =  view.findViewById(R.id.pass_user);
        firebaseAuth = FirebaseAuth.getInstance();

        final Button btn_registrasi =  view.findViewById(R.id.btn_register);
        btn_registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, new RegisterForm())
                                   .addToBackStack(null).commit();
            }
        });

        //Button Login
        final Button btn_login = view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checklogin();
            }
        });
        return view;
    }

    private void Checklogin() {
        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, "Loading");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    userid = user.getUid();


                    mDatabase.child("Users").child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            int status_user=Integer.valueOf(dataSnapshot.child("status").getValue().toString());
                                Intent in = new Intent(getActivity(), Menuactivity.class);
                                in.putExtra("keylogin", status_user);
                                startActivity(in);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getActivity(), "Database Error, Refresh  please", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
