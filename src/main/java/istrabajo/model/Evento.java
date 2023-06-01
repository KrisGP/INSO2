/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase entidad representa la tabla "eventos" en la base de datos
 * @author Luis y Cristina
 */
@Entity
@Table(name="eventos")
public class Evento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEvento;
    @Column(name="nombreEvento", length=45, unique=true)
    private String nombreEvento;
    @Column(name="descripcion", length=200)
    private String descripcion;
    @Column(name="costePapeletasTotal")
    private BigDecimal costePapeletasTotal;
    @Column(name="costePapeletasSinIva", nullable=true)
    private BigDecimal costePapeletasSinIva;
    @Column(name="sePuedeRepetirNumero", nullable=true)
    private boolean sePuedeRepetirNumero;
    @Column(name="fechaInicio")
    private Date fechaInicio;
    @Column(name="fechaFinalizacion")
    private Date fechaFinalizacion;
    @Column(name="numeroPapeletas")
    private int numeroPapeletas;

    public int getNumeroPapeletas() {
        return numeroPapeletas;
    }

    public void setNumeroPapeletas(int numeroPapeletas) {
        this.numeroPapeletas = numeroPapeletas;
    }
    
    //EventoTienePapeletas
    @OneToMany(cascade=CascadeType.PERSIST, mappedBy="evento")
    private List<Papeleta> papeletas;
    
    //EventoTienePremios
    @OneToMany(cascade=CascadeType.PERSIST, mappedBy="evento")
    private List<Premio> premios;
    
    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCostePapeletasTotal() {
        return costePapeletasTotal;
    }

    public void setCostePapeletasTotal(BigDecimal costePapeletasTotal) {
        this.costePapeletasTotal = costePapeletasTotal;
    }

    public BigDecimal getCostePapeletasSinIva() {
        return costePapeletasSinIva;
    }

    public void setCostePapeletasSinIva(BigDecimal costePapeletasSinIva) {
        this.costePapeletasSinIva = costePapeletasSinIva;
    }

    public boolean isSePuedeRepetirNumero() {
        return sePuedeRepetirNumero;
    }

    public void setSePuedeRepetirNumero(boolean sePuedeRepetirNumero) {
        this.sePuedeRepetirNumero = sePuedeRepetirNumero;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }
    
    
}
