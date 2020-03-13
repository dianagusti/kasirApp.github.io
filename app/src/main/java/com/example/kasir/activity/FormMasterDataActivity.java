package com.example.kasir.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kasir.ConfigDB;
import com.example.kasir.R;
import com.example.kasir.helper.RequestHandler;
import com.example.kasir.utils.UtilsBitmap;
import com.example.kasir.utils.UtilsDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormMasterDataActivity extends AppCompatActivity {

    private static final String TAG = "TambahDataMasterActivit";

    public static EditText edtInputKode;
    private EditText edtInputMasukKategori, edtInputNamaProduk, edtInputHargaJual, edtInputSatuan, edtIdProduk;
    private Spinner spinnerKategori;
    private final int SELECT_PHOTO = 123;
    private ImageView imgIcon;
    private Bitmap bitmap;
    private Button btnHapusMaster, btnEditMaster, btnSimpanMaster;
    private Toolbar mToolbar;

    private int GALLERY = 1, CAMERA = 2;
    Bitmap FixBitmap;

    String ImageTag = "image_tag" ;

    String ImageName = "image_data" ;

    ByteArrayOutputStream byteArrayOutputStream ;

    byte[] byteArray ;

    String ConvertImage ;
    private ProgressDialog progressDialog;
    private URL url;
    private HttpURLConnection httpURLConnection;

    OutputStream outputStream;

    BufferedWriter bufferedWriter ;

    int RC ;

    boolean check = true;

    BufferedReader bufferedReader ;

    StringBuilder stringBuilder;
    private Button btnScan;
    private RequestQueue queue;

    private List<DataSpinner> dataSpinnerKategoriList = new ArrayList<>();
    private int intKat_id = 0;
    private int index_kat = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data_master);

        byteArrayOutputStream = new ByteArrayOutputStream();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));

        imgIcon = (ImageView) findViewById(R.id.imgIcon);
        spinnerKategori = (Spinner) findViewById(R.id.TypeKategori);

        edtIdProduk = (EditText) findViewById(R.id.edtIdProduk);
        edtInputMasukKategori = (EditText) findViewById(R.id.MasukKategori);
        edtInputNamaProduk = (EditText) findViewById(R.id.NamaProduk);
        edtInputKode = (EditText) findViewById(R.id.Kode);
        edtInputHargaJual = (EditText) findViewById(R.id.HargaJual);
        edtInputSatuan = (EditText) findViewById(R.id.Satuan);

        btnHapusMaster = (Button) findViewById(R.id.HapusMaster);
        btnEditMaster = (Button) findViewById(R.id.EditMaster);
        btnSimpanMaster = (Button) findViewById(R.id.SimpanMaster);
        btnScan = (Button) findViewById(R.id.btnScan);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormMasterDataActivity.this, ScanBarcodeActivity.class);
                intent.putExtra("isFromTransaksi", false);
                startActivity(intent);
            }
        });



        Intent intent = getIntent();
        int idProduk = intent.getIntExtra("idProduk", 0);
        intKat_id = intent.getIntExtra("kat_id", 0);
        String gambarProduk = intent.getStringExtra("gambarProduk");
        String namaProduk = intent.getStringExtra("namaProduk");
        String kodeProduk = intent.getStringExtra("kodeProduk");
        String hargaJual = intent.getStringExtra("hargaJual");
        int pcs = intent.getIntExtra("pcs", 0);

        if(idProduk > 0){
            edtIdProduk.setText(String.valueOf(idProduk));
            edtInputNamaProduk.setText(namaProduk);
            edtInputKode.setText(kodeProduk);
            edtInputHargaJual.setText(hargaJual);
            edtInputSatuan.setText(String.valueOf(pcs));

            btnSimpanMaster.setVisibility(View.GONE);
            edtIdProduk.setVisibility(View.VISIBLE);

            Glide.with(getApplicationContext())
                    .load(ConfigDB.URL_PHOTO+gambarProduk)
                    .placeholder(R.drawable.ic_add)
                    .into(imgIcon);
            Log.e("TAG_URL_PHOTO", ConfigDB.URL_PHOTO+gambarProduk);


        }else{
            btnHapusMaster.setVisibility(View.GONE);
            btnEditMaster.setVisibility(View.GONE);
            edtIdProduk.setVisibility(View.GONE);
        }

        btnHapusMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnEditMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doEditProduct();
            }
        });

        getKategori();


        btnSimpanMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpanData();
            }
        });
    }

//    public static Bitmap getBitmapFromURL(String src, Activity activity) {
//        try {
////            URL url = new URL(src);
////            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
////            connection.setDoInput(true);
////            connection.connect();
////            InputStream input = connection.getInputStream();
////            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//
////            Bitmap theBitmap = Glide.
////                    with(activity).
////                    load(src).
////                    asBitmap().
////                    into(100, 100). // Width and height
////                    get();
//
//
//            return myBitmap;
//        } catch (IOException e) {
//            // Log exception
//            return null;
//        }
//    }

    private void initSpinnerKategori(List<String> paths) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),
                R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(adapter);
        spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(intKat_id> 0){
            spinnerKategori.setSelection(index_kat);
        }
    }

