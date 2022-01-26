package com.learnaem.core.xmlreader.core.configurations;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * @author Prathap Mullaguri
 */
@ObjectClassDefinition(name = "BT PLC XML Reader", description = "This configuration lets us read XML file from the system or XML response from a URLl̥")
public @interface XMLReaderConfiguration {

    /**
     * scheduler name.
     * 
     * @return name
     */
    @AttributeDefinition(name = "Scheduler name", description = "Name of the scheduler", type = AttributeType.STRING)
    String name() default "XML Reader Scheduler";
    /**
     * scheduler enable flag.
     * 
     * @return enable flag
     */
    @AttributeDefinition(name = "Enabled", description = "Flag to enable/disable a scheduler", type = AttributeType.BOOLEAN)
    boolean enabled() default true;
    /**
     * cron expression for scheduler.
     * 
     * @return cron exp
     */
    @AttributeDefinition(name = "Cron expression", description = "Cron expression used by the scheduler", type = AttributeType.STRING)
    String cronExpression() default "0 */1 * * * ?";
    /**
     * xml response url.
     * 
     * @return response url
     */
    @AttributeDefinition(name = "XML response URL", description = "URL from where XML response is to be read", type = AttributeType.STRING)
    String xmlResponseURL() default "https://www.btcontactcentrejobs.com/jobs/feeds/all-vacancies";
    /**
     * Secure proxy ip.
     * 
     * @return ip
     */
    @AttributeDefinition(name = "Secure Proxy IP", description = "Secure Proxy IP.", type = AttributeType.STRING)
    String secureProxyIp() default "xorv5.nat.bt.com";
    /**
     * Secure proxy port.
     * 
     * @return port
     */
    @AttributeDefinition(name = "Secure Proxy Port", description = "Secure Proxy Port.", type = AttributeType.STRING)
    String secureProxyPort() default "8080";
    /**
     * Secure proxy port.
     * 
     * @return port
     */
    @AttributeDefinition(name = "Email template", description = "Email template.", type = AttributeType.STRING)
    String emailTemplate() default "/etc/notification/email/bt-plc/contact-center-job-template.html";
    /**
     * Secure proxy port.
     * 
     * @return port
     */
    @AttributeDefinition(name = "Email ID", description = "Email ID.", type = AttributeType.STRING)
    String emailId() default "";
}
