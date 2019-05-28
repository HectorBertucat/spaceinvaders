package fr.unilim.iut.spaceinvaders;

import fr.unilim.iut.spaceinvaders.utils.*;
import fr.unilim.iut.spaceinvaders.moteurjeu.*;

public class SpaceInvaders implements Jeu {

    	private static final char MARQUE_FIN_LIGNE = '\n';
		private static final char MARQUE_VIDE = '.';
		private static final char MARQUE_VAISSEAU = 'V';
		int longueur;
    	int hauteur;
    	Vaisseau vaisseau;

    	//... Code déjà écrit
	    
    	public SpaceInvaders(int longueur, int hauteur) {
 		   this.longueur = longueur;
 		   this.hauteur = hauteur;
 	   }

    public void evoluer(Commande commandeUser) {

        if (commandeUser.gauche) {
            deplacerVaisseauVersLaGauche();
        }

        if (commandeUser.droite) {
            deplacerVaisseauVersLaDroite();
        }

    }

    public boolean etreFini() {
        return false;
    }

	public void positionnerUnNouveauVaisseau(Dimension dimension, Position position) {

		int x = position.abscisse();
		int y = position.ordonnee();

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position du vaisseau est en dehors de l'espace jeu");

		int longueurVaisseau = dimension.longueur();
		int hauteurVaisseau = dimension.hauteur();

		if (!estDansEspaceJeu(x + longueurVaisseau - 1, y))
			throw new DebordementEspaceJeuException("Le vaisseau déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurVaisseau + 1))
			throw new DebordementEspaceJeuException("Le vaisseau déborde de l'espace jeu vers le bas à cause de sa hauteur");

		vaisseau = new Vaisseau(longueurVaisseau, hauteurVaisseau);
		vaisseau.positionner(x, y);
	}


	public String recupererEspaceJeuDansChaineASCII() {
			StringBuilder espaceDeJeu = new StringBuilder();
			for (int y = 0; y < hauteur; y++) {
				for (int x = 0; x < longueur; x++) {
					espaceDeJeu.append(recupererMarqueDeLaPosition(x, y));
				}
				espaceDeJeu.append(MARQUE_FIN_LIGNE);
			}
			return espaceDeJeu.toString();
		}


		private char recupererMarqueDeLaPosition(int x, int y) {
			char marque;
			if (this.aUnVaisseauQuiOccupeLaPosition(x, y))
			      marque=MARQUE_VAISSEAU;
			else
			      marque=MARQUE_VIDE;
			return marque;
		}


		private boolean aUnVaisseauQuiOccupeLaPosition(int x, int y) {
			return this.aUnVaisseau() && vaisseau.occupeLaPosition(x, y);
		}


		public boolean aUnVaisseau() {
			return vaisseau!=null;
		}


	private boolean estDansEspaceJeu(int x, int y) {
		return ((x >= 0) && (x < longueur)) && ((y >= 0) && (y < hauteur));
	}

    public void deplacerVaisseauVersLaGauche() {
        if (vaisseau.abscisseLaPlusAGauche() > 0) vaisseau.seDeplacerVersLaGauche();
    }

    public void deplacerVaisseauVersLaDroite() {
        if (vaisseau.abscisseLaPlusADroite() < (longueur-1)) vaisseau.seDeplacerVersLaDroite();
    }

    public Vaisseau recupererVaisseau() {
    	    return this.vaisseau;
    }
}
