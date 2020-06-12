package fr.application.lyscan.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import fr.application.lyscan.R;
import fr.application.lyscan.data.database.ChapitreDAO;
import fr.application.lyscan.data.database.DAOBase;
import fr.application.lyscan.data.database.MangaDAO;
import fr.application.lyscan.data.type.Manga;

// save the manga in the database

public class AsyncTaskDB extends AsyncTask<DAOBase, Void, Void> {
    private final WeakReference<Context> _context;
    private final Manga _manga;
    private ProgressDialog progressDialog;
    private final Bitmap _bitmap;

    public AsyncTaskDB(Context context, Manga manga, Bitmap bitmap) {
        this._context = new WeakReference<>(context);
        this._manga = manga;
        this._bitmap = bitmap;
    }

    @Override
    protected void onPreExecute() {
        Context context = this._context.get();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(context.getString(R.string.BdLoad));
        progressDialog.setMessage(context.getString(R.string.wait));
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }


    @Override
    protected Void doInBackground(DAOBase... db) {
        // img save
        String name = API_url.format(_manga.getName_raw());
        Uri uri = new SaveImg().saveimg(_context.get(), name, _bitmap);
        if (uri != null) _manga.setImg_addr(uri.toString());
        MangaDAO mangaDAO = (MangaDAO) db[0];
        mangaDAO.open();
        mangaDAO.add(_manga);
        int id_manga = _manga.getId(mangaDAO);
        mangaDAO.close();

        ChapitreDAO chapitreDAO = (ChapitreDAO) db[1];
        chapitreDAO.open();
        chapitreDAO.add(_manga.getChapitres(), id_manga);
        chapitreDAO.close();
        return null;
    }

    protected void onPostExecute(Void result) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
