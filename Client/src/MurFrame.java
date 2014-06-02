import model.Mur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * Created by Hakim on 02/06/14.
 */
public class MurFrame extends JFrame implements ActionListener{
    private Mur murAmi;
    private String nom;
    private JList<String> messagesDeMur = new JList<String>();
    private JLabel amiLabel = new JLabel();
    private JLabel writeLabel = new JLabel("Ecrire un message");
    private JTextField writeTextBox = new JTextField();
    private JButton writeButton = new JButton("Ecrire");
    private JLabel messageLabel = new JLabel("Messages");

    public MurFrame(Mur murAmi, String nom) {
        this.murAmi = murAmi;
        this.nom = nom;
        try {
            amiLabel.setText(murAmi.getNom());
            setTitle("Le mur de " + murAmi.getNom());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        writeButton.addActionListener(this);

        setLayout(new GridLayout(6, 1));

        add(amiLabel);
        add(writeLabel);
        add(writeTextBox);
        add(writeButton);
        add(messageLabel);
        add(messagesDeMur);

        majMessages();
        pack();
        setVisible(true);

    }

    public void majMessages() {

        java.util.List<String> listeMessage = null;
        try {
            listeMessage = murAmi.getContenu();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        if(listeMessage != null) {
            DefaultListModel listModel = new DefaultListModel();

            for(String msg : listeMessage) {
                listModel.addElement(msg);
            }

            messagesDeMur.setModel(listModel);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(writeButton)) {
            try {
                murAmi.ecrireMessage(writeTextBox.getText(), nom);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        }
    }
}
