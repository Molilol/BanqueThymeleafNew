package moli.ExoEvooq.wrapper;

import moli.ExoEvooq.infrastructure.persistance.AccountEntity;
import moli.ExoEvooq.infrastructure.persistance.ClientEntity;
import moli.ExoEvooq.infrastructure.persistance.OperationEntity;
import moli.ExoEvooq.service.ClientService;
import moli.ExoEvooq.vue.AccountDTO;
import moli.ExoEvooq.vue.ClientDTO;
import moli.ExoEvooq.vue.OperationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class WrapperEntityToDTO {

    public ClientDTO clientEntityToClientDTO(ClientEntity clientEntity) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(clientEntity.getId());
        clientDTO.setName(clientEntity.getName());
        clientDTO.setDate(clientEntity.getDate());
        List<AccountDTO> accountDTOList = accountEntitySetToAccountDTOList(clientEntity.getAccounts());
        clientDTO.setAccountClient(accountDTOList);
        return clientDTO;
    }

    public AccountDTO accountEntityToAccountDTO(AccountEntity accountEntity) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(accountEntity.getId());
        accountDTO.setDevise(accountEntity.getDevise());
        accountDTO.setDate(accountEntity.getDate());
        accountDTO.setOperationList(operationEntitySetToOperationDTOList(accountEntity.getOperations()));
        return accountDTO;
    }

    public List<AccountDTO> accountEntitySetToAccountDTOList(Set<AccountEntity> accountEntitySet) {
        List<AccountDTO> accountDTOList = new ArrayList<>();
        for (AccountEntity accountEntity : accountEntitySet) {
            AccountDTO accountDTO = accountEntityToAccountDTO(accountEntity);
            accountDTO.setOperationList(operationEntitySetToOperationDTOList(accountEntity.getOperations()));
            accountDTOList.add(accountDTO);
        }
        return accountDTOList;
    }

    public OperationDTO operationEntityToOperationDTO(OperationEntity operationEntity) {
        return new OperationDTO(
                operationEntity.getId(),
                operationEntity.getOperationType(),
                operationEntity.getMontant(),
                operationEntity.getDate()
                );
    }

    public List <OperationDTO> operationEntityPageToOperationDTOList (Page<OperationEntity> operationEntityPage) {
        List <OperationDTO> operationDTOList = new ArrayList<>();
        for (OperationEntity operationEntity : operationEntityPage) {
            operationDTOList.add(operationEntityToOperationDTO(operationEntity));
        }
        return operationDTOList;
    }

    public List<OperationDTO> operationEntitySetToOperationDTOList(Set<OperationEntity> operationEntityList) {
        List<OperationDTO> operationDTOList = new ArrayList<>();
        for (OperationEntity operationEntity : operationEntityList) {
            operationDTOList.add(operationEntityToOperationDTO(operationEntity));
        }
        return operationDTOList;
    }
}
