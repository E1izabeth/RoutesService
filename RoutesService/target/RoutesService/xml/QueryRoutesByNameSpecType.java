//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.17 at 01:01:36 PM MSK 
//


package main.webapp.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QueryRoutesByNameSpecType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QueryRoutesByNameSpecType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="ExactName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryRoutesByNameSpecType")
public class QueryRoutesByNameSpecType {

    @XmlAttribute(name = "ExactName", required = true)
    protected String exactName;

    /**
     * Gets the value of the exactName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExactName() {
        return exactName;
    }

    /**
     * Sets the value of the exactName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExactName(String value) {
        this.exactName = value;
    }

}
