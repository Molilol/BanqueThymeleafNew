package moli.ExoEvooq.domain;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationTest {


    @Test(expected=UnsupportedOperationException.class)
    public void deviseDifferentSoustraireTest() {
        Montant montant = new Montant(100.0, "euros");
        Montant montantASoustraire = new Montant(50.0, "dollar");
        montant.soustraire(montantASoustraire);

    }


    @Test
    public void soustraireTest() {
        Montant montant = new Montant(100.0, "euros");
        Montant montantASoustraire = new Montant(50.0, "euros");

        assertEquals(50.0, montant.soustraire(montantASoustraire).getMontant());
    }

    @Test
   public void additionTest() {
        Montant montant = new Montant(100.0, "euros");
        Montant montantAAddictionner = new Montant(50.0, "euros");
        montant.addition(montantAAddictionner);

        assertEquals(150.0, montant.addition(montantAAddictionner).getMontant());
    }


    @Test
    public void getTotalTest() {
        Montant montant1 = new Montant(100.0, "euros");
        Operation operation1 = new Operation(Operation.OperationType.DEPOSER, montant1);

        Montant montant2 = new Montant(200.0, "euros");
        Operation operation2 = new Operation(Operation.OperationType.DEPOSER, montant2);

        Montant montant3 = new Montant(100.0, "euros");
        Operation operation3 = new Operation(Operation.OperationType.RETIRER, montant3);

        Account account = new Account("euros", List.of(
                operation1,
                operation2,
                operation3
        ));

        Montant montantResult = account.getTotal();

        Assertions.assertNotNull(account.getId());
        assertEquals(200, montantResult.getMontant());
        assertEquals("euros", montantResult.getDevise());

    }


}