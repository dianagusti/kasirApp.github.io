package com.example.kasir.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;

public class UtilsBarcode {

    private static Bitmap generateQRCode(String data) {
        com.google.zxing.Writer writer = new QRCodeWriter();
        String finaldata = Uri.encode(data, "ISO-8859-1");
        Bitmap mBitmap = null;
        try {
            BitMatrix bm = writer.encode(finaldata, BarcodeFormat.QR_CODE, 350, 350);
            mBitmap = Bitmap.createBitmap(350, 350, Bitmap.Config.ARGB_8888);
            for (int i = 0; i < 350; i++) {
                for (int j = 0; j < 350; j++) {
                    mBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK: Color.WHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return mBitmap;
    }


    public static  Bitmap generateBarCode(String data){
        com.google.zxing.Writer c9 = new Code128Writer();
        Bitmap mBitmap = null;
        try {
            BitMatrix bm = c9.encode(data,BarcodeFormat.CODE_128,350, 350);
            mBitmap = Bitmap.createBitmap(350, 350, Bitmap.Config.ARGB_8888);

            for (int i = 0; i < 350; i++) {
                for (int j = 0; j < 350; j++) {

                    mBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return mBitmap;
    }
}
