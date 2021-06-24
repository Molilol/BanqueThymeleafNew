package moli.ExoEvooq.infrastructure;

import moli.ExoEvooq.infrastructure.persistance.OperationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OperationRepoHibernate extends CrudRepository<OperationEntity, String> {

    Optional<OperationEntity> findById(String id);

    List<OperationEntity> findByAccountId(String accountId);

    OperationEntity save(OperationEntity operationEntity);


}
