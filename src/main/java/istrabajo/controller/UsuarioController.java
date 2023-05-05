/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.controller;

import istrabajo.ejb.UsuarioFacadeLocal;
import istrabajo.model.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author LuisPC
 */
@Named
@ViewScoped
public class UsuarioController implements Serializable{
    public static final String TIPOUSUARIO = "USUARIO";
    public static final String TIPOADMINISTRADOR = "ADMINISTRADO";
    public static final String TIPODEPENDIENTE = "DEPENDIENTE";
    
    @EJB
    private UsuarioFacadeLocal usuarioEjb;
    private Usuario usuario;
    

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
    
    public void iniciarSesion() throws IOException{
        if(usuarioEjb.validaCredenciales(usuario.getNombreUsuario(), usuario.getContrasena())){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inicio de sesión exitoso", null));
            
            if(usuarioEjb.tipoUser(usuario.getNombreUsuario()).equals(TIPOUSUARIO)){
                FacesContext facesContext = FacesContext.getCurrentInstance();
                NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
                navigationHandler.handleNavigation(facesContext, null, "inicio?faces-redirect=true");
            }
            
            else if(usuarioEjb.tipoUser(usuario.getNombreUsuario()).equals(TIPOADMINISTRADOR)){
                FacesContext facesContext = FacesContext.getCurrentInstance();
                NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
                navigationHandler.handleNavigation(facesContext, null, "inicioAdmin?faces-redirect=true");
            }
            
            else {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
                navigationHandler.handleNavigation(facesContext, null, "inicioDepend?faces-redirect=true");
            }  
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciales incorrectas", null));
        }
    }

    @PostConstruct
    public void init(){
        usuario = new Usuario();
    }
    
    public void guardar(){
        try{
            usuario.setRol(TIPOUSUARIO);
            usuario.setSaldo(BigDecimal.ZERO);
            usuarioEjb.create(usuario);
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(facesContext, null, "inicioSesion?faces-redirect=true");      
        }
        catch(Exception e){
            
        }
    }
}
