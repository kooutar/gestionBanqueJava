package Models.Compte;

import Models.Operation.Retrait;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CompteEpargne extends Compte {
    private double tauxInteret; // En pourcentage
    private ScheduledExecutorService scheduler;
    public CompteEpargne(String code, double solde) {
        super(code);
        this.solde = solde;
    }

    public CompteEpargne( double soldeInitial) {
        super(soldeInitial);
        this.tauxInteret = 10;
    }


    @Override
    public boolean retirer(double montant, String destination) {
        if (this.solde >= montant) {
            Retrait retrait = new Retrait(montant, destination);
            this.solde -= montant;
            this.listeOperations.put(this.code,retrait);
            return true;
        }
        return false;
    }

    @Override
    public double calculerInteret() {
        return this.solde * (this.tauxInteret / 100);
    }

    @Override
    public void afficherDetails() {
        System.out.println("=== COMPTE ÉPARGNE ===");
        System.out.println("Code: " + code);
        System.out.println("Solde: " + String.format("%.2f", solde) + " €");
        System.out.println("Taux d'intérêt: " + tauxInteret + "%");
        System.out.println("Intérêts calculés: " + String.format("%.2f", calculerInteret()) + " €");
        System.out.println("Nombre d'opérations: " + listeOperations.size());
        getAllOperations();


    }

    public double getTauxInteret() { return tauxInteret; }

    public void appliquerInteret() {
        solde += solde * (tauxInteret / 100.0);
    }

    // démarre la mise à jour automatique
    public void startInteret() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            appliquerInteret();
        }, 0, 5, TimeUnit.SECONDS); // délai initial = 0, période = 5s
    }
}
