/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase entidad representa la tabla "recargassaldo" en la base de datos
 * @author Luis y Cristina
 */
@Entity
@Table(name="recargassaldo")
public class RecargasSaldo implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRecarga;
   
    @JoinColumn(name="idTarjeta")
    @ManyToOne
    private Tarjeta idTarjeta;
    
    @JoinColumn(name="idUsuario")
    @ManyToOne
    private Usuario idUsuario;
    
    @Column(name="ValorRecarga")
    private String valorRecarga;
    @Column(name="tipoOperacion")
    private String tipoOperacion;
    @Column(name="fechaRecarga", nullable=true)
    private Calendar fechaRecarga;

    public int getIdRecarga() {
        return idRecarga;
    }

    public void setIdRecarga(int idRecarga) {
        this.idRecarga = idRecarga;
    }

    public Tarjeta getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(Tarjeta idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getValorRecargo() {
        return valorRecarga;
    }

    public void setValorRecargo(String valorRecargo) {
        this.valorRecarga = valorRecargo;
    }

    public Calendar getFechaRecarga() {
        return fechaRecarga;
    }

    public void setFechaRecarga(Calendar fechaRecarga) {
        this.fechaRecarga = fechaRecarga;
    }

    public String getValorRecarga() {
        return valorRecarga;
    }

    public void setValorRecarga(String valorRecarga) {
        this.valorRecarga = valorRecarga;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }
}

