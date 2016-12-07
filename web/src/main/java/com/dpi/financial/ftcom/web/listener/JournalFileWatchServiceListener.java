package com.dpi.financial.ftcom.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;

/**
 * Created by h.mohammadi on 11/19/2016.
 */
@Deprecated
public class JournalFileWatchServiceListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
