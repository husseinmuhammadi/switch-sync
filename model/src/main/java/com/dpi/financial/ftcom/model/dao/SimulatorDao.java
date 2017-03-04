package com.dpi.financial.ftcom.model.dao;

import com.dpi.financial.ftcom.model.base.GenericDao;

import com.dpi.financial.ftcom.model.to.Simulator;

import javax.ejb.Stateless;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.text.MessageFormat;

@Stateless
public class SimulatorDao extends GenericDao<Simulator> {

    Logger logger = LoggerFactory.getLogger(SimulatorDao.class);
    public SimulatorDao() {
        super(Simulator.class);
    }

    public List<Simulator> findAll() {
        return createNamedQuery(Simulator.FIND_ALL).getResultList();}

    public Simulator findByRrn(String rrn) {
        logger.info(MessageFormat.format("Find simulator : {0}", rrn));
        return createNamedQuery(Simulator.FIND_BY_RRN).setParameter("rrn", rrn).getSingleResult();
    }
}
