import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * Created by Hakim on 02/06/14.
 */
/*
    Interface de l'Annuaire permettant d'enregister l'association nom/clé de stub
 */
public interface Annuaire extends Remote {

    public void registerUser (String nom, String address) throws RemoteException;

    public Map<String, String> getRegisteredUsers () throws RemoteException;

    public String getUserAddress(String nom) throws  RemoteException;
}
