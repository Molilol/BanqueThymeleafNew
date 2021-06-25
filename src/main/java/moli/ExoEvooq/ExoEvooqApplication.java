package moli.ExoEvooq;

import moli.ExoEvooq.domain.Operation;
import moli.ExoEvooq.infrastructure.AccountRepoHibernate;
import moli.ExoEvooq.infrastructure.OperationRepoHibernate;
import moli.ExoEvooq.infrastructure.persistance.AccountEntity;
import moli.ExoEvooq.infrastructure.persistance.ClientEntity;
import moli.ExoEvooq.infrastructure.persistance.OperationEntity;
import moli.ExoEvooq.service.ClientService;
import moli.ExoEvooq.vue.AccountDTO;
import moli.ExoEvooq.vue.ClientDTO;
import moli.ExoEvooq.vue.OperationDTO;
import moli.ExoEvooq.wrapper.WrapperDTOtoEntity;
import org.hibernate.collection.internal.PersistentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @Autowired
    WrapperDTOtoEntity wrapperDTOtoEntity;

    @Bean
    CommandLineRunner runner() {
        return args -> {
            ClientEntity clientJohn = new ClientEntity("John");
          //  clientJohn.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
            clientJohn.setDate(LocalDateTime.now());
            AccountEntity account = new AccountEntity(clientJohn, "Euros");
            account.setDate(LocalDateTime.now());
            OperationEntity operation = new OperationEntity();
            operation.setOperationType("DEPOSER");
            operation.setMontant(200.0);
            operation.setDate(LocalDateTime.now());
            OperationEntity operation2 = new OperationEntity();
            operation2.setOperationType("DEPOSER");
            operation2.setMontant(1000000.0);
            operation2.setDate(LocalDateTime.now());
            OperationEntity operationSous = new OperationEntity();
            operationSous.setOperationType("RETIRER");
            operationSous.setMontant(200.0);
            operationSous.setDate(LocalDateTime.now());
            operation.setAccount(account);
            operation2.setAccount(account);
            operationSous.setAccount(account);

            Set<OperationEntity> operationEntitySet = new HashSet<>();
            operationEntitySet.add(operation);
            operationEntitySet.add(operation2);
            operationEntitySet.add(operationSous);

            account.setOperations(operationEntitySet);
            Set<AccountEntity> accountEntitySet = new HashSet<>();

            AccountEntity account2 = new AccountEntity(clientJohn, "Euros");
            account2.setDate(LocalDateTime.now());
            OperationEntity operation3 = new OperationEntity();
            operation3.setOperationType("DEPOSER");
            operation3.setMontant(15200.50);
            operation3.setDate(LocalDateTime.now());
            OperationEntity operation4 = new OperationEntity();
            operation4.setOperationType("DEPOSER");
            operation4.setMontant(10000.0);
            operation4.setDate(LocalDateTime.now());
            operation3.setAccount(account2);
            operation4.setAccount(account2);
            Set<OperationEntity> operationEntitySet2 = new HashSet<>();
            operationEntitySet2.add(operation3);
            operationEntitySet2.add(operation4);
            account2.setOperations(operationEntitySet2);
            accountEntitySet.add(account);
            accountEntitySet.add(account2);
            clientJohn.setAccounts(accountEntitySet);
            clientService.addNewClient(clientJohn);

            ClientEntity clientIvan = new ClientEntity("Ivan");
          //  clientIvan.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
            clientIvan.setDate(LocalDateTime.now());
            AccountEntity accountIvan = new AccountEntity(clientIvan, "Euros");
            OperationEntity operationIvan1 = new OperationEntity();
            operationIvan1.setOperationType("DEPOSER");
            operationIvan1.setMontant(10000.0);
            OperationEntity operationIvan2 = new OperationEntity();
            operationIvan2.setOperationType("DEPOSER");
            operationIvan2.setMontant(5000.0);
            operationIvan1.setAccount(accountIvan);
            operationIvan2.setAccount(accountIvan);

            Set<OperationEntity> operationEntitySetIvan = new HashSet<>();
            operationEntitySetIvan.add(operationIvan1);
            operationEntitySetIvan.add(operationIvan2);

            accountIvan.setOperations(operationEntitySetIvan);
            Set<AccountEntity> setAccountIvan = new HashSet<>();
            setAccountIvan.add(accountIvan);

            clientIvan.setAccounts(setAccountIvan);
            clientService.addNewClient(clientIvan);

         /*   ClientDTO clientDTO = new ClientDTO();
            clientDTO.setName("TestDTO");
            List<AccountDTO> accountDTOList = new ArrayList<>();
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setDevise("Euros");
            //accountDTO.setClientDTO(clientDTO);
            List <OperationDTO> operationDTOList = new ArrayList<>();
            OperationDTO operationDTO = new OperationDTO();
            operationDTO.setMontant(1000.0);
            operationDTO.setOperationType(Operation.OperationType.DEPOSER.name());
            operationDTOList.add(operationDTO);
            accountDTO.setOperationList(operationDTOList);
            accountDTOList.add(accountDTO);
            clientDTO.setAccountClient(accountDTOList);

            clientService.addNewClient(wrapperDTOtoEntity.clientDTOtoClientEntity(clientDTO));
            */
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
