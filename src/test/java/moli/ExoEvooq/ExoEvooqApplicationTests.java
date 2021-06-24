package moli.ExoEvooq;

import moli.ExoEvooq.domain.Account;
import moli.ExoEvooq.domain.Client;
import moli.ExoEvooq.domain.Montant;
import moli.ExoEvooq.domain.Operation;
import moli.ExoEvooq.infrastructure.ClientRepoHibernate;
import moli.ExoEvooq.service.ClientService;
import moli.ExoEvooq.infrastructure.JsonFileClientRepository;
import moli.ExoEvooq.infrastructure.persistance.ClientEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.*;

@SpringBootTest
public class ExoEvooqApplicationTests {

  /*  @Test
    public void save() throws IOException {
        JsonFileClientRepository jsonFileClientRepository = new JsonFileClientRepository();

        Client client = new Client("Ivan2");

        Montant montant1 = new Montant(200.0, "euros");
        Operation operation1 = new Operation(Operation.OperationType.DEPOSER, montant1);
        List<Operation> operationList = new ArrayList<>();
        operationList.add(operation1);

        Account account1 = new Account( "euros", operationList);

        List<Account> accountList = new ArrayList<>();
        accountList.add(account1);
        client.setAccountList(accountList);

        jsonFileClientRepository.save(client);

    } */

    @Autowired
    ClientService clientService;
    @Autowired
    ClientRepoHibernate clientRepoHibernate;


    @Test
    public void createClient() {

        ClientEntity clientEntityJohn = new ClientEntity();
        clientEntityJohn.setName("John");
        clientService.addNewClient(clientEntityJohn);

        //Optional<ClientEntity> clientEntityFindJohn = clientRepoHibernate.findByName("John");
        // Assertions.assertEquals("John", clientEntityFindJohn.get().getName());

    }



   /* @Test
    public void doNotCreateClientWithSameNameAndNoOverwrite() {
        ClientEntity clientEntityJohn = new ClientEntity();
        //clientEntityJohn.setId(UUID.randomUUID().toString());
        clientEntityJohn.setName("John");
        clientService.addNewClient(clientEntityJohn);

        ClientEntity clientEntityJohn2 = new ClientEntity();
       // clientEntityJohn2.setId(UUID.randomUUID().toString());
        clientEntityJohn2.setName("John");
        clientService.addNewClient(clientEntityJohn2);

        List<ClientEntity> clientList = clientRepoHibernate.findAllByName("John");

        Assertions.assertEquals(1, clientList.size());
        Assertions.assertEquals(clientEntityJohn.getId(), clientList.get(0).getId());

    } */


}
