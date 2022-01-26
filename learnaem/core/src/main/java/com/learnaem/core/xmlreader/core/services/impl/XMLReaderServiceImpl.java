package com.learnaem.core.xmlreader.core.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.mcm.emailprovider.EmailService;
import com.learnaem.core.xmlreader.core.models.Items;
import com.learnaem.core.xmlreader.core.services.XMLReaderService;

/**
 * @author Prathap Mullaguri
 */
@Component(immediate = true, service = XMLReaderService.class)
public class XMLReaderServiceImpl implements XMLReaderService {

    // Logger
    private final Logger     log                = LoggerFactory.getLogger(this.getClass());
    private static final int TIMEOUT_VALUE      = 30;
    private static final int TIMEOUT_MULTIPLIER = 1000;
    private static final int COUNT_VAL          = 3;

    @Reference
    private EmailService emailService;

    /**
     * read xml data from url.
     * 
     * @param responseURL
     *            - String
     * @return items
     */
    @Override
    public Items readXMLFromURL(String responseURL, String secureProxyIp, int secureProxyPort, String emailTemplate,
            String emailId, int count) {
        URLConnection urlConnection = null;
        InputStreamReader inputStreamReader = null;
        StringBuilder builder = new StringBuilder();
        Items items = null;

        try {
            SocketAddress addr = new InetSocketAddress(secureProxyIp, secureProxyPort);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
            URL url = new URL(responseURL);
            urlConnection = url.openConnection(proxy);

            urlConnection.setReadTimeout(TIMEOUT_VALUE * TIMEOUT_MULTIPLIER);

            inputStreamReader = new InputStreamReader(urlConnection.getInputStream(), Charset.defaultCharset());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int eof;
            while ((eof = bufferedReader.read()) != -1) {
                builder.append((char) eof);
            }
            bufferedReader.close();
            inputStreamReader.close();

            String xmlResponse = builder.toString();
            xmlResponse = xmlResponse.substring(xmlResponse.indexOf('\n') + 1);
            JAXBContext jaxbContext = JAXBContext.newInstance(com.learnaem.core.xmlreader.core.models.Items.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            items = (com.learnaem.core.xmlreader.core.models.Items) unmarshaller.unmarshal(new StringReader(xmlResponse));
        } catch (IOException e) {
            log.error("Exception while getting data from URL, {}", e.getMessage());
            sendEmail(emailTemplate, emailId, count);
        } catch (JAXBException e) {
            log.error("Exception while unmarshling data, {}", e.getMessage());
            sendEmail(emailTemplate, emailId, count);
        }
        return items;
    }

    private void sendEmail(String emailTemplate, String emailId, int count) {
        if (count == COUNT_VAL && StringUtils.isNotEmpty(emailTemplate) && StringUtils.isNotEmpty(emailId)) {
            log.info("Inside send email.");
            Map<String, String> emailParams = new HashMap<>();
            String[] emailList = emailId.split(";");
          /* // emailParams.put(EmailServiceConstants.SENDER_EMAIL_ADDRESS, Constants.SENDER_EMAIL_ADDRESS);
            emailParams.put("info", Constants.JOB_IMPORT_FAILED_INFO);
            emailParams.put(EmailServiceConstants.SUBJECT, Constants.JOB_IMPORT_FAILED_SUBJECT);
            emailService.sendEmail(emailTemplate, emailParams, emailList);*/
        }
    }
}
