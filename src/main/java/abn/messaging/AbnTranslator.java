package abn.messaging;

import abn.domain.AbnFeedback;
import abn.domain.AbnTransaction;
import abn.domain.enums.AbnState;
import inter.domain.InterFeedback;
import inter.domain.InterTransaction;
import inter.domain.enums.InterState;

public class AbnTranslator {
    public AbnTransaction transaction(InterTransaction interTransaction) {
        return new AbnTransaction(
                interTransaction.getToAccount(),
                interTransaction.getFromAccount(),
                interTransaction.getAmount(),
                interTransaction.getFromReference());
    }

    public InterTransaction transaction(AbnTransaction abnTransaction) {
        return new InterTransaction(
                abnTransaction.getToAccount(),
                abnTransaction.getFromAccount(),
                abnTransaction.getAmount(),
                abnTransaction.getFromReference());
    }

    public AbnFeedback feedback(InterFeedback interFeedback) {
        return new AbnFeedback(
                transaction(interFeedback.getTransaction()),
                state(interFeedback.getState()),
                interFeedback.getMessage()
        );
    }

    public InterFeedback feedback(AbnFeedback abnFeedback) {
        return new InterFeedback(
                transaction(abnFeedback.getTransaction()),
                state(abnFeedback.getState()),
                abnFeedback.getMessage()
        );
    }

    private AbnState state(InterState interState) {
        return AbnState.valueOf(interState.toString());
    }

    private InterState state(AbnState abnState) {
        return InterState.valueOf(abnState.toString());
    }
}
