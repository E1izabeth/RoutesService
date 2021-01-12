
package xml.webapp.main;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for localDate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="localDate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="Mills" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "localDate")
public class LocalDate {

    @XmlAttribute(name = "Mills", required = true)
    protected long mills;

    /**
     * Gets the value of the mills property.
     * 
     */
    public long getMills() {
        return mills;
    }

    /**
     * Sets the value of the mills property.
     * 
     */
    public void setMills(long value) {
        this.mills = value;
    }

    public LocalDate withMills(long value) {
        setMills(value);
        return this;
    }

}
