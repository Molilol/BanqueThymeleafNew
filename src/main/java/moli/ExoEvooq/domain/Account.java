package moli.ExoEvooq.domain;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class Account {

    @NotBlank
    private String id;

    @NotBlank
    private String clientId;

    @NotBlank
    private String devise;

    @NotEmpty
    @NotNull
    private List<@NotNull @Valid Operation> operations;


    public Account(String devise, List<@NotNull @Valid Operation> operations) {
        this.devise = devise;
        this.operations = operations;
    }

    public Account(String id, String clientId, String devise, List<@NotNull @Valid Operation> operations) {
        this.id = id;
        this.clientId = clientId;
        this.devise = devise;
        this.operations = operations;
    }

    public Montant getTotal() {

        Montant montant = new Montant(0.0, devise);

        for (Operation operation : this.operations) {

            montant = operation.appliquerOperation(montant);

        }

        return montant;
    }


}
