
package ar.edu.ubp.das.supermercadows2.services.jaxws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ObtenerSucursalesCompletasResponse", namespace = "http://services.supermercadows2.das.ubp.edu.ar/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObtenerSucursalesCompletasResponse", namespace = "http://services.supermercadows2.das.ubp.edu.ar/")
public class ObtenerSucursalesCompletasResponse {

    @XmlElement(name = "SucursalesCompletasResponse", namespace = "")
    private String sucursalesCompletasResponse;

    /**
     * 
     * @return
     *     returns String
     */
    public String getSucursalesCompletasResponse() {
        return this.sucursalesCompletasResponse;
    }

    /**
     * 
     * @param sucursalesCompletasResponse
     *     the value for the sucursalesCompletasResponse property
     */
    public void setSucursalesCompletasResponse(String sucursalesCompletasResponse) {
        this.sucursalesCompletasResponse = sucursalesCompletasResponse;
    }

}
