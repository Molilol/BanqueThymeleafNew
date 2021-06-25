package moli.ExoEvooq.vue;

import java.time.LocalDateTime;

public class OperationDTO {

    String id;
    String operationType;
    Double montant;
    LocalDateTime date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OperationDTO(String id, String operationType, Double montant) {
        this.id = id;
        this.operationType = operationType;
        this.montant = montant;
    }

    public OperationDTO(String operationType, Double montant) {
        this.operationType = operationType;
        this.montant = montant;
    }

    public OperationDTO() {
    }
}
