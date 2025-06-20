
package ar.edu.ubp.das.supermercadows.services.jaxws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ObtenerInformacionPreciosProductosResponse", namespace = "http://services.supermercadows.das.ubp.edu.ar/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObtenerInformacionPreciosProductosResponse", namespace = "http://services.supermercadows.das.ubp.edu.ar/")
public class ObtenerInformacionPreciosProductosResponse {

    @XmlElement(name = "InformacionPreciosProductosResponse", namespace = "")
    private String informacionPreciosProductosResponse;

    /**
     * 
     * @return
     *     returns String
     */
    public String getInformacionPreciosProductosResponse() {
        return this.informacionPreciosProductosResponse;
    }

    /**
     * 
     * @param informacionPreciosProductosResponse
     *     the value for the informacionPreciosProductosResponse property
     */
    public void setInformacionPreciosProductosResponse(String informacionPreciosProductosResponse) {
        this.informacionPreciosProductosResponse = informacionPreciosProductosResponse;
    }

}
