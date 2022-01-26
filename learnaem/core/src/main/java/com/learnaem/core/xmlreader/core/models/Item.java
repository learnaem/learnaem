package com.learnaem.core.xmlreader.core.models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Prathap Mullaguri
 */
@XmlRootElement(name = "Item")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item implements Serializable {

    /**
     * Generated serialVersionUID.
     */
    private static final long serialVersionUID = -2525852165658067914L;

    /**
     * job title.
     */
    @XmlElement(name = "JobTitle")
    private String jobTitle;
    /**
     * location.
     */
    @XmlElement(name = "Location")
    private String location;
    /**
     * job reference.
     */
    @XmlElement(name = "JobRef")
    private String jobRef;
    /**
     * job post date.
     */
    @XmlElement(name = "PostDate")
    private String postDate;
    /**
     * job closing date.
     */
    @XmlElement(name = "ClosingDate")
    private String closingDate;
    /**
     * job category.
     */
    @XmlElement(name = "JobCategory")
    private String jobCategory;
    /**
     * job apply url.
     */
    @XmlElement(name = "ApplyURL")
    private String applyURL;

    /**
     * @return jobTitle
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * @param jobTitle
     *            - String
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location
     *            - String
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return jobRef
     */
    public String getJobRef() {
        return jobRef;
    }

    /**
     * @param jobRef
     *            - String
     */
    public void setJobRef(String jobRef) {
        this.jobRef = jobRef;
    }

    /**
     * @return postDate
     */
    public String getPostDate() {
        return postDate;
    }

    /**
     * @param postDate
     *            - String
     */
    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    /**
     * @return closingDate
     */
    public String getClosingDate() {
        return closingDate;
    }

    /**
     * @param closingDate
     *            - double
     */
    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    /**
     * @return jobCategory
     */
    public String getJobCategory() {
        return jobCategory;
    }

    /**
     * @param jobCategory
     *            - double
     */
    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    /**
     * @return applyURL
     */
    public String getApplyURL() {
        return applyURL;
    }

    /**
     * @param applyURL
     *            - String
     */
    public void setApplyURL(String applyURL) {
        this.applyURL = applyURL;
    }

    /**
     * Overridden toString() method.
     */
    @Override
    public String toString() {
        return new StringBuilder().append("Item [JobTitle=").append(jobTitle).append(", Location=").append(location)
                .append(", JobRef=").append(jobRef).append(", PostDate=").append(postDate).append(", ClosingDate=")
                .append(closingDate).append(", JobCategory=").append(jobCategory).append(", ApplyURL=").append(applyURL)
                .append("]").toString();
    }

}
