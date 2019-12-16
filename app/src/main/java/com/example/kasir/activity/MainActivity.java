package com.example.kasir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kasir.helper.PreferencesUtils;
import com.example.kasir.R;
import com.example.kasir.utils.UtilsDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View logOut = findViewById(R.id.logOut);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferencesUtils.setLogin(MainActivity.this, false);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    public void onMenuClicked(View view) {

        int id = view.getId();

        if (id == R.id.cardHealtId) {
            UtilsDialog.showToast(this, "Health clicked!");
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

    public void openBarcode(View view) {

        startActivity(new Intent(MainActivity.this, BarcodeActivity.class));
        //finish();
    }
}
