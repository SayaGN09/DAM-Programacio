package com.exercici0306;

import java.util.List;
import java.util.ArrayList;

public class Vaixell implements Transportable {

    protected String nom;
    protected double capacitat;
    protected List<Carrega> carrega;

    public Vaixell(String nom, double capacitat) {
        this.nom = nom;
        this.capacitat = capacitat;
        this.carrega = new ArrayList<>();    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getCapacitat() {
        return capacitat;
    }
   
    public void setCapacitat(double capacitat) {
        this.capacitat = capacitat;
    }

    public List<Carrega> getCarregues() {
        return carrega;
    }

    public void afegirCarrega(Carrega c) {
        carrega.add(c);
    }

    @Override
    public double getPesTotal() {
        doule pesTotal = 0.0;
        for (Carrega c : carrega) {
            pesTotal += c.getPes();
        }
        return pesTotal;
    }

    @Override
    public String toString() {
        return "Vaixell: Nom = " + nom + ", capacitat = " + capacitat +", pesActual = " + getPesTotal() + ".";
    }
}
