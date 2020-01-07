package com.example.kasir.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kasir.helper.PreferencesUtils;
import com.example.kasir.helper.RequestHandler;
import com.example.kasir.helper.SqliteHelper;
import com.example.kasir.ConfigDB;
import com.example.kasir.R;
import com.example.kasir.utils.UtilsDialog;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    //Declaration TextInputLayout
    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;

    //Declaration EditTexts
    EditText editTextEmail;
    EditText editTextPassword;

    //Declaration Button
    Button buttonLogin;


    // ini oncreate pertama kali
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //# 1
        checkIsLogin();

        //# 2
        setContentView(R.layout.activity_login); //xml layout

        //# 3
        initCreateAccountTextView();

        //# 4
        initViews();

        //set click event of login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check user input is correct or not
                if (validate()) { //passed :D

                    //Get values from EditText fields
                    final String email = editTextEmail.getText().toString(); //passed :D
                    final String password = editTextPassword.getText().toString(); //passed :D

                    loginUser(email, password); //ini?variable wkwkw yg isinya email sma pass nya, ini method jangan lupa.
                }
            }
        });

    }

    //# 1
    private void checkIsLogin() {

        //mengecek masih login/engga, ini value dibuat dan diambil dari lokal penyimpanan
        if (PreferencesUtils.isLogin(this)) {

            //jika isLogin == true, buka MainActivity
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

            //activity dari LoginActivity finish/didestroy
            finish();
        }
    }

    //# 3
    //this method used to set Create account TextView text and click event( maltipal colors
    // for TextView yet not supported in Xml so i have done it programmatically)
    private void initCreateAccountTextView() {
        TextView textViewCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);
        textViewCreateAccount.setText(fromHtml("<font color='#ffffff'>I don't have account yet. </font><font color='#0c0099'>create one</font>"));
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    //# 4
    //this method is used to connect XML views to its Objects
    private void initViews() {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

    }

    private void loginUser(final String email, final String password) {

        //ini inner class. tau?engga. didalam class ada class. seharusnya static biar performance lebih baik.

        loginUser ae = new loginUser(email, password);
        /*
        selesai :D, ayok jelasin wkwkw. tau maksunya ini? tau. apa?engga tau aja wkwk ga yakin aku wkwk
        ini membuat obyek baru. obyek loginUser, email dan password sebagai parameter di constructornya. tau kan? iya tau
        sip. ini obyek asyntask namanya. berjalan di background.
        */
        ae.execute();
        /*
        ini untuk execute task untuk menjalankan task di background. tau?. background itu seperti berjalan di luar.
        jadi tidak mempengaruhi kinerja UI. iya mas tau. mari kita buka loginUser nya...
        */
    }

    //This method is for handling fromHtml method deprecation
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        //cek apakah email valid engga, misal format nama@domain.com. harus valid
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            /*
            perhatikan tanda !, jangan sampai salah. tau kan?iya tidak kan tndanya
            tanda ! artinya not. misal nilai true, kalau !true jadi false.
            ini nilainya true ("android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()"), karena ! jadi false.
            bahasa indonesianya if not true = false, maka..., !android.util.Patterns
            ini pattern dari android untuk mempermudah. Patterns.EMAIL_ADDRESS.matcher(NAMA_EMAIL).matches() artinya jika match/valid.
            emang bentuknya gitu kalo pattern d android? iya itu akses function, nilainya kan boolean. next?iya
            jika eengga valid setError nanti muncul di input emailnya
            */
            valid = false;
            textInputLayoutEmail.setError("Please enter valid email!");
        } else {
            valid = true;
            textInputLayoutEmail.setError(null);
        }

        /*
        Handling validation for Password field,
        aku yakin kamu udah paham. hehe. gimana?udah hehe. shiap.. mana lagi?. setelah ini apa emangnya? wkkwk
        tinggal diurutkan, ini kan method validate. digunakan untuk apa, dicari dulu. tadi apa hayo,buat validasi inputan email + pass kan?
        yups gans.next... wkwkkw
        */
        if (Password.isEmpty()) {
            valid = false;
            textInputLayoutPassword.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                textInputLayoutPassword.setError(null);
            } else {
                valid = false;
                textInputLayoutPassword.setError("Password is to short!");
            }
        }

        /*
        ini bukan aku yang ngetik hlo. hayo apa? kan aku copas dari gatau lupa hehe :D
        yang engga tau yang mana? eh engga bentar aku baca hehe, yg dri public boolean validate kan?
        iya baca itu kan 1 method/function,okayy bentar,,
        */

        return valid;
    }

    /*
    gausah static dulu. banyak yang perlu diubah kamu bingung nanti. :D, iya td ku juga maubilan gitu, tusemua merah oren2
    ini merah karena aku keluarkan dari method. harusnya class tidak boleh ada didalam method. enga usah d ubah2 gpp mas wkwkw
    */
    private class loginUser extends AsyncTask<Void,Void,String> {

        ProgressDialog loading;
        private String email;
        private String password;

        loginUser(String email, String password) {
            this.email = email;
            this.password = password;
        }

        //sebelum task dijalankan, loading progress
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(LoginActivity.this,
                    "Login...","Tunggu...",false,false);
        }

        //task sedang berjalan di background, untuk request ke server php
        @Override
        protected String doInBackground(Void... v) {
            //ini berupa map<String, String>. ini nya params email dan password
            HashMap<String,String> params = new HashMap<>();

            params.put(ConfigDB.KEY_EMP_EMAIL, email);
            params.put(ConfigDB.KEY_EMP_PASSWORD, password);

            // init obyek
            RequestHandler rh = new RequestHandler();

            //request ke php server dengan params diatas.ini yg d maksud params yg mana?okayy mas engga usah detail2. ini agak expert :D
            return rh.sendPostRequest(ConfigDB.URL_LOGIN, params);
        }

        //task selesai dijalankan, berisi response dari request http. (berdasarkan php nya)
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            //dismiss loading.
            loading.dismiss();

            Log.d(TAG, "pesan : " + response);

            if (response == null) {
                UtilsDialog.showToast(getApplicationContext(), "Ada kesalahan, pesan : " + response);
                return;
            }

            if (response.startsWith("Error")) {
                UtilsDialog.showToast(getApplicationContext(), "Ada kesalahan, pesan : " + response);
                return;
            }

            /*
            * sampai disini ngerti?ngerti in syaa Allah, next. hehe.
            * */
            try {
                /*
                * ini json objek di java, untuk parse response json string/text kedalam bentuk objek.
                * */
                JSONObject jsonObject = new JSONObject(response);

                //coba get objek dengan key "responseCode". tau kan? hayo, engga tau hehe. ini hlo. penting tau maksudnya dulu.
                Object objResponseCode = jsonObject.get("responseCode");

                //jika objek instance dari Integer, maksunya objek dari superclass integer/ parent katakanlah.
                if (objResponseCode instanceof Integer) {
                    //ubah objek ke integer
                    Integer responseCode = ((Integer) objResponseCode);

                    //jika responseCode == 200, kan tadi ada 0 ada 200 aku tulis.
                    if (responseCode == 200) {

                        //login berhasil/valid
                        UtilsDialog.showToast(getApplicationContext(), "Login user berhasil");

                        //simpan data boolean login = true, ke preferences lokal
                        PreferencesUtils.setLogin(LoginActivity.this, true);

                        //memulai activity MainActivity baru
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                        //akhiri activity LoginActivity. ok mas next...
                        finish();
                        return; // return == berhenti
                    }
                }

                //login user gagal..
                UtilsDialog.showToast(getApplicationContext(), "Login user gagal");
            } catch (Exception e) {

                //ada kesalahan, kemungkinan gagal ubah json response ke object.
                UtilsDialog.showToast(getApplicationContext(), "Gagal parsing JSON");
                Log.e(TAG, "Gagal parsing JSON :" + e);
                e.printStackTrace();
            }
        }
    }
}