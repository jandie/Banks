package repo;

import inter.domain.InterTransaction;
import inter.repo.TransactionRepo;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class TransactionRepoIT {
    @Test
    public void addTransactionTest() {
        InterTransaction interTransaction = new InterTransaction(
                "NLABNA1",
                "NLRABO1",
                50.6,
                new ArrayList<>(Arrays.asList("testreference")));

        new TransactionRepo().saveTransaction(interTransaction);

        new TransactionRepo().deleteSimilarTransaction(interTransaction);
    }

    @Test
    public void getTransactionTest() {
        InterTransaction interTransaction = new InterTransaction(
                "NLABNA1",
                "NLRABO1",
                50.6,
                new ArrayList<>(Arrays.asList("testreference")));

        new TransactionRepo().saveTransaction(interTransaction);

        interTransaction = new TransactionRepo()
                .getSimilarTransaction(interTransaction);

        System.out.println(interTransaction.toString());

        new TransactionRepo().deleteSimilarTransaction(interTransaction);
    }

    @Test
    public void UpdateTest() {
        InterTransaction interTransaction = new InterTransaction(
                "NLABNA1",
                "NLRABO1",
                50.6,
                new ArrayList<>(Arrays.asList("testreference")));

        new TransactionRepo().saveTransaction(interTransaction);

        interTransaction.increaseCount();
        interTransaction.increaseCount();
        interTransaction.increaseCount();

        new TransactionRepo().updateTransaction(interTransaction);

        interTransaction = new TransactionRepo()
                .getSimilarTransaction(interTransaction);

        new TransactionRepo().deleteSimilarTransaction(interTransaction);

        Assert.assertEquals(4, interTransaction.getCount());
    }
}
