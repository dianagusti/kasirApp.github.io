package com.example.kasir.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kasir.ConfigDB;
import com.example.kasir.R;
import com.example.kasir.adapter.MasterDataAdapter;
import com.example.kasir.model.MasterDataModel;
import com.example.kasir.utils.UtilsDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterDataActivity extends AppCompatActivity {

    private static final String TAG = "MasterDataActivity";

    private RecyclerView recyclerView;

    private MasterDataAdapter masterDataAdapter;
    private Toolbar mToolbar;
    private RequestQueue queue;
    private SwipeRefreshLayout swipeRefresh;
    // berupa list MasterDataModel
    List<MasterDataModel> listMasterData = new ArrayList<>();


    @Override
    protected void onResume() {
        super.onResume();

        getMasterData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_data);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));


        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipeRefresh);

        // init layout, untuk menset layout

        recyclerView = findViewById(R.id.recyclerView);

        // adapter yang digunakan untuk listview / recyclerview nya nanti
        masterDataAdapter = new MasterDataAdapter(getBaseContext());

        // recyclerview setlayoutnya dengan linearlayout (vertical/horizontal)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // recyclerview diset adapter untuk list datanya
        recyclerView.setAdapter(masterDataAdapter);

        // mengambil data dari mySQL server
        getMasterData();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMasterData();
            }
        });
    }



    public void getMasterData() {

        swipeRefresh.setRefreshing(true);
        queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, ConfigDB.URL_GET_PRODUK, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                setelah proses selesai dieksekusi, berupa response JSON dari file PHP nya tadi.


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
                            listMasterData.clear();


                            // menambahkan data dari JSON array ke objek MasterDataModel
                            for (int interasi=0; interasi< jsonArray.length(); interasi++) {

                                // mengambil data JSON object dari JSON array.
                                JSONObject jsonObject = jsonArray.getJSONObject(interasi);

                                // mengambil data dari objek produk_id
                                String produk_id = jsonObject.getString("produk_id");

                                // mengambil data dari objek produk_kode
                                String produk_kode = jsonObject.getString("produk_kode");

                                // mengambil data dari objek produk_kat_id
                                String produk_kat_id = jsonObject.getString("produk_kat_id");

                                // mengambil data dari objek produk_nama
                                String produk_nama = jsonObject.getString("produk_nama");

                                // mengambil data dari objek produk_satuan
                                String produk_satuan = jsonObject.getString("produk_satuan");

                                // mengambil data dari objek produk_harga
                                String produk_harga = jsonObject.getString("produk_harga");

                                // mengambil data dari objek produk_harga
                                String produk_gambar = jsonObject.getString("produk_gambar");

                                // jika sudah, buat objek MasterDataModel dengan value icon gambar, produk_nama dan produk_harga
                                MasterDataModel masterData = new MasterDataModel(
                                        //gambar               //produk_nama   //produk_harga
                                        R.drawable.masterdata, produk_nama, produk_harga, Integer.valueOf(produk_kat_id), Integer.valueOf(produk_id), produk_nama, produk_gambar, produk_kode,
                                        produk_harga,  Integer.valueOf(produk_satuan)
                                );

                                // menambahkan object MasterDataModel ke dalam LIST MasterDataModel
                                listMasterData.add(masterData);
                            }

                            // mengupdate data dari listview/recyclerview nya

                            // ini untuk update perubahan master data

                            masterDataAdapter.setMasterDataModels(listMasterData);

                            // notify perubahan data
                            masterDataAdapter.notifyDataSetChanged();

                            swipeRefresh.setRefreshing(false);

                            Log.e(TAG, "onPostExecute: berhasil lihat produk : " + jsonArray.toString());
                        } catch (Exception e) {

                            // jika gagal parsing data JSON / ada kesalahan eksekusi
                            UtilsDialog.showToast(getApplicationContext(), "Gagal parsing JSON, pesan : " + e.getMessage());
                            Log.e(TAG, "Gagal parsing JSON :" + e);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                 Log.e("error", error.toString());
             }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };

        queue.add(request);
    }

    private void updatMasterData(List<MasterDataModel> masterData) {

    }

    public void onTambahData(View view) {
        //ini onclick, dari sini buat buka activity
        Intent intent = new Intent(MasterDataActivity.this, FormMasterDataActivity.class);
        startActivity(intent);
        UtilsDialog.showToast(getBaseContext(), "Tambah Data Clicked!");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case android.R.id.home: {

                finish();

                return true;
            }

            default: {

                return super.onOptionsItemSelected(item);
            }
        }
    }
}

