package com.learnaem.core.xmlreader.core.services;

import com.learnaem.core.xmlreader.core.models.Items;

/**
 * @author Prathap Mullaguri
 */
public interface XMLWriterService {

    /**
     * This method writes the XML data into JCR as nodes and its properties.
     * 
     * @param items - Items
     */
    void writeXMLToJCR(Items items);
}
