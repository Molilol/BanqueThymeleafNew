package moli.ExoEvooq.infrastructure.persistance;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CLIENTS")
@Data
public class ClientEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<AccountEntity> accounts = new HashSet<>();

    public ClientEntity() {
    }

    public ClientEntity(String name) {
        this.name = name;
    }


}
