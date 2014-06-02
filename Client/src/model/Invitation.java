package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Hakim on 05/05/14.
 *
 * Chaque client a un stub Invitation qu'il exporte et enregistre dans le RMIregistry.
 * A travers le stub Invitation, un autre utilisateur peut inviter l'utilisateur propriétaire
 * du stub à devenire ami.
 *
 * la demande d'ami se fait par la méthode invite.
 * la méthode retourInvitation est une méthode de callback, pour assurer que l'invitation
 * est enregistrée du côté de l'inviteur et l'invité.
 *
 * l'acceptation d'une demande d'ami se fait par la méthode accept.
 * Lors de l'acceptation d'une demande d'amie, les deux clients s'échangent leur stub Mur.
 * la méthode retourAccept sert de callback pour assurer que l'acceptation d'amitié se fasse
 * pour les deux clients.
 */
public interface Invitation extends Remote{

    public void accept(Mur ami, Invitation stubAmi) throws RemoteException;

    public void invite(Invitation inviteur) throws RemoteException;

    public String quiEsTu() throws RemoteException;

    public void retourInvitation(Invitation invitation)throws RemoteException;

    public void retourAccept(Mur murAmi, Invitation stubAmi) throws  RemoteException;
}
