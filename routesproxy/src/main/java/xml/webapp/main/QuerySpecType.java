
package xml.webapp.main;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QuerySpecType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QuerySpecType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Filter" type="{main.webapp.xml}string" minOccurs="0"/>
 *         &lt;element name="SortOn" type="{main.webapp.xml}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="PageSize" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="PageNumber" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuerySpecType", propOrder = {
    "filter",
    "sortOn"
})
public class QuerySpecType {

    @XmlElement(name = "Filter")
    protected String filter;
    @XmlElement(name = "SortOn")
    protected String sortOn;
    @XmlAttribute(name = "PageSize")
    protected Integer pageSize;
    @XmlAttribute(name = "PageNumber")
    protected Integer pageNumber;

    /**
     * Gets the value of the filter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilter() {
        return filter;
    }

    /**
     * Sets the value of the filter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilter(String value) {
        this.filter = value;
    }

    /**
     * Gets the value of the sortOn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSortOn() {
        return sortOn;
    }

    /**
     * Sets the value of the sortOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSortOn(String value) {
        this.sortOn = value;
    }

    /**
     * Gets the value of the pageSize property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * Sets the value of the pageSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPageSize(Integer value) {
        this.pageSize = value;
    }

    /**
     * Gets the value of the pageNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets the value of the pageNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPageNumber(Integer value) {
        this.pageNumber = value;
    }

    public QuerySpecType withFilter(String value) {
        setFilter(value);
        return this;
    }

    public QuerySpecType withSortOn(String value) {
        setSortOn(value);
        return this;
    }

    public QuerySpecType withPageSize(Integer value) {
        setPageSize(value);
        return this;
    }

    public QuerySpecType withPageNumber(Integer value) {
        setPageNumber(value);
        return this;
    }

}
