package com.example.capstone.adminBuilding.edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.capstone.adminBuilding.add.Buildings;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;

public class BitmapToByteArrayTask extends AsyncTask<Bitmap, Void, byte[]> {
    private WeakReference<Context> contextRef;
    private editBldg bldg;
    public BitmapToByteArrayTask(Context context, editBldg bldg) {
        contextRef = new WeakReference<>(context);
        this.bldg = bldg;
    }

    @Override
    protected byte[] doInBackground(Bitmap... bitmaps) {
        Bitmap bitmap = bitmaps[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    protected void onPostExecute(byte[] bytes) {
        Context context = contextRef.get();
        if (context != null) {
            bldg.send(bytes);
        }
    }
}

