/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Clase entidad representa la tabla "tarjetas" en la base de datos
 * @author Luis y Cristina
 */
@Entity
@Table(name="tarjetas")
public class Tarjeta implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTarjetas;
    @Column(name="tarjetascreditocol", length=16, unique=true)
    private String tarjetasCreditoCol;
    @Column(name="nombreCompletoTarjeta", length=100)
    private String nombreCompletoTarjeta;    
    @Column(name="mesCaduca", length=2)
    private String mesCaduca;
    @Column(name="anyoCaduca", length=2)
    private String anyoCaduca;   
    @Column(name="cvc", length=3)
    private String cvc;
   
    @ManyToOne
    @JoinColumn(name="idUsuario")
    private Usuario idUsuario;

    public Usuario getUsuario() {
        return idUsuario;
    }

    public void setUsuario(Usuario usuario) {
        this.idUsuario = usuario;
    }
    
    public int getIdTarjetas() {
        return idTarjetas;
    }

    public void setIdTarjetas(int idTarjetas) {
        this.idTarjetas = idTarjetas;
    }

    public String getTarjetasCreditoCol() {
        return tarjetasCreditoCol;
    }

    public void setTarjetasCreditoCol(String tarjetasCreditoCol) {
        this.tarjetasCreditoCol = tarjetasCreditoCol;
    }

    public String getNombreCompletoTarjeta() {
        return nombreCompletoTarjeta;
    }

    public void setNombreCompletoTarjeta(String nombreCompletoTarjeta) {
        this.nombreCompletoTarjeta = nombreCompletoTarjeta;
    }

    public String getMesCaduca() {
        return mesCaduca;
    }

    public void setMesCaduca(String mesCaduca) {
        this.mesCaduca = mesCaduca;
    }

    public String getAnyoCaduca() {
        return anyoCaduca;
    }

    public void setAnyoCaduca(String anyoCaduca) {
        this.anyoCaduca = anyoCaduca;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }
    
}
