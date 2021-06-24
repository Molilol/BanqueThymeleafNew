package moli.ExoEvooq.vue;

public class OperationDTO {

    String id;
    String operationType;
    String montant;

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

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public OperationDTO(String id, String operationType, String montant) {
        this.id = id;
        this.operationType = operationType;
        this.montant = montant;
    }

    public OperationDTO(String operationType, String montant) {
        this.operationType = operationType;
        this.montant = montant;
    }

    public OperationDTO() {
    }
}
