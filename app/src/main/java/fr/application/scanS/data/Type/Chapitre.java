package fr.application.scanS.data.Type;

public class Chapitre {
    private int id;
    private int chapitre_nb;
    private String chapitre_name;
    private int ifRead;
    private int id_manga;

    public Chapitre(int id, int chapitre_nb, String chapitre_name,int ifRead ,int id_manga) {
        this.id = id;
        this.chapitre_nb = chapitre_nb;
        this.chapitre_name = chapitre_name;
        this.ifRead = ifRead;
        this.id_manga = id_manga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapitre_nb() {
        return chapitre_nb;
    }

    public void setChapitre_nb(int chapitre_nb) {
        this.chapitre_nb = chapitre_nb;
    }

    public String getChapitre_name() {
        return chapitre_name;
    }

    public void setChapitre_name(String chapitre_name) {
        this.chapitre_name = chapitre_name;
    }

    public int getId_manga() {
        return id_manga;
    }

    public void setId_manga(int id_manga) {
        this.id_manga = id_manga;
    }

    public int getIfRead() {
        return ifRead;
    }

    public void setIfRead(int ifRead) {
        this.ifRead = ifRead;
    }
}


