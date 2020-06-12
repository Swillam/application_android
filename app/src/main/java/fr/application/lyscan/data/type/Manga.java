package fr.application.lyscan.data.type;

import java.io.Serializable;
import java.util.ArrayList;

import fr.application.lyscan.data.database.MangaDAO;

public class Manga implements Serializable {
    private String name_eng;
    private String name_raw;
    private String description;
    private int in_progress;
    private String img_addr;
    private ArrayList<Chapitre> chapitres = null ;
    private static final long serialVersionUID = 0L;


    public int getIn_progress() {
        return in_progress;
    }

    public void setIn_progress(int in_progress) {
        this.in_progress = in_progress;
    }

    public Manga(String name_eng, String name_raw, String description, int in_progress, String img_addr) {
        this.name_eng = name_eng == null || name_eng.equals("null") ? null : name_eng;
        this.name_raw = name_raw;
        this.description = description;
        this.in_progress = in_progress;
        this.img_addr = img_addr;
    }

    public int getId(MangaDAO mangaDAO) {
        return mangaDAO.getIdManga(name_raw);
    }


    public String getName_eng() {
        return name_eng;
    }

    public void setName_eng(String name_eng) {
        this.name_eng = name_eng;
    }

    public String getName_raw() {
        return name_raw;
    }

    public void setName_raw(String name_raw) {
        this.name_raw = name_raw;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_addr() {
        return img_addr;
    }

    public void setImg_addr(String img_addr) {
        this.img_addr = img_addr;
    }

    public ArrayList<Chapitre> getChapitres() {
        return chapitres;
    }

    public void setChapitres(ArrayList<Chapitre> chapitres) {
        this.chapitres = chapitres;
    }


    public String getName() {
        if(this.name_eng != null){
            return this.name_eng;
        }

        return this.name_raw;
    }

    public Chapitre getChapitrelast() {
        for (Chapitre c : this.chapitres) {
            if (c.getIfRead() == 0) {
                return c;
            }
        }
        return null;
    }

    public void setNoRead() {
        for (Chapitre c : this.chapitres) {
            c.setIfRead();
        }
    }
}

