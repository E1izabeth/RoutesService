
package xml.webapp.main;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DistanceQueryResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DistanceQueryResultType">
 *   &lt;complexContent>
 *     &lt;extension base="{main.webapp.xml}ObjectType">
 *       &lt;attribute name="Value" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DistanceQueryResultType")
public class DistanceQueryResultType
    extends ObjectType
{

    @XmlAttribute(name = "Value", required = true)
    protected long value;

    /**
     * Gets the value of the value property.
     * 
     */
    public long getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     */
    public void setValue(long value) {
        this.value = value;
    }

    public DistanceQueryResultType withValue(long value) {
        setValue(value);
        return this;
    }

    @Override
    public DistanceQueryResultType withDiscoverability(DiscoverabilityType value) {
        setDiscoverability(value);
        return this;
    }

}
