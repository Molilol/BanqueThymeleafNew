package moli.ExoEvooq.infrastructure.persistance;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "OPERATIONS")
@Setter
@Getter
public class OperationEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String operationType;

    @JoinColumn(name = "account_id")
    @ManyToOne
    private AccountEntity account;

    private Double montant;

    private LocalDateTime date;

    public OperationEntity() {
    }

}
