package com.dpi.financial.ftcom.service.base.meb.atm.journal;

import com.dpi.financial.ftcom.api.base.meb.atm.JournalFileService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.meb.atm.journal.JournalFileDao;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.meb.atm.journal.JournalFile;
import com.dpi.financial.ftcom.model.type.atm.journal.JournalFileState;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;
import com.dpi.financial.ftcom.utility.date.DateUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@Local(JournalFileService.class)
public class JournalFileServiceImpl extends GeneralServiceImpl<JournalFile>
        implements JournalFileService {

    Logger logger = LoggerFactory.getLogger(JournalFileServiceImpl.class);

    @EJB
    private JournalFileDao dao;

    @Override
    public GenericDao<JournalFile> getGenericDao() {
        return dao;
    }

    @Override
    public List<JournalFile> findAll(Terminal terminal) {
        logger.info(MessageFormat.format("Find journal files for terminal {0}", terminal.getLuno()));
        return dao.findAll(terminal);
    }

    @Override
    public List<JournalFile> getJournalFileList(Terminal terminal, Date journalDateFrom, Date journalDateTo) {
        findAll(terminal).stream()
                .filter(item -> DateUtil.isBetweenDate(item.getJournalDate(), journalDateFrom, journalDateTo))
                .collect(Collectors.toList());
        return findAll(terminal);
    }
}
