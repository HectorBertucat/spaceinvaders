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
    	Missile missile;

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

		if (commandeUser.tir && !this.aUnMissile()) {
			tirerUnMissile(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR),
					Constante.MISSILE_VITESSE);
		}

    }

    public boolean etreFini() {
        return false;
    }

	public void positionnerUnNouveauVaisseau(Dimension dimension, Position position, int i) {

		int x = position.abscisse();
		int y = position.ordonnee();
		int vitesse = i;

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position du vaisseau est en dehors de l'espace jeu");

		int longueurVaisseau = dimension.longueur();
		int hauteurVaisseau = dimension.hauteur();

		if (!estDansEspaceJeu(x + longueurVaisseau - 1, y))
			throw new DebordementEspaceJeuException("Le vaisseau déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurVaisseau + 1))
			throw new DebordementEspaceJeuException("Le vaisseau déborde de l'espace jeu vers le bas à cause de sa hauteur");

		vaisseau = new Vaisseau(dimension,position,vitesse);
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
				marque = Constante.MARQUE_VAISSEAU;
			else if (this.aUnMissileQuiOccupeLaPosition(x, y))
				marque = Constante.MARQUE_MISSILE;
			else marque = Constante.MARQUE_VIDE;
			return marque;
		}

	private boolean aUnMissileQuiOccupeLaPosition(int x, int y) {
		return this.aUnMissile() && missile.occupeLaPosition(x, y);
	}

	private boolean aUnVaisseauQuiOccupeLaPosition(int x, int y) {
			return this.aUnVaisseau() && vaisseau.occupeLaPosition(x, y);
		}

		public boolean aUnVaisseau() {
			return vaisseau!=null;
		}

	public boolean aUnMissile() {
		return missile!=null;
	}


	private boolean estDansEspaceJeu(int x, int y) {
		return ((x >= 0) && (x < longueur)) && ((y >= 0) && (y < hauteur));
	}

    public void deplacerVaisseauVersLaGauche() {
		if (0 < vaisseau.abscisseLaPlusAGauche())
			vaisseau.deplacerHorizontalementVers(Direction.GAUCHE);
		if (!estDansEspaceJeu(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusHaute())) {
			vaisseau.positionner(0, vaisseau.ordonneeLaPlusHaute());
		}
    }

    public void deplacerVaisseauVersLaDroite() {
		if (vaisseau.abscisseLaPlusADroite() < (longueur - 1)) {
			vaisseau.deplacerHorizontalementVers(Direction.DROITE);
			if (!estDansEspaceJeu(vaisseau.abscisseLaPlusADroite(), vaisseau.ordonneeLaPlusHaute())) {
				vaisseau.positionner(longueur - vaisseau.longueur(), vaisseau.ordonneeLaPlusHaute());
			}
		}
    }

    public Vaisseau recupererVaisseau() {
    	    return this.vaisseau;
    }

	public Missile recuperUnMissile() {
		return this.missile;
	}

	public void initialiserJeu() {
		Position positionVaisseau = new Position(this.longueur/2,this.hauteur-1);
		Dimension dimensionVaisseau = new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR);
		positionnerUnNouveauVaisseau(dimensionVaisseau, positionVaisseau, Constante.VAISSEAU_VITESSE);
	}

	public void tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {

		if ((vaisseau.hauteur()+ dimensionMissile.hauteur()) > this.hauteur )
			throw new MissileException("Pas assez de hauteur libre entre le vaisseau et le haut de l'espace jeu pour tirer le missile");

		this.missile = this.vaisseau.tirerUnMissile(dimensionMissile,vitesseMissile);
	}

	public void deplacerMissile() {
    		if (this.missile.ordonneeLaPlusBasse() > 0) {
				this.missile.deplacerVerticalementVers(Direction.HAUT_ECRAN);
			}
    		else {
				this.missile = null;
			}

	}
}
