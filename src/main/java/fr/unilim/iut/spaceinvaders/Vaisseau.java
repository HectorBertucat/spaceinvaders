package fr.unilim.iut.spaceinvaders;

public class Vaisseau {

	int x;
	int y;
	int longueur;
	int hauteur;

	public Vaisseau(int longueur, int hauteur, int x, int y) {
		this.longueur=longueur;
		this.hauteur=hauteur;
		this.x = x;
		this.y = y;
	}

    public Vaisseau(int longueur, int hauteur) {
        this(longueur, hauteur, 0, 0);
    }
	
	public boolean occupeLaPosition(int x, int y) {

		return  (estAbscisseCouverte(x) && estOrdonneCouverte(y));

	}

    private boolean estOrdonneCouverte(int y) {
        return (ordonneLaPlusBasse() <=y) && (y <= ordonneeLaPlusHaute());
    }

    private int ordonneLaPlusBasse() {
        return ordonneeLaPlusHaute()-this.hauteur+1;
    }

    private boolean estAbscisseCouverte(int x) {
        return (abscisseLaPlusAGauche() <= x) && (x <= abscisseLaPlusADroite());
    }

    public int abscisseLaPlusADroite() {
        return this.x+this.longueur-1;
    }


    public void seDeplacerVersLaDroite() {
		this.x = this.x + 1;
	}

	public int abscisseLaPlusAGauche() {
		return this.x;
	}

	public int ordonneeLaPlusHaute() {
		return this.y;
	}

	public void seDeplacerVersLaGauche() {
		this.x = this.x - 1;
	}

	public void positionner(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
