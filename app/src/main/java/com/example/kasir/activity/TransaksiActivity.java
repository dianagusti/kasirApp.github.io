package com.example.kasir.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kasir.ConfigDB;
import com.example.kasir.R;
import com.example.kasir.app.App;
import com.example.kasir.model.MasterDataModel;
import com.example.kasir.utils.UtilsDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransaksiActivity extends AppCompatActivity {

    private EditText edtCari;
    private ImageView btnBarcode;
    private RecyclerView recyclerView;
    private LinearLayout btnNext;
    private MasterDataTransaksiAdapter masterDataAdapter;
    private Toolbar mToolbar;
    List<MasterDataModel> listMasterData = new ArrayList<>();
    private static final String TAG = "MasterDataActivity";
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Transaksi");
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));


        edtCari = (EditText)findViewById(R.id.edtCari);
        btnBarcode = (ImageView)findViewById(R.id.btnBarcode);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        btnNext = (LinearLayout)findViewById(R.id.btnNext);

        masterDataAdapter = new MasterDataTransaksiAdapter(getBaseContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(masterDataAdapter);

        btnBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransaksiActivity.this, ScanBarcodeActivity.class);
                intent.putExtra("isFromTransaksi", true);
//                startActivity(intent);

                startActivityForResult(intent, 22);
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                App.getInstance().listMasterData.clear();

                Intent  i = new Intent(TransaksiActivity.this, TransaksiDetailActivity.class);

                for(int index =0; index < listMasterData.size(); index++){

                    if(listMasterData.get(index).getTotal() > 0){
                        App.getInstance().listMasterData.add(listMasterData.get(index));

                        int totalItem = listMasterData.get(index).getTotal() * Integer.parseInt(listMasterData.get(index).getHargaJual());

                        App.getInstance().intTotal = App.getInstance().intTotal + totalItem;

                    }
                }
                startActivity(i);
            }
        });

        getMasterData();
    }


    public void getMasterData() {

         queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, ConfigDB.URL_GET_PRODUK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "pesan : " + response);

                if (response == null) {
                    UtilsDialog.showToast(getApplicationContext(), "Ada kesalahan, pesan : " + response);

                    return;
                }

                try {
                    // mengkonversi data string/text JSON ke bentuk JSON array di dalam java
                    JSONArray jsonArray = new JSONArray(response);
                    listMasterData.clear();

                    // menambahkan data dari JSON array ke objek MasterDataModel
                    for (int interasi=0; interasi< jsonArray.length(); interasi++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(interasi);

                        String produk_id = jsonObject.getString("produk_id");
                        String produk_kode = jsonObject.getString("produk_kode");
                        String produk_kat_id = jsonObject.getString("produk_kat_id");
                        String produk_nama = jsonObject.getString("produk_nama");
                        String produk_satuan = jsonObject.getString("produk_satuan");
                        String produk_harga = jsonObject.getString("produk_harga");
                        MasterDataModel masterData = new MasterDataModel(
                                //gambar               //produk_nama   //produk_harga
                                R.drawable.masterdata, produk_nama, produk_harga, Integer.valueOf(produk_kat_id), Integer.valueOf(produk_id), produk_nama, "",  produk_kode,
                                produk_harga,  Integer.valueOf(produk_satuan)
                        );

                         listMasterData.add(masterData);
                    }
//                    masterDataAdapter.setMasterDataModels(listMasterData);
                    masterDataAdapter.notifyDataSetChanged();


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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String returnValue = data.getStringExtra("data");

        Log.e("BARCODE", returnValue);

        if(returnValue.length() > 0){
            getProdukData(returnValue);
        }
    }


    public void getProdukData(final String barcode) {
        Log.e(TAG, "getProdukData  " );
        queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, ConfigDB.URL_GET_PRODUK_BY_BARCODE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, "getProdukData : onResponse" + response);
                List<MasterDataModel> listMasterData2 = new ArrayList<>();

                try {
                     JSONArray jsonArray = new JSONArray(response);

                     for (int interasi=0; interasi< jsonArray.length(); interasi++) {

                         JSONObject jsonObject = jsonArray.getJSONObject(interasi);
                         String produk_id = jsonObject.getString("produk_id");
                         String produk_kode = jsonObject.getString("produk_kode");
                         String produk_kat_id = jsonObject.getString("produk_kat_id");
                         String produk_nama = jsonObject.getString("produk_nama");
                         String produk_satuan = jsonObject.getString("produk_satuan");
                         String produk_harga = jsonObject.getString("produk_harga");
                         String produk_gambar = jsonObject.getString("produk_gambar");
                         MasterDataModel masterData = new MasterDataModel(
                                 R.drawable.masterdata, produk_nama, produk_harga, Integer.valueOf(produk_kat_id), Integer.valueOf(produk_id), produk_nama, produk_gambar, produk_kode,
                                produk_harga,  Integer.valueOf(produk_satuan)
                        );

                        // menambahkan object MasterDataModel ke dalam LIST MasterDataModel
                         masterData.setTotal(1);
                         listMasterData2.add(masterData);

                         App.getInstance().intTotal = Integer.valueOf(masterData.getHargaJual());
                     }

                    App.getInstance().listMasterData.clear();
                    App.getInstance().listMasterData = listMasterData2;


                    Intent i = new Intent(TransaksiActivity.this, PenjualanActivity.class);
                    startActivity(i);


                 } catch (Exception e) {

                    // jika gagal parsing data JSON / ada kesalahan eksekusi
                    UtilsDialog.showToast(getApplicationContext(), "Gagal parsing JSON, pesan : " + e.getMessage());
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
                params.put("barcode",barcode);
                return params;
            }
        };

        queue.add(request);
    }

    public class MasterDataTransaksiAdapter extends RecyclerView.Adapter<MasterDataTransaksiAdapter.ViewHolder> {

        private Context context;
//        private List<MasterDataModel> masterDataModels = new ArrayList<>();

        public MasterDataTransaksiAdapter(Context context) {
            this.context = context;
        }

        //ini nanti isi dari listnya
//        public void setMasterDataModels(List<MasterDataModel> masterDataModelsParam) {
//
//            this.masterDataModels = masterDataModelsParam;
//        }

        //untuk set layout kamu pertama kali
        @NonNull
        @Override
        public MasterDataTransaksiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_master_transaksi_data, parent, false);
            return new MasterDataTransaksiAdapter.ViewHolder(view);
        }


        //untuk mengatur cardview kamu nanti
        public class ViewHolder extends RecyclerView.ViewHolder  {

            private ImageView imageView,imgMinus, imgPlus;
            private TextView txtTitle, txtDesc;
            private LinearLayout btnRow;
            EditText edtCount;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                btnRow = itemView.findViewById(R.id.btnRow);
                imageView = itemView.findViewById(R.id.imgIcon);
                txtTitle = itemView.findViewById(R.id.txtTitle);
                txtDesc = itemView.findViewById(R.id.txtDesc);
                imgMinus = itemView.findViewById(R.id.imgMinus);
                imgPlus = itemView.findViewById(R.id.imgPlus);
                edtCount = itemView.findViewById(R.id.edtCount);

            }


        }

        @Override
        public void onBindViewHolder(@NonNull final MasterDataTransaksiAdapter.ViewHolder viewHolder, final int position) {

            //untuk set gambar, text...
            //position = dari 0 sampai jumlah listnya, ini nanti looping dari 0 sampai jumlah listnya
            final MasterDataModel dataModel = listMasterData.get(position);

            viewHolder.imageView.setImageResource(dataModel.getImgResource());
            viewHolder.txtTitle.setText(dataModel.getTitle());
            viewHolder.txtDesc.setText(dataModel.getDesc());

            viewHolder.imgMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int nilai = listMasterData.get(position).getTotal();
                    if(nilai != 0) {
                        listMasterData.get(position).setTotal(nilai = nilai - 1);
                    }

                    viewHolder.edtCount.setText(String.valueOf(nilai));
                }
            });

            viewHolder.imgPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int nilai = listMasterData.get(position).getTotal();

                    listMasterData.get(position).setTotal(nilai = nilai+1);

                    viewHolder.edtCount.setText(String.valueOf(nilai));
                }
            });

