package moli.ExoEvooq.wrapper;

import moli.ExoEvooq.infrastructure.persistance.AccountEntity;
import moli.ExoEvooq.infrastructure.persistance.ClientEntity;
import moli.ExoEvooq.infrastructure.persistance.OperationEntity;
import moli.ExoEvooq.vue.AccountDTO;
import moli.ExoEvooq.vue.ClientDTO;
import moli.ExoEvooq.vue.OperationDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class WrapperDTOtoEntity {


    public ClientEntity clientDTOtoClientEntity(ClientDTO clientDTO) {
        ClientEntity clientEntity = new ClientEntity();
        if (clientDTO.getId() != null) {
            clientEntity.setId(clientDTO.getId());
        }
        clientEntity.setName(clientDTO.getName());
        if (clientEntity.getDate() == null){
            clientEntity.setDate(clientDTO.getDate());
        } else {

        }
        Set<AccountEntity> accountEntitySet = accountDTOListToAccountSetEntity(clientDTO.getAccountClient(), clientEntity);
        clientEntity.setAccounts(accountEntitySet);
        return clientEntity;
    }

    public OperationEntity operationDTOtoOperationEntity(OperationDTO operationDTO, AccountEntity accountEntity) {
        OperationEntity operationEntity = new OperationEntity();
        if (operationDTO.getId() != null) {
            operationEntity.setId(operationDTO.getId());
        }
        operationEntity.setOperationType(operationDTO.getOperationType());
        operationEntity.setAccount(accountEntity);
        operationEntity.setMontant(operationDTO.getMontant());
        operationEntity.setDate(operationDTO.getDate());
        return operationEntity;
    }

    public Set<OperationEntity> operationDTOListToOperationEntitySet(List<OperationDTO> operationDTOList, AccountEntity accountEntity) {
        Set<OperationEntity> operationEntitySet = new HashSet<>();
        for (OperationDTO operationDTO : operationDTOList) {
            operationEntitySet.add(operationDTOtoOperationEntity(operationDTO, accountEntity));
        }
        return operationEntitySet;
    }

    public AccountEntity accountDTOtoAccountEntity(AccountDTO accountDTO, ClientEntity clientEntity) {
        AccountEntity accountEntity = new AccountEntity();
        if (accountDTO.getId() != null) {
            accountEntity.setId(accountDTO.getId());
        }
        accountEntity.setClient(clientEntity);
        accountEntity.setDevise(accountDTO.getDevise());
        accountEntity.setDate(accountDTO.getDate());
        return accountEntity;
    }

    public Set<AccountEntity> accountDTOListToAccountSetEntity(List <AccountDTO> accountDTOList, ClientEntity clientEntity) {
        Set<AccountEntity> accountEntitySet = new HashSet<>();
        for (AccountDTO accountDTO : accountDTOList) {
            AccountEntity accountEntity = accountDTOtoAccountEntity(accountDTO, clientEntity);
            Set<OperationEntity> operationEntitySet = operationDTOListToOperationEntitySet(accountDTO.getOperationList(), accountEntity);
            accountEntity.setOperations(operationEntitySet);
            accountEntitySet.add(accountEntity);
        }
        return accountEntitySet;
    }

}
