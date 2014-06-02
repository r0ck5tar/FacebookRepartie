package model;

import evenements.NouvelAmiListener;
import evenements.NouvelleInvitationListener;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Hakim on 02/06/14.
 */
public class InvitationImpl extends UnicastRemoteObject implements Invitation {
    private MurImpl mur;
    private NouvelleInvitationListener invitationListener;
    private NouvelAmiListener nouvelAmiListener;

    public InvitationImpl(MurImpl mur) throws RemoteException {
        this.mur = mur;
    }

    @Override
    public void accept(Mur ami, Invitation stubAmi) throws RemoteException {
        if(stubAmi != null) {
            if(mur.getDemandeAmiEnAttente().contains(stubAmi)) {
                mur.getListeAmis().add(ami);
                System.out.println(mur.getNom() + " est maintenant ami avec " + stubAmi.quiEsTu());
                stubAmi.retourAccept(mur, (Invitation) this);
                mur.getDemandeAmiEnAttente().remove(stubAmi);
                nouvelAmiListener.onNouvelAmi(ami.getNom());
            }
            else{
                System.out.println(mur.getNom() + " n'a pas pu devenir ami avec " + stubAmi.quiEsTu());
            }
        }
    }

    @Override
    public void invite(Invitation inviteur) throws RemoteException {
        if(!mur.getInvitationsEnAttente().contains(inviteur)
                && !mur.getDemandeAmiEnAttente().contains(inviteur)){
            mur.getInvitationsEnAttente().add(inviteur);
            System.out.println(mur.getNom() + " a reçu une invitation de " + inviteur.quiEsTu());
            invitationListener.onInvitation(inviteur.quiEsTu());
            inviteur.retourInvitation((Invitation) this);
        }
    }

    @Override
    public String quiEsTu() throws RemoteException {
        return mur.getNom();
    }

    @Override
    public void retourInvitation(Invitation invitation) throws RemoteException {
        if(!mur.getDemandeAmiEnAttente().contains(invitation)) {
            mur.getDemandeAmiEnAttente().add(invitation);
            System.out.println(mur.getNom()
                    + " a rajouté le stub de " + invitation.quiEsTu() +
                    " à sa liste de Demande d'amis en attente");
        }
    }

    @Override
    public void retourAccept(Mur murAmi, Invitation stubAmi) throws RemoteException {
        if(mur.getInvitationsEnAttente().contains(stubAmi)) {
            mur.getInvitationsEnAttente().remove(stubAmi);
            mur.getListeAmis().add(murAmi);
            System.out.println(mur.getNom() + " est maintenant ami avec " + stubAmi.quiEsTu());
            nouvelAmiListener.onNouvelAmi(murAmi.getNom());
        }
    }

    public void setInvitationListener(NouvelleInvitationListener listener) {
        this.invitationListener = listener;
    }

    public void setNouvelAmiListener(NouvelAmiListener listener) {
        this.nouvelAmiListener = listener;
    }
}
