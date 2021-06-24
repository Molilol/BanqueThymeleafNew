package moli.ExoEvooq.wrapper;

import moli.ExoEvooq.domain.Account;
import moli.ExoEvooq.infrastructure.persistance.AccountEntity;
import moli.ExoEvooq.infrastructure.persistance.ClientEntity;
import moli.ExoEvooq.infrastructure.persistance.OperationEntity;
import moli.ExoEvooq.service.ClientService;
import moli.ExoEvooq.vue.AccountDTO;
import moli.ExoEvooq.vue.ClientDTO;
import moli.ExoEvooq.vue.OperationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class WrapperEntityToDTO {

    @Autowired
    ClientService clientService;

    public ClientDTO clientEntityToClientDTO(ClientEntity clientEntity) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(clientEntity.getName());
        List<AccountDTO> accountDTOList = accountEntitySetToAccountDTOList(clientEntity.getAccounts());
        clientDTO.setAccountClient(accountDTOList);
        return clientDTO;
    }

    public AccountDTO accountEntityToAccountDTO(AccountEntity accountEntity) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setDevise(accountEntity.getDevise());
        accountDTO.setTotal(clientService.totalAccount(accountEntity));
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
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setOperationType(operationEntity.getOperationType());
        operationDTO.setMontant(operationEntity.getMontant());
        return operationDTO;
    }

    public List<OperationDTO> operationEntitySetToOperationDTOList(Set<OperationEntity> operationEntityList) {
        List<OperationDTO> operationDTOList = new ArrayList<>();
        for (OperationEntity operationEntity : operationEntityList) {
            operationDTOList.add(operationEntityToOperationDTO(operationEntity));
        }
        return operationDTOList;
    }
}
