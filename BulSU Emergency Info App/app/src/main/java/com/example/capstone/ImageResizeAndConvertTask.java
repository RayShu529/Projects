package com.example.capstone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageResizeAndConvertTask extends AsyncTask<Uri, Void, Uri> {
    private static final String TAG = "ImageResizeAndConvertTask";
    private Context context;
    private OnImageResizedAndConvertedListener listener;

    public ImageResizeAndConvertTask(Context context, OnImageResizedAndConvertedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Uri doInBackground(Uri... uris) {
        if (uris.length == 0 || uris[0] == null) {
            return null;
        }

        Uri sourceUri = uris[0];

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(sourceUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // Create a new file to store the resized image in PNG format
            File outputDir = context.getCacheDir();
            File outputFile = File.createTempFile("resized_image", ".png", outputDir);

            OutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fileOutputStream); // PNG format with maximum quality
            fileOutputStream.close();

            return Uri.fromFile(outputFile);
        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Uri resizedAndConvertedUri) {
        if (listener != null) {
            listener.onImageResizedAndConverted(resizedAndConvertedUri);
        }
    }

    public interface OnImageResizedAndConvertedListener {
        void onImageResizedAndConverted(Uri resizedAndConvertedUri);
    }
}

