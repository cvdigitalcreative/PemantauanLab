package com.rosalina.pemantauanlab.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rosalina.pemantauanlab.Model;
import com.rosalina.pemantauanlab.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class Lapor_frag extends Fragment {

    List<Model> list = new ArrayList<>();
    FirebaseAuth firebaseAuth;
    public Lapor_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_lapor_frag, container, false);
        final EditText namamhs =  view.findViewById(R.id.text_input_namamhs);
        final EditText kelas = view.findViewById(R.id.text_input_kelas);
        final EditText namabarang = view.findViewById(R.id.text_input_namabrg);
        final EditText nounit =  view.findViewById(R.id.text_input_nounit);
        final EditText lokasi = view.findViewById(R.id.text_input_lokasi);
        final EditText jumlah = view.findViewById(R.id.text_input_jumlah);
        final EditText uraian =  view.findViewById(R.id.text_input_uraiankerusakan);



//
//        int datetime = calendar.get(Calendar.DATE);
//        int hours = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);

        firebaseAuth = FirebaseAuth.getInstance();

        Button btn_submit =  view.findViewById(R.id.btn_kirim);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to signup.");

                Date calendar = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                String currentdate = dateFormat.format(calendar);
                System.out.println(currentdate);
//                DateFormat dateFormat = DateFormat.getDateF
//                String currentdate = currenttime.toString();
//                System.out.println(currenttime);


//                String month = calendar.get(Calendar.MONTH);
//                String date = calendar.get(Calendar.DATE);
//                String hours = calendar.get(Calendar.HOUR_OF_DAY);
//                String minute = calendar.get(Calendar.MINUTE);


                //check for null valued Editext
                if (!isEmpty(namamhs.getText().toString().trim()) && !isEmpty(kelas.getText().toString().trim())
                        && !isEmpty(namabarang.getText().toString().trim()) && !isEmpty(nounit.getText().toString().trim())
                        && !isEmpty(lokasi.getText().toString().trim()) && !isEmpty(jumlah.getText().toString().trim())
                        && !isEmpty(uraian.getText().toString())) {

                    //Insert Data Laporan
                    final String string_namamhs = namamhs.getText().toString();
                    final String string_kelas = kelas.getText().toString();
                    final String string_namabarang = namabarang.getText().toString();
                    final String string_nounit = nounit.getText().toString();
                    final String string_lokasi = lokasi.getText().toString();
                    final String string_jumlah = jumlah.getText().toString();
                    final String string_uraian = uraian.getText().toString();

                    Insertdatalaporan(string_namamhs, string_kelas, string_namabarang,
                            string_nounit, string_lokasi, string_jumlah,
                            string_uraian, currentdate);

                    Toast.makeText(getActivity(), "Laporan Telah Berhasil Di Upload", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getActivity(), "Periksa Jika Masih Ada Yang Kosong", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private boolean isEmpty(String string){
        return string.equals("");
    }

    private void Insertdatalaporan(String namamhs, String kelas, String namabarang, String nounit, String lokasi, String jumlah,
                                   String uraian, String currentdate) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        String uid = currentuser.getUid();

        String key = database.getReference("Lapor").push().getKey();
        DatabaseReference reference = database.getReference().child("Laporan").child(key);

      // Model gvalue = reference.setValue(Model.class);

      // String nama1 = gvalue.getNama();
//        String kelas = gvalue.getKelas();
//        String namabarang = gvalue.getNama_barang();
//        String nounit = gvalue.getNo_unit();
//        String lokasi = gvalue.getLokasi();
//        String jumlah = gvalue.getJumlah();
//        String uraian = gvalue.getUraian_kerusakan();

        //Write harus pake model
        reference.child("status_laporan").setValue("unread");
        reference.child("userUid").setValue(uid);
        reference.child("nama_pelapor").setValue(namamhs);
        reference.child("kelas").setValue(kelas);
        reference.child("nama_barang").setValue(namabarang);
        reference.child("no_unit").setValue(nounit);
        reference.child("lokasi").setValue(lokasi);
        reference.child("jumlah").setValue(jumlah);
        reference.child("uraian").setValue(uraian);
        reference.child("date").setValue(currentdate);
        redirectScreen();
    }

    private void redirectScreen() {
        Log.d(TAG, "Redirecting to login screen.");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new Home_frag())
                .addToBackStack(null).commit();
    }
}
