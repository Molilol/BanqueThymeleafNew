package moli.ExoEvooq.service;

import moli.ExoEvooq.infrastructure.AccountRepoHibernate;
import moli.ExoEvooq.infrastructure.ClientRepoHibernate;
import moli.ExoEvooq.infrastructure.persistance.AccountEntity;
import moli.ExoEvooq.infrastructure.persistance.ClientEntity;
import moli.ExoEvooq.vue.AccountDTO;
import moli.ExoEvooq.vue.OperationCreateDTO;
import moli.ExoEvooq.vue.OperationDTO;
import moli.ExoEvooq.wrapper.WrapperDTOtoEntity;
import moli.ExoEvooq.wrapper.WrapperEntityToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Component
public class OperationService {

    @Autowired
    AccountRepoHibernate accountRepoHibernate;
    @Autowired
    ClientRepoHibernate clientRepoHibernate;
    @Autowired
    WrapperEntityToDTO wrapperEntityToDTO;
    @Autowired
    WrapperDTOtoEntity wrapperDTOtoEntity;

    public void createOperation(OperationCreateDTO operationCreateDTO) {
        Optional<AccountEntity> accountEntityOptional = accountRepoHibernate.findById(operationCreateDTO.getAccountId());
        if (accountEntityOptional.isPresent()) {
            AccountEntity accountEntity = accountEntityOptional.get();
            Optional<ClientEntity> clientEntityOptional = clientRepoHibernate.findById(accountEntity.getClient().getId());
            if (clientEntityOptional.isPresent()) {
                ClientEntity clientEntity = clientEntityOptional.get();
                OperationDTO operationDTO = new OperationDTO();
                operationDTO.setMontant(operationCreateDTO.getMontant());
                operationDTO.setOperationType(operationCreateDTO.getTypeOperation());
                operationDTO.setDate(LocalDateTime.now());
                AccountDTO accountDTO = wrapperEntityToDTO.accountEntityToAccountDTO(accountEntity);
                accountDTO.getOperationList().add(operationDTO);
                accountRepoHibernate.save(wrapperDTOtoEntity.accountDTOtoAccountEntity(accountDTO, clientEntity));
            }
        }
    }

}
