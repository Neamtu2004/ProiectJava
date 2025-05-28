package org.example.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Meniu {

    List<Produs>produse;

    public Meniu() {
        File file = new File("src/main/resources/produse.json");
        if (file.exists()) {
            produse = citireFisier();
        }
        else {
            produse = new ArrayList<>();
            adaugaProduseImplicite();
            salvareFisier(produse);
        }
    }
    private void adaugaProduseImplicite() {

        produse.add(new Produs(Categorie.BAUTURI_NESPIRTOASE,"Apă plată", 5.0, List.of("Apă"), true));
        produse.add(new Produs(Categorie.BAUTURI_NESPIRTOASE,"Apă minerală", 5.0,  List.of("Apă carbogazoasă"), true));
        produse.add(new Produs(Categorie.BAUTURI_NESPIRTOASE,"Limonadă", 8.0, List.of("Lămâie", "Apă", "Zahăr"), true));


        produse.add(new Produs(Categorie.APERITIVE,"Bruschete cu roșii", 12.0, List.of("Roșii", "Pâine", "Ulei de măsline"), true));
        produse.add(new Produs(Categorie.APERITIVE,"Bruschete pesto", 13.0,List.of("Pesto", "Pâine", "Parmezan"), true));
        produse.add(new Produs(Categorie.APERITIVE,"Bruschete cu somon", 15.0,List.of("Somon afumat", "Pâine", "Crema de brânză"), true));

        produse.add(new Produs(Categorie.FEL_PRINCIPAL,"Supă de pui", 14.0, List.of("Pui", "Morcovi", "Țelină"), true));
        produse.add(new Produs(Categorie.FEL_PRINCIPAL,"Friptură de vită", 25.0, List.of("Vită", "Condimente"), true));
        produse.add(new Produs(Categorie.FEL_PRINCIPAL,"Paste vegetariene", 18.0,  List.of("Paste", "Legume", "Ulei de măsline"), true));
        produse.add(new Produs(Categorie.FEL_PRINCIPAL,"Cartofi wedges", 10.0,List.of("Cartofi", "Condimente"), true));


    }



    public void addProdus(Produs produs) {
        if (!produse.contains(produs)) {
            produse.add(produs);
        } else {
            System.out.println("Produsul există deja în meniu.");
        }
    }
    public void removeProdus(Produs produs){
        if(produse.contains(produs)) {
            produse.remove(produs);
        } else {
            System.out.println("Produsul nu există în meniu.");
        }
    }


    public void afisareMeniu() {
        for (Produs produs : produse) {
            System.out.println(produs);
        }
    }

    public List<Produs> afisareMeniuCategorie(Categorie c) {
        List<Produs>meniuCategorie=new ArrayList<>();
        for(Produs produs: produse) {
            if(c.equals(produs.getCategorie()) && produs.isDisponibil()) {
                meniuCategorie.add(produs);
            }
        }
        return meniuCategorie;
    }
    public Produs getProdusId(String id) {
        for (Produs produs : produse) {
            if (produs.getIdProdus().equalsIgnoreCase(id)) {
                return produs;
            }
        }
        return null;
    }

    public static void salvareFisier(List<Produs> lista) {
        try {
            ObjectMapper mapper=new ObjectMapper();
            File file=new File("src/main/resources/produse.json");
            mapper.writeValue(file,lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<Produs> citireFisier() {
        try {
            File file=new File("src/main/resources/produse.json");
            ObjectMapper mapper=new ObjectMapper();
            List<Produs> produse = mapper
                    .readValue(file, new TypeReference<List<Produs>>(){});
            return produse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

