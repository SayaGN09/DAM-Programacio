package com.exercici0306;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Port {

    private String nom;
    private List<Vaixell> vaixells;

    public Port(String nom) {
        this.nom = nom;
        this.vaixells = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Vaixell> getVaixells() {
        return vaixells;
    }

    public void afegirVaixell(Vaixell v) {
        vaixells.add(v);
    }

    public void printVaixells() {
        for (Vaixell v : vaixells) {
            System.out.println(v);
        }
    }

    public List<String> validarNormatives() {
        List<String> resultats = new ArrayList<>();
        for (Vaixell v : vaixells) {
            String tipus = v instanceof VaixellPassatgers ? "VaixellPassatgers" : "VaixellMeraderies";
            boolean compleix = ((Reglamentari) v).compleixNormativa();
            resultats.add("Nom: " + v.geNom() + ", Tipus: " + tipus + ", Compleix Normativa: " + compleix);
        }
        return resultats;
    }

    public void printNormatives() {
        List<String> normatives = validarNormatives();
        for (String normativa : normatives) {
            System.out.println(normativa);
        }
    }    

    @Override
    public String toString() {
        return "Port: nom = " + nom + ", vaixells = " + vaixells.size() + ".";
    }
}
