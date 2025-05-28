package org.example.model;

import java.util.List;

public class CosComanda {
    private List<Produs> listaProduse;
    private double total;

    public CosComanda(){}
    public CosComanda(List<Produs> listaProduse, double total) {
        this.listaProduse = listaProduse;
        this.total = total;
    }

    public List<Produs> getListaProduse() {
        return listaProduse;
    }

    public void setListaProduse(List<Produs> listaProduse) {
        this.listaProduse = listaProduse;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void adaugaProdus(Produs p) {
        listaProduse.add(p);
        total=total+p.getPret();
    }



    @Override
    public String toString() {
        return "CosComanda{" +
                "listaProduse=" + listaProduse +
                ", total=" + total +
                '}';
    }

    public void clear() {
        listaProduse.clear();
        total=0;
    }


}

