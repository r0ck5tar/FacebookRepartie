import model.Invitation;
import model.InvitationImpl;
import model.MurImpl;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Hakim on 02/06/14.
 */
public class Client {
    Annuaire annuaire;
    MurImpl mur;
    InvitationImpl invitation;
    Invitation stubInvitation;

    public Client(String nom) throws RemoteException {
        mur = new MurImpl(nom);
        invitation = new InvitationImpl(mur);

        Registry registryAnnuaire = LocateRegistry.getRegistry(1096);

        try {
            annuaire = (Annuaire) registryAnnuaire.lookup("Annuaire");
        } catch (NotBoundException e) {
            System.err.println("Annuaire unavailable");
            annuaire = null;
        }

        int port = 1097;

        Registry registry;
        try{
            registry = LocateRegistry.createRegistry(port);
        }
        catch (RemoteException e) {
            registry = LocateRegistry.getRegistry(port);
            System.err.print(e);
        }

        try{
            stubInvitation = (Invitation) UnicastRemoteObject.exportObject(invitation, port);
        }
        catch (ExportException e) {
            stubInvitation = (Invitation) UnicastRemoteObject.toStub(invitation);
        }

        String address = "rmi://" + mur.getNom() + ":" + port;
        try {
            registry.bind(address, stubInvitation);
        }
        catch (AlreadyBoundException e1) {
            System.err.println("\nClient already registered");
        }

        annuaire.registerUser(nom, address);
        System.out.println("Client registered");
    }

    public Invitation findUser(String nom) throws RemoteException {
        String userAddress = annuaire.getUserAddress(nom);

        Registry registry = LocateRegistry.getRegistry(1097);

        try {
            Invitation invitation = (Invitation) registry.lookup(userAddress);
            return invitation;
        } catch (NotBoundException e) {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        new LoginFrame();
        /*
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String nom;
        nom = console.readLine();
        */


        /*
        String command = "";
        model.Invitation foundInvitation = null;

        while (!command.equals("quit")) {
            command = console.readLine();

            if(command.equals("find")) {
                System.out.print("name: ");
                String name = console.readLine();

                foundInvitation = client.findUser(name);

                if(foundInvitation != null) {
                    System.out.print("Invite " + foundInvitation.quiEsTu() + "? (y/n) " );
                    String response = console.readLine();

                    if(response.equals("y")) {
                        foundInvitation.invite(client.invitation);
                    }
                }
            }
        }
        */
    }
}
