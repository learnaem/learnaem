package com.learnaem.core.xmlreader.core.services;

/**
 * @author Prathap Mullaguri
 */
public interface XMLReaderService {

    /**
     * This method writes XML data into JCR.
     * 
     * @param responseURL - String
     * @param secureProxyPort - String
     * @param secureProxyIp - int
     * @param emailId - String
     * @param emailTemplate - String
     * @param count - int
     * 
     * @return items
     */
    com.learnaem.core.xmlreader.core.models.Items readXMLFromURL(String responseURL, String secureProxyIp, int secureProxyPort, String emailTemplate, String emailId, int count);
}
