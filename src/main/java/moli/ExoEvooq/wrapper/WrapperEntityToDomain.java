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
        List<Account> accountList = new ArrayList<>();
        for (AccountEntity accountEntity : clientEntity.getAccounts()) {
            accountList.add(accountEntityToAccountDomain(accountEntity, clientEntity));
        }
        Client client = new Client(
                clientEntity.getId(),
                clientEntity.getName(),
                accountList,
                clientEntity.getDate());
        return client;
    }

    public Account accountEntityToAccountDomain(AccountEntity accountEntity, ClientEntity clientEntity) {
        List<Operation> operations = new ArrayList<>();
        for (OperationEntity operationEntity : accountEntity.getOperations()) {
            operations.add(operationEntityToOperationDomain(operationEntity, accountEntity));
        }
        Account account = new Account(
                accountEntity.getId(),
                clientEntity.getId(),
                accountEntity.getDevise(),
                operations,
                accountEntity.getDate()
        );
        return account;
    }

    public Operation operationEntityToOperationDomain(OperationEntity operationEntity, AccountEntity accountEntity) {
        Montant montant = new Montant(
                operationEntity.getMontant(),
                accountEntity.getDevise()
        );
        Operation operation = new Operation(
                operationEntity.getId(),
                Operation.OperationType.valueOf(operationEntity.getOperationType()),
                montant,
                operationEntity.getDate()
        );

        return operation;
    }


}
