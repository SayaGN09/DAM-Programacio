package com.exercici0306;

public class VaixellPassatgers extends Vaixell implements Reglamentari {

    private int numPassatgers;
    private int maxPassatgers;

    public VaixellPassatgers(String nom, double capacitat, int maxPassatgers) {
        super(nom, capacitat);
        this.numPassatgers = 0;
        this.maxPassatgers = maxPassatgers;
    }

    public int getNumPassatgers() {
        return numPassatgers;
    }

    public void setNumPassatgers(int numPassatgers) {
        this.numPassatgers = numPassatgers;
    }

    public int getMaxPassatgers() {
        return maxPassatgers;
    }

    public void setMaxPassatgers(int maxPassatgers) {
        this.maxPassatgers = maxPassatgers;
    }

    public void afegirPassatger() {
        if (numPassatgers >= maxPassatgers) {
            throw new IllegalStateException("No es poden afegir mes passatgers");
        }
    }

    @Override
    public boolean compleixNormativa() {
        return numPassatgers <= maxPassatgers;
    }

    @Override
    public String toString() {
        return "VaixellMercaderies: Nom = " + nom + ", capacitat = " + capacitat +", numPassatgers = " + numPassatgers + "maxPassatgers = " + maxPassatgers + ".";
    }
}
