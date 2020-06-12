package fr.application.lyscan.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.application.lyscan.data.type.Manga;


public class MangaDAO extends DAOBase{
    private static final String TABLE_NAME = "Manga";
    static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    private static final String KEY = "id_manga";
    private static final String NAME_ENG = "name_eng";
    private static final String NAME_RAW = "name_raw";
    private static final String DESCRIBE = "description";
    private static final String IN_PROGRESS = "in_progress";
    private static final String IMAGE = "img_addr";
    static final String TABLE_CREATE =
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
        mDb.update(TABLE_NAME, value, KEY + " = ?", new String[]{String.valueOf(m.getId(this))});
    }

    public Manga select(int id) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id_manga = ?", new String[]{String.valueOf(id)});
        if (c.getCount() > 0) {
            c.moveToFirst();
            String name_eng = c.getString(1);
            String name_raw = c.getString(2);
            String description = c.getString(3);
            int in_progress = c.getInt(4);
            String img_addr = c.getString(5);
            c.close();
            return new Manga(name_eng, name_raw, description, in_progress, img_addr);
        }
        c.close();
        return null;
    }

    public ArrayList<Manga> selectAll() {
        Cursor c = mDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<Manga> ListManga = new ArrayList<>();
        if (c.getCount() > 0) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                String name_eng = c.getString(1);
                String name_raw = c.getString(2);
                String description = c.getString(3);
                int in_progress = c.getInt(4);
                String img_addr = c.getString(5);
                ListManga.add(new Manga(name_eng, name_raw, description, in_progress, img_addr));
            }
            c.close();
            return ListManga;
        }
        c.close();
        return new ArrayList<>();
    }

    public int getIdManga(String name_raw) {
        Cursor c = mDb.rawQuery("select " + KEY + " from " + TABLE_NAME + " where " + NAME_RAW + " = ?", new String[]{name_raw});
        if (c.getCount() > 0) {
            c.moveToFirst();
            return c.getInt(0);
        }
        c.close();
        return -1;
    }

}




