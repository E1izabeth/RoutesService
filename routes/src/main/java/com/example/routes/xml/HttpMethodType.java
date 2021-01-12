//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.12 at 06:22:02 AM MSK 
//


package com.example.routes.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HttpMethodType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HttpMethodType"&gt;
 *   &lt;restriction base="{main.webapp.xml}string"&gt;
 *     &lt;enumeration value="Post"/&gt;
 *     &lt;enumeration value="Put"/&gt;
 *     &lt;enumeration value="Get"/&gt;
 *     &lt;enumeration value="Delete"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "HttpMethodType")
@XmlEnum
public enum HttpMethodType {

    @XmlEnumValue("Post")
    POST("Post"),
    @XmlEnumValue("Put")
    PUT("Put"),
    @XmlEnumValue("Get")
    GET("Get"),
    @XmlEnumValue("Delete")
    DELETE("Delete");
    private final String value;

    HttpMethodType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HttpMethodType fromValue(String v) {
        for (HttpMethodType c: HttpMethodType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
