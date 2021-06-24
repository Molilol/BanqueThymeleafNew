package moli.ExoEvooq.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(of = "id")
@Data
public class Operation {

    public enum OperationType {

        RETIRER, DEPOSER;

    }

    @NotNull
    private String id;

    @NotNull
    private OperationType operationType;

    @NotNull
    private Montant montant;

    public Operation(OperationType operationType, Montant montant) {
        this.operationType = operationType;
        this.montant = montant;
    }

    public Operation(String id, OperationType operationType, Montant montant) {
        this.id = id;
        this.operationType = operationType;
        this.montant = montant;
    }

    public Montant appliquerOperation(Montant montantCourant) {

        if (this.getOperationType() == Operation.OperationType.RETIRER) {
            return montantCourant.soustraire(this.montant);
        } else if (this.getOperationType() == Operation.OperationType.DEPOSER) {
            return montantCourant.addition(this.montant);
        }
        return montantCourant;
    }

}


