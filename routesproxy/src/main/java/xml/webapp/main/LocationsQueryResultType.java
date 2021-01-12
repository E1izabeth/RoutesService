
package xml.webapp.main;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LocationsQueryResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LocationsQueryResultType">
 *   &lt;complexContent>
 *     &lt;extension base="{main.webapp.xml}ObjectType">
 *       &lt;sequence>
 *         &lt;element name="Locations" type="{main.webapp.xml}LocationsListType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="PageSize" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="PageNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="PagesCount" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="TotalCount" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocationsQueryResultType", propOrder = {
    "locations"
})
public class LocationsQueryResultType
    extends ObjectType
{

    @XmlElement(name = "Locations", required = true)
    protected LocationsListType locations;
    @XmlAttribute(name = "PageSize", required = true)
    protected long pageSize;
    @XmlAttribute(name = "PageNumber", required = true)
    protected long pageNumber;
    @XmlAttribute(name = "PagesCount", required = true)
    protected long pagesCount;
    @XmlAttribute(name = "TotalCount", required = true)
    protected long totalCount;

    /**
     * Gets the value of the locations property.
     * 
     * @return
     *     possible object is
     *     {@link LocationsListType }
     *     
     */
    public LocationsListType getLocations() {
        return locations;
    }

    /**
     * Sets the value of the locations property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationsListType }
     *     
     */
    public void setLocations(LocationsListType value) {
        this.locations = value;
    }

    /**
     * Gets the value of the pageSize property.
     * 
     */
    public long getPageSize() {
        return pageSize;
    }

    /**
     * Sets the value of the pageSize property.
     * 
     */
    public void setPageSize(long value) {
        this.pageSize = value;
    }

    /**
     * Gets the value of the pageNumber property.
     * 
     */
    public long getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets the value of the pageNumber property.
     * 
     */
    public void setPageNumber(long value) {
        this.pageNumber = value;
    }

    /**
     * Gets the value of the pagesCount property.
     * 
     */
    public long getPagesCount() {
        return pagesCount;
    }

    /**
     * Sets the value of the pagesCount property.
     * 
     */
    public void setPagesCount(long value) {
        this.pagesCount = value;
    }

    /**
     * Gets the value of the totalCount property.
     * 
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * Sets the value of the totalCount property.
     * 
     */
    public void setTotalCount(long value) {
        this.totalCount = value;
    }

    public LocationsQueryResultType withLocations(LocationsListType value) {
        setLocations(value);
        return this;
    }

    public LocationsQueryResultType withPageSize(long value) {
        setPageSize(value);
        return this;
    }

    public LocationsQueryResultType withPageNumber(long value) {
        setPageNumber(value);
        return this;
    }

    public LocationsQueryResultType withPagesCount(long value) {
        setPagesCount(value);
        return this;
    }

    public LocationsQueryResultType withTotalCount(long value) {
        setTotalCount(value);
        return this;
    }

    @Override
    public LocationsQueryResultType withDiscoverability(DiscoverabilityType value) {
        setDiscoverability(value);
        return this;
    }

}
