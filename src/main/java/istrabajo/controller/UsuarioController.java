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
     * Comprueba que el parámetro "mes" esté entre 1 y 12.
     * Reformatea "mes" para que sea una cadena de texto de longitud 2, añadiendole un 
     * 0 a la izquierda cuando sea necesario.
     * Ejemplo: 
     * mes = 9 
     * Devolvería: "09"
     * 
     * @param mes
     * @return
     * @throws NumberFormatException Si "mes" no está entre 1 y 12
     *                               Si "mes" no es un número Integer válido
     */
    public static String validarYFormatearMesCaducidad(String mes) throws NumberFormatException {
        int mesInt = Integer.parseInt(mes);
        String mesFormateado = String.valueOf(mesInt);
        if(mesInt > 12 || mesInt < 0) {
            throw new NumberFormatException();
        }
        if(mesInt < 10) {
            mesFormateado = "0" + mesFormateado;
        }
        return mesFormateado;
    }
    

    
    /**
     * Registra la tarjeta introducida a al usuario que tiene la sesión abierta
     * Primero valida los datos introducidos sean correctos
     * 
     */
    public void registrarTarjeta() {
        //Validar que la tarjeta sea correcta
        String mesFormateado = null;
        try {
            //Validamos que la tarjeta sea correcta
            mesFormateado = validarYFormatearMesCaducidad(this.tarjeta.getMesCaduca());
            int anyoFormateado = Integer.parseInt(this.tarjeta.getAnyoCaduca());
            
            //Comprobamos que no exista en la base de datos
            if(tarjetaEjb.getTarjeta(tarjeta.getTarjetasCreditoCol()) == null) {
                //Si existe, mostramos un mensaje de alerta al usuario
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ya tienes esta tarjeta registrada", null));
            } else {
                //Si no existe, introducimos la tarjeta al usuario que tenga la sesión abierta
                usuario = usuarioEjb.find(SesionUsuario.getInstance().getIdUsuario());
                tarjeta.setUsuario(usuario);
                usuario.addTarjeta(tarjeta);
                usuarioEjb.edit(usuario);
            }

        } catch(NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error en el formato de la fecha. Revisela", null));
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
