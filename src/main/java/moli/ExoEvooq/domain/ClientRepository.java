package moli.ExoEvooq.domain;

import java.io.IOException;


public interface ClientRepository {

    Client findById(Long id);

    void save(Client clientToSave) throws IOException;

}
