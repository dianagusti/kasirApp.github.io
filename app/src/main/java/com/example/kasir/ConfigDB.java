package com.example.kasir;

public class ConfigDB {

    private static final String HOST = "http://192.168.43.253/";

    public static final String URL_REGISTER = HOST + "skripsi_diana/register.php";
    public static final String URL_LOGIN = HOST + "skripsi_diana/login.php";

    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_NAMA = "nama";
    public static final String KEY_EMP_EMAIL = "email";
    public static final String KEY_EMP_PASSWORD = "password";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
}
