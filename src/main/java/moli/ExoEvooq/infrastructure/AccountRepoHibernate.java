package moli.ExoEvooq.infrastructure;

import moli.ExoEvooq.infrastructure.persistance.AccountEntity;
import org.springframework.data.repository.CrudRepository;


public interface AccountRepoHibernate extends CrudRepository<AccountEntity, String> {

    AccountEntity save(AccountEntity accountEntity);


}
