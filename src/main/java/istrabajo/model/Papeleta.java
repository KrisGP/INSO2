/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase entidad representa la tabla "papeletas" en la base de datos
 * @author Luis y Cristina
 */
@Entity
@Table(name="papeletas")
public class Papeleta implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPapeleta;
    @Column(name="combinacionPapeleta", length=10)
    private String combinacionPapeleta;
    @Column(name="fechaCompra")
    private Calendar fechaCompra;
    @Column(name="posicionPremio", length= 45, nullable=true)
    private String posicionPremio;

    public int getIdPapeleta() {
        return idPapeleta;
    }

    public void setIdPapeleta(int idPapeleta) {
        this.idPapeleta = idPapeleta;
    }

    public String getCombinacionPapeleta() {
        return combinacionPapeleta;
    }

    public void setCombinacionPapeleta(String combinacionPapeleta) {
        this.combinacionPapeleta = combinacionPapeleta;
    }

    public Calendar getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Calendar fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getPosicionPremio() {
        return posicionPremio;
    }

    public void setPosicionPremio(String posicionPremio) {
        this.posicionPremio = posicionPremio;
    }
    
    
}
