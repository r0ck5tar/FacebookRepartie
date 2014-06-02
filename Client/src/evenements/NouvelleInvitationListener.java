package evenements;

/**
 * Created by Hakim on 02/06/14.
 */
/*
    Permet la mise à jour de l'interface graphique lors de la réception d'une demande d'ami.
 */
public interface NouvelleInvitationListener {
    public void onInvitation(String nom);
}