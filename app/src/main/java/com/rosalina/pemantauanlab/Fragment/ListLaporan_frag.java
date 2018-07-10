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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rosalina.pemantauanlab.Adapter.Kasi_RcAdapter;
import com.rosalina.pemantauanlab.Adapter.KepalaLab_RcAdapter;
import com.rosalina.pemantauanlab.Model.Model;
import com.rosalina.pemantauanlab.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListLaporan_frag extends Fragment {
    private KepalaLab_RcAdapter kepalaLab_rcAdapter;
    private Kasi_RcAdapter kasiRcAdapter;
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
        Bundle bundle = getArguments();
        int getkey_status = bundle.getInt("keystatus");
        String uid = (String) bundle.getString("validator");
        System.out.println("uid" +uid);
        System.out.println("coy");
        System.out.println(getkey_status);

        recyclerView = view.findViewById(R.id.recycle);
        firebaseDatabase = FirebaseDatabase.getInstance();
        kepalaLab_rcAdapter = new KepalaLab_RcAdapter(list);
        kasiRcAdapter = new Kasi_RcAdapter(list);
        myRef = firebaseDatabase.getReference("Laporan");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerFunction(recyclerView, getkey_status, uid);

        return view;
    }


    private void RecyclerFunction(final RecyclerView recyclerView, final int status, String uid) {
        if (status == 0){
            myRef.orderByChild("terima").equalTo("ya").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Model model = new Model();

                        String uid = dataSnapshot1.getKey();
                        String nama = dataSnapshot1.child("nama_pelapor").getValue().toString();
                        String kelas = dataSnapshot1.child("kelas").getValue().toString();
                        String namabarang = dataSnapshot1.child("nama_barang").getValue().toString();
                        String nounit = dataSnapshot1.child("no_unit").getValue().toString();
                        String lokasi = dataSnapshot1.child("lokasi").getValue().toString();
                        String jumlah = dataSnapshot1.child("jumlah").getValue().toString();
                        String uraian = dataSnapshot1.child("uraian").getValue().toString();
                        String status_ongoing = dataSnapshot1.child("status_ongoing").getValue().toString();
                        String status_done = dataSnapshot1.child("status_done").getValue().toString();
                        String terima = dataSnapshot1.child("terima").getValue().toString();

                        model.setUid(uid);
                        model.setNama(nama);
                        model.setKelas(kelas);
                        model.setNama_barang(namabarang);
                        model.setNo_unit(nounit);
                        model.setLokasi(lokasi);
                        model.setJumlah(jumlah);
                        model.setUraian_kerusakan(uraian);
                        model.setStatus_ongoing(status_ongoing);
                        model.setStatus_done(status_done);
                        model.setTerima(terima);
                        list.add(model);
                        }
                        recyclerView.setAdapter(kepalaLab_rcAdapter);

                            }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Failed to read database value", Toast.LENGTH_LONG).show();
                }
                        });
                    }
                    else{
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                Model model = new Model();

                                String uid = dataSnapshot1.getKey();
                                String nama = dataSnapshot1.child("nama_pelapor").getValue().toString();
                                String kelas = dataSnapshot1.child("kelas").getValue().toString();
                                String namabarang = dataSnapshot1.child("nama_barang").getValue().toString();
                                String nounit = dataSnapshot1.child("no_unit").getValue().toString();
                                String lokasi = dataSnapshot1.child("lokasi").getValue().toString();
                                String jumlah = dataSnapshot1.child("jumlah").getValue().toString();
                                String uraian = dataSnapshot1.child("uraian").getValue().toString();
                                String status_ongoing = dataSnapshot1.child("status_ongoing").getValue().toString();
                                String status_done = dataSnapshot1.child("status_done").getValue().toString();
                                String terima = dataSnapshot1.child("terima").getValue().toString();

                                model.setUid(uid);
                                model.setNama(nama);
                                model.setKelas(kelas);
                                model.setNama_barang(namabarang);
                                model.setNo_unit(nounit);
                                model.setLokasi(lokasi);
                                model.setJumlah(jumlah);
                                model.setUraian_kerusakan(uraian);
                                model.setStatus_ongoing(status_ongoing);
                                model.setStatus_done(status_done);
                                model.setTerima(terima);
                                list.add(model);
                            }
                            recyclerView.setAdapter(kasiRcAdapter);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getActivity(), "Failed to read database value", Toast.LENGTH_LONG).show();
                        }
                    });

            }
    }
}
