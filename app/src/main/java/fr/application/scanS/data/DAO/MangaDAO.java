package fr.application.scanS.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.application.scanS.data.Type.Manga;


public class MangaDAO extends DAOBase{
    public static final String TABLE_NAME = "Manga";
    public static final String KEY = "id_manga";
    public static final String NAME_ENG = "name_eng";
    public static final String NAME_RAW = "name_raw";
    public static final String DESCRIBE = "description";
    public static final String IN_PROGRESS = "in_progress";
    public static final String IMAGE = "img_addr";


    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY + " INTEGER PRIMARY KEY NOT NULL, " +
                    NAME_ENG + " TEXT, " +
                    NAME_RAW + " TEXT,"+
                    DESCRIBE + " TEXT,"+
                    IN_PROGRESS+ " INTEGER NOT NULL,"+
                    IMAGE + " TEXT);";

    public MangaDAO(Context pContext) {
        super(pContext);
    }


    public void add(Manga m) {
        ContentValues value = new ContentValues();
        value.put(KEY,m.getId());
        value.put(NAME_ENG, m.getName_eng());
        value.put(NAME_RAW, m.getName_raw());
        value.put(DESCRIBE, m.getDescription());
        value.put(IN_PROGRESS, m.getIn_progress());
        value.put(IMAGE, m.getImg_addr());
        mDb.insert(TABLE_NAME, null, value);

    }

    public void delete(int id) {
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
    }

    public void modify(Manga m) {
        ContentValues value = new ContentValues();
        value.put(NAME_ENG, m.getName_eng());
        value.put(NAME_RAW, m.getName_raw());
        value.put(DESCRIBE, m.getDescription());
        value.put(IN_PROGRESS, m.getIn_progress());
        value.put(IMAGE, m.getImg_addr());
        mDb.update(TABLE_NAME, value, KEY + " = ?",new String[] {String.valueOf(m.getId())});
    }

    public Manga select(int id) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id_manga = ?", new String[] {String.valueOf(id)});
        c.moveToFirst();
        String name_eng = c.getString(1);
        String name_raw = c.getString(2);
        String description = c.getString(3);
        int in_progress = c.getInt(4);
        String img_addr = c.getString(5);
        c.close();
        return new Manga(id,name_eng,name_raw,description,in_progress,img_addr);
    }

    public ArrayList<Manga> selectAll(){
        Cursor c = mDb.rawQuery("SELECT * FROM " + TABLE_NAME , null);
        ArrayList<Manga> ListManga = new ArrayList<Manga>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int id = c.getInt(0);
            String name_eng = c.getString(1);
            String name_raw = c.getString(2);
            String description = c.getString(3);
            int in_progress = c.getInt(4);
            String img_addr = c.getString(5);
            ListManga.add(new Manga(id,name_eng,name_raw,description,in_progress,img_addr));
        }
        c.close();
        return  ListManga;
    }
}




