package com.dpi.financial.ftcom.security.design.command.card.verification.command;

import com.dpi.financial.ftcom.security.design.command.ICommand;
import com.dpi.financial.ftcom.security.design.command.card.verification.CardVerificationValueService;

public class GenerateCardVerificationValueCommand implements ICommand {
    private CardVerificationValueService cardVerificationValueService;

    public GenerateCardVerificationValueCommand(CardVerificationValueService cardVerificationValueService) {
        this.cardVerificationValueService = cardVerificationValueService;
    }

    @Override
    public void execute() {
        cardVerificationValueService.generateCardVerificationValue();
    }
}
