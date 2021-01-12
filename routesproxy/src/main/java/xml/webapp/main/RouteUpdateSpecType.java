
package xml.webapp.main;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RouteUpdateSpecType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RouteUpdateSpecType">
 *   &lt;complexContent>
 *     &lt;extension base="{main.webapp.xml}RouteType">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RouteUpdateSpecType")
public class RouteUpdateSpecType
    extends RouteType
{


    @Override
    public RouteUpdateSpecType withCoordinates(CoordinatesType value) {
        setCoordinates(value);
        return this;
    }

    @Override
    public RouteUpdateSpecType withFrom(LocationType value) {
        setFrom(value);
        return this;
    }

    @Override
    public RouteUpdateSpecType withTo(LocationType value) {
        setTo(value);
        return this;
    }

    @Override
    public RouteUpdateSpecType withCreationDate(LocalDate value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public RouteUpdateSpecType withId(long value) {
        setId(value);
        return this;
    }

    @Override
    public RouteUpdateSpecType withName(String value) {
        setName(value);
        return this;
    }

    @Override
    public RouteUpdateSpecType withDistance(long value) {
        setDistance(value);
        return this;
    }

    @Override
    public RouteUpdateSpecType withDiscoverability(DiscoverabilityType value) {
        setDiscoverability(value);
        return this;
    }

}
