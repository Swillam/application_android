package fr.application.scanS.data.Type;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

public class Manga {
    private int id;
    private String name_eng;
    private String name_raw;
    private String description;
    private int in_progress;
    private String img_addr;
    private ArrayList<Chapitre> chapitres = null ;

    public int getIn_progress() {
        return in_progress;
    }

    public void setIn_progress(int in_progress) {
        this.in_progress = in_progress;
    }

    public Manga(int id, String name_eng, String name_raw, String description, int in_progress, String img_addr) {
        this.id = id;
        this.name_eng = name_eng;
        this.name_raw = name_raw;
        this.description = description;
        this.in_progress = in_progress;
        this.img_addr = img_addr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        Iterator<Chapitre> it = this.chapitres.iterator();
        Chapitre first = null;
        int i =0;
        while(it.hasNext()) {
            if(i==0){
                first = it.next();
                i=1;
            }
            Chapitre c = it.next();
            if(c.getIfRead() == 1){
                return c;
            }
        }
        return first;
    }
}

