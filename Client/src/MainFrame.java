import evenements.NotificationListener;
import evenements.NouvelAmiListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * Created by Hakim on 02/06/14.
 */
public class MainFrame extends JFrame implements ActionListener, NouvelAmiListener {
    private Client client;
    private JList<String> publications;
    private PanelAmi panelAmi;
    private PanelDemandeAmi panelDemandeAmi;
    private PanelMessage panelMessage;
    private PanelNotification panelNotification;

    public MainFrame(Client client) {
        this.client = client;
        client.invitation.setNouvelAmiListener(this);
        panelAmi = new PanelAmi(client);
        panelDemandeAmi = new PanelDemandeAmi(client);
        panelMessage = new PanelMessage(client);
        panelAmi.stubInvitationButton.addActionListener(this);
        panelNotification = new PanelNotification(client);

        setTitle(client.mur.getNom());
        setLayout(new BorderLayout());
        add(panelAmi, BorderLayout.WEST);
        add(panelDemandeAmi, BorderLayout.EAST);
        add(panelMessage, BorderLayout.CENTER);
        add(panelNotification, BorderLayout.NORTH);


        pack();
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(panelAmi.stubInvitationButton)) {
            try {
                panelAmi.stubInvitationAmi.invite(client.stubInvitation);
                panelDemandeAmi.majDemandes();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void onNouvelAmi(String nom) {
        panelAmi.majListeAmis();
        panelDemandeAmi.majInvitations();
        panelDemandeAmi.majDemandes();
    }
}
