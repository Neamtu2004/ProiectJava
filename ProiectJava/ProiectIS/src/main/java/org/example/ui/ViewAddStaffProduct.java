package org.example.ui;

import org.example.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewAddStaffProduct extends JFrame {

    private JPanel contentPane;
    private JPanel panelOptiuni;
    private JPanel panelCentral;

    public ViewAddStaffProduct() {
        setTitle("Restaurant Magic - admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // Panelul cu butoanele sus
        panelOptiuni = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        contentPane.add(panelOptiuni, BorderLayout.NORTH);

        JButton btnAddProduct = new JButton("Adăugare produse");
        JButton btnAddStaff = new JButton("Adăugare chelner");
        JButton btnUpdateProduct=new JButton("Modificare produs");

        panelOptiuni.add(btnAddProduct);
        panelOptiuni.add(btnAddStaff);
        panelOptiuni.add(btnUpdateProduct);


        panelCentral = new JPanel(new BorderLayout());
        contentPane.add(panelCentral, BorderLayout.CENTER);


        btnAddProduct.addActionListener(e -> {
            panelCentral.removeAll();
            panelCentral.add(createFormAddProduct(), BorderLayout.CENTER);
            panelCentral.revalidate();
            panelCentral.repaint();
        });

        btnAddStaff.addActionListener(e -> {
            panelCentral.removeAll();
            panelCentral.add(createFormAddStaff(), BorderLayout.CENTER);
            panelCentral.revalidate();
            panelCentral.repaint();
        });

        btnUpdateProduct.addActionListener(e->{
            panelCentral.removeAll();
            panelCentral.add(updateFormProduct(), BorderLayout.CENTER);
            panelCentral.revalidate();
            panelCentral.repaint();
        });
    }

    private JPanel createFormAddProduct() {
        List<Produs>meniu= Meniu.citireFisier();
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        JTextField txtCategorie = new JTextField();
        JTextField txtNume = new JTextField();
        JTextField txtPret = new JTextField();
        JTextField txtIngrediente = new JTextField();
        JTextField txtDisponibil = new JTextField();
        JButton btnAdauga = new JButton("Adaugă produs");

        panel.add(new JLabel("Categorie:"));
        panel.add(txtCategorie);
        panel.add(new JLabel("Nume:"));
        panel.add(txtNume);
        panel.add(new JLabel("Preț:"));
        panel.add(txtPret);
        panel.add(new JLabel("Ingrediente (separate prin virgulă):"));
        panel.add(txtIngrediente);
        panel.add(new JLabel("Disponibil (true/false):"));
        panel.add(txtDisponibil);
        panel.add(new JLabel());
        panel.add(btnAdauga);

        btnAdauga.addActionListener(e -> {
            String categorie = txtCategorie.getText().trim();
            String nume = txtNume.getText().trim();
            String pretStr = txtPret.getText().trim();
            String ingredienteStr = txtIngrediente.getText().trim();
            String disponibil = txtDisponibil.getText().trim();


            if (categorie.isEmpty() || nume.isEmpty() || pretStr.isEmpty() || ingredienteStr.isEmpty() || disponibil.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completează toate câmpurile!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else
            {

                for(Produs p:meniu){
                    if(p.getNume().equalsIgnoreCase(nume)){
                        JOptionPane.showMessageDialog(this, "Exista deja un produs cu acest nume", "Eroare", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            double pret;
            try {
                pret = Double.parseDouble(pretStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preț invalid!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean disp=Boolean.parseBoolean(disponibil);
            Categorie categ= Categorie.valueOf(categorie);
            List<String> ingrediente = List.of(ingredienteStr.split("\\s*,\\s*"));


            Produs p=new Produs(categ, nume, pret, ingrediente, disp);

            meniu.add(p);
            Meniu.salvareFisier(meniu);


            txtCategorie.setText("");
            txtNume.setText("");
            txtPret.setText("");
            txtIngrediente.setText("");
            txtDisponibil.setText("");

            JOptionPane.showMessageDialog(this, "Produs adaugat cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);
        });

        return panel;
    }

    private JPanel createFormAddStaff() {
        List<User>useri=User.citireFisier();
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JTextField txtEmail = new JTextField();
        JTextField txtParola = new JTextField();
        JTextField txtRol = new JTextField();
        JButton btnAdaugaStaff = new JButton("Adaugă chelner");

        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Parola:"));
        panel.add(txtParola);
        panel.add(new JLabel("Rol:"));
        panel.add(txtRol);
        panel.add(new JLabel());
        panel.add(btnAdaugaStaff);

        btnAdaugaStaff.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String parola = txtParola.getText().trim();
            String rol = txtRol.getText().trim();

            if (email.isEmpty() || parola.isEmpty() || rol.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completează toate câmpurile!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else{
                for(User u:useri){
                    if(u.getEmail().equalsIgnoreCase(email)){
                        JOptionPane.showMessageDialog(this, "Utilizatorul exista deja in baza de date!", "Eroare", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            Rol r= Rol.valueOf(rol);

            User u=new User(email, parola, r);
            useri.add(u);

            User.salvareFisier(useri);

            txtEmail.setText("");
            txtParola.setText("");
            txtRol.setText("");

            JOptionPane.showMessageDialog(this, "Chelner adaugat cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);
        });

        return panel;
    }
    private JPanel updateFormProduct(){
        List<Produs> meniu = Meniu.citireFisier();
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JComboBox<String> comboProduse = new JComboBox<>();
        for (Produs p : meniu) {
            comboProduse.addItem(p.getNume());
        }

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        JTextField txtNume = new JTextField();
        JTextField txtPret = new JTextField();
        JTextField txtIngrediente = new JTextField();
        JButton btnSalveaza = new JButton("Salvează modificările");

        formPanel.add(new JLabel("Nume:"));
        formPanel.add(txtNume);
        formPanel.add(new JLabel("Preț:"));
        formPanel.add(txtPret);
        formPanel.add(new JLabel("Ingrediente:"));
        formPanel.add(txtIngrediente);
        formPanel.add(new JLabel());
        formPanel.add(btnSalveaza);

        panel.add(comboProduse, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);

        comboProduse.addActionListener(e -> {
            String numeSelectat = (String) comboProduse.getSelectedItem();
            Produs produsSelectat = meniu
                    .stream()
                    .filter(p -> p.getNume().equalsIgnoreCase(numeSelectat))
                    .findFirst().orElse(null);

            if (produsSelectat != null) {
                txtNume.setText(produsSelectat.getNume());
                txtPret.setText(String.valueOf(produsSelectat.getPret()));
                txtIngrediente.setText(String.join(", ", produsSelectat.getIngrediente()));
            }
        });

        // Inițializează prima selecție
        if (!meniu.isEmpty()) {
            comboProduse.setSelectedIndex(0);
        }

        btnSalveaza.addActionListener(e -> {
            String numeNou = txtNume.getText().trim();
            String pretStr = txtPret.getText().trim();
            String ingredienteStr = txtIngrediente.getText().trim();

            if (numeNou.isEmpty() || pretStr.isEmpty() || ingredienteStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completează toate câmpurile!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double pretNou;
            try {
                pretNou = Double.parseDouble(pretStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preț invalid!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String numeSelectat = (String) comboProduse.getSelectedItem();
            for (Produs p : meniu) {
                if (p.getNume().equalsIgnoreCase(numeSelectat)) {
                    p.setNume(numeNou);
                    p.setPret(pretNou);
                    p.setIngrediente(List.of(ingredienteStr.split("\\s*,\\s*")));
                    break;
                }
            }

            Meniu.salvareFisier(meniu);
            JOptionPane.showMessageDialog(this, "Produs modificat cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);
        });

        return panel;
    }


}
