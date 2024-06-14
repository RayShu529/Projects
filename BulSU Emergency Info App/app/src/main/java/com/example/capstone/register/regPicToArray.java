package com.example.capstone.register;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.capstone.manageFirstAids.manageFaid;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;

public class regPicToArray extends AsyncTask<Bitmap, Void, byte[]> {
    private WeakReference<Context> contextRef;
    private RegPage reg;
    public regPicToArray(Context context,RegPage reg) {
        contextRef = new WeakReference<>(context);
        this.reg = reg;
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
            reg.send(bytes);
        }
    }
}
