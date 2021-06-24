package moli.ExoEvooq;

import moli.ExoEvooq.infrastructure.AccountRepoHibernate;
import moli.ExoEvooq.infrastructure.OperationRepoHibernate;
import moli.ExoEvooq.infrastructure.persistance.AccountEntity;
import moli.ExoEvooq.infrastructure.persistance.ClientEntity;
import moli.ExoEvooq.infrastructure.persistance.OperationEntity;
import moli.ExoEvooq.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ExoEvooqApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExoEvooqApplication.class, args);

    }

    @Autowired
    ClientService clientService;
    @Autowired
    AccountRepoHibernate accountRepoHibernate;
    @Autowired
    OperationRepoHibernate operationRepoHibernate;

    @Bean
    CommandLineRunner runner() {
        return args -> {


            ClientEntity clientJohn = new ClientEntity("John");
            AccountEntity account = new AccountEntity(clientJohn, "Euros");
            OperationEntity operation = new OperationEntity();
            operation.setOperationType("DEPOSER");
            operation.setMontant("200");
            OperationEntity operation2 = new OperationEntity();
            operation2.setOperationType("DEPOSER");
            operation2.setMontant("300");
            operation.setAccount(account);
            operation2.setAccount(account);

            Set<OperationEntity> operationEntitySet = new HashSet<>();
            operationEntitySet.add(operation);
            operationEntitySet.add(operation2);

            account.setOperations(operationEntitySet);
            Set<AccountEntity> accountEntitySet = new HashSet<>();
            accountEntitySet.add(account);

            clientJohn.setAccounts(accountEntitySet);
            clientService.addNewClient(clientJohn);


          /*  ClientEntity clientIvan = new ClientEntity("Ivan");
            AccountEntity accountIvan = new AccountEntity(clientIvan, "Euros");
            OperationEntity operationIvan1 = new OperationEntity();
            operationIvan1.setOperationType("DEPOSER");
            operationIvan1.setMontant("10000");
            OperationEntity operationIvan2 = new OperationEntity();
            operationIvan2.setOperationType("DEPOSER");
            operationIvan2.setMontant("5000");
            operationIvan1.setAccount(accountIvan);
            operationIvan2.setAccount(accountIvan);

            Set<OperationEntity> operationEntitySetIvan = new HashSet<>();
            operationEntitySetIvan.add(operationIvan1);
            operationEntitySetIvan.add(operationIvan2);

            accountIvan.setOperations(operationEntitySetIvan);
            Set<AccountEntity> setAccountIvan = new HashSet<>();
            setAccountIvan.add(accountIvan);

            clientIvan.setAccounts(setAccountIvan);
            clientService.addNewClient(clientIvan); */
        };

    }
    //CommandLineRunner runner(ClientRepoHibernate clientRepoHibernate) {
	/*@Bean
	CommandLineRunner runner(ClientService clientService) {
		return args -> {
			ClientEntity clientEntity = new ClientEntity();
			clientEntity.setId(UUID.randomUUID().toString());
			clientEntity.setName("Ivan");
			clientService.addNewClient(clientEntity);
		//clientRepoHibernate.save(clientEntity);

		};

	}*/

}
