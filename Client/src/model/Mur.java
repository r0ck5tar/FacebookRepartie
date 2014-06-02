package model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Hakim on 05/05/14.
 */
/*
    Le Mur d'une personne contient ses messages publiés (par lui-même ou par des amis),
    ainsi qu'une liste d'amis.

    Lorsqu'un utilisateur est ami avec un autre utilisateur, il a accès au stub Mur de ce dernier.
    Il peut donc récupérer les messages sur le Mur de son ami, ainsi qu'obtenir sa liste d'amis.

    Avec la méthode notifier, un utilisateur peut envoyer des notifications à
    l'utilisateur dont appartient ce Mur

    Au contraire des stub Invitations, les stubs Mur ne sont pas bindé dans le RMI registry,
    et ne sont pas donc trouvable par un lookup dans le rmiregistry.

    Un utilisateur ne peut obtenir le stub Mur d'un autre Utilisateur qu'en devenant son ami
    (au travers son stub Invitation)
 */
public interface Mur extends Remote{
    public void notifier(String notification) throws RemoteException ;

    public ArrayList<String> getContenu() throws RemoteException;

    public ArrayList<Mur> getListeAmis() throws RemoteException ;

    public String getNom() throws RemoteException;

    public void ecrireMessage(String message, String nom) throws RemoteException;
}
