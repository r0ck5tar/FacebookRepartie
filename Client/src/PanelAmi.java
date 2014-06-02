import evenements.NouvelAmiListener;
import model.Invitation;
import model.Mur;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * Created by Hakim on 02/06/14.
 */
public class PanelAmi extends JPanel implements ActionListener{
    private Client client;

    JList<String> amis = new JList<String>();
    JTextField searchTextBox = new JTextField();
    JLabel stubInvitationLabel = new JLabel("-");
    JButton stubInvitationButton = new JButton("inviter");
    Invitation stubInvitationAmi;
    JButton searchButton = new JButton("recherche");
    JButton viewButton = new JButton("voir mur");

    public PanelAmi(Client client) {
        this.client = client;

        searchButton.addActionListener(this);
        stubInvitationButton.addActionListener(this);
        stubInvitationButton.setVisible(false);
        viewButton.setVisible(false);
        viewButton.addActionListener(this);

        setLayout(new GridLayout(8, 0));
        JLabel searchLabel = new JLabel("Cherche un ami:");
        add(searchLabel);
        add(searchTextBox);
        add(searchButton);
        add(stubInvitationLabel);
        add(stubInvitationButton);
        JLabel listeAmisLabel = new JLabel("Liste d'amis:");
        add(listeAmisLabel);
        add(amis);
        add(viewButton);
        majListeAmis();
        setVisible(true);
    }

    public void majListeAmis() {

        java.util.List<Mur> listeAmis = null;
        try {
            listeAmis = client.mur.getListeAmis();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if(listeAmis.size() > 0) {
            DefaultListModel listModel = new DefaultListModel();

            for(Mur ami : listeAmis) {
                try {
                    listModel.addElement(ami.getNom());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            amis.setModel(listModel);
            viewButton.setVisible(true);
        }
        else{
            viewButton.setVisible(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(searchButton)) {
            try {
                stubInvitationAmi = client.findUser(searchTextBox.getText());
                if(stubInvitationAmi != null) {
                    System.out.println(searchTextBox.getText() + " found");
                    stubInvitationLabel.setText(stubInvitationAmi.quiEsTu());
                    stubInvitationButton.setVisible(true);
                }
                else{
                    stubInvitationLabel.setText("-");
                    stubInvitationButton.setVisible(false);
                    System.out.println(searchTextBox.getText() + " not found");
                }
            } catch (RemoteException e1) {
                stubInvitationAmi = null;
            }
        }

        if(e.getSource().equals(viewButton)) {
            int index = amis.getSelectedIndex();
            if(index >=0) {
                try {
                    Mur ami = client.mur.getListeAmis().get(index);
                    if(ami != null) {
                        new MurFrame(ami, client.mur.getNom());
                    }
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
