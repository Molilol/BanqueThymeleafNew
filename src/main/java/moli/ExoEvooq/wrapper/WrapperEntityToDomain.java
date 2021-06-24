package moli.ExoEvooq.wrapper;

import moli.ExoEvooq.domain.Account;
import moli.ExoEvooq.domain.Client;
import moli.ExoEvooq.domain.Montant;
import moli.ExoEvooq.domain.Operation;
import moli.ExoEvooq.infrastructure.persistance.AccountEntity;
import moli.ExoEvooq.infrastructure.persistance.ClientEntity;
import moli.ExoEvooq.infrastructure.persistance.OperationEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WrapperEntityToDomain {

    public Client ClientEntityToDomain(ClientEntity clientEntity) {
        List<Operation> operationList = new ArrayList<>();
        List<Account> accountList = new ArrayList<>();
        for (AccountEntity accountEntity : clientEntity.getAccounts()) {
            for (OperationEntity operationEntity : accountEntity.getOperations()) {
                Montant montant = new Montant(
                        Double.parseDouble(operationEntity.getMontant()),
                        accountEntity.getDevise());
                Operation operation = new Operation(
                        operationEntity.getId(),
                        Operation.OperationType.valueOf(operationEntity.getOperationType()),
                        montant);
                operationList.add(operation);
            }
            Account account = new Account(
                    accountEntity.getId(),
                    clientEntity.getId(),
                    accountEntity.getDevise(),
                    operationList);
            accountList.add(account);
        }
        Client client = new Client(
                clientEntity.getId(),
                clientEntity.getName(),
                accountList);
        return client;
    }


}
