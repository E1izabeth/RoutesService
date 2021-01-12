
package xml.webapp.main;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LinkType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LinkType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="Name" use="required" type="{main.webapp.xml}string" />
 *       &lt;attribute name="Id" type="{main.webapp.xml}id" />
 *       &lt;attribute name="Kind" use="required" type="{main.webapp.xml}string" />
 *       &lt;attribute name="Href" use="required" type="{main.webapp.xml}string" />
 *       &lt;attribute name="Relation" use="required" type="{main.webapp.xml}LinkRelationType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LinkType")
public class LinkType {

    @XmlAttribute(name = "Name", required = true)
    protected String name;
    @XmlAttribute(name = "Id")
    protected Long id;
    @XmlAttribute(name = "Kind", required = true)
    protected String kind;
    @XmlAttribute(name = "Href", required = true)
    protected String href;
    @XmlAttribute(name = "Relation", required = true)
    protected LinkRelationType relation;

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
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the kind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKind() {
        return kind;
    }

    /**
     * Sets the value of the kind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKind(String value) {
        this.kind = value;
    }

    /**
     * Gets the value of the href property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHref() {
        return href;
    }

    /**
     * Sets the value of the href property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHref(String value) {
        this.href = value;
    }

    /**
     * Gets the value of the relation property.
     * 
     * @return
     *     possible object is
     *     {@link LinkRelationType }
     *     
     */
    public LinkRelationType getRelation() {
        return relation;
    }

    /**
     * Sets the value of the relation property.
     * 
     * @param value
     *     allowed object is
     *     {@link LinkRelationType }
     *     
     */
    public void setRelation(LinkRelationType value) {
        this.relation = value;
    }

    public LinkType withName(String value) {
        setName(value);
        return this;
    }

    public LinkType withId(Long value) {
        setId(value);
        return this;
    }

    public LinkType withKind(String value) {
        setKind(value);
        return this;
    }

    public LinkType withHref(String value) {
        setHref(value);
        return this;
    }

    public LinkType withRelation(LinkRelationType value) {
        setRelation(value);
        return this;
    }

}
