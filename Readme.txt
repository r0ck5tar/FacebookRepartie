Ce projet consiste de deux applications: L'annuaire et le Client.

L'annuaire doit être lancé en un exemplaire avant de lancer les Clients.


Compilation:

Pour compiler l'Annuaire, utiliser la commande "ant".
Pareil pour compiler le Client, utiliser la commande "ant".


NB: Le client a besoin du stub de l'Annuaire pour fonctionner. Il faut copier le Annuaire.class (Annuaire/bin/Annuaire.class) dans le répertoire Client/bin


Lancer le programme:

Pour lancer l'Annuaire depuis un console, il faut taper "java AnnuaireImpl".
Pour lancer un Client depuis un console, il faut taper "java Client".



Utilisation:

On peut mettre n'importe quel nom dans la fenêtre qui s'affiche lors du démarrage du client. Un utilisateur avec ce nom sera créé, son stub sera exporté, et la clé de son stub sera enregistré dans l'Annuaire. (Certaines de ces informations seront affichées dans le console) - c.f. le demo (demo.gif)



Bugs:

Pas de vérification si on essaie d'inviter quelqu'un avec qui on est déjà ami.

Une fois qu'un client se déconnecte, tous ses données disparaissent. (les clients ne sont pas persistants).
Par ailleurs, on n'a pas géré la mise à jour de l'Annuaire (on enlève pas les clients déconnectés de l'annuaire)

