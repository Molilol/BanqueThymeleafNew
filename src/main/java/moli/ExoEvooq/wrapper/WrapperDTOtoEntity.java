package moli.ExoEvooq.wrapper;

import moli.ExoEvooq.infrastructure.persistance.AccountEntity;
import moli.ExoEvooq.infrastructure.persistance.ClientEntity;
import moli.ExoEvooq.infrastructure.persistance.OperationEntity;
import moli.ExoEvooq.vue.AccountDTO;
import moli.ExoEvooq.vue.ClientDTO;
import moli.ExoEvooq.vue.OperationDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class WrapperDTOtoEntity {


    public ClientEntity clientDTOtoClientEntity(ClientDTO clientDTO) {
        ClientEntity clientEntity = new ClientEntity();
        if (clientDTO.getId() != null) {
            clientEntity.setId(clientDTO.getId());
        }
        clientEntity.setName(clientDTO.getName());
        Set<AccountEntity> accountEntitySet = new HashSet<>();
        Set<OperationEntity> operationEntitySet = new HashSet<>();

        for (AccountDTO accountDTO : clientDTO.getAccountClient()) {
            AccountEntity accountEntity = new AccountEntity();
            if (accountDTO.getId() != null) {
                accountEntity.setId(accountDTO.getId());
            }
            accountEntity.setClient(clientEntity);
            accountEntity.setDevise(accountDTO.getDevise());

            for (OperationDTO operationDTO : accountDTO.getOperationList()) {
                OperationEntity operationEntity = new OperationEntity();
                if (operationDTO.getId() != null) {
                    operationEntity.setId(operationDTO.getId());
                }
                operationEntity.setOperationType(operationDTO.getOperationType());
                operationEntity.setAccount(accountEntity);
                operationEntity.setMontant(operationDTO.getMontant());
                operationEntitySet.add(operationEntity);
            }
            accountEntity.setOperations(operationEntitySet);
            accountEntitySet.add(accountEntity);
        }
        clientEntity.setAccounts(accountEntitySet);
        return clientEntity;
    }

}
