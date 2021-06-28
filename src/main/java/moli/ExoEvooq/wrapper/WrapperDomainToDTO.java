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

    public OperationDTO operationDomainToOperationDTO(Operation operation) {
        return new OperationDTO(
                operation.getId(),
                String.valueOf(operation.getOperationType()),
                operation.getMontant().getMontant(),
                operation.getDate()
        );
    }

    public AccountDTO accountDomainToAccountDTO(Account account) {
        List<OperationDTO> operationDTOList = new ArrayList<>();
        for (Operation operation : account.getOperations()) {
            operationDTOList.add(operationDomainToOperationDTO(operation));
        }
        return new AccountDTO(
                account.getId(),
                account.getDevise(),
                operationDTOList,
                account.getTotal().getMontant(),
                account.getDate()
        );
    }

    public ClientDTO clientDomainToClientDTO(Client client) {
        List<AccountDTO> accountDTOList = new ArrayList<>();
        for (Account account : client.getAccountList()) {
            accountDTOList.add(accountDomainToAccountDTO(account));
        }
        return new ClientDTO(
                client.getId(),
                client.getName(),
                accountDTOList,
                client.getDate()
        );
    }

    public List<ClientDTO> clientDomainListToClientDTOList(List<Client> clientList) {
        List<ClientDTO> clientDTOList = new ArrayList<>();
        for (Client client : clientList) {
            clientDTOList.add(clientDomainToClientDTO(client));
        }
        return clientDTOList;
    }

}
