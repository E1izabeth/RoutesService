//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.11.28 at 01:11:15 AM MSK 
//


package com.example.navigator.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeleteRouteByDistanceSpecType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeleteRouteByDistanceSpecType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="ExactDistance" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeleteRouteByDistanceSpecType")
public class DeleteRouteByDistanceSpecType {

    @XmlAttribute(name = "ExactDistance", required = true)
    protected long exactDistance;

    /**
     * Gets the value of the exactDistance property.
     * 
     */
    public long getExactDistance() {
        return exactDistance;
    }

    /**
     * Sets the value of the exactDistance property.
     * 
     */
    public void setExactDistance(long value) {
        this.exactDistance = value;
    }

}
