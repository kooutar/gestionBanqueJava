package Models.Operation;

public class Versement extends Operation {
    private String source;

    public Versement(double montant, String source) {
        super(montant);
        this.source = source;
    }

    @Override
    public String getTypeOperation() {
        return "VERSEMENT - Source: " + source;
    }

    public String getSource() { return source; }

    public String toString() {
        return "source de operation : " + source ;
    }
}
