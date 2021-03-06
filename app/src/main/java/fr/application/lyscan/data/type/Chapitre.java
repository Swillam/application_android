package fr.application.lyscan.data.type;

import android.content.Context;

import java.io.Serializable;

import fr.application.lyscan.data.database.ChapitreDAO;

public class Chapitre implements Serializable {
    private int id;
    private int chapitre_nb;
    private String chapitre_name;
    private int ifRead;
    private static final long serialVersionUID = 0L;

    public Chapitre(int id, int chapitre_nb, String chapitre_name, int ifRead) {
        this.id = id;
        this.chapitre_nb = chapitre_nb;
        this.chapitre_name = chapitre_name;
        this.ifRead = ifRead;
    }

    public int getId() {
        return id;
    }

    public int getChapitre_nb() {
        return chapitre_nb;
    }

    public void setChapitre_nb(int chapitre_nb) {
        this.chapitre_nb = chapitre_nb;
    }

    public String getChapitre_name() {
        if(chapitre_name.equals("")){
            return String.valueOf(chapitre_nb);
        }
        return chapitre_name;
    }

    public void setChapitre_name(String chapitre_name) {
        this.chapitre_name = chapitre_name;
    }


    public int getIfRead() {
        return ifRead;
    }

    void setIfRead() {
        this.ifRead = 0;
    }

    public void setIfReadInDB(int ifRead, Context context) {
        this.ifRead = ifRead;
        ChapitreDAO chapitreDAO = new ChapitreDAO(context);
        chapitreDAO.open();
        chapitreDAO.modify(this);
        chapitreDAO.close();
    }
}


