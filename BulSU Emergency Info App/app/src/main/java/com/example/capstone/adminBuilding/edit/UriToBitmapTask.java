package com.example.capstone.adminBuilding.edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

public class UriToBitmapTask extends AsyncTask<Uri, Void, Bitmap> {
    private WeakReference<Context> contextRef;
    private OnBitmapLoadedListener listener;

    public UriToBitmapTask(Context context, OnBitmapLoadedListener listener) {
        this.contextRef = new WeakReference<>(context);
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(Uri... uris) {
        try {
            Context context = contextRef.get();
            if (context != null) {
                InputStream inputStream = context.getContentResolver().openInputStream(uris[0]);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            listener.onBitmapLoaded(bitmap);
        }
    }

    public interface OnBitmapLoadedListener {
        void onBitmapLoaded(Bitmap bitmap);
    }
}

