package org.example.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Rol;
import org.example.model.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.lang.reflect.Type;



public class ViewLoginStaff extends JFrame {

    public ViewLoginStaff() {
        setTitle("Login Staff");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // nu închide aplicația

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField txtUsername = new JTextField();
        JPasswordField txtParola = new JPasswordField();
        JButton btnLogin = new JButton("Login");

        panel.add(new JLabel("Username:"));
        panel.add(txtUsername);
        panel.add(new JLabel("Parolă:"));
        panel.add(txtParola);
        panel.add(new JLabel());
        panel.add(btnLogin);

        add(panel);

        // acțiune la login
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String parola = new String(txtParola.getPassword());
            User u;
            try {
                u=findStaff(username,parola);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if(u!=null && u.getRol()== Rol.MANAGER) {
                btnLogin.addActionListener(a -> {
                    new ViewAddStaffProduct().setVisible(true);
                });
            }else if(u!=null && u.getRol()==Rol.PERSONAL){
                new ViewStaff().setVisible(true);
                JOptionPane.showMessageDialog(this, "ai logat corect: " + username);
            }
            else{
                JOptionPane.showMessageDialog(this, "Nu exista un user cu acest username: " + username);
            }
        });
    }

    public User findStaff(String username, String parola) throws IOException {
        File f=new File("src/main/resources/utilizatori.json");
        ObjectMapper mapper=new ObjectMapper();
        List<User>users=mapper.readValue(f, new TypeReference<List<User>>(){});
        for(User u : users){
            if(u.getEmail().equalsIgnoreCase(username) && u.getPassword().equalsIgnoreCase(parola)){
                return u;
            }
        }
        return null;
    }
}