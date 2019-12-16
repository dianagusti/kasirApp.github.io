package com.example.kasir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.kasir.R;
import com.example.kasir.utils.UtilsBarcode;
import com.example.kasir.utils.UtilsDialog;

public class BarcodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

    }

    public void generateBarcode(View view) {

        final EditText inputBarcode = findViewById(R.id.textBarcode);
        final ImageView imageView = findViewById(R.id.barcode);

        final String kodeBarcode = inputBarcode.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UtilsDialog.showToast(getApplicationContext(), "Mencoba generate.");

                        Bitmap bitmap = UtilsBarcode.generateBarCode(kodeBarcode);
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                        }
                        UtilsDialog.showToast(getApplicationContext(), "Selesai generate.");
                    }
                });

            }
        }).start();
    }
}
