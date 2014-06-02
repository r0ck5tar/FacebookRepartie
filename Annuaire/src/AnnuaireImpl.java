import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hakim on 02/06/14.
 *
 * La classe Annuaire contient un hashmap reliant les noms d'utilisateur à leur clé de stub.
 * Les clients peuvent obtenir un le stub de cet Annuaire et obtenir la clé d'un stub Invitation d'un autre client.
 *
 * Aucune donnée d'utilisateur n'est stocké dans l'annuaire.
 */
public class AnnuaireImpl implements  Annuaire{
    private HashMap<String, String> registeredUsers;
    private Registry registry;

    public AnnuaireImpl() throws RemoteException {
        registeredUsers = new HashMap<String, String>();

        try {
            registry = LocateRegistry.createRegistry(1096);
        } catch (RemoteException e) {
            registry = LocateRegistry.getRegistry(1096);
        }
    }

    /*
        Les clients appellent cette méthode distante pour s'enregister dans l'annuaire
     */
    @Override
    public void registerUser(String nom, String address) throws RemoteException {
        registeredUsers.put(nom, address);
        System.out.println("Registered user " + nom + " at address " + address);
    }

    @Override
    public Map<String, String> getRegisteredUsers() throws RemoteException {
        return registeredUsers;
    }


    /*
        Les clients appellent cette méthode distante pour obtenir la clé d'un autre client.
        (Le client fait ensuite un lookup pour obtenir le stub.
        Une fois ce stub obtenu, les deux clients interagissent en peer-to-peer)
     */
    @Override
    public String getUserAddress(String nom) throws RemoteException {
        return registeredUsers.get(nom);
    }


    public static void main(String[] args) throws RemoteException {
        AnnuaireImpl annuaire = new AnnuaireImpl();
        Annuaire stubAnnuaire;

        try{
            stubAnnuaire = (Annuaire) UnicastRemoteObject.exportObject(annuaire, 1096);
        }
        catch (Exception e) {
            stubAnnuaire = (Annuaire) UnicastRemoteObject.toStub(annuaire);
            //System.err.print(e);
        }

        try {
            annuaire.registry.bind("Annuaire", stubAnnuaire);
            System.out.println("Annuaire started");
        } catch (AlreadyBoundException e) {
            System.err.println("Annuaire already started");
        }
    }
}
