package moli.ExoEvooq.infrastructure;

import moli.ExoEvooq.domain.Client;
import moli.ExoEvooq.infrastructure.persistance.ClientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepoHibernate extends CrudRepository<ClientEntity, String> {

    Optional<ClientEntity> findById(String id);

    Optional<ClientEntity> findByName(String name);

    List<ClientEntity> findAll();

    ClientEntity save(ClientEntity clientEntity);


}
