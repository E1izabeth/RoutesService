
package xml.webapp.main;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RoutesQueryResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RoutesQueryResultType">
 *   &lt;complexContent>
 *     &lt;extension base="{main.webapp.xml}ObjectType">
 *       &lt;sequence>
 *         &lt;element name="Routes" type="{main.webapp.xml}RoutesListType"/>
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
@XmlType(name = "RoutesQueryResultType", propOrder = {
    "routes"
})
public class RoutesQueryResultType
    extends ObjectType
{

    @XmlElement(name = "Routes", required = true)
    protected RoutesListType routes;
    @XmlAttribute(name = "PageSize", required = true)
    protected long pageSize;
    @XmlAttribute(name = "PageNumber", required = true)
    protected long pageNumber;
    @XmlAttribute(name = "PagesCount", required = true)
    protected long pagesCount;
    @XmlAttribute(name = "TotalCount", required = true)
    protected long totalCount;

    /**
     * Gets the value of the routes property.
     * 
     * @return
     *     possible object is
     *     {@link RoutesListType }
     *     
     */
    public RoutesListType getRoutes() {
        return routes;
    }

    /**
     * Sets the value of the routes property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoutesListType }
     *     
     */
    public void setRoutes(RoutesListType value) {
        this.routes = value;
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

    public RoutesQueryResultType withRoutes(RoutesListType value) {
        setRoutes(value);
        return this;
    }

    public RoutesQueryResultType withPageSize(long value) {
        setPageSize(value);
        return this;
    }

    public RoutesQueryResultType withPageNumber(long value) {
        setPageNumber(value);
        return this;
    }

    public RoutesQueryResultType withPagesCount(long value) {
        setPagesCount(value);
        return this;
    }

    public RoutesQueryResultType withTotalCount(long value) {
        setTotalCount(value);
        return this;
    }

    @Override
    public RoutesQueryResultType withDiscoverability(DiscoverabilityType value) {
        setDiscoverability(value);
        return this;
    }

}
