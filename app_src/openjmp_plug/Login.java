package openjmp_plug;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Class for making a login for the database
 * @author nicolaboscolo
 */
public class Login extends JDialog implements ActionListener {

  JPanel panel;
  JLabel user_label, password_label;
  JTextField userName_text;
  JPasswordField password_text;
  JButton submit;
  private String userName;
  private char[] password;

  /**
   * creates a Jdialog for login
   */
  Login() {
    //Username Label
    setModal(true);
    user_label = new JLabel();
    user_label.setText("User Name :");
    userName_text = new JTextField();
    //Password Label
    password_label = new JLabel();
    password_label.setText("Password :");
    password_text = new JPasswordField();
    //Submit
    submit = new JButton("LOGIN");
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(submit);
    panel = new JPanel(new GridLayout(3, 1));
    panel.add(user_label);
    panel.add(userName_text);
    panel.add(password_label);
    panel.add(password_text);
    panel.add(buttonPanel, BorderLayout.CENTER);

    //Adding the listeners to components
    submit.addActionListener(this);
    add(panel, BorderLayout.CENTER);
    setTitle("Database login");
    setSize(350, 200);
    setVisible(true);
  }

  /**
   * get the username inserd
   * @return the username
   */
  public String getUserName() {
    return userName;
  }

  /**
   * get the password
   * @return the password
   */
  public char[] getPassword() {
    return password;
  }

  /**
   * save the username and password
   */
  @Override
  public void actionPerformed(ActionEvent ae) {
    userName = userName_text.getText();
    password = password_text.getPassword();
    setVisible(false);
    dispose();
  }
}
