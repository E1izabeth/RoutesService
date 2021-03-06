
package xml.webapp.main;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ActionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="Name" use="required" type="{main.webapp.xml}string" />
 *       &lt;attribute name="Method" use="required" type="{main.webapp.xml}HttpMethodType" />
 *       &lt;attribute name="Href" use="required" type="{main.webapp.xml}string" />
 *       &lt;attribute name="ParamKind" type="{main.webapp.xml}string" />
 *       &lt;attribute name="ResultKind" type="{main.webapp.xml}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActionType")
public class ActionType {

    @XmlAttribute(name = "Name", required = true)
    protected String name;
    @XmlAttribute(name = "Method", required = true)
    protected HttpMethodType method;
    @XmlAttribute(name = "Href", required = true)
    protected String href;
    @XmlAttribute(name = "ParamKind")
    protected String paramKind;
    @XmlAttribute(name = "ResultKind")
    protected String resultKind;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the method property.
     * 
     * @return
     *     possible object is
     *     {@link HttpMethodType }
     *     
     */
    public HttpMethodType getMethod() {
        return method;
    }

    /**
     * Sets the value of the method property.
     * 
     * @param value
     *     allowed object is
     *     {@link HttpMethodType }
     *     
     */
    public void setMethod(HttpMethodType value) {
        this.method = value;
    }

    /**
     * Gets the value of the href property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHref() {
        return href;
    }

    /**
     * Sets the value of the href property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHref(String value) {
        this.href = value;
    }

    /**
     * Gets the value of the paramKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParamKind() {
        return paramKind;
    }

    /**
     * Sets the value of the paramKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamKind(String value) {
        this.paramKind = value;
    }

    /**
     * Gets the value of the resultKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultKind() {
        return resultKind;
    }

    /**
     * Sets the value of the resultKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultKind(String value) {
        this.resultKind = value;
    }

    public ActionType withName(String value) {
        setName(value);
        return this;
    }

    public ActionType withMethod(HttpMethodType value) {
        setMethod(value);
        return this;
    }

    public ActionType withHref(String value) {
        setHref(value);
        return this;
    }

    public ActionType withParamKind(String value) {
        setParamKind(value);
        return this;
    }

    public ActionType withResultKind(String value) {
        setResultKind(value);
        return this;
    }

}