//            viewHolder.btnRow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(context, FormMasterDataActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("idProduk",dataModel.getIdProduk());
//                    intent.putExtra("kat_id",dataModel.getKatId());
//                    intent.putExtra("namaProduk",dataModel.getNamaProduk());
//                    intent.putExtra("kodeProduk",dataModel.getKode());
//                    intent.putExtra("hargaJual",dataModel.getHargaJual());
//                    intent.putExtra("pcs",dataModel.getPcs());
//
//                    context.startActivity(intent);
//                }
//            });
        }

        @Override
        public int getItemCount() {
            //ini nanti jumlah list kamu, sesuaikan jumlah listnya
            return listMasterData.size();
        }

//        @Override
//        public Filter getFilter() {
//            return new Filter() {
//                @Override
//                protected FilterResults performFiltering(CharSequence charSequence) {
//                    String charString = charSequence.toString();
//                    if (charString.isEmpty()) {
//                        contactListFiltered = contactList;
//                    } else {
//                        List<> filteredList = new ArrayList<>();
//                        for (Contact row : contactList) {
//
//                            // name match condition. this might differ depending on your requirement
//                            // here we are looking for name or phone number match
//                            if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence)) {
//                                filteredList.add(row);
//                            }
//                        }
//
//                        contactListFiltered = filteredList;
//                    }
//
//                    FilterResults filterResults = new FilterResults();
//                    filterResults.values = contactListFiltered;
//                    return filterResults;
//                }
//
//                @Override
//                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                    contactListFiltered = (ArrayList<MasterDataModel>) filterResults.values;
//                    notifyDataSetChanged();
//                }
//            };
//        }




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
