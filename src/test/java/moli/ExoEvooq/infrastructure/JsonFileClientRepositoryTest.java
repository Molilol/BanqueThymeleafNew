package moli.ExoEvooq.infrastructure;

import moli.ExoEvooq.domain.Account;
import moli.ExoEvooq.domain.Client;
import moli.ExoEvooq.domain.Montant;
import moli.ExoEvooq.domain.Operation;
import moli.ExoEvooq.infrastructure.persistance.AccountEntity;
import moli.ExoEvooq.infrastructure.persistance.ClientEntity;
import moli.ExoEvooq.infrastructure.persistance.OperationEntity;
import moli.ExoEvooq.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonFileClientRepositoryTest {

    public static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @Autowired
    ClientService clientService;

    @Test
    void save() throws Exception {
        JsonFileClientRepository jsonFileClientRepository = new JsonFileClientRepository();
        Client client = new Client("Ivan");
        Montant montant1Account1 = new Montant(100.0, "euros");
        Operation operation1 = new Operation(Operation.OperationType.DEPOSER, montant1Account1);
        Montant montant2Account1 = new Montant(200.0, "euros");
        Operation operation2 = new Operation(Operation.OperationType.DEPOSER, montant2Account1);

        List<Operation> operationListAccount1 = List.of(operation1, operation2);
        Account account1 = new Account("euros", operationListAccount1);

        Montant montant1Account2 = new Montant(400.0, "euros");
        Operation operation3 = new Operation(Operation.OperationType.DEPOSER, montant1Account2);
        Montant montant2Account2 = new Montant(300.0, "euros");
        Operation operation4 = new Operation(Operation.OperationType.DEPOSER, montant2Account2);


        List<Operation> operationListAccount2 = List.of(operation3, operation4);
        Account account2 = new Account("euros", operationListAccount2);

        List<Account> accountList = List.of(account1, account2);
        client.setAccountList(accountList);

        jsonFileClientRepository.save(client);

        String file = "C:\\Users\\Moli\\Desktop\\PROG\\ExoStageEvooq\\ExoEvooq\\src\\main\\resources\\saveClient\\" + client.getName() + ".json";
        String json = readFileAsString(file);

         assertEquals(true, json.contains("Ivan"));


    }


}
