
package xml.webapp.main;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DiscoverabilityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DiscoverabilityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Links" type="{main.webapp.xml}LinksListType" minOccurs="0"/>
 *         &lt;element name="Actions" type="{main.webapp.xml}ActionsListType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiscoverabilityType", propOrder = {
    "links",
    "actions"
})
public class DiscoverabilityType {

    @XmlElement(name = "Links")
    protected LinksListType links;
    @XmlElement(name = "Actions")
    protected ActionsListType actions;

    /**
     * Gets the value of the links property.
     * 
     * @return
     *     possible object is
     *     {@link LinksListType }
     *     
     */
    public LinksListType getLinks() {
        return links;
    }

    /**
     * Sets the value of the links property.
     * 
     * @param value
     *     allowed object is
     *     {@link LinksListType }
     *     
     */
    public void setLinks(LinksListType value) {
        this.links = value;
    }

    /**
     * Gets the value of the actions property.
     * 
     * @return
     *     possible object is
     *     {@link ActionsListType }
     *     
     */
    public ActionsListType getActions() {
        return actions;
    }

    /**
     * Sets the value of the actions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionsListType }
     *     
     */
    public void setActions(ActionsListType value) {
        this.actions = value;
    }

    public DiscoverabilityType withLinks(LinksListType value) {
        setLinks(value);
        return this;
    }

    public DiscoverabilityType withActions(ActionsListType value) {
        setActions(value);
        return this;
    }

}
