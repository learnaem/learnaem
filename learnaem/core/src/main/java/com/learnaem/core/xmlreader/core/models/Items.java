package com.learnaem.core.xmlreader.core.models;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Prathap Mullaguri
 */
@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class Items implements Serializable {

    // Generated serialVersionUID
    private static final long serialVersionUID = -7423990858185520203L;

    // Instance of one product
    @XmlElement(name = "item")
    private List<Item> item;

    /**
     * constructor.
     */
    public Items() {
        super();
    }

    /**
     * @param item - List<Item>
     */
    public Items(List<Item> item) {
        super();
        this.item = item;
    }

    /**
     * @return item list
     */
    public List<Item> getItem() {
        return item;
    }

    /**
     * @param item - List<Item>
     */
    public void setItem(List<Item> item) {
        this.item = item;
    }

    /**
     * Overridden toString() method.
     */
    @Override
    public String toString() {
        return "items [item =" + item + "]";
    }
}
