import evenements.NotificationListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * Created by Hakim on 02/06/14.
 */
public class PanelNotification extends JPanel implements NotificationListener, ActionListener{
    private Client client;
    private JList<String> notifications = new JList<String>();
    private JLabel notificationLabel = new JLabel("Notifications");
    private JButton deleteNotificationButton = new JButton("Supprimer");

    public PanelNotification(Client client) {
        this.client = client;
        client.mur.setNotificationListener(this);

        deleteNotificationButton.setVisible(false);
        deleteNotificationButton.addActionListener(this);

        setLayout(new GridLayout(3, 1));

        add(notificationLabel);
        add(notifications);
        add(deleteNotificationButton);
    }

    public void majNotifications() {

        java.util.List<String> listeNotification = client.mur.getNotifications();


        if(listeNotification != null) {
            DefaultListModel listModel = new DefaultListModel();

            for(String msg : listeNotification) {
                listModel.addElement(msg);
            }

            notifications.setModel(listModel);
            deleteNotificationButton.setVisible(true);
        }
        else{
            deleteNotificationButton.setVisible(false);
        }
    }

    @Override
    public void onNotification(String notification) {
        majNotifications();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(deleteNotificationButton)) {
            client.mur.lireNotification();
            majNotifications();
        }
    }
}
