/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.controller;

import istrabajo.SesionUsuario;
import istrabajo.ejb.TarjetaFacadeLocal;
import istrabajo.ejb.UsuarioFacadeLocal;
import istrabajo.model.Tarjeta;
import istrabajo.model.Usuario;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
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
    @EJB
    private TarjetaFacadeLocal tarjetaEjb;
    
    private Usuario usuario;
    private Tarjeta tarjeta;
    
    @PostConstruct
    public void init() {
        usuario = new Usuario();
        tarjeta = new Tarjeta();
    }
        
    /**
     * Registra la tarjeta introducida a al usuario que tiene la sesión abierta
     * Primero valida los datos introducidos sean correctos
     * 
     */
    public void registrarTarjeta() {
       if(tarjeta.getTarjetasCreditoCol().length() == 16 
          && (tarjeta.getTarjetasCreditoCol().charAt(0) == TarjetaController.NUMEROVISA
          || tarjeta.getTarjetasCreditoCol().charAt(0) == TarjetaController.NUMEROMASTERCARD)) {
            
            //Validar que la tarjeta sea correcta
            String mesFormateado = null;
            try {
                //Validamos que la tarjeta sea correcta
                mesFormateado = TarjetaController.validarYFormatearMesCaducidad(this.tarjeta.getMesCaduca());
                int anyoFormateado = Integer.parseInt(this.tarjeta.getAnyoCaduca());

                //Comprobar la tarjeta. Comprobar de que tipo es. Incluirlo al final
                String tipoTarjeta;
                tipoTarjeta = TarjetaController.getTipoTarjeta(tarjeta);

                //Comprobamos que no exista en la base de datos
                if(tarjetaEjb.getTarjeta(tarjeta.getTarjetasCreditoCol()) != null) {
                    //Si existe, mostramos un mensaje de alerta al usuario
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ya tienes esta tarjeta registrada", null));
                } else {
                    //Si no existe, introducimos la tarjeta al usuario que tenga la sesión abierta
                    usuario = usuarioEjb.find(SesionUsuario.getInstance().getIdUsuario());
                    tarjeta.setUsuario(usuario);
                    usuario.addTarjeta(tarjeta);
                    usuarioEjb.edit(usuario);

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrada la tarjeta de tipo " + tipoTarjeta, null));
                }

            } catch(NumberFormatException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error en el formato de la fecha. Revisela", null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error en el número de la tarjeta. Debe de ser una tarjeta Visa o Mastercard", null));
        }
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
