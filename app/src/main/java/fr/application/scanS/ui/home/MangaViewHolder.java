package fr.application.scanS.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import javax.net.ssl.HttpsURLConnection;

import fr.application.scanS.R;
import fr.application.scanS.data.DAO.MangaDAO;
import fr.application.scanS.data.Type.Chapitre;
import fr.application.scanS.data.Type.Manga;

public class MangaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView _titreManga, _chapitreName, _chapitreNb;
    private final ImageView _img;
    private MangaAdapter.OnMangaListener onMangaListener;

    public MangaViewHolder(@NonNull View itemView, MangaAdapter.OnMangaListener mangaListener) {
        super(itemView);
        this._titreManga = itemView.findViewById(R.id.item_title);
        this._chapitreName = itemView.findViewById(R.id.item_chapter_name);
        this._chapitreNb = itemView.findViewById(R.id.item_chapter_nb);
        this._img = itemView.findViewById(R.id.item_image);
        this.onMangaListener = mangaListener;

        itemView.setOnClickListener(this);
    }
    public void layoutForManga(Manga m, Context context){
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


    public class AsyncTaskImg extends AsyncTask<Manga,Integer, Uri> {
        private ImageView img;
        private Context context;
        private String url = "https://lyscanapp-6638bb1c.localhost.run/image.php?image=";


        public AsyncTaskImg(Context context) {
            this.context = context;
        }


        @Override
        protected Uri doInBackground(Manga... mangas) {
            try{
                Uri uri;
                if(mangas[0].getImg_addr() == null){ // si pas d'image en cache enregistré
                    uri = download(mangas[0]);
                    return uri;
                }
                else{
                    uri = Uri.parse(mangas[0].getImg_addr());
                    File imgFile = new File(uri.getPath());
                    if (!(imgFile.exists())) { // si defaut de cache
                        uri = download(mangas[0]);
                    }
                    return uri;
                }
            }

            catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Uri result) {
            _img.setImageURI(result);
        }

        private Uri download(Manga m) throws IOException {
            // download img
            String name = format(m.getName_raw());
            String imgUrl = this.url + name+".jpg";
            Log.i("titre",imgUrl);
            URL url = new URL(imgUrl.toLowerCase());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // save img
            if(bitmap != null ){
                File cache = context.getCacheDir();
                OutputStream fOut;
                File img = new File(cache, name+".jpg");
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
                return uri;}
            return null;
        }
        public String format(String string){
            string = Normalizer.normalize(string, Normalizer.Form.NFD);
            string = string.replaceAll("[^\\p{ASCII}]", "");
            string = string.replace(" ","_");
            string = string.toLowerCase();
            return string;
        }
    }

}
