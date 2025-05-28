import org.example.model.Categorie;
import org.example.model.Meniu;
import org.example.model.Produs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RaresTest {
    private JTextField txtCategorie;
    private JTextField txtNume;
    private JTextField txtPret;
    private JTextField txtIngrediente;
    private JTextField txtDisponibil;
    private JButton btnAdauga;
    private List<Produs> meniu;

    @BeforeEach
    public void setUp() {
        txtCategorie = new JTextField();
        txtNume = new JTextField();
        txtPret = new JTextField();
        txtIngrediente = new JTextField();
        txtDisponibil = new JTextField();
        btnAdauga = new JButton("Adaugă produs");

        meniu = new ArrayList<>();
        Meniu.salvareFisier(meniu); // Golește fișierul înainte de test
    }

    private void setFields(String categorie, String nume, String pret, String ingrediente, String disponibil) {
        txtCategorie.setText(categorie);
        txtNume.setText(nume);
        txtPret.setText(pret);
        txtIngrediente.setText(ingrediente);
        txtDisponibil.setText(disponibil);
    }

    @Test
    public void testCampuriGoale() {
        setFields("", "", "", "", "");
        assertTrue(txtCategorie.getText().isEmpty());
    }

    @Test
    public void testPretInvalid() {
        setFields("FEL_PRINCIPAL", "Pizza", "abc", "sos, branza", "true");
        Exception ex = assertThrows(NumberFormatException.class, () -> {
            Double.parseDouble(txtPret.getText());
        });
        assertNotNull(ex);
    }

    @Test
    public void testProdusDejaExistent() {
        Produs existent = new Produs(Categorie.FEL_PRINCIPAL, "Pizza", 40.0, List.of("Sos", "Mozzarella"), true);
        meniu.add(existent);
        Meniu.salvareFisier(meniu);

        setFields("FEL_PRINCIPAL", "Pizza", "50.0", "Sos, Sunca", "true");
        List<Produs> lista = Meniu.citireFisier();
        boolean exista = lista.stream().anyMatch(p -> p.getNume().equalsIgnoreCase("Pizza"));
        assertTrue(exista);
    }

    @Test
    public void testAdaugareCuSucces() {
        setFields("DESERT", "Tiramisu", "25.0", "Mascarpone, Cafea", "true");

        Produs p = new Produs(Categorie.APERITIVE, "Tiramisu", 25.0, List.of("Mascarpone", "Cafea"), true);
        meniu.add(p);
        Meniu.salvareFisier(meniu);

        List<Produs> salvate = Meniu.citireFisier();
        assertTrue(salvate.stream().anyMatch(prod -> prod.getNume().equalsIgnoreCase("Tiramisu")));
    }

    @Test
    public void testCategorieInvalida() {
        setFields("INVALID", "ProdusNou", "20", "a, b", "true");
        assertThrows(IllegalArgumentException.class, () -> {
            Categorie.valueOf(txtCategorie.getText());
        });
    }

    @Test
    public void testDisponibilInvalid() {
        setFields("FEL_PRINCIPAL", "Paste", "30.0", "paste, rosii", "maybe");
        boolean rezultat = Boolean.parseBoolean(txtDisponibil.getText());
        assertFalse(rezultat); // deoarece "maybe" nu e true sau false
    }
}
