package moli.ExoEvooq.service;

import moli.ExoEvooq.domain.Account;
import moli.ExoEvooq.domain.Montant;
import moli.ExoEvooq.domain.Operation;
import moli.ExoEvooq.infrastructure.ClientRepoHibernate;
import moli.ExoEvooq.infrastructure.persistance.AccountEntity;
import moli.ExoEvooq.infrastructure.persistance.ClientEntity;
import moli.ExoEvooq.infrastructure.persistance.OperationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class ClientService {

    @Autowired
    ClientRepoHibernate clientRepoHibernate;

    @Transactional
    public void addNewClient(ClientEntity client) {
        Optional<ClientEntity> clientByName = clientRepoHibernate.findByName(client.getName());
        if (!clientByName.isPresent()) {
            clientRepoHibernate.save(client);
        }
    }



    public String totalAccount(AccountEntity accountEntity) {
        List<Operation> operations = new ArrayList<>();
        for (OperationEntity operationEntity : accountEntity.getOperations()) {
            Montant montant = new Montant(
                    Double.parseDouble(operationEntity.getMontant()),
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