package com.rosalina.pemantauanlab.Fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.rosalina.pemantauanlab.R;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class Data_frag extends Fragment {
    ListView listView;
    String[] t1={"Data Lab 1","Data Lab 2", "Data Lab 3", "Data Lab 4", "Data Lab 5", "Data Lab 6", "Data Lab 7", "Data Lab 8",
            "Data Lab 9", "Data Lab 10"};
    int[] dirFile ={R.raw.file_lab1, R.raw.file_lab2, R.raw.file_lab3,R.raw.file_lab4,R.raw.file_lab5,
            R.raw.file_lab6,R.raw.file_lab7,R.raw.file_lab8,R.raw.file_lab9, R.raw.file_lab10};

    public Data_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_frag, container, false);
        listView = view.findViewById(R.id.listview);
        listView.setAdapter(new dataListAdapter(t1, dirFile));
        //File file = new File(Environment.getExternalStorageDirectory()+"/excelfie/" +filename)
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.fromFile(),"application/vnd.ms-excel");
//                startActivity(intent);
//            }
//        });
        //Reading excel file
//        File fileDirectory = new File (Environment.getDataDirectory()+ "/excelfile/");
//        dirFile = fileDirectory.listFiles();
        return view;
    }

    class dataListAdapter extends BaseAdapter {
        String[] Detail;
        int[] dirFile;

        public dataListAdapter(String[] detail, int[] dirFile) {
            super();
            this.Detail = detail;
            this.dirFile = dirFile;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return Detail.length;
        }

        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.listview, parent, false);

            TextView detail;

            detail = row.findViewById(R.id.nama_file);
            detail.setText(Detail[position]);

            return (row);
        }
    }
}
