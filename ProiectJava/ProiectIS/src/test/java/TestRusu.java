import org.example.model.Categorie;
import org.example.model.Comanda;
import org.example.model.Produs;
import org.example.ui.ViewStaff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestRusu {
    private ViewStaff viewStaff;
    private List<Comanda> lista;

    @BeforeEach
    public void setUp(){
        viewStaff=new ViewStaff();

        Produs p2=new Produs(Categorie.APERITIVE,"Bruschete pesto", 13.0,List.of("Pesto", "Pâine", "Parmezan"), true);
        Produs p1=new Produs(Categorie.FEL_PRINCIPAL, "Pizza", 45.99, List.of("Aluat", "sos rosi", "mozarella", "sunca"),false);

        Comanda comanda = new Comanda(1,List.of(p1,p2), "in așteptare", 15, "Cash", "CHSDAEFEL");
        Comanda comanda2 = new Comanda(2,List.of(p1), "Servita", 0, "Card", "CHSDFGFLEWEL");

        lista=new ArrayList<>();
        lista.add(comanda);
        lista.add(comanda2);

        viewStaff.listaComenzi=lista;
    }

    @Test
    public void testIncarcaTabelDoarNeservite() {
        viewStaff.incarcaTabelComenzi();
        int rows = viewStaff.ordersTable.getRowCount();
        assertEquals(1, rows);

    }
    @Test
    public void testIncarcaTabelComenzi_NullLista() {
        viewStaff.listaComenzi = null;

        assertDoesNotThrow(() -> viewStaff.incarcaTabelComenzi());


        assertEquals(0, viewStaff.ordersTable.getRowCount());
    }

    @Test
    public void testActualizeazaStatus(){
        viewStaff.incarcaTabelComenzi();
        viewStaff.ordersTable.setRowSelectionInterval(0,0); //selectez prima linie din tabel
        viewStaff.statusComboBox.setSelectedItem("Preparare");
        viewStaff.timpEstimatField.setText("25");

        viewStaff.actualizeazaStatusComanda();

        Comanda c=lista.get(0);
        assertEquals("Preparare", c.getStatus());
        assertEquals(25, c.getTimpEstimare());
    }

    @Test
    public void testActualizeazaStatus_TimpInvalid() {
        viewStaff.incarcaTabelComenzi();
        viewStaff.ordersTable.setRowSelectionInterval(0, 0);
        viewStaff.statusComboBox.setSelectedItem("Preparare");
        viewStaff.timpEstimatField.setText("abc"); //introduc text in loc de numar

        viewStaff.actualizeazaStatusComanda();

        Comanda c = lista.get(0);

        assertNotEquals("Preparare", c.getStatus());
    }

    @Test
    public void testActualizeazaStatus_TimpGol() {
        viewStaff.incarcaTabelComenzi();
        viewStaff.ordersTable.setRowSelectionInterval(0, 0);
        viewStaff.statusComboBox.setSelectedItem("Preparare");
        viewStaff.timpEstimatField.setText("");

        viewStaff.actualizeazaStatusComanda();

        Comanda c = lista.get(0);

        assertNotEquals("Preparare", c.getStatus());
    }

    @Test
    public void testActualizeazaDisponibilitateInexistent() {
        viewStaff.numeProdusField.setText("Supa");
        viewStaff.disponibilCheckbox.setSelected(true);


        assertDoesNotThrow(() -> viewStaff.actualizeazaDisponibilitateProdus());
    }






}
