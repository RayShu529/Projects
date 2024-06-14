package com.example.capstone.adminBuilding.add;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class imgToByte extends AsyncTask<Uri, Void, byte[]> {
    private Context context;
    private OnByteArrayConvertedListener listener;

    public imgToByte(Context context, OnByteArrayConvertedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected byte[] doInBackground(Uri... uris) {
        try {
            Uri uri = uris[0];
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            return byteArray;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(byte[] bytes) {
        if (listener != null) {
            listener.onByteArrayConverted(bytes);
            Log.d("TAG", "SUCCESS  :"+bytes.toString());
        }
        else{
            Log.d("TAG", "ERROR  :"+bytes.toString());
        }
    }

    public interface OnByteArrayConvertedListener {
        void onByteArrayConverted(byte[] bytes);
    }
}

