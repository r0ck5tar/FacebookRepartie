import evenements.NouvelleInvitationListener;
import model.Invitation;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Created by Hakim on 02/06/14.
 */
public class PanelDemandeAmi extends JPanel implements ActionListener, NouvelleInvitationListener {
    private Client client;
    private JList<String> invitationsEnAttente = new JList<String>();
    private JList<String> demandesEnAttente = new JList<String>();
    JButton acceptButton = new JButton("accept");

    public PanelDemandeAmi (Client client) {
        this.client = client;
        client.invitation.setInvitationListener(this);

        setLayout(new GridLayout(5, 0));

        JLabel invitationLabel = new JLabel("Invitations d'amis");
        JLabel demandeLabel = new JLabel("Demandes d'amis");
        acceptButton.addActionListener(this);

        add(invitationLabel);
        add(invitationsEnAttente);
        add(acceptButton);
        add(demandeLabel);
        add(demandesEnAttente);
        majInvitations();
        majDemandes();
    }

    public void majInvitations() {

        java.util.List<Invitation> listeInvitations = client.mur.getInvitationsEnAttente();

        if(listeInvitations != null) {
            DefaultListModel listModel = new DefaultListModel();

            for(Invitation invitation : listeInvitations) {
                try {
                    listModel.addElement(invitation.quiEsTu());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            invitationsEnAttente.setModel(listModel);
        }
    }

    public void majDemandes() {

        java.util.List<Invitation> listeDemanndes = listeDemanndes = client.mur.getDemandeAmiEnAttente();


        if(listeDemanndes != null) {
            DefaultListModel listModel = new DefaultListModel();

            for(Invitation invitation : listeDemanndes) {
                try {
                    listModel.addElement(invitation.quiEsTu());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            demandesEnAttente.setModel(listModel);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(acceptButton)) {
            int index = invitationsEnAttente.getSelectedIndex();
            try {
                client.mur.getInvitationsEnAttente().get(index).accept(client.mur, client.stubInvitation);
                invitationsEnAttente.remove(index);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void onInvitation(String nom) {
        majInvitations();
    }
}
