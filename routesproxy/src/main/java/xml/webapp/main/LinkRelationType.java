
package xml.webapp.main;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LinkRelationType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LinkRelationType">
 *   &lt;restriction base="{main.webapp.xml}string">
 *     &lt;enumeration value="Up"/>
 *     &lt;enumeration value="Down"/>
 *     &lt;enumeration value="Forward"/>
 *     &lt;enumeration value="Backward"/>
 *     &lt;enumeration value="Related"/>
 *     &lt;enumeration value="Alternate"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LinkRelationType")
@XmlEnum
public enum LinkRelationType {

    @XmlEnumValue("Up")
    UP("Up"),
    @XmlEnumValue("Down")
    DOWN("Down"),
    @XmlEnumValue("Forward")
    FORWARD("Forward"),
    @XmlEnumValue("Backward")
    BACKWARD("Backward"),
    @XmlEnumValue("Related")
    RELATED("Related"),
    @XmlEnumValue("Alternate")
    ALTERNATE("Alternate");
    private final String value;

    LinkRelationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LinkRelationType fromValue(String v) {
        for (LinkRelationType c: LinkRelationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
