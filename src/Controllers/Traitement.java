package Controllers;


import Models.Compte.Compte;
import Models.Compte.CompteCourant;
import Models.Compte.CompteEpargne;


import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ScheduledExecutorService;

public class Traitement {

    private final Scanner scanner=new Scanner(System.in);
    private Compte compte;
    private ScheduledExecutorService scheduler;

    public void   afficherMenuPrincipal(){

      boolean continuer = true;

      while (continuer) {
          try {

              System.out.println("    SYSTÈME DE GESTION BANCAIRE");
              System.out.println("1. Créer un compte courant");
              System.out.println("2. Créer un compte épargne");
              System.out.println(" 3 acceder a mon compte");
              System.out.println("0. Quitter");
              System.out.print("Votre choix: ");
              int choix = scanner.nextInt();
              switch (choix) {
                  case 1: creerCompteCourant(); break;
                  case 2: creerCompteEpargne(); break;
                  case 3: menuCompte(); break;

                  case 0:
                      continuer = false;
                      System.out.println("Merci d'avoir utilisé notre système bancaire !");
                      break;
                  default:
                      System.out.println("Choix invalide. Veuillez réessayer.");
              }
          } catch (InputMismatchException e) {
              System.out.println("Erreur: Veuillez saisir un nombre valide.");
              scanner.nextLine(); // Nettoyer le buffer
          } catch (Exception e) {
              System.out.println("Erreur inattendue: " + e.getMessage());
          }
      }
  }

    private void menuCompte(){
      System.out.print("Veuillez saisir votre code de compte: ");
      String codeCompte = scanner.next();
      this.rechercheCompte(codeCompte);
  }

    private  void afficheSousMenu(){
      boolean continuer = true;
      while (continuer) {
          System.out.println("1. Effectuer un versement");
          System.out.println("2. Effectuer un retrait");
          System.out.println("3. Effectuer un virement");
          System.out.println("4. Consulter le solde d'un compte");
          System.out.println("5. Consulter mes opérations ");
          System.out.println("0. Deconnexion");
          System.out.print("Votre choix: ");
          int choix = scanner.nextInt();
          scanner.nextLine();
          try{
              switch (choix) {
                  case 0: continuer = false; break;
                  case 1: effectuerVersement() ; break;
                  case 3: virement(); break;
                  case 2: effectuerRetrait(); break;
                  case 4: consulterSolde(); break;
                  case 5:consulterOperations(); break;
              }
          } catch (Exception e) {
              throw new RuntimeException(e);
          }
      }
  }

    private  void rechercheCompte(String codeCompte){
         Compte c=Compte.comptes.get(codeCompte);
         if(c!=null){
           if(c instanceof  CompteCourant){
              this.compte= new CompteCourant(codeCompte,c.getSolde());
          }
           if(c instanceof CompteEpargne){
              this.compte =new CompteEpargne(codeCompte,c.getSolde());
               System.out.println(compte.getSolde());

           }
          this.afficheSousMenu();

      }else{
          System.out.println("Vous avez pas un compte!");
      }
  }
    private void listerComptes() {
    }

    private void consulterOperations() {
        if(compte instanceof CompteCourant){
            compte.afficherDetails();
        }
        if(compte instanceof CompteEpargne){
            compte.afficherDetails();
        }
    }

    private void consulterSolde() {
        if(compte instanceof CompteEpargne){
            ((CompteEpargne) compte).startInteret();
            System.out.println("Votre solde est: "+ compte.getSolde());
        }
        if(compte instanceof CompteCourant){
            System.out.println("Votre solde est: "+ compte.getSolde());
        }

    }

    private void effectuerVersement() {
        try {
            System.out.print("Montant à verser: ");
            double montant = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Source du versement: ");
            String source = scanner.nextLine();
            this.compte.verser(montant,source);
            System.out.println("✅ Versement effectué avec succès !");

        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    private void effectuerRetrait() {
           System.out.println("donnez le montant");
           double montant=scanner.nextDouble();
           System.out.println("donnez destination");
           String destination=scanner.next();
           if(this.compte instanceof  CompteCourant){
                if(compte.retirer(montant,destination)){
                  System.out.println("retait fais avec succes");
                }else {
                    System.out.println("prob");
                }
           }else{
               System.out.println("pouvez pas fair un retrais ");
           }
           if(this.compte instanceof  CompteEpargne){
               if(compte.retirer(montant,destination)){
                   System.out.println("retait fais avec succes");
               }
           }

    }

    private void virement() {
        System.out.println("le code de compte pour verser a le ");
        String codeCompteDestination=scanner.nextLine();
        System.out.println(" le montant :");
        double montant=scanner.nextDouble();
        compte.verserCompteToCompte(montant,codeCompteDestination);
        System.out.println("le virement vers "+ codeCompteDestination + " effucteur avec succes");
    }

    private void creerCompteEpargne() {
        try {
            System.out.print("Solde initial: ");
            double solde = scanner.nextDouble();

            scanner.nextLine();
            this.compte= new CompteEpargne(solde);
            System.out.println("✅ Compte épargne créé avec succès !");
            System.out.println("Code du compte: " + this.compte.getCode());

        } catch (Exception e) {
            System.out.println("❌ Erreur: " + e.getMessage());
        }
    }

    private void creerCompteCourant() {
        try {
            System.out.print("Solde initial: ");
            double solde = scanner.nextDouble();

            this.compte = new CompteCourant(solde);
            System.out.println("✅ Compte courant créé avec succès !");
            System.out.println("Code du compte: " + this.compte.getCode());

        } catch (Exception e) {
            System.out.println("❌ Erreur: " + e.getMessage());
        }
    }

}
