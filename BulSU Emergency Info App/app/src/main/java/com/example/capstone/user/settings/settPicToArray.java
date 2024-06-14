package com.example.capstone.user.settings;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.capstone.register.RegPage;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;

public class settPicToArray extends AsyncTask<Bitmap, Void, byte[]> {
    private WeakReference<Context> contextRef;
    private settings sett;
    public settPicToArray(Context context,settings sett) {
        contextRef = new WeakReference<>(context);
        this.sett = sett;
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
            sett.send(bytes);
        }
    }
}
