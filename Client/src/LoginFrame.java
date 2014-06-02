import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * Created by Hakim on 31/05/14.
 */
public class LoginFrame extends JFrame implements ActionListener {
    private JLabel nameLabel = new JLabel("Nom");
    private JTextField nameTextBox = new JTextField();
    private JButton loginButton = new JButton("S'enregistrer");

    public LoginFrame(){
        setLayout(new GridLayout(3, 1));

        add(nameLabel);
        add(nameTextBox);
        add(loginButton);
        loginButton.addActionListener(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(loginButton)) {
            try {
                Client client = new Client(nameTextBox.getText());
                new MainFrame(client);
                this.dispose();
            } catch (RemoteException e1) {
                JOptionPane.showMessageDialog(this, "Connection error", "Unable to login", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
