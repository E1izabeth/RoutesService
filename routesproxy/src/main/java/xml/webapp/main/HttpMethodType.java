
package xml.webapp.main;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HttpMethodType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HttpMethodType">
 *   &lt;restriction base="{main.webapp.xml}string">
 *     &lt;enumeration value="Post"/>
 *     &lt;enumeration value="Put"/>
 *     &lt;enumeration value="Get"/>
 *     &lt;enumeration value="Delete"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
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
