package com.example.kasir.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kasir.R;
import com.example.kasir.adapter.MasterDataAdapter;
import com.example.kasir.model.MasterDataModel;
import com.example.kasir.utils.UtilsDialog;

import java.util.ArrayList;
import java.util.List;

public class MasterDataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MasterDataAdapter masterDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_data);

        recyclerView = findViewById(R.id.recyclerView);

        //recyclerview
        masterDataAdapter = new MasterDataAdapter(getBaseContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(masterDataAdapter);

        //ini untuk set dinamic datanya, contoh
        List<MasterDataModel> models = new ArrayList<>();
        models.add(new MasterDataModel(R.drawable.masterdata, "Title 1", "Desc 1"));
        models.add(new MasterDataModel(R.drawable.masterdata, "Title 2", "Desc 2"));
        models.add(new MasterDataModel(R.drawable.masterdata, "Title 3", "Desc 3"));
        models.add(new MasterDataModel(R.drawable.masterdata, "Title 4", "Desc 4"));
        models.add(new MasterDataModel(R.drawable.masterdata, "Title 5", "Desc 5"));
        models.add(new MasterDataModel(R.drawable.masterdata, "Title 6", "Desc 6"));
        models.add(new MasterDataModel(R.drawable.masterdata, "Title 7", "Desc 7"));
        models.add(new MasterDataModel(R.drawable.masterdata, "Title 8", "Desc 8"));
        models.add(new MasterDataModel(R.drawable.masterdata, "Title Diana", "Desc Diana"));

        masterDataAdapter.setMasterDataModels(models);
        masterDataAdapter.notifyDataSetChanged();

    }

    public void onTambahData(View view) {
        //ini onclick, dari sini buat buka activity.. hayuk..
        Intent intent = new Intent(MasterDataActivity.this, TambahDataMasterActivity.class);
        startActivity(intent);
        UtilsDialog.showToast(getBaseContext(), "Tambah Data Clicked!");
    }
}
