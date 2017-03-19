package co.com.ias.model;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

/**
 * Created by Marlon Olaya on 19/03/2017.
 */
public class AveView {
        private String pais;
        private String nombreComun;
        private String nombreCientifico;
        private String Zona;


        public String getPais() {
            return pais;
        }

        public void setPais(String pais) {
            this.pais = pais;
        }

        public String getNombreComun() {
            return nombreComun;
        }

        public void setNombreComun(String nombreComun) {
            this.nombreComun = nombreComun;
        }

        public String getNombreCientifico() {
            return nombreCientifico;
        }

        public void setNombreCientifico(String nombreCientifico) {
            this.nombreCientifico = nombreCientifico;
        }

        public String getZona() {
            return Zona;
        }

        public void setZona(String zona) {
            Zona = zona;
        }
}
