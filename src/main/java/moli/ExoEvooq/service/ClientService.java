package moli.ExoEvooq.service;

import moli.ExoEvooq.domain.Account;
import moli.ExoEvooq.domain.Montant;
import moli.ExoEvooq.domain.Operation;
import moli.ExoEvooq.infrastructure.ClientRepoHibernate;
import moli.ExoEvooq.infrastructure.persistance.AccountEntity;
import moli.ExoEvooq.infrastructure.persistance.ClientEntity;
import moli.ExoEvooq.infrastructure.persistance.OperationEntity;
import moli.ExoEvooq.vue.AccountDTO;
import moli.ExoEvooq.vue.ClientDTO;
import moli.ExoEvooq.vue.OperationDTO;
import moli.ExoEvooq.vue.UserCreateDTO;
import moli.ExoEvooq.wrapper.WrapperDTOtoEntity;
import moli.ExoEvooq.wrapper.WrapperEntityToDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class ClientService {

    @Autowired
    ClientRepoHibernate clientRepoHibernate;
    @Autowired
    WrapperEntityToDomain wrapperEntityToDomain;
    @Autowired
    WrapperDTOtoEntity wrapperDTOtoEntity;


    @Transactional
    public void addNewClient(ClientEntity client) {
        Optional<ClientEntity> clientByName = clientRepoHibernate.findByName(client.getName());
        if (!clientByName.isPresent()) {
            clientRepoHibernate.save(client);
        }
    }

 /*   public String totalAccount(AccountEntity accountEntity) {
        List<Operation> operations = new ArrayList<>();
        for (OperationEntity operationEntity : accountEntity.getOperations()) {
            Montant montant = new Montant(
                    operationEntity.getMontant(),
                    accountEntity.getDevise());
            Operation operation = new Operation(
                    Operation.OperationType.valueOf(operationEntity.getOperationType()),
                    montant);
            operations.add(operation);
        }
        Account account = new Account(
                accountEntity.getDevise(),
                operations);
        Montant montantTotalPerAccount = account.getTotal();
        return String.valueOf(montantTotalPerAccount.getMontant());
    } */

    public String syntheseTotalAllClients(List<ClientDTO> clientDTOList) {
        double syntheseTotalAllClients = 0.0;
        for (ClientDTO clientDTO : clientDTOList) {
            syntheseTotalAllClients += clientDTO.totalAllAccount();
        }
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return formatter.format(syntheseTotalAllClients);
    }

    public Account accountEntityToAccountDomain(AccountEntity accountEntity) {
        ClientEntity clientEntity = clientRepoHibernate.findById(accountEntity.getClient().getId()).get();
        Account account = wrapperEntityToDomain.accountEntityToAccountDomain(accountEntity, clientEntity);
        return account;
    }

    public String nbAccountAllClients(List<ClientDTO> clientDTOList) {
        int nbAccountAllClients = 0;
        for (ClientDTO clientDTO : clientDTOList) {
            nbAccountAllClients += clientDTO.getAccountClient().size();
        }
        return String.valueOf(nbAccountAllClients);
    }

    public void createUser(UserCreateDTO userCreateDTO) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(userCreateDTO.getName());
        clientDTO.setDate(LocalDateTime.now());
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setDevise("Euros");
        accountDTO.setDate(LocalDateTime.now());
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setOperationType("DEPOSER");
        operationDTO.setMontant(userCreateDTO.getMontant());
        operationDTO.setDate(LocalDateTime.now());
        List<OperationDTO> operationDTOList = new ArrayList<>();
        operationDTOList.add(operationDTO);
        accountDTO.setOperationList(operationDTOList);
        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(accountDTO);
        clientDTO.setAccountClient(accountDTOList);
        addNewClient(wrapperDTOtoEntity.clientDTOtoClientEntity(clientDTO));
    }
}

   /* public List<ClientDeployment> getDeploymentsPerClient() {
        List<Deployment> deployments = deploymentRepository.findLastDeploymentPerClientAndEnvironment();
        Map<String, List<Deployment>> deploymentsGroupedByClients = deployments.stream()
                .collect(Collectors.groupingBy(deployment -> deployment.getClient()));

        return deploymentsGroupedByClients.entrySet().stream()
                .map(x -> new ClientDeployment(x.getKey(), x.getValue()))
                .collect(Collectors.toList());
    }

 */

/*  public ClientEntity makeClientEntityByName(String clientName) {
        ClientEntity clientEntity = clientRepoHibernate.findByName(clientName);
        List<AccountEntity> accountEntityList = accountRepoHibernate.findByClientId(clientName);
        clientEntity.setAccounts(Set.copyOf(accountEntityList));
        List<OperationEntity> operationEntityList = new ArrayList<>();
        for (AccountEntity accountEntity : accountEntityList) {
            operationEntityList = operationRepoHibernate.findByAccountId(accountEntity.getId());
        }




     /*   Map<String, List<OperationEntity>> operationEntityListByAccount = operationEntityList.stream()
                .collect(Collectors.groupingBy(operation -> operation.getAccount().getId()));

        return operationEntityListByAccount.entrySet().stream()
                .map(x -> new OperationEntity(x.getValue(), x/)).collect(Collectors.toList()); */