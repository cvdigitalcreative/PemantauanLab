package com.rosalina.pemantauanlab.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rosalina.pemantauanlab.Adapter.ListviewAdapter;
import com.rosalina.pemantauanlab.Model;
import com.rosalina.pemantauanlab.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListLaporan_frag extends Fragment {
    private ListviewAdapter listviewAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    List<Model> list = new ArrayList<>();
    RecyclerView recyclerView;
    Context context;

    public ListLaporan_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_frag, container, false);

            recyclerView = view.findViewById(R.id.recycle);
            firebaseDatabase = FirebaseDatabase.getInstance();
            myRef = firebaseDatabase.getReference("Laporan");

            listviewAdapter = new ListviewAdapter(list);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            System.out.println(dataSnapshot);

                            //Read Using Model too
//                        Model value = dataSnapshot1.getValue(Model.class);
                            Model model = new Model();

//                        String nama = value.getNama();
//                        String kelas = value.getKelas();
//                        String namabarang = value.getNama_barang();
//                        String nounit = value.getNo_unit();
//                        String lokasi = value.getLokasi();
//                        String jumlah = value.getJumlah();
//                        String uraian = value.getUraian_kerusakan();

                            String nama = dataSnapshot1.child("nama_pelapor").getValue().toString();
                            String kelas = dataSnapshot1.child("kelas").getValue().toString();
                            String namabarang = dataSnapshot1.child("nama_barang").getValue().toString();
                            String nounit = dataSnapshot1.child("no_unit").getValue().toString();
                            String lokasi = dataSnapshot1.child("lokasi").getValue().toString();
                            String jumlah = dataSnapshot1.child("jumlah").getValue().toString();
                            String uraian = dataSnapshot1.child("uraian").getValue().toString();
//
                            model.setNama(nama);
                            model.setKelas(kelas);
                            model.setNama_barang(namabarang);
                            model.setNo_unit(nounit);
                            model.setLokasi(lokasi);
                            model.setJumlah(jumlah);
                            model.setUraian_kerusakan(uraian);
                            list.add(model);
                        }
                    recyclerView.setAdapter(listviewAdapter);
                    }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Failed to read database value", Toast.LENGTH_LONG).show();
                }
            });
        return view;
    }

}
