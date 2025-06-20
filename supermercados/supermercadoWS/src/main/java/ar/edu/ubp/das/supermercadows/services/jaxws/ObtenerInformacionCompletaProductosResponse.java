
package ar.edu.ubp.das.supermercadows.services.jaxws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ObtenerInformacionCompletaProductosResponse", namespace = "http://services.supermercadows.das.ubp.edu.ar/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObtenerInformacionCompletaProductosResponse", namespace = "http://services.supermercadows.das.ubp.edu.ar/")
public class ObtenerInformacionCompletaProductosResponse {

    @XmlElement(name = "InformacionCompletaProductosResponse", namespace = "")
    private String informacionCompletaProductosResponse;

    /**
     * 
     * @return
     *     returns String
     */
    public String getInformacionCompletaProductosResponse() {
        return this.informacionCompletaProductosResponse;
    }

    /**
     * 
     * @param informacionCompletaProductosResponse
     *     the value for the informacionCompletaProductosResponse property
     */
    public void setInformacionCompletaProductosResponse(String informacionCompletaProductosResponse) {
        this.informacionCompletaProductosResponse = informacionCompletaProductosResponse;
    }

}
