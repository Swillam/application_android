package fr.application.scanS.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.application.scanS.data.type.Chapitre;

public class ChapitreDAO extends DAOBase {
    private static final String TABLE_NAME = "Chapitre";
    static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    private static final String KEY = "id_chapitre";
    private static final String CHAPITRE_NB = "chapitre_nb";
    private static final String CHAPITRE_NAME = "chapitre_name";
    private static final String LU = "ifRead";
    private static final String ID_MANGA = "id_manga";
    static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY + " INTEGER PRIMARY KEY NOT NULL, " +
                    CHAPITRE_NB + " TEXT, " +
                    CHAPITRE_NAME + " TEXT, "+
                    LU + " INTEGER, "+
                    ID_MANGA + " INTEGER, "+
                    "FOREIGN KEY(" + ID_MANGA + ") REFERENCES Manga(id_manga)" +
                    " ON DELETE CASCADE );";

    public ChapitreDAO(Context pContext) {
        super(pContext);
    }

    private void add(Chapitre c, int id_manga) {
        ContentValues value = new ContentValues();
        value.put(CHAPITRE_NB, c.getChapitre_nb());
        value.put(CHAPITRE_NAME, c.getChapitre_name());
        value.put(LU, c.getIfRead());
        value.put(ID_MANGA, id_manga);
        mDb.insert(TABLE_NAME, null, value);
    }

    // add few chapter
    public void add(ArrayList<Chapitre> chapitres, int id_manga) {
        mDb.beginTransaction();
        try {
            for (Chapitre c : chapitres) {
                c.setId_manga(id_manga);
                add(c, id_manga);
            }
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
        }

    }

    public void delete(int id) {
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
    }

    public void modify(Chapitre c) {
        ContentValues value = new ContentValues();
        value.put(CHAPITRE_NB, c.getChapitre_nb());
        value.put(CHAPITRE_NAME, c.getChapitre_name());
        value.put(LU,c.getIfRead());
        mDb.update(TABLE_NAME, value, KEY + '=' + c.getId(), null);
    }

    public Chapitre select(int id) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id_chapitre = ?", new String[] {String.valueOf(id)});
        c.moveToFirst();
        int chapitre_nb = c.getInt(1);
        String chapitre_name = c.getString(2);
        int ifRead = c.getInt(3);
        int id_manga = c.getInt(4);
        c.close();
        return new Chapitre(id,chapitre_nb,chapitre_name,ifRead,id_manga);
    }
    public ArrayList<Chapitre> getAll(){
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, null);
        ArrayList<Chapitre> ListChapitre = new ArrayList<>();
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

    public ArrayList<Chapitre> getMangaChapitres(int id_manga) {
        Cursor c = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_MANGA +" = ?", new String[] {String.valueOf(id_manga)});
        ArrayList<Chapitre> ListChapitre = new ArrayList<>();
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




