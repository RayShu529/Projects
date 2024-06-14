package com.example.capstone.adminSetting;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.capstone.adminBuilding.edit.editBldg;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;

public class BitToArr extends AsyncTask<Bitmap, Void, byte[]> {
    private WeakReference<Context> contextRef;
    private adminSett bldg;
    public BitToArr(Context context,adminSett bldg) {
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
            bldg.updateEvac(bytes);
        }
    }
}
