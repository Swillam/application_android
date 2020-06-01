package fr.application.scanS.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.Normalizer;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import fr.application.scanS.R;
import fr.application.scanS.data.DAO.MangaDAO;
import fr.application.scanS.data.Type.Chapitre;
import fr.application.scanS.data.Type.Manga;

public class MangaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView _titreManga, _chapitreName, _chapitreNb;
    private final ImageView _img;
    private final ProgressBar _loadimg;
    private MangaAdapter.OnMangaListener onMangaListener;

    MangaViewHolder(@NonNull View itemView, MangaAdapter.OnMangaListener mangaListener) {
        super(itemView);
        this._titreManga = itemView.findViewById(R.id.item_title);
        this._chapitreName = itemView.findViewById(R.id.item_chapter_name);
        this._chapitreNb = itemView.findViewById(R.id.item_chapter_nb);
        this._img = itemView.findViewById(R.id.item_image);
        this._loadimg = itemView.findViewById(R.id.load_image);
        this.onMangaListener = mangaListener;

        // you go into manga details fragment when you click in the manga item
        itemView.setOnClickListener(this);
    }

    void layoutForManga(Manga m, Context context) {
        Chapitre c = m.getChapitrelast();
        this._titreManga.setText(m.getName());
        this._chapitreName.setText(c.getChapitre_name());
        this._chapitreNb.setText(String.valueOf(c.getChapitre_nb()));
        new AsyncTaskImg(context).execute(m);
    }

    @Override
    public void onClick(View v) {
        onMangaListener.OnMangaListener(getAdapterPosition());
    }


    @SuppressLint("StaticFieldLeak")
    public class AsyncTaskImg extends AsyncTask<Manga, Void, Uri> {
        private Context context;
        private String url = "https://lyscanapp-a8898b0f.localhost.run/image.php?image=";


        AsyncTaskImg(Context context) {
            this.context = context;
        }


        @Override
        protected Uri doInBackground(Manga... mangas) {
            try {
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
            _loadimg.setVisibility(View.INVISIBLE);
            _img.setImageURI(result);
        }

        private Uri download(Manga m) throws IOException {
            // download img
            String name = format(m.getName_raw());
            String imgUrl = this.url + name + ".jpg";
            URL url = new URL(imgUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // save img
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
            string = string.replace(" ","_");
            string = string.toLowerCase();
            return string;
        }
    }

}
