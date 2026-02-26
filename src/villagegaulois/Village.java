package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int indice = marche.trouverEtalLibre();
		marche.utiliserEtal(indice,vendeur,produit,nbProduit);
		chaine.append("Le vendeur " + vendeur.getNom() + "vend des fleurs a l'etal n°" + indice +".\n");
		 return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] test =  marche.trouverEtals(produit);
		if (test.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché;\n");
		}
		else if (test.length == 1) {
			chaine.append("Seul le vendeur " + test[0].getVendeur().getNom() + " propose des " + produit + " au marché.\n");
		}
		else {
			chaine.append("Les vendeurs aui proposent des " + produit + " sont :\n");
			for (int i = 0; i < test.length; i++) {
				chaine.append("- " + test[i].getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal etal = marche.trouverVendeur(vendeur);
		if (etal == null) {
	        chaine.append("Le villageois " + vendeur.getNom() + " n'occupe aucun etal sur le marché.\n");
	    }
		chaine.append(etal.libererEtal());
	    return chaine.toString();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village " + this.getNom() + " possède plusieurs étals :\n");
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}
	
	public class Marche {
		private Etal[] etals;
		
		public Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
		        etals[i] = new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur,produit,nbProduit);
		}
		
		public int trouverEtalLibre() {
			for (int i = 0 ; i < etals.length ; i++) {
				if (etals[i].isEtalOccupe() == false) {
					return i;
				}
			}
			return -1;
		}
		
		public Etal[] trouverEtals(String produit) {
			 int compteur = 0;
			for (int i = 0 ; i < etals.length ; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
		            compteur++;
				}
			}
			Etal[] resultats = new Etal[compteur];
			int index = 0;
		    for (int i = 0; i < etals.length; i++) {
		    	if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
		            resultats[index] = etals[i];
		            index++;
		    	}
		    }
		    return resultats;
		}
		public Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		public String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int vide = 0;
			for (int i = 0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				} else {
					vide++;
				}
			}
			if (vide > 0) {
				chaine.append("Il reste " + vide + " étals non utilisés dans le marché.\n");
			}
			return chaine.toString();
		}
	}
}