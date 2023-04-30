/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Clase entidad representa la tabla "usuariotienetarjeta" en la base de datos
 * @author Luis y Cristina
 */
@Entity
@Table(name="usuariotienetarjeta")
public class UsuarioTieneTarjeta implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuarioTieneTarjeta;
    
    @JoinColumn(name="idUsuario")
    @OneToMany(targetEntity=istrabajo.model.Usuario.class)
    private Usuario idUsuario;
    
    @JoinColumn(name="idTarjeta")
    @ManyToOne
    private Tarjeta idTarjeta;

    public int getIdUsuarioTieneTarjeta() {
        return idUsuarioTieneTarjeta;
    }

    public void setIdUsuarioTieneTarjeta(int idUsuarioTieneTarjeta) {
        this.idUsuarioTieneTarjeta = idUsuarioTieneTarjeta;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Tarjeta getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(Tarjeta idTarjeta) {
        this.idTarjeta = idTarjeta;
    }
}