package fr.application.lyscan.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SaveImg {
    public final Uri saveimg(Context context, String name, Bitmap img) {
        try {
            File cache = context.getCacheDir();
            OutputStream fOut;
            File imgF = new File(cache, name + ".jpg");
            fOut = new FileOutputStream(imgF);
            img.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            fOut.flush();
            fOut.close();
            return Uri.fromFile(imgF);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
