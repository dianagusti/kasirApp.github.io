package com.example.kasir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kasir.helper.PreferencesUtils;
import com.example.kasir.R;
import com.example.kasir.utils.UtilsDialog;
//kalau engga ke register ada warning orange
public class MainActivity extends AppCompatActivity {

    /*
    * onCreate, pertama kali diakses. di onCreate ada setContentView(R.layout.activity_main); untuk set konten layout
    * gimana? misal;
    *
    *
    * */

    //untuk set konten dan init data view. terserah intinya ini pertama kali diakses :D
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button
        View logOut = findViewById(R.id.logOut);

        //Button logout set onclick
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ga usah gitu mas, alurnya aja dari atas ituu ngapain smpe bawah wkwkw
                PreferencesUtils.setLogin(MainActivity.this, false);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    //ini method generated dari xml onclick
    public void onMenuClicked(View view) {

        int id = view.getId();

        if (id == R.id.cardHealtId) {
            UtilsDialog.showToast(this, "Transaksi clicked!");
        } else if (id == R.id.cardMasterId) {

            Intent intent = new Intent(this, MasterDataActivity.class);
            startActivity(intent);

            UtilsDialog.showToast(this, "Master clicked!");
        } else if (id == R.id.cardInventoriId) {

            UtilsDialog.showToast(this, "Inventori clicked!");
        } else if (id == R.id.cardLaporanId) {

            UtilsDialog.showToast(this, "Laporan clicked!");
        }
    }

    //ini untuk open activity barcodee
    public void openBarcode(View view) {

        startActivity(new Intent(MainActivity.this, BarcodeActivity.class));
        //finish();
    }
}