//    private void getKategori() {
//
//        new getKategori().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//    }

    public void SimpanData() {


        doInsertProduct();
    }

    public void pickImage(View view) {
//        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//        photoPickerIntent.setType("image/*");
//        startActivityForResult(photoPickerIntent, SELECT_PHOTO);

        showPictureDialog();

    }


    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery",
                "Camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();

                    imgIcon.setImageBitmap(FixBitmap);

                    try {
                        FixBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);

                        Glide.with(this)
                                .load(FixBitmap)
                                .apply(new RequestOptions().centerCrop())
                                .into(imgIcon);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


            }

        } else if (requestCode == CAMERA) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
            try {

                Glide.with(this).load(FixBitmap)
                        .apply(new RequestOptions().centerCrop())
                        .into(imgIcon);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            /*String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            imgIcon.setImageBitmap(bitmap);

            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();*/


            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), pickedImage);

                Glide.with(this).load(bitmap)
                        .apply(new RequestOptions().centerCrop())
                        .into(imgIcon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void getKategori() {


        queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, ConfigDB.URL_GET_KATEGORI, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Log.e(TAG, "pesan : " + s);

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
                        String kat_id = jsonObject.getString("kat_id");
                        String namaKategori = jsonObject.getString("kat_nama");

                        dataList.add(namaKategori);

                        dataSpinnerKategoriList.add(new DataSpinner(kat_id, namaKategori));

                        if(intKat_id == Integer.parseInt(kat_id)){
                            index_kat = i;
                        }
                    }



                    initSpinnerKategori(dataList);

//                UtilsDialog.showLongToast(getApplicationContext(), "berhasil lihat kategori : " + jsonArray.toString());
                    Log.e(TAG, "onPostExecute: berhasil lihat kategori : " + jsonArray.toString());
                } catch (Exception e) {
                    UtilsDialog.showToast(getApplicationContext(), "Gagal parsing JSON, pesan : " + e.getMessage());
                    Log.e(TAG, "Gagal parsing JSON :" + e);
                    e.printStackTrace();
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


    public void doInsertProduct() {

        final String imageValue = UtilsBitmap.getStringImage(FixBitmap);

        final String kat_id = dataSpinnerKategoriList.get(spinnerKategori.getSelectedItemPosition()).getId();

        final String namaProduk = edtInputNamaProduk.getText().toString();
        final String kode = edtInputKode.getText().toString();
        final String hargaJual = edtInputHargaJual.getText().toString();
        final String satuan = edtInputSatuan.getText().toString();

        if (kat_id.isEmpty()) {
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

        queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, ConfigDB.URL_INSERT_PRODUK, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

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
                            bitmap = null;
                            finish();
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("error", error.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("image_data", imageValue);
//                params.put("image_data", "");
                params.put("image_tag", UtilsDialog.getRandom());
                params.put(ConfigDB.KEY_EMP_KATEGORI_PRODUK_ID, kat_id);
                params.put(ConfigDB.KEY_EMP_NAMA_PRODUK, namaProduk);
                params.put(ConfigDB.KEY_EMP_KODE_PRODUK, kode);
                params.put(ConfigDB.KEY_EMP_HARGA_PRODUK, hargaJual);
                params.put(ConfigDB.KEY_EMP_SATUAN_PRODUK, satuan);
                return params;
            }
        };

        queue.add(request);
    }



    public void doEditProduct() {

        DataSpinner s = new DataSpinner();

        String imageValue = "";

        if(FixBitmap != null){
            imageValue = UtilsBitmap.getStringImage(FixBitmap);
        }


        final String kat_id = dataSpinnerKategoriList.get(spinnerKategori.getSelectedItemPosition()).getId();

        final String idProduk = edtIdProduk.getText().toString();
        final String namaProduk = edtInputNamaProduk.getText().toString();
        final String kode = edtInputKode.getText().toString();
        final String hargaJual = edtInputHargaJual.getText().toString();
        final String satuan = edtInputSatuan.getText().toString();

        if (kat_id.isEmpty()) {
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

        queue = Volley.newRequestQueue(this);

        final String finalImageValue = imageValue;
        StringRequest request = new StringRequest(Request.Method.POST, ConfigDB.URL_EDIT_PRODUK, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

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
                            UtilsDialog.showToast(getApplicationContext(), "Berhasil update produk");
                            bitmap = null;
                            finish();
                            return;
                        }
                    }

                    UtilsDialog.showToast(getApplicationContext(), "Gagal update produk");
                } catch (Exception e) {
                    UtilsDialog.showToast(getApplicationContext(), "Gagal parsing JSON : " + e.getMessage());
                    Log.e(TAG, "Gagal parsing JSON :" + e);
                    e.printStackTrace();
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

                params.put("image_data", finalImageValue);
                params.put("image_tag", UtilsDialog.getRandom());

                params.put(ConfigDB.KEY_EMP_KATEGORI_PRODUK_ID, kat_id);
                params.put(ConfigDB.KEY_EMP_ID_PRODUK, idProduk);
                params.put(ConfigDB.KEY_EMP_NAMA_PRODUK, namaProduk);
                params.put(ConfigDB.KEY_EMP_KODE_PRODUK, kode);
                params.put(ConfigDB.KEY_EMP_HARGA_PRODUK, hargaJual);
                params.put(ConfigDB.KEY_EMP_SATUAN_PRODUK, satuan);
                return params;
            }
        };

        queue.add(request);
    }

    public class DataSpinner {


        public String id;
        public String name;


        public DataSpinner(){
        }

        public DataSpinner(String id,  String name){

            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
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
