package rabo.messaging;

import abn.domain.AbnFeedback;
import abn.domain.AbnTransaction;
import abn.domain.enums.AbnState;
import inter.domain.InterFeedback;
import inter.domain.InterTransaction;
import inter.domain.enums.InterState;
import rabo.domain.RaboFeedback;
import rabo.domain.RaboTransaction;
import rabo.domain.enums.RaboState;

public class RaboTranslator {
    public RaboTransaction transaction(InterTransaction interTransaction) {
        return new RaboTransaction(
                interTransaction.getToAccount(),
                interTransaction.getFromAccount(),
                interTransaction.getAmount(),
                interTransaction.getFromReference());
    }

    public InterTransaction transaction(RaboTransaction raboTransaction) {
        return new InterTransaction(
                raboTransaction.getToAccount(),
                raboTransaction.getFromAccount(),
                raboTransaction.getAmount(),
                raboTransaction.getFromReference());
    }

    public RaboFeedback feedback(InterFeedback interFeedback) {
        return new RaboFeedback(
                transaction(interFeedback.getTransaction()),
                state(interFeedback.getState()),
                interFeedback.getMessage()
        );
    }

    public InterFeedback feedback(RaboFeedback raboFeedback) {
        return new InterFeedback(
                transaction(raboFeedback.getTransaction()),
                state(raboFeedback.getState()),
                raboFeedback.getMessage()
        );
    }

    private RaboState state(InterState interState) {
        return RaboState.valueOf(interState.toString());
    }

    private InterState state(RaboState raboState) {
        return InterState.valueOf(raboState.toString());
    }
}
