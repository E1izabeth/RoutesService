
package xml.webapp.main;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ObjectType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObjectType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Discoverability" type="{main.webapp.xml}DiscoverabilityType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectType", propOrder = {
    "discoverability"
})
@XmlSeeAlso({
    LocationsQueryResultType.class,
    DistanceQueryResultType.class,
    LocationServiceType.class,
    RouteServiceType.class,
    RouteType.class,
    RoutesQueryResultType.class,
    LocationType.class
})
public abstract class ObjectType {

    @XmlElement(name = "Discoverability")
    protected DiscoverabilityType discoverability;

    /**
     * Gets the value of the discoverability property.
     * 
     * @return
     *     possible object is
     *     {@link DiscoverabilityType }
     *     
     */
    public DiscoverabilityType getDiscoverability() {
        return discoverability;
    }

    /**
     * Sets the value of the discoverability property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiscoverabilityType }
     *     
     */
    public void setDiscoverability(DiscoverabilityType value) {
        this.discoverability = value;
    }

    public ObjectType withDiscoverability(DiscoverabilityType value) {
        setDiscoverability(value);
        return this;
    }

}
