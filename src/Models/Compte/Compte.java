package Models.Compte;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

import Models.Operation.Operation;
import Models.Operation.Versement;

// Classe abstraite Compte
public abstract class Compte {
    protected String code;
    protected double solde;
    protected HashMap<String,Operation> listeOperations;
    static public HashMap<String,Compte> comptes= new HashMap<String,Compte>();
    static  int cpt=10000;
    public Compte(String code) {
        this.code = code;
        this.listeOperations = new HashMap<>();
    }

   public  Compte(double soldeInitial) {
        this.solde=soldeInitial;
        this.code="CPT-"+(cpt++);
        comptes.put(this.code, this);

   }

    public static void afficherComptes() {
        if (comptes.isEmpty()) {
            System.out.println("Aucun compte trouvé.");
        } else {
            for (Map.Entry<String, Compte> entry : comptes.entrySet()) {
                System.out.println("Clé : " + entry.getKey() + " => " + entry.getValue());
            }
        }
    }


    // Méthodes abstraites
    public abstract boolean retirer(double montant, String destination);
    public abstract double calculerInteret();
    public abstract void afficherDetails();

    // Méthodes concrètes
    public void verser(double montant, String source) {
        Versement versement = new Versement(montant, source);
        this.solde += montant;
        this.listeOperations.put(this.code,versement);
    }

    public  void verserCompteToCompte(double montant, String destination) {
        Versement versement = new Versement(montant, destination);
        this.solde -= montant;
        Compte c= Compte.comptes.get(destination);
        if(c!=null){
            c.setSolde(c.getSolde() + montant);
            this.listeOperations.put(this.code,versement);
        }else {
            System.out.println("destination no exist");
        }

    }

    public void depot(double montant, String destination) {
        Compte compte = Compte.comptes.get(destination);
         if(compte!=null) {
             compte.verser(montant,compte.getCode());
         }
    }

    // Getters et Setters
    public String getCode() { return code; }
    public double getSolde() { return solde; }
    public Operation getListeOperations() { return listeOperations.get(this.code); }

    protected void setSolde(double solde) { this.solde = solde; }

    public void setListOperation(Operation operation) { this.listeOperations.put(this.code,operation);}
    public void  getAllOperations(){
        for (Map.Entry<String, Operation> entry : listeOperations.entrySet()) {
            String key = entry.getKey();              // la clé (par ex. code opération)
            Operation op = entry.getValue();          // l’objet Operation
            System.out.println("Code: " + key + " -> " + op.toString());
        }
    }
}