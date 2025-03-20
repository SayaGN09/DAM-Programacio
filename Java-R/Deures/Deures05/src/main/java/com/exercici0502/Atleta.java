package com.exercici0502;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Atleta {
    protected int id;
    protected String nom;
    protected int edat;
    protected String pais;
    protected boolean equip;

    public Atleta(int id, String nom, int edat, String pais, boolean equip) {
        this.id = id;
        this.nom = nom;
        this.edat = edat;
        this.pais = pais;
        this.equip = equip;
    }

    public void setNom(String nom) {
        this.nom = nom;
        updateDB();
    }

    public void setEdat(int edat) {
        this.edat = edat;
        updateDB();
    }

    public void setPais(String pais) {
        this.pais = pais;
        updateDB();
    }

    public void setEquip(boolean equip) {
        this.equip = equip;
        updateDB();
    }

    public String getNom() {
        return nom;
    }

    public int getEdat() {
        return edat;
    }

    public String getPais() {
        return pais;
    }

    public boolean isEquip() {
        return equip;
    }

    public void updateDB() {
        String sql = String.format(
            "UPDATE atletes SET nom='%s', edat=%d, pais='%s', equip=%b WHERE id_atleta=%d;",
            nom, edat, pais, equip, id
        );
        AppData.getInstance().update(sql);
    }
}
