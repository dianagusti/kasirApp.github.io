package com.example.kasir.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.kasir.ConfigDB;
import com.example.kasir.R;
import com.example.kasir.adapter.MasterDataAdapter;
import com.example.kasir.helper.RequestHandler;
import com.example.kasir.model.MasterDataModel;
import com.example.kasir.utils.UtilsDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MasterDataActivity extends AppCompatActivity {

    private static final String TAG = "MasterDataActivity";

    private RecyclerView recyclerView;

    private MasterDataAdapter masterDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_data);

        // init layout, untuk menset layout
        recyclerView = findViewById(R.id.recyclerView);

        // adapter yang digunakan untuk listview / recyclerview nya nanti
        masterDataAdapter = new MasterDataAdapter(getBaseContext());

        // recyclerview setlayoutnya dengan linearlayout (vertical/horizontal)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // recyclerview diset adapter untuk list datanya
        recyclerView.setAdapter(masterDataAdapter);

        // mengambil data dari mySQL server
        getData();
    }

    private void getData() {
        // inner class ini menggunakan asynctask untuk biar bisa berjalan dibackground tanpa mengganggu proses foreground(UI)
        new getProduk().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class getProduk extends AsyncTask<Void,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // sebelum proses dieksekusi
        }

        @Override
        protected String doInBackground(Void... v) {

            //proses background sedang berjalan, untuk mengambil data produk mySQL, ini menggunakan HTTP, bukan retrofit.

            HashMap<String,String> params = new HashMap<>();
            //params.put(ConfigDB.KEY_EMP_KATEGORI_PRODUK, kategori);

            RequestHandler rh = new RequestHandler();

            // get data produk
            return rh.sendPostRequest(ConfigDB.URL_GET_PRODUK, params);
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            /*
                setelah proses selesai dieksekusi, berupa response JSON dari file PHP nya tadi.
            */

            // log ini untuk menampilkan logcat aja (pesan log), engga pengaruh ke sistem/koding.
            Log.d(TAG, "pesan : " + response);

            // mengecek response tadi null (kosong) / tidak. jika response == null, maka ada pesan error nya
            if (response == null) {
                UtilsDialog.showToast(getApplicationContext(), "Ada kesalahan, pesan : " + response);

                // return biar kode di bawahnya tidak dieksekusi / berhenti di sini tidak sampai kebawah.
                return;
            }

            // jika s tidak null
            try {
                // mengkonversi data string/text JSON ke bentuk JSON array di dalam java
                JSONArray jsonArray = new JSONArray(response);

                // berupa list MasterDataModel
                List<MasterDataModel> listMasterData = new ArrayList<>();

                // menambahkan data dari JSON array ke objek MasterDataModel
                for (int interasi=0; interasi< jsonArray.length(); interasi++) {

                    // mengambil data JSON object dari JSON array.
                    JSONObject jsonObject = jsonArray.getJSONObject(interasi);

                    // mengambil data dari objek produk_nama
                    String produk_nama = jsonObject.getString("produk_nama");

                    // mengambil data dari objek produk_harga
                    String produk_harga = jsonObject.getString("produk_harga");

                    // jika sudah, buat objek MasterDataModel dengan value icon gambar, produk_nama dan produk_harga
                    MasterDataModel masterData = new MasterDataModel(
                            //gambar               //produk_nama   //produk_harga
                            R.drawable.masterdata, produk_nama, produk_harga
                    );

                    // menambahkan object MasterDataModel ke dalam LIST MasterDataModel
                    listMasterData.add(masterData);
                }

                // mengupdate data dari listview/recyclerview nya
                updatMasterData(listMasterData);

                Log.d(TAG, "onPostExecute: berhasil lihat produk : " + jsonArray.toString());
            } catch (Exception e) {

                // jika gagal parsing data JSON / ada kesalahan eksekusi
                UtilsDialog.showToast(getApplicationContext(), "Gagal parsing JSON, pesan : " + e.getMessage());
                Log.e(TAG, "Gagal parsing JSON :" + e);
            }
        }
    }

    private void updatMasterData(List<MasterDataModel> masterData) {
        // ini untuk update perubahan master data

        masterDataAdapter.setMasterDataModels(masterData);

        // notify perubahan data
        masterDataAdapter.notifyDataSetChanged();
    }

    public void onTambahData(View view) {
        //ini onclick, dari sini buat buka activity
        Intent intent = new Intent(MasterDataActivity.this, TambahDataMasterActivity.class);
        startActivity(intent);
        UtilsDialog.showToast(getBaseContext(), "Tambah Data Clicked!");
    }
}

