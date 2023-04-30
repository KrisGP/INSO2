/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase entidad representa la tabla "usuarios" en la base de datos
 * @author Luis y Cristina
 */
@Entity
@Table(name="usuarios")
public class Usuario implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;
    @Column(name="nombreUsuario", length=45, unique=true)
    private String nombreUsuario;
    @Column(name="contrasenaUsuario", length=45)
    private String contrasena;
    @Column(name="nombre", length=45)
    private String nombre;
    @Column(name="primerApellido", length=45)
    private String primerApellido;
    @Column(name="segundoApellido", length=45, nullable=true)
    private String segundoApellido;
    @Column(name="rol", length=45)
    private String rol;
    @Column(name="diaNacimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date diaNacimiento;
    @Column(name="dni" , length=10)
    private String dni;
    @Column(name="saldo")
    private BigDecimal saldo;
    
    //UsuarioTieneTarjeta    el mappedBy se refiere a que en la clase tarjeta, es el atributo "usuario"
    @OneToMany(cascade=CascadeType.PERSIST, mappedBy="usuario")
    private List<Tarjeta> tarjetas;

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Date getDiaNacimiento() {
        return diaNacimiento;
    }

    public void setDiaNacimiento(Date diaNacimiento) {
        this.diaNacimiento = diaNacimiento;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    
    
    
}