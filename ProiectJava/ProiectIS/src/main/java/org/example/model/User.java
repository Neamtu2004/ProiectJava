package org.example.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class User {
    private String email;
    private String password;
    private Rol rol;

    public User(String email, String password, Rol rol) {
        super();
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public User(){}
    public String getEmail() {
        return email;
    }

    public void afisareComenziNefinalizate() {
    }

    public void updateStareComanda(Comanda c, String s) {
        c.setStatus(s);
    }

    public void setTimpEstimareComanda(Comanda c, int minute) {
        c.setTimpEstimare(minute);
        System.out.println("Timp estimat setat: " + minute + " minute");
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", password=" + password + ", rol=" + rol + "]";
    }

    public static void salvareFisier(List<User> lista) {
        try {
            ObjectMapper mapper=new ObjectMapper();
            File file=new File("src/main/resources/utilizatori.json");
            mapper.writeValue(file,lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<User> citireFisier() {
        try {
            File file=new File("src/main/resources/utilizatori.json");
            ObjectMapper mapper=new ObjectMapper();
            List<User> useri = mapper
                    .readValue(file, new TypeReference<List<User>>(){});
            return useri;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




}