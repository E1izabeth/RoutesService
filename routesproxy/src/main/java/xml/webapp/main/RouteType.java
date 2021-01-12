
package xml.webapp.main;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RouteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RouteType">
 *   &lt;complexContent>
 *     &lt;extension base="{main.webapp.xml}ObjectType">
 *       &lt;sequence>
 *         &lt;group ref="{main.webapp.xml}RouteInfoGroup"/>
 *         &lt;element name="CreationDate" type="{main.webapp.xml}localDate"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Id" use="required" type="{main.webapp.xml}id" />
 *       &lt;attribute name="Name" use="required" type="{main.webapp.xml}nonEmptyString" />
 *       &lt;attribute name="Distance" use="required" type="{main.webapp.xml}distance" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RouteType", propOrder = {
    "coordinates",
    "from",
    "to",
    "creationDate"
})
@XmlSeeAlso({
    RouteUpdateSpecType.class
})
public class RouteType
    extends ObjectType
{

    @XmlElement(name = "Coordinates", required = true)
    protected CoordinatesType coordinates;
    @XmlElement(name = "From", required = true)
    protected LocationType from;
    @XmlElement(name = "To", required = true)
    protected LocationType to;
    @XmlElement(name = "CreationDate", required = true)
    protected LocalDate creationDate;
    @XmlAttribute(name = "Id", required = true)
    protected long id;
    @XmlAttribute(name = "Name", required = true)
    protected String name;
    @XmlAttribute(name = "Distance", required = true)
    protected long distance;

    /**
     * Gets the value of the coordinates property.
     * 
     * @return
     *     possible object is
     *     {@link CoordinatesType }
     *     
     */
    public CoordinatesType getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the value of the coordinates property.
     * 
     * @param value
     *     allowed object is
     *     {@link CoordinatesType }
     *     
     */
    public void setCoordinates(CoordinatesType value) {
        this.coordinates = value;
    }

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setFrom(LocationType value) {
        this.from = value;
    }

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setTo(LocationType value) {
        this.to = value;
    }

    /**
     * Gets the value of the creationDate property.
     * 
     * @return
     *     possible object is
     *     {@link LocalDate }
     *     
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the value of the creationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDate }
     *     
     */
    public void setCreationDate(LocalDate value) {
        this.creationDate = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the distance property.
     * 
     */
    public long getDistance() {
        return distance;
    }

    /**
     * Sets the value of the distance property.
     * 
     */
    public void setDistance(long value) {
        this.distance = value;
    }

    public RouteType withCoordinates(CoordinatesType value) {
        setCoordinates(value);
        return this;
    }

    public RouteType withFrom(LocationType value) {
        setFrom(value);
        return this;
    }

    public RouteType withTo(LocationType value) {
        setTo(value);
        return this;
    }

    public RouteType withCreationDate(LocalDate value) {
        setCreationDate(value);
        return this;
    }

    public RouteType withId(long value) {
        setId(value);
        return this;
    }

    public RouteType withName(String value) {
        setName(value);
        return this;
    }

    public RouteType withDistance(long value) {
        setDistance(value);
        return this;
    }

    @Override
    public RouteType withDiscoverability(DiscoverabilityType value) {
        setDiscoverability(value);
        return this;
    }

}
