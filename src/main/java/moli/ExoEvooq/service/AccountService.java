package moli.ExoEvooq.service;

import moli.ExoEvooq.infrastructure.AccountRepoHibernate;
import moli.ExoEvooq.infrastructure.ClientRepoHibernate;
import moli.ExoEvooq.infrastructure.persistance.ClientEntity;
import moli.ExoEvooq.vue.AccountCreateDTO;
import moli.ExoEvooq.vue.AccountDTO;
import moli.ExoEvooq.vue.OperationDTO;
import moli.ExoEvooq.wrapper.WrapperDTOtoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class AccountService {

    @Autowired
    ClientRepoHibernate clientRepoHibernate;
    @Autowired
    AccountRepoHibernate accountRepoHibernate;
    @Autowired
    WrapperDTOtoEntity wrapperDTOtoEntity;

    public void createAccount(AccountCreateDTO accountCreateDTO) {

        Optional<ClientEntity> clientEntityOp = clientRepoHibernate.findById(accountCreateDTO.getClientId());
        if (clientEntityOp.isPresent()) {
            ClientEntity clientEntity = clientEntityOp.get();
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setDevise(accountCreateDTO.getDevise());
            accountDTO.setDate(LocalDateTime.now());
            OperationDTO operationDTO = new OperationDTO();
            operationDTO.setDate(LocalDateTime.now());
            operationDTO.setOperationType("DEPOSER");
            operationDTO.setMontant(accountCreateDTO.getSomme());
            List<OperationDTO> operationDTOList = new ArrayList<>();
            operationDTOList.add(operationDTO);
            accountDTO.setOperationList(operationDTOList);
            accountRepoHibernate.save(wrapperDTOtoEntity.accountDTOtoAccountEntity(accountDTO, clientEntity));
        }
    }

}
