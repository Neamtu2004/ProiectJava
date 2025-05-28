package org.example.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Comanda {
    private int id;
    private List<Produs> produse;
    private String status; // "In asteptare", "In preparare", "Servita"
    private int timpEstimare; // minute
    private String metodaPlata; // "Cash" / "Card"
    private String nrChitanta;

    public Comanda(){}
    public Comanda(int id, List<Produs> produse, String status, int timpEstimare, String metodaPlata, String nrChitanta) {
        this.id = id;
        this.produse = produse;
        this.status = status;
        this.timpEstimare = timpEstimare;
        this.metodaPlata = metodaPlata;
        this.nrChitanta = nrChitanta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Produs> getProduse() {
        return produse;
    }

    public void setProduse(List<Produs> produse) {
        this.produse = produse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTimpEstimare() {
        return timpEstimare;
    }

    public void setTimpEstimare(int timpEstimare) {
        this.timpEstimare = timpEstimare;
    }

    public String getMetodaPlata() {
        return metodaPlata;
    }

    public void setMetodaPlata(String metodaPlata) {
        this.metodaPlata = metodaPlata;
    }

    public String getNrChitanta() {
        return nrChitanta;
    }

    public void setNrChitanta(String nrChitanta) {
        this.nrChitanta = nrChitanta;
    }

    public static void salvareFisier(List<Comanda> lista) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/resources/comenzi.json");
            mapper.writeValue(file, lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Comanda> citireFisier() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/resources/comenzi.json");

            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }

            return mapper.readValue(file, new TypeReference<List<Comanda>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void adaugaComanda(Comanda comandaNoua) {
        List<Comanda> comenzi = citireFisier();
        comenzi.add(comandaNoua);
        salvareFisier(comenzi);
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "id=" + id +
                ", produse=" + produse +
                ", status='" + status + '\'' +
                ", timpEstimare=" + timpEstimare +
                ", metodaPlata='" + metodaPlata + '\'' +
                ", nrChitanta='" + nrChitanta + '\'' +
                '}';
    }
}
