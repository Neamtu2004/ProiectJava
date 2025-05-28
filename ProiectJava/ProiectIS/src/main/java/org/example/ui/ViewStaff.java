package org.example.ui;

import org.example.model.Comanda;
import org.example.model.Meniu;
import org.example.model.Produs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ViewStaff extends JFrame {
    public JTable ordersTable;
    private DefaultTableModel model;
    public JComboBox<String> statusComboBox;
    public JTextField timpEstimatField;
    public JTextField numeProdusField;
    public JCheckBox disponibilCheckbox;

    public List<Comanda> listaComenzi;

    public ViewStaff() {
        setTitle("Staff");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        model = new DefaultTableModel(new String[]{"ID", "Produse", "Status", "Timp estimat (min)"}, 0);
        ordersTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(ordersTable);

        // Panel pentru actualizare status și timp estimat
        JPanel updatePanel = new JPanel();
        statusComboBox = new JComboBox<>(new String[]{"In asteptare", "Preparare", "Servita"});
        timpEstimatField = new JTextField(5);
        JButton updateStatusBtn = new JButton("Actualizează Status");
        updatePanel.add(new JLabel("Status:"));
        updatePanel.add(statusComboBox);
        updatePanel.add(new JLabel("Timp estimat:"));
        updatePanel.add(timpEstimatField);
        updatePanel.add(updateStatusBtn);

        // Panel pentru modificare disponibilitate produs
        JPanel disponibilitatePanel = new JPanel();
        numeProdusField = new JTextField(15);
        disponibilCheckbox = new JCheckBox("Disponibil");
        JButton updateDisponibilitateBtn = new JButton("Actualizează Disponibilitate");
        disponibilitatePanel.add(new JLabel("Produs:"));
        disponibilitatePanel.add(numeProdusField);
        disponibilitatePanel.add(disponibilCheckbox);
        disponibilitatePanel.add(updateDisponibilitateBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(updatePanel, BorderLayout.NORTH);
        add(disponibilitatePanel, BorderLayout.SOUTH);


        listaComenzi = Comanda.citireFisier();


        incarcaTabelComenzi();

        updateStatusBtn.addActionListener(e -> actualizeazaStatusComanda());

        updateDisponibilitateBtn.addActionListener(e -> actualizeazaDisponibilitateProdus());
    }

    public void incarcaTabelComenzi() {
        model.setRowCount(0);
        if (listaComenzi == null) return;


        List<Comanda> comenziActive = listaComenzi.stream()
                .filter(c -> !"Servita".equalsIgnoreCase(c.getStatus()))
                .collect(Collectors.toList());

        for (Comanda c : comenziActive) {
            String produseStr = c.getProduse()
                    .stream()
                    .map(Produs::getNume)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
            model.addRow(new Object[]{
                    c.getId(),
                    produseStr,
                    c.getStatus(),
                    c.getTimpEstimare() > 0 ? c.getTimpEstimare() : "-"
            });
        }
    }

    public void actualizeazaStatusComanda() {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selectați o comandă din tabel.");
            return;
        }

        String nouStatus = (String) statusComboBox.getSelectedItem();
        String timpText = timpEstimatField.getText().trim();

        if (!"Servita".equalsIgnoreCase(nouStatus) && timpText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduceți timpul estimat pentru comenzile în lucru.");
            return;
        }

        int id = (int) model.getValueAt(selectedRow, 0);
        Comanda comandaSelectata = listaComenzi
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);

        if (comandaSelectata != null) {
            try {
                int timp = timpText.isEmpty() ? 0 : Integer.parseInt(timpText);
                comandaSelectata.setTimpEstimare(timp);
                comandaSelectata.setStatus(nouStatus);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Timp estimat invalid, introduceți un număr.");
                return;
            }

            Comanda.salvareFisier(listaComenzi);
            incarcaTabelComenzi();
            JOptionPane.showMessageDialog(this, "Comanda a fost actualizată.");
        }
    }

    public void actualizeazaDisponibilitateProdus() {
        String numeProdus = numeProdusField.getText().trim();
        boolean disponibil = disponibilCheckbox.isSelected();

        if (numeProdus.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduceți numele produsului.");
            return;
        }

        List<Produs>meniu= Meniu.citireFisier();


        boolean gasit = false;
        for (Produs p : meniu) {
                if(p.getNume().equalsIgnoreCase(numeProdus)){
                    p.setDisponibil(disponibil);
                    gasit=true;
            }
        }

        if (!gasit) {
            JOptionPane.showMessageDialog(this, "Produsul nu a fost gasit in meniu.");
            return;
        }

        Meniu.salvareFisier(meniu);
        JOptionPane.showMessageDialog(this, "Disponibilitatea Produsului a fost actualizata cu success");

    }


}
