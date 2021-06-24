package moli.ExoEvooq.wrapper;

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

@Component
public class WrapperEntityToDTO {

    @Autowired
    ClientService clientService;

    public ClientDTO clientEntityToClientDTO(ClientEntity clientEntity) {

        ClientDTO clientDTO = new ClientDTO();
        if (clientEntity.getId() != null) {
            clientDTO.setId(clientEntity.getId());
        }
        clientDTO.setName(clientEntity.getName());
        List<AccountDTO> accountDTOList = new ArrayList<>();
        for (AccountEntity accountEntity : clientEntity.getAccounts()) {
            AccountDTO accountDTO = new AccountDTO();
            if (accountEntity.getId() != null) {
                accountDTO.setId(accountEntity.getId());
            }
            accountDTO.setClientDTO(clientDTO);
            accountDTO.setDevise(accountEntity.getDevise());
            accountDTO.setTotal(clientService.totalAccount(accountEntity));
            List<OperationDTO> operationDTOList = new ArrayList<>();
            for (OperationEntity operationEntity : accountEntity.getOperations()) {
                OperationDTO operationDTO = new OperationDTO();
                if (operationEntity.getId() != null) {
                    operationDTO.setId(operationEntity.getId());
                }
                operationDTO.setOperationType(operationEntity.getOperationType());
                operationDTO.setMontant(operationEntity.getMontant());
                operationDTOList.add(operationDTO);
            }
            accountDTO.setOperationList(operationDTOList);
            accountDTOList.add(accountDTO);
        }
        clientDTO.setAccountClient(accountDTOList);

        return clientDTO;
    }

}
