package org.example.ui;

import org.example.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.random;

public class ViewMenu extends JFrame {
    private JPanel contentPane;
    private JPanel panelProduse;
    private JPanel panelCos;
    private Meniu meniu;
    private CosComanda cosComanda = new CosComanda(new ArrayList<>(), 0);

    public ViewMenu() {
        setTitle("Meniu Restaurant - Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);


        JPanel panelCategorii = new JPanel();
        panelCategorii.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        contentPane.add(panelCategorii, BorderLayout.NORTH);

        JButton btnSpirtoase = new JButton("Băuturi spirtoase");
        JButton btnNespirtoase = new JButton("Băuturi nealcoolice");
        JButton btnAperitive = new JButton("Aperitive");
        JButton btnFelPrincipal = new JButton("Fel principal");
        JButton btnLogin = new JButton("Login Staff");
        JButton btnCos = new JButton("Coș");

        panelCategorii.add(btnSpirtoase);
        panelCategorii.add(btnNespirtoase);
        panelCategorii.add(btnAperitive);
        panelCategorii.add(btnFelPrincipal);
        panelCategorii.add(btnLogin);
        panelCategorii.add(btnCos);

        panelProduse = new JPanel();
        panelProduse.setLayout(new BoxLayout(panelProduse, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelProduse);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        panelCos = new JPanel();
        panelCos.setLayout(new BoxLayout(panelCos, BoxLayout.Y_AXIS));

        btnSpirtoase.addActionListener(e -> afiseazaProduse(Categorie.BAUTURI_SPIRTOASE));
        btnNespirtoase.addActionListener(e -> afiseazaProduse(Categorie.BAUTURI_NESPIRTOASE));
        btnAperitive.addActionListener(e -> afiseazaProduse(Categorie.APERITIVE));
        btnFelPrincipal.addActionListener(e -> afiseazaProduse(Categorie.FEL_PRINCIPAL));
        btnLogin.addActionListener(e -> new ViewLoginStaff().setVisible(true));
        btnCos.addActionListener(e -> afiseazaCos());
    }

    private void afiseazaProduse(Categorie categorie) {
        panelProduse.removeAll();
        meniu = new Meniu();
        List<Produs> produse = meniu.afisareMeniuCategorie(categorie);
        if (produse.isEmpty()) {
            panelProduse.add(new JLabel("Nu există produse în această categorie."));
        } else {
            for (Produs p : produse) {
                JPanel panelProdus = new JPanel(new BorderLayout());
                panelProdus.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                if (p.isDisponibil()) {
                    JLabel lblProdus = new JLabel(p.afisareProdusPret());
                    JButton btnComanda = new JButton("Adaugă în coș");
                    JButton btnInfo = new JButton("ℹ Info");

                    btnComanda.addActionListener(e -> {
                        cosComanda.adaugaProdus(p);
                        JOptionPane.showMessageDialog(
                                this,
                                "Ai adăugat în coș: " + p.getNume(),
                                "Produs adăugat",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    });

                    btnInfo.addActionListener(e -> afiseazaPopUpInformatii(p));

                    JPanel btnPanel = new JPanel();
                    btnPanel.add(btnInfo);
                    btnPanel.add(btnComanda);

                    panelProdus.add(lblProdus, BorderLayout.CENTER);
                    panelProdus.add(btnPanel, BorderLayout.EAST);

                    panelProduse.add(panelProdus);
                    panelProduse.add(Box.createVerticalStrut(10));
                }
                else{
                    panelProduse.add(new JLabel("Nu există produse în această categorie."));
                }
            }
        }

        panelProduse.revalidate();
        panelProduse.repaint();
    }

    private void afiseazaPopUpInformatii(Produs produs) {
        JDialog popup = new JDialog(this, "Informații produs", true);
        popup.setSize(300, 200);
        popup.setLayout(new BorderLayout());

        JTextArea textInfo = new JTextArea();
        textInfo.setEditable(false);
        textInfo.setText("Ingrediente: " + produs.getIngrediente());

        JButton btnClose = new JButton("X");
        btnClose.setForeground(Color.RED);
        btnClose.addActionListener(e -> popup.dispose());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(btnClose);

        popup.add(topPanel, BorderLayout.NORTH);
        popup.add(new JScrollPane(textInfo), BorderLayout.CENTER);
        popup.setLocationRelativeTo(this);
        popup.setVisible(true);
    }

    private void afiseazaCos() {
        panelProduse.removeAll();
        panelProduse.add(new JLabel("Produse în coș:"));
        double total=0;
        for (Produs p : cosComanda.getListaProduse()) {
            panelProduse.add(new JLabel("- " + p.getNume() + " - " + p.getPret() + " lei"));
            total=total+p.getPret();
        }

        panelProduse.add(new JLabel("TOTAL - " + total));

        JButton btnPlaseaza = new JButton("Plasează comanda");
        btnPlaseaza.addActionListener(e -> proceseazaComanda());

        panelProduse.add(Box.createVerticalStrut(10));
        panelProduse.add(btnPlaseaza);

        panelProduse.revalidate();
        panelProduse.repaint();
    }

    private void proceseazaComanda() {
        if (cosComanda.getListaProduse().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Coșul este gol.", "Eroare", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] optiuni = {"Cash", "Card"};
        String metodaPlata = (String) JOptionPane.showInputDialog(
                this,
                "Alege metoda de plată:",
                "Metodă plată",
                JOptionPane.QUESTION_MESSAGE,
                null,
                optiuni,
                optiuni[0]
        );

        if (metodaPlata == null) return;


        int id = 1000 + (int)(Math.random() * 9000);

        Comanda comanda = new Comanda(
                id,
                new ArrayList<>(cosComanda.getListaProduse()),
                "În așteptare",
                15,
                metodaPlata,
                "CH-" + System.currentTimeMillis()
        );

        List<Comanda> comenzi = Comanda.citireFisier();
        if (comenzi == null) {
            comenzi = new ArrayList<>();
        }
        comenzi.add(comanda);
        Comanda.salvareFisier(comenzi);

        JOptionPane.showMessageDialog(this, "Comanda a fost plasată cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);
        cosComanda.clear();
        afiseazaProduse(Categorie.FEL_PRINCIPAL);
    }
}
