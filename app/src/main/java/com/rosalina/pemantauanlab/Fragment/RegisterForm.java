package com.rosalina.pemantauanlab.Fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rosalina.pemantauanlab.R;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterForm extends Fragment {
    FirebaseAuth firebaseAuth;

    public RegisterForm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register_form, container, false);

        //Init
        firebaseAuth = FirebaseAuth.getInstance();

        final EditText mEditUsername = view.findViewById(R.id.username);
        final EditText mEditmail = view.findViewById(R.id.regis_mail);
        final EditText mEditpass = view.findViewById(R.id.regis_pass);
        final Spinner spinnerregister = view.findViewById(R.id.spinner_register);

        spinnerRegister(spinnerregister);

        final Button btn_register =  view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to signup.");

                //check for null valued Editext
                if (!isEmpty(mEditUsername.getText().toString().trim()) && !isEmpty(mEditmail.getText().toString().trim())
                        && !isEmpty(mEditpass.getText().toString().trim())) {

                    //InsertData
                    signupUser(mEditUsername.getText().toString().trim(),
                            mEditmail.getText().toString().trim(),
                            mEditpass.getText().toString().trim(),
                            spinnerregister.getSelectedItemPosition());

                    Toast.makeText(getActivity(), "Terima Kasih, Pendaftaran Berhasil", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getActivity(), "Periksa Jika Masih Ada Yang Kosong", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private void spinnerRegister(Spinner spinnerReg) {
        final ArrayAdapter<String> spinadapter = new ArrayAdapter<>(this. getActivity(), android.R.layout.simple_dropdown_item_1line, getResources()
                .getStringArray(R.array.status_login));
        spinnerReg.setAdapter(spinadapter);
    }

    private void signupUser(final String username, final String email, final String password, final int status) {

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(email, password). addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
                    reference.child("username").setValue(username);
                    reference.child("email").setValue(email);
                    reference.child("password").setValue(password);
                    reference.child("status").setValue(status);
                }
            }
        });


        redirectScreen();
    }

    private void redirectScreen() {
        Log.d(TAG, "Redirecting to login screen.");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new LoginForm())
                .addToBackStack(null).commit();
    }

    private boolean isEmpty(String string){
        return string.equals("");
    }

}
