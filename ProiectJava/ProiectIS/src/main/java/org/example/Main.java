package org.example;

import org.example.ui.ViewMenu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ViewMenu clientMenu = new ViewMenu();
            clientMenu.setVisible(true);
        });
    }
}