
package xml.webapp.main;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LocationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LocationType">
 *   &lt;complexContent>
 *     &lt;extension base="{main.webapp.xml}ObjectType">
 *       &lt;attribute name="Id" use="required" type="{main.webapp.xml}id" />
 *       &lt;attribute name="X" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="Y" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="Z" use="required" type="{http://www.w3.org/2001/XMLSchema}float" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocationType")
@XmlSeeAlso({
    LocationUpdateSpecType.class
})
public class LocationType
    extends ObjectType
{

    @XmlAttribute(name = "Id", required = true)
    protected long id;
    @XmlAttribute(name = "X", required = true)
    protected int x;
    @XmlAttribute(name = "Y", required = true)
    protected int y;
    @XmlAttribute(name = "Z", required = true)
    protected float z;

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the x property.
     * 
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the value of the x property.
     * 
     */
    public void setX(int value) {
        this.x = value;
    }

    /**
     * Gets the value of the y property.
     * 
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the value of the y property.
     * 
     */
    public void setY(int value) {
        this.y = value;
    }

    /**
     * Gets the value of the z property.
     * 
     */
    public float getZ() {
        return z;
    }

    /**
     * Sets the value of the z property.
     * 
     */
    public void setZ(float value) {
        this.z = value;
    }

    public LocationType withId(long value) {
        setId(value);
        return this;
    }

    public LocationType withX(int value) {
        setX(value);
        return this;
    }

    public LocationType withY(int value) {
        setY(value);
        return this;
    }

    public LocationType withZ(float value) {
        setZ(value);
        return this;
    }

    @Override
    public LocationType withDiscoverability(DiscoverabilityType value) {
        setDiscoverability(value);
        return this;
    }

}
