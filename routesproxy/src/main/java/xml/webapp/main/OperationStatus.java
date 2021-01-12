
package xml.webapp.main;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OperationStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OperationStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Accomplished"/>
 *     &lt;enumeration value="Denied"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OperationStatus")
@XmlEnum
public enum OperationStatus {

    @XmlEnumValue("Accomplished")
    ACCOMPLISHED("Accomplished"),
    @XmlEnumValue("Denied")
    DENIED("Denied");
    private final String value;

    OperationStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OperationStatus fromValue(String v) {
        for (OperationStatus c: OperationStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
