package com.example.kasir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.kasir.ConfigDB;
import com.example.kasir.R;
import com.example.kasir.helper.PreferencesUtils;
import com.example.kasir.helper.RequestHandler;
import com.example.kasir.utils.UtilsDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TambahDataMasterActivity extends AppCompatActivity {

    private static final String TAG = "TambahDataMasterActivit";

    private EditText inputMasukKategori, inputNamaProduk, inputKode, inputHargaJual, inputSatuan;
    private Spinner spinnerKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_master);

        spinnerKategori = findViewById(R.id.TypeKategori);
        inputMasukKategori = findViewById(R.id.MasukKategori);
        inputNamaProduk = findViewById(R.id.NamaProduk);
        inputKode = findViewById(R.id.Kode);
        inputHargaJual = findViewById(R.id.HargaJual);
        inputSatuan = findViewById(R.id.Satuan);

        getKategori();
    }

    private void initSpinnerKategori(List<String> paths) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),
                R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(adapter);
        spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               /*
                String selectedPath = paths.get(position);
                String inputnim = inputNIM.getText().toString();
                boolean isMahasiswa = isMahasiswa();

                if (isMahasiswa) {
                    if (inputnim.length() > 10) {
                        inputNIM.setText(inputnim.substring(0, 10));
                        inputNIM.setSelection(10);
                    }
                }
ViewUtils.setInputType(inputNIM,
                        isMahasiswa ? InputType.TYPE_CLASS_TEXT : InputType.TYPE_CLASS_NUMBER);

                inputNIM.setFilters(new InputFilter[]{
                        new InputFilter.LengthFilter(isMahasiswa ? 10 : 20)});

                inputNIM.setHint(isMahasiswa ? "NIM" : "NIK / NIP");

                txtJudulRegister.setText(
                        String.format("%s%s!", getString(R.string.mendaftar_sebagai), selectedPath));

                if (isIDAfterChanged) {
                    setErrorNimNik(inputnim);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getKategori() {

        new getKategori().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void onClickSimpanData(View view) {
        String kategori = spinnerKategori.getSelectedItem().toString();
        String namaProduk = inputNamaProduk.getText().toString();
        String kode = inputKode.getText().toString();
        String hargaJual = inputHargaJual.getText().toString();
        String satuan = inputSatuan.getText().toString();

        if (kategori.isEmpty()) {
            UtilsDialog.showToast(this, "Kategori tidak boleh kosong!");
            return;
        } else if (namaProduk.isEmpty()) {
            UtilsDialog.showToast(this, "Nama Produk tidak boleh kosong!");
            return;
        } else if (kode.isEmpty()) {
            UtilsDialog.showToast(this, "Kode Produk tidak boleh kosong!");
            return;
        } else if (hargaJual.isEmpty()) {
            UtilsDialog.showToast(this, "Harga Produk tidak boleh kosong!");
            return;
        } else if (satuan.isEmpty()) {
            UtilsDialog.showToast(this, "Silahkan isi satuan produk!");
            return;
        }

        //jika semua udah valid, lanjut input data
        new insertProduk(kategori, namaProduk, kode, hargaJual, satuan)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class getKategori extends AsyncTask<Void,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... v) {
            HashMap<String,String> params = new HashMap<>();
            //params.put(ConfigDB.KEY_EMP_KATEGORI_PRODUK, kategori);

            RequestHandler rh = new RequestHandler();

            return rh.sendPostRequest(ConfigDB.URL_GET_KATEGORI, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d(TAG, "pesan : " + s);

            if (s == null) {
                UtilsDialog.showToast(getApplicationContext(), "Ada kesalahan, pesan : " + s);
                return;
            }

            if (s.startsWith("Error")) {
                UtilsDialog.showToast(getApplicationContext(), "Ada kesalahan, pesan : " + s);
                return;
            }

            try {
                JSONArray jsonArray = new JSONArray(s);
                List<String> dataList = new ArrayList<>();

                for (int i=0; i< jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String namaKategori = jsonObject.getString("kat_nama");
                    dataList.add(namaKategori);
                }

                initSpinnerKategori(dataList);

                UtilsDialog.showLongToast(getApplicationContext(), "berhasil lihat kategori : " + jsonArray.toString());
                Log.d(TAG, "onPostExecute: berhasil lihat kategori : " + jsonArray.toString());
            } catch (Exception e) {
                UtilsDialog.showToast(getApplicationContext(), "Gagal parsing JSON, pesan : " + e.getMessage());
                Log.e(TAG, "Gagal parsing JSON :" + e);
                e.printStackTrace();
            }
        }
    }

    private class insertProduk extends AsyncTask<Void,Void,String> {

        String kategori;
        String namaProduk;
        String kode;
        String hargaJual;
        String satuan;
        //long timeStamp;

        ProgressDialog loading;

        public insertProduk(String kategori, String namaProduk, String kode, String hargaJual, String satuan) {
            this.kategori = kategori;
            this.namaProduk = namaProduk;
            this.kode = kode;
            this.hargaJual = hargaJual;
            this.satuan = satuan;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(TambahDataMasterActivity.this,
                    "Login...","Tunggu...",false,false);
        }

        @Override
        protected String doInBackground(Void... v) {
            HashMap<String,String> params = new HashMap<>();
            params.put(ConfigDB.KEY_EMP_KATEGORI_PRODUK, kategori);
            params.put(ConfigDB.KEY_EMP_NAMA_PRODUK, namaProduk);
            params.put(ConfigDB.KEY_EMP_KODE_PRODUK, kode);
            params.put(ConfigDB.KEY_EMP_HARGA_PRODUK, hargaJual);
            params.put(ConfigDB.KEY_EMP_SATUAN_PRODUK, satuan);

            RequestHandler rh = new RequestHandler();

            return rh.sendPostRequest(ConfigDB.URL_INSERT_PRODUK, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();

            Log.d(TAG, "pesan : " + s);

            if (s == null) {
                UtilsDialog.showToast(getApplicationContext(), "Ada kesalahan, pesan : " + s);
                return;
            }

            if (s.startsWith("Error")) {
                UtilsDialog.showToast(getApplicationContext(), "Ada kesalahan, pesan : " + s);
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject(s);
                Object objResponseCode = jsonObject.get("responseCode");

                if (objResponseCode instanceof  Integer) {
                    Integer responseCode = ((Integer) objResponseCode);
                    if (responseCode == 200) {
                        UtilsDialog.showToast(getApplicationContext(), "Berhasil menambahkan produk");
                        return;
                    }
                }

                UtilsDialog.showToast(getApplicationContext(), "Gagal menambahkan produk");
            } catch (Exception e) {
                UtilsDialog.showToast(getApplicationContext(), "Gagal parsing JSON : " + e.getMessage());
                Log.e(TAG, "Gagal parsing JSON :" + e);
                e.printStackTrace();
            }
        }
    }
}
