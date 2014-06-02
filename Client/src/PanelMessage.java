import evenements.NouveauMessageListener;
import model.Mur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * Created by Hakim on 02/06/14.
 */
public class PanelMessage extends JPanel implements ActionListener, NouveauMessageListener{
    private Client client;
    private JList<String> messages = new JList<String>();
    private JLabel messageLabel = new JLabel("messages");
    private JTextField messageTextBox = new JTextField();
    private JLabel messageTextBoxLabel = new JLabel("Publier un message");
    private JButton publishButton = new JButton("Publier");

    public PanelMessage(Client client) {
        this.client = client;
        client.mur.setNouveauMessageListener(this);

        setLayout(new GridLayout(5, 1));

        publishButton.addActionListener(this);

        add(messageTextBoxLabel);
        add(messageTextBox);
        add(publishButton);
        add(messageLabel);
        add(messages);
        majMessages();
    }

    public void majMessages() {

        java.util.List<String> listeMessage = null;
        try {
            listeMessage = client.mur.getContenu();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if(listeMessage != null) {
            DefaultListModel listModel = new DefaultListModel();

            for(String msg : listeMessage) {
                listModel.addElement(msg);
            }

            messages.setModel(listModel);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(publishButton)) {
            client.mur.publishMessage(messageTextBox.getText());
            System.out.println(messageTextBox.getText());
            majMessages();
        }
    }

    @Override
    public void onNouveauMessage() {
        majMessages();
    }
}
