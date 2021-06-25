package moli.ExoEvooq.wrapper;

import moli.ExoEvooq.domain.Account;
import moli.ExoEvooq.domain.Client;
import moli.ExoEvooq.domain.Operation;
import moli.ExoEvooq.vue.AccountDTO;
import moli.ExoEvooq.vue.ClientDTO;
import moli.ExoEvooq.vue.OperationDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WrapperDomainToDTO {

    public OperationDTO operationDomainToOperationDTO (Operation operation) {
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setId(operation.getId());
        operationDTO.setOperationType(String.valueOf(operation.getOperationType()));
        operationDTO.setMontant(operation.getMontant().getMontant());
        operationDTO.setDate(operation.getDate());
        return operationDTO;
    }

    public AccountDTO accountDomainToAccountDTO (Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(accountDTO.getId());
        accountDTO.setDevise(account.getDevise());
        List<OperationDTO> operationDTOList = new ArrayList<>();
        for (Operation operation : account.getOperations()) {
            operationDTOList.add(operationDomainToOperationDTO(operation));
        }
        accountDTO.setOperationList(operationDTOList);
        accountDTO.setTotal(account.getTotal().getMontant());
        accountDTO.setDate(account.getDate());
        return accountDTO;
    }

    public ClientDTO clientDomainToClientDTO (Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        List <AccountDTO> accountDTOList = new ArrayList<>();
        for (Account account : client.getAccountList()) {
            accountDTOList.add(accountDomainToAccountDTO(account));
        }
        clientDTO.setAccountClient(accountDTOList);
        clientDTO.setDate(client.getDate());
        return clientDTO;
    }

}
