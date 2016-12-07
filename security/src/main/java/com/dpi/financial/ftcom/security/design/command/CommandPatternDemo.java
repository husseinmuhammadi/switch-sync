package com.dpi.financial.ftcom.security.design.command;

import com.dpi.financial.ftcom.model.to.cms.card.CardMaster;
import com.dpi.financial.ftcom.security.design.command.card.verification.CardVerificationValueService;
import com.dpi.financial.ftcom.security.design.command.card.verification.command.GenerateCardVerificationValueCommand;

/**
 * Created by Hossein on 08/11/2016.
 */
public class CommandPatternDemo {
    public void run() {
        CardVerificationValueService cardVerificationValueService = new CardVerificationValueService(new CardMaster());

        GenerateCardVerificationValueCommand command = new GenerateCardVerificationValueCommand(cardVerificationValueService);
        Broker broker = new Broker(null);
        broker.process(command);
    }

}
