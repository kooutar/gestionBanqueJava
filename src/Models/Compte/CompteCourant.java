package Models.Compte;

import Models.Operation.Operation;
import Models.Operation.Retrait;

import java.sql.SQLOutput;
import java.util.Map;

public class CompteCourant extends Compte {
    private double decouvert; // Montant autorisé en négatif

    public CompteCourant(String code, double solde) {
        super(code);
        this.solde=solde;
        this.decouvert = -500;
    }


    public CompteCourant(double soldeInitial) {
        super(soldeInitial);
        this.decouvert = -500;
    }

    @Override
    public boolean retirer(double montant, String destination) {
        if (this.solde - montant >= this.decouvert) {
            Retrait retrait = new Retrait(montant, destination);
            this.solde -= montant;
            this.listeOperations.put(this.code,retrait);
            return true;
        }
        return false;
    }

    @Override
    public double calculerInteret() {
        return 0; // Pas d'intérêts sur compte courant
    }

    @Override
    public void afficherDetails() {
        System.out.println("=== COMPTE COURANT ===");
        System.out.println("Code: " + code);
        System.out.println("Solde: " + String.format("%.2f", solde) + " €");
        System.out.println("Découvert autorisé: " + String.format("%.2f", decouvert) + " €");
        System.out.println("Nombre d'opérations: " + listeOperations.size());
        getAllOperations();


    }

    public double getDecouvert() { return decouvert; }
}
