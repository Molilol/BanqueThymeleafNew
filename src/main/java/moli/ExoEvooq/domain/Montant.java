package moli.ExoEvooq.domain;

import lombok.Value;

@Value
public class Montant {

    Double montant;

    String devise;

    public Montant soustraire(Montant other) {
        if (devise.equals(other.getDevise())) {
            return new Montant(montant - other.getMontant(), other.getDevise());
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public Montant addition(Montant other) {
        if (devise.equals(other.getDevise())) {
            return new Montant(montant + other.getMontant(), other.getDevise());
        } else {
            throw new UnsupportedOperationException();
        }

    }

}
