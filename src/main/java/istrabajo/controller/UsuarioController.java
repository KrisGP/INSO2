/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.controller;

import istrabajo.SesionUsuario;
import istrabajo.ejb.UsuarioFacadeLocal;
import istrabajo.model.Tarjeta;
import istrabajo.model.Usuario;
import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 */
@Named
@ViewScoped
public class UsuarioController implements Serializable {
        
    @EJB
    private UsuarioFacadeLocal usuarioEjb;
    
    private Usuario usuario;
    private Tarjeta tarjeta;
    
    @PostConstruct
    public void init() {
        usuario = new Usuario();
        tarjeta = new Tarjeta();
    }

    public void registrarTarjeta() {
        //Validar que la tarjeta sea correcta
        //reformatear dia y año de expiracion
        //comprobar que no exista ya en la base de datos
        usuario = usuarioEjb.find(SesionUsuario.getInstance().getIdUsuario());
        tarjeta.setUsuario(usuario);
        usuario.addTarjeta(tarjeta);
        
        usuarioEjb.edit(usuario);
        
    }
    
    public UsuarioFacadeLocal getUsuarioEjb() {
        return usuarioEjb;
    }

    public void setUsuarioEjb(UsuarioFacadeLocal usuarioEjb) {
        this.usuarioEjb = usuarioEjb;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }
    
    
    
}
