package com.dpi.financial.ftcom.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class SyncPhaseListener implements PhaseListener {
    Logger logger = LoggerFactory.getLogger(SyncPhaseListener.class);

    @Override
    public void afterPhase(PhaseEvent event) {
        logger.info("--- After phase: " + event.getPhaseId());
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        logger.info("--- Before phase: " + event.getPhaseId());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}
