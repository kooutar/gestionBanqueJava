package Models.Operation;

import java.time.LocalDateTime;
import java.util.UUID;

abstract  public class Operation {
     protected String numero;
     protected LocalDateTime date;
     protected double montant;

     public Operation(double montant) {
         this.numero = UUID.randomUUID().toString();
         this.date = LocalDateTime.now();
         this.montant = montant;
     }

     // Getters
     public String getNumero() { return numero; }
     public LocalDateTime getDate() { return date; }
     public double getMontant() { return montant; }

     public abstract String getTypeOperation();
     public abstract String toString();
}
