package fr.application.scanS.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.application.scanS.data.Type.Chapitre;

public class ChapitreDAO extends DAOBase {
    public static final String TABLE_NAME = "Chapitre";
    public static final String KEY = "id_chapitre";
    public static final String CHAPITRE_NB = "chapitre_nb";
    public static final String CHAPITRE_NAME = "chapitre_name";
    public static final String LU = "ifRead";
    public static final String ID_MANGA = "id_manga";


    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY + " INTEGER PRIMARY KEY NOT NULL, " +
                    CHAPITRE_NB + " TEXT, " +
                    CHAPITRE_NAME + " TEXT, "+
                    LU + " INTEGER, "+
                    ID_MANGA + " INTEGER, "+
                    "FOREIGN KEY("+ ID_MANGA +") REFERENCES "+ MangaDAO.TABLE_NAME+"("+ MangaDAO.KEY +
                    "));";

    public ChapitreDAO(Context pContext) {
        super(pContext);
    }


    public void add(Chapitre c) {
        ContentValues value = new ContentValues();
        value.put(KEY,c.getId());
        value.put(CHAPITRE_NB, c.getChapitre_nb());
        value.put(CHAPITRE_NAME, c.getChapitre_name());
        value.put(ID_MANGA, c.getId_manga());
        mDb.insert(TABLE_NAME, null, value);

    }

    public void delete(int id) {
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
    }

    public void modify(Chapitre c) {
        ContentValues value = new ContentValues();
        value.put(CHAPITRE_NB, c.getChapitre_nb());
        value.put(CHAPITRE_NAME, c.getChapitre_name());
        value.put(LU,c.getIfRead());
        mDb.update(TABLE_NAME, value, KEY+'=' + String.valueOf(c.getId()),null);
    }

    public Chapitre select(int id) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id_chapitre = ?", new String[] {String.valueOf(id)});
        c.moveToFirst();
        int chapitre_nb = c.getInt(1);
        String chapitre_name = c.getString(2);
        int ifRead = c.getInt(3);
        int id_manga = c.getInt(4);
        return new Chapitre(id,chapitre_nb,chapitre_name,ifRead,id_manga);
    }
    public ArrayList<Chapitre> getAll(){
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, null);
        ArrayList<Chapitre> ListChapitre = new ArrayList<Chapitre>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int id = c.getInt(0);
            int chapitre_nb = c.getInt(1);
            String chapitre_name = c.getString(2);
            int ifRead = c.getInt(3);
            int id_manga = c.getInt(4);
            ListChapitre.add(new Chapitre(id,chapitre_nb,chapitre_name,ifRead,id_manga));
        }
        c.close();
        return ListChapitre;
    }

    public ArrayList<Chapitre> getChapitres(int id_manga) {
        Cursor c = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_MANGA +" = ?", new String[] {String.valueOf(id_manga)});
        ArrayList<Chapitre> ListChapitre = new ArrayList<Chapitre>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int id = c.getInt(0);
            int chapitre_nb = c.getInt(1);
            String chapitre_name = c.getString(2);
            int ifRead = c.getInt(3);
            ListChapitre.add(new Chapitre(id,chapitre_nb,chapitre_name,ifRead,id_manga));
        }
        c.close();
        return ListChapitre;
    }
}




