Ce projet consiste de deux applications: L'annuaire et le Client.

L'annuaire doit �tre lanc� en un exemplaire avant de lancer les Clients.


Compilation:

Pour compiler l'Annuaire, utiliser la commande "ant".
Pareil pour compiler le Client, utiliser la commande "ant".


NB: Le client a besoin du stub de l'Annuaire pour fonctionner. Il faut copier le Annuaire.class (Annuaire/bin/Annuaire.class) dans le r�pertoire Client/bin


Lancer le programme:

Pour lancer l'Annuaire depuis un console, il faut taper "java AnnuaireImpl".
Pour lancer un Client depuis un console, il faut taper "java Client".



Utilisation:

On peut mettre n'importe quel nom dans la fen�tre qui s'affiche lors du d�marrage du client. Un utilisateur avec ce nom sera cr��, son stub sera export�, et la cl� de son stub sera enregistr� dans l'Annuaire. (Certaines de ces informations seront affich�es dans le console) - c.f. le demo (demo.gif)



Bugs:

Pas de v�rification si on essaie d'inviter quelqu'un avec qui on est d�j� ami.

Une fois qu'un client se d�connecte, tous ses donn�es disparaissent. (les clients ne sont pas persistants).
Par ailleurs, on n'a pas g�r� la mise � jour de l'Annuaire (on enl�ve pas les clients d�connect�s de l'annuaire)

