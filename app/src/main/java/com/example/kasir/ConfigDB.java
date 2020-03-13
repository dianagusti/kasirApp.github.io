package com.example.kasir;

public class ConfigDB {


//    private static final String HOST = "http://192.168.14.30/"; '

//    private static final String HOST = "http://192.168.0.101/";     //wifi
    private static final String HOST = "http://192.168.99.24/";     //wifi



    public static final String URL_PHOTO = HOST+"skripsi_diana/upload/";



//    private static final String HOST = "http://192.168.43.3/";

    public static final String URL_REGISTER = HOST + "skripsi_diana/register.php";
    public static final String URL_LOGIN = HOST + "skripsi_diana/login.php";
    public static final String URL_GET_PRODUK_BY_BARCODE = HOST + "skripsi_diana/get_produk_by_barcode.php";
    public static final String URL_GET_KATEGORI = HOST + "skripsi_diana/get_kategori.php";
    public static final String URL_GET_PRODUK = HOST + "skripsi_diana/get_produk.php";
    public static final String URL_INSERT_PRODUK = HOST + "skripsi_diana/insert_produk.php";
    public static final String URL_EDIT_PRODUK = HOST + "skripsi_diana/edit_produk.php";



    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_NAMA = "nama";
    public static final String KEY_EMP_EMAIL = "email";
    public static final String KEY_EMP_PASSWORD = "password";

    public static final String KEY_EMP_KATEGORI_GAMBAR = "produk_gambar";
    public static final String KEY_EMP_KATEGORI_PRODUK_ID = "kat_id";
    public static final String KEY_EMP_KATEGORI_PRODUK = "kat_nama";
    public static final String KEY_EMP_ID_PRODUK = "produk_id";
    public static final String KEY_EMP_NAMA_PRODUK = "produk_nama";
    public static final String KEY_EMP_KODE_PRODUK= "produk_kode";
    public static final String KEY_EMP_HARGA_PRODUK = "produk_harga";
    public static final String KEY_EMP_SATUAN_PRODUK = "produk_satuan";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
}
