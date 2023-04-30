/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.CascadeType;
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
 * Clase entidad representa la tabla "regalos" en la base de datos
 * @author Luis y Cristina
 */
@Entity
@Table(name="regalos")
public class Regalo implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRegalo;
    @Id
    
    @JoinColumn(name="idUsuario")
    @OneToMany(targetEntity=istrabajo.model.Usuario.class)
    private Usuario idUsuario;
    
    @JoinColumn(name="idArticulo")
    @ManyToOne
    private Articulo idArticulo;
    
    @Column(name="fecha")
    private Calendar fecha;
    
    @Column(name="haSidoEntregado", nullable=true)
    private boolean haSidoEntregado;

    public int getIdRegalo() {
        return idRegalo;
    }

    public void setIdRegalo(int idRegalo) {
        this.idRegalo = idRegalo;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Articulo getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Articulo idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public boolean isHaSidoEntregado() {
        return haSidoEntregado;
    }

    public void setHaSidoEntregado(boolean haSidoEntregado) {
        this.haSidoEntregado = haSidoEntregado;
    }
    
    
}
