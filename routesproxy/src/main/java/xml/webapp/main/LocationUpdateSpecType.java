
package xml.webapp.main;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LocationUpdateSpecType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LocationUpdateSpecType">
 *   &lt;complexContent>
 *     &lt;extension base="{main.webapp.xml}LocationType">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocationUpdateSpecType")
public class LocationUpdateSpecType
    extends LocationType
{


    @Override
    public LocationUpdateSpecType withId(long value) {
        setId(value);
        return this;
    }

    @Override
    public LocationUpdateSpecType withX(int value) {
        setX(value);
        return this;
    }

    @Override
    public LocationUpdateSpecType withY(int value) {
        setY(value);
        return this;
    }

    @Override
    public LocationUpdateSpecType withZ(float value) {
        setZ(value);
        return this;
    }

    @Override
    public LocationUpdateSpecType withDiscoverability(DiscoverabilityType value) {
        setDiscoverability(value);
        return this;
    }

}
