package org.example.model;

import java.util.List;
import java.util.UUID;


public class Produs {
    private String idProdus;
    private Categorie categorie;
    private String nume;
    private double pret;
    private List<String> ingrediente;
    private boolean disponibil;

    public Produs(Categorie categorie, String nume, double pret, List<String> ingrediente, boolean disponibil) {
        super();
        this.idProdus = UUID.randomUUID().toString(); //genereaza automat un id Unic
        this.categorie = categorie;
        this.nume = nume;
        this.pret = pret;
        this.ingrediente = ingrediente;
        this.disponibil = disponibil;
    }

    public Produs(){}



    public String getIdProdus() {
        return idProdus;
    }



    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public List<String> getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(List<String> ingrediente) {
        this.ingrediente = ingrediente;
    }

    public boolean isDisponibil() {
        return disponibil;
    }

    public void setDisponibil(boolean disponibil) {
        this.disponibil = disponibil;
    }

    public String afisareProdusPret() {
        return nume + " " + pret;
    }

    @Override
    public String toString() {
        return "Produs [idProdus=" + idProdus + ", categorie=" + categorie + ", nume=" + nume + ", pret=" + pret
                + ", ingrediente=" + ingrediente + ", disponibil=" + disponibil + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Produs produs = (Produs) o;

        return idProdus == produs.idProdus;
    }

}
