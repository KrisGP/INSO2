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
 * Clase entidad representa la tabla "usuariocomprapapeletas" en la base de datos
 * @author Luis y Cristina
 */
@Entity
@Table(name="usuariocomprapapeletas")
public class UsuarioCompraPapeletas implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCompra;
    
    @JoinColumn(name="idUsuario")
    @ManyToOne
    private Usuario idUsuario;
    
    @JoinColumn(name="idPapeleta")
    @ManyToOne
    private Papeleta idPapeleta;
    
    @Column(name="fecha", nullable=true)
    private Calendar fecha;

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Papeleta getIdPapeleta() {
        return idPapeleta;
    }

    public void setIdPapeleta(Papeleta idPapeleta) {
        this.idPapeleta = idPapeleta;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }
}