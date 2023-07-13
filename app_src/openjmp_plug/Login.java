package openjmp_plug;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login extends JDialog implements ActionListener {
   JPanel panel;
   JLabel user_label, password_label, message;
   JTextField userName_text;
   JPasswordField password_text;
   JButton submit, cancel;
   private String userName;
   private char[] password;
   
   Login() {
      // Username Label
	  setModal(true);
      user_label = new JLabel();
      user_label.setText("User Name :");
      userName_text = new JTextField();
      // Password Label
      password_label = new JLabel();
      password_label.setText("Password :");
      password_text = new JPasswordField();
      // Submit
      submit = new JButton("LOGIN");
      panel = new JPanel(new GridLayout(3, 1));
      panel.add(user_label);
      panel.add(userName_text);
      panel.add(password_label);
      panel.add(password_text);
      message = new JLabel();
      panel.add(message);
      panel.add(submit);
      
      // Adding the listeners to components
      submit.addActionListener(this);
      add(panel, BorderLayout.CENTER);
      setTitle("Database login");
      setSize(350,200);
      setVisible(true);
   }
   
   public String getUserName() {return userName;}
   public char[] getPassword() {return password;}
   
   public static void main(String[] args) {
      Login l = new Login();
      
      System.out.println(l.getUserName() + new String(l.getPassword()));
   }
   @Override
   public void actionPerformed(ActionEvent ae) {
      userName = userName_text.getText();
      password = password_text.getPassword();
      setVisible(false);
      dispose();
   }
}