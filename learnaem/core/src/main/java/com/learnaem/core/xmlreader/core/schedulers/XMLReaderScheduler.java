package com.learnaem.core.xmlreader.core.schedulers;

import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.learnaem.core.xmlreader.core.configurations.XMLReaderConfiguration;
import com.learnaem.core.xmlreader.core.models.Items;
import com.learnaem.core.xmlreader.core.services.XMLReaderService;
import com.learnaem.core.xmlreader.core.services.XMLWriterService;


/**
 * @author Prathap Mullaguri
 */
@Component(immediate = true, service = Runnable.class)
@Designate(ocd = XMLReaderConfiguration.class)
public class XMLReaderScheduler implements Runnable {
    private static final int COUNT_VAL = 3;
    // Logger
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // Id of the scheduler based on its name
    private int schedulerId;

    // Scheduler instance injected
    @Reference
    private Scheduler scheduler;

    // XMLReaderService is injected
    @Reference
    private XMLReaderService xmlReaderService;

    // XMLWriterService is injected
    @Reference
    private XMLWriterService xmlWriterService;

    // URL of the XML response
    private String url;

    // Flag to check if the scheduler is enabled
    private boolean isEnabled;

    // Proxy IP to access the URL
    private String secureProxyIp;

    // Proxy port to access the URL
    private int secureProxyPort;

    // Email template
    private String emailTemplate;

    // Email id to which email will be sent
    private String emailId;

    /**
     * Activate method to initialize stuff.
     * 
     * @param xmlReaderConfiguration - XMLReaderConfiguration
     */
    @Activate
    protected void activate(XMLReaderConfiguration xmlReaderConfiguration) {
        schedulerId = xmlReaderConfiguration.name().hashCode();
        url = xmlReaderConfiguration.xmlResponseURL();
        isEnabled = xmlReaderConfiguration.enabled();
        secureProxyIp = xmlReaderConfiguration.secureProxyIp();
        secureProxyPort = Integer.parseInt(xmlReaderConfiguration.secureProxyPort());
        emailTemplate = xmlReaderConfiguration.emailTemplate();
        emailId = xmlReaderConfiguration.emailId();
        // Removing scheduler
        removeScheduler();
        // Updating the scheduler id
        schedulerId = xmlReaderConfiguration.name().hashCode();
        // Again adding the scheduler
        addScheduler(xmlReaderConfiguration);
    }

    /**
     * Modifies the scheduler id on modification.
     * 
     * @param xmlReaderConfiguration - XMLReaderConfiguration
     */
    @Modified
    protected void modified(XMLReaderConfiguration xmlReaderConfiguration) {
        url = xmlReaderConfiguration.xmlResponseURL();
        isEnabled = xmlReaderConfiguration.enabled();
        secureProxyIp = xmlReaderConfiguration.secureProxyIp();
        secureProxyPort = Integer.parseInt(xmlReaderConfiguration.secureProxyPort());
        emailTemplate = xmlReaderConfiguration.emailTemplate();
        emailId = xmlReaderConfiguration.emailId();
        // Removing scheduler
        removeScheduler();
        // Updating the scheduler id
        schedulerId = xmlReaderConfiguration.name().hashCode();
        // Again adding the scheduler
        addScheduler(xmlReaderConfiguration);
    }

    /**
     * This method deactivates the scheduler and removes it.
     * 
     * @param xmlReaderConfiguration - XMLReaderConfiguration
     */
    @Deactivate
    protected void deactivate(XMLReaderConfiguration xmlReaderConfiguration) {
        // Removing the scheduler
        removeScheduler();
    }

    /**
     * This method removes the scheduler.
     */
    private void removeScheduler() {
        log.debug("Removing scheduler: {}", schedulerId);
        // Unscheduling/removing the scheduler
        scheduler.unschedule(String.valueOf(schedulerId));
    }

    /**
     * This method adds the scheduler.
     * 
     * @param schedulerConfiguration - XMLReaderConfiguration
     */
    private void addScheduler(XMLReaderConfiguration xmlReaderConfiguration) {
        // Check if the scheduler is enabled
        if (isEnabled) {
            // Scheduler option takes the cron expression as a parameter and run accordingly
            ScheduleOptions scheduleOptions = scheduler.EXPR(xmlReaderConfiguration.cronExpression());
            // Adding some parameters
            scheduleOptions.name(xmlReaderConfiguration.name());
            scheduleOptions.canRunConcurrently(false);

            // Scheduling the job
            scheduler.schedule(this, scheduleOptions);
            log.debug("Scheduler added");
        } else {
            log.debug("Scheduler is disabled");
        }
    }

    /**
     * Overridden run method to execute Job.
     */
    @Override
    public void run() {
        if (isEnabled) {
            log.debug("Scheduler is Running");
            Items items = null;
            // Reads XML data from a file in the system

            // Reads XML data from a response
            if (url != null && !url.isEmpty()) {
                int count = 1;
                while (items == null) {
                    items = xmlReaderService.readXMLFromURL(url, secureProxyIp, secureProxyPort, emailTemplate, emailId, count);
                    if (count == COUNT_VAL) {
                        break;
                    }
                    count++;
                }
                if (items != null) {
                    xmlWriterService.writeXMLToJCR(items);
                }
            }
        }
    }
}