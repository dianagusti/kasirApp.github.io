package com.example.kasir;

public class ConfigDB {

    private static final String HOST = "http://192.168.0.251/";

    public static final String URL_REGISTER = HOST + "skripsi_diana/register.php";
    public static final String URL_LOGIN = HOST + "skripsi_diana/login.php";
    public static final String URL_GET_KATEGORI = HOST + "skripsi_diana/get_kategori.php";
    public static final String URL_GET_PRODUK = HOST + "skripsi_diana/get_produk.php";
    public static final String URL_INSERT_PRODUK = HOST + "skripsi_diana/insert_produk.php";

    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_NAMA = "nama";
    public static final String KEY_EMP_EMAIL = "email";
    public static final String KEY_EMP_PASSWORD = "password";

    public static final String KEY_EMP_KATEGORI_PRODUK = "kat_nama";
    public static final String KEY_EMP_NAMA_PRODUK = "produk_nama";
    public static final String KEY_EMP_KODE_PRODUK= "produk_kode";
    public static final String KEY_EMP_HARGA_PRODUK = "produk_harga";
    public static final String KEY_EMP_SATUAN_PRODUK = "produk_satuan";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
}
