
package xml.webapp.main;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RouteCreationSpecType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RouteCreationSpecType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{main.webapp.xml}RouteInfoGroup"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Name" use="required" type="{main.webapp.xml}nonEmptyString" />
 *       &lt;attribute name="Distance" use="required" type="{main.webapp.xml}distance" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RouteCreationSpecType", propOrder = {
    "coordinates",
    "from",
    "to"
})
public class RouteCreationSpecType {

    @XmlElement(name = "Coordinates", required = true)
    protected CoordinatesType coordinates;
    @XmlElement(name = "From", required = true)
    protected LocationType from;
    @XmlElement(name = "To", required = true)
    protected LocationType to;
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

    public RouteCreationSpecType withCoordinates(CoordinatesType value) {
        setCoordinates(value);
        return this;
    }

    public RouteCreationSpecType withFrom(LocationType value) {
        setFrom(value);
        return this;
    }

    public RouteCreationSpecType withTo(LocationType value) {
        setTo(value);
        return this;
    }

    public RouteCreationSpecType withName(String value) {
        setName(value);
        return this;
    }

    public RouteCreationSpecType withDistance(long value) {
        setDistance(value);
        return this;
    }

}
