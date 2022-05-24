package com.example.mygarage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {
    //functia care formeaza imaginea avand ca parametru sirul de byte[]
    public static Bitmap getImage(byte[] image) {

        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    //encodeaza imaginea intr-un byte[] pentru a putea fi salvata in baza de date
    public static byte[] getBytes(InputStream inputStream) throws IOException{
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int length = 0;
        while((length = inputStream.read(buffer)) != -1) {
            byteBuff.write(buffer, 0, length);
        }

        return byteBuff.toByteArray();
    }

    public static void openCarPartsSite(Context context) {

        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.autokarma.ro"));
        context.startActivity(browser);
    }

    public static void findService(Context context) {

        Uri gmmIntentUri = Uri.parse("geo:0,0?q=service");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);
    }
}
