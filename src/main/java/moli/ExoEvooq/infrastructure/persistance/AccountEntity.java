package moli.ExoEvooq.infrastructure.persistance;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ACCOUNTS")
@Setter
@Getter
public class AccountEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    private String devise;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<OperationEntity> operations = new HashSet<>();

    public AccountEntity() {
    }

    public AccountEntity(ClientEntity clientEntity, String devise) {
        this.client = clientEntity;
        this.devise = devise;
    }

}
