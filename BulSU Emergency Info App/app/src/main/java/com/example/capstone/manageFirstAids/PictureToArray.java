package com.example.capstone.manageFirstAids;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.capstone.adminSetting.adminSett;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;

public class PictureToArray extends AsyncTask<Bitmap, Void, byte[]> {
    private WeakReference<Context> contextRef;
    private manageFaid faid;
    public PictureToArray(Context context,manageFaid faid) {
        contextRef = new WeakReference<>(context);
        this.faid = faid;
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
            faid.send(bytes);
        }
    }
}

