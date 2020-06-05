package fr.application.scanS.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.text.Normalizer;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import fr.application.scanS.R;
import fr.application.scanS.data.database.MangaDAO;
import fr.application.scanS.data.type.Manga;

public class AsyncTaskImg extends AsyncTask<Manga, Void, Uri> {

    private final String img_url = API_url.url + "image.php?image=";
    private final WeakReference<Context> _context;
    private final WeakReference<ImageView> _img;
    private final WeakReference<ProgressBar> _loadimg;
    private String _titreManga;

    public AsyncTaskImg(Context context, ImageView img, ProgressBar loadimg) {
        this._context = new WeakReference<>(context);
        this._img = new WeakReference<>(img);
        this._loadimg = new WeakReference<>(loadimg);
    }


    @Override
    protected Uri doInBackground(Manga... mangas) {
        try {
            _titreManga = mangas[0].getName();
            Uri uri;
            if (mangas[0].getImg_addr() == null) { // no image in cache saved
                uri = download(mangas[0]);
            } else {
                uri = Uri.parse(mangas[0].getImg_addr());
                File imgFile = new File(Objects.requireNonNull(uri.getPath()));
                if (!(imgFile.exists())) { // if cache failure
                    uri = download(mangas[0]);
                }
            }
            return uri;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Uri result) {
        if (_loadimg.get() != null) {// if the fragment is still running
            _loadimg.get().setVisibility(View.INVISIBLE);
            Context context = this._context.get();
            if (result == null) {
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, context.getString(R.string.error_img) + "manga " + _titreManga, duration);
                toast.show();
            } else {
                this._img.get().setImageURI(result);
            }
        }
    }

    private Uri download(Manga m) throws IOException {
        // download img
        String name = format(m.getName_raw());
        String imgUrl = this.img_url + name + ".jpg";
        URL url = new URL(imgUrl);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        // save img
        Context context = this._context.get();
        File cache = context.getCacheDir();
        OutputStream fOut;
        File img = new File(cache, name + ".jpg");
        fOut = new FileOutputStream(img);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
        fOut.flush();
        fOut.close();

        // save the uri to the db
        Uri uri = Uri.fromFile(img);
        m.setImg_addr(uri.toString());
        MangaDAO mangaDAO = new MangaDAO(context);
        mangaDAO.open();
        mangaDAO.modify(m);
        mangaDAO.close();
        return uri;
    }

    // remove accent, the escape and lower letters
    String format(String string) {
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        string = string.replaceAll("[^\\p{ASCII}]", "");
        string = string.replace(" ", "_");
        string = string.toLowerCase();
        return string;
    }
}
