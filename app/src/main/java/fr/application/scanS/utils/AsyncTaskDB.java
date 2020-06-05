package fr.application.scanS.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import fr.application.scanS.R;
import fr.application.scanS.data.database.ChapitreDAO;
import fr.application.scanS.data.database.DAOBase;
import fr.application.scanS.data.database.MangaDAO;
import fr.application.scanS.data.type.Manga;

public class AsyncTaskDB extends AsyncTask<DAOBase, Void, Void> {
    private final WeakReference<Context> _context;
    private final Manga manga;
    private ProgressDialog progressDialog;

    public AsyncTaskDB(Context context, Manga manga) {
        this._context = new WeakReference<>(context);
        this.manga = manga;
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
        MangaDAO mangaDAO = (MangaDAO) db[0];
        mangaDAO.open();
        mangaDAO.add(manga);
        int id_manga = manga.getId(mangaDAO);
        mangaDAO.close();

        ChapitreDAO chapitreDAO = (ChapitreDAO) db[1];
        chapitreDAO.open();
        chapitreDAO.add(manga.getChapitres(), id_manga);
        chapitreDAO.close();
        return null;
    }

    protected void onPostExecute(Void result) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
