package Models.Operation;

public class Retrait extends Operation {
    private String destination;

    public Retrait(double montant, String destination) {
        super(montant);
        this.destination = destination;
    }

    @Override
    public String getTypeOperation() {
        return "RETRAIT - Destination: " + destination;
    }

    @Override
    public String toString() {
        return "fait un Retrait le "+ date + " sous le nemuro " + numero + " la destination est " + destination + " le montant est " + montant;
    }

    public String getDestination() { return destination; }
}
