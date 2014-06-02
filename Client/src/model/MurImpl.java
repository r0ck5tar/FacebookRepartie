package model;

import evenements.NotificationListener;
import evenements.NouveauMessageListener;
import model.Invitation;
import model.Mur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by Hakim on 05/05/14.
 */
public class MurImpl extends UnicastRemoteObject implements Mur {
    private String nom;
    private ArrayList<String> messagesDeMur;
    private ArrayList<Mur> listeAmis;
    private ArrayList<Invitation> invitationsEnAttente; //c'est lui qui invite
    private ArrayList<Invitation> demandeAmiEnAttente; //c'est moi qui demande
    private ArrayList<String> notifications;
    private NotificationListener notificationListener;
    private NouveauMessageListener nouveauMessageListener;

    public MurImpl(String nom) throws RemoteException {
        this.nom = nom;
        messagesDeMur = new ArrayList<String>();
        notifications = new ArrayList<String>();
        this.invitationsEnAttente = new ArrayList<Invitation>();
        this.demandeAmiEnAttente = new ArrayList<Invitation>();
        this.listeAmis = new ArrayList<Mur>();
        this.notifications = new ArrayList<String>();
    }

    /*
      Méthodes distantes
     */
    @Override
    public void notifier(String notification)  throws RemoteException {
        notifications.add(notification);
        notificationListener.onNotification(notification);
    }

    @Override
    public ArrayList<String> getContenu()  throws RemoteException {
        return messagesDeMur;
    }

    @Override
    public ArrayList<Mur> getListeAmis()   throws RemoteException{
        return this.listeAmis;
    }


    /*
        Méthodes locales
     */
    public void publishMessage(String message)  {
        messagesDeMur.add(message);
        try {
            for(Mur ami : listeAmis) {
                ami.notifier(nom + " a publié : " + message );
            }
        }
        catch (RemoteException e) { }
    }


    public void lireNotification() {
        //enlever la dernière notification de la liste de notifications

        if(notifications.size()>0){
            notifications.remove(0);
        }
    }

    @Override
    public void ecrireMessage(String message, String nom) throws RemoteException {
        messagesDeMur.add(message+"  --"+nom);
        nouveauMessageListener.onNouveauMessage();
    }


    /*
    Getters
     */

    public String getNom() {return nom;}

    public ArrayList<Invitation> getInvitationsEnAttente() {
        return invitationsEnAttente;
    }

    public ArrayList<String> getMessagesDeMur() {
        return messagesDeMur;
    }

    public ArrayList<Invitation> getDemandeAmiEnAttente() {
        return demandeAmiEnAttente;
    }

    public ArrayList<String> getNotifications(){return notifications;}

    public void setNotificationListener(NotificationListener listener) {
        this.notificationListener = listener;
    }

    public void setNouveauMessageListener(NouveauMessageListener listener) {
        this.nouveauMessageListener = listener;
    }
}
