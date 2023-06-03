/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.controller;

import istrabajo.SesionUsuario;
import istrabajo.ejb.EventoFacadeLocal;
import istrabajo.ejb.PapeletaFacadeLocal;
import istrabajo.ejb.UsuarioCompraPapeletasFacadeLocal;
import istrabajo.ejb.UsuarioFacadeLocal;
import istrabajo.model.Evento;
import istrabajo.model.Papeleta;
import istrabajo.model.Usuario;
import istrabajo.model.UsuarioCompraPapeletas;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Luis
 */
@Named
@ViewScoped
public class UsuarioCompraPapeletasController implements Serializable{
    @EJB
    private UsuarioCompraPapeletasFacadeLocal usuarioCompraPapeletasEjb;
    @EJB
    private PapeletaFacadeLocal papeletasEjb;
    @EJB
    private UsuarioFacadeLocal usuarioEjb;
    @EJB
    private EventoFacadeLocal eventoEjb;

    
    private UsuarioCompraPapeletas usuarioCompraPapeletas;
    
    private Evento eventoSeleccionado;
    
    /**
     * Lista que almacenará todas las papeletas que puede comprar el usuario en ese momento
     */
    private List<Papeleta> listaPapeletasEventoDisponibles;
    /**
     * Lista de las papeletas que ha seleccionado el usuario para comprar
     */
    private List<Papeleta> papeletasSeleccionadas;
    
    
    /**
     * Utiliza papeletasEjb para conseguir las papeletas que estás disponibles para comprar del eventoActual
     */
    public void iniciarListaPapeletas() {
       //Esto es para poder comprar papeletas del primer evento, porque no se consigue pasar el valor de un eventp
       //
       eventoSeleccionado = eventoEjb.find(0);
       listaPapeletasEventoDisponibles = papeletasEjb.getPapeletasDisponiblesEvento(eventoSeleccionado.getIdEvento());
    }
    
    public void redirigirComprarPapeletas() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext, null, "usuarioCompraPapeletas?faces-redirect=true");
    }

    /**
     * Método utilizado para comprar las papeletas
     */
    public void comprarPapeletas() {
        if(papeletasSeleccionadas != null) {
            //Conseguir el total de la compra.
            int num = papeletasSeleccionadas.size();
            int i=0;
            Usuario usuario = usuarioEjb.find(SesionUsuario.getInstance().getIdUsuario());
            Calendar fecha = Calendar.getInstance();
            //List<UsuarioCompraPapeletas> lista = new ArrayList();
            
            BigDecimal total = eventoSeleccionado.getCostePapeletasTotal().multiply(new BigDecimal(String.valueOf(num)));
            //Comprobamos si hay saldo disponible
            BigDecimal saldoActual = SesionUsuario.getInstance().getSaldo();
            if(saldoActual.compareTo(total) == 1 ||  saldoActual.compareTo(total) == 0) {
                //Actualizamos el saldo en la base de datos y en la sesión abierta
                //A lo que haya, con el método actualizar saldo, se lo resta.
                usuarioEjb.actualizarSaldo(total.negate());
                while(i < num) {
                    //Priemro actualizamos la papeleta con la fecha de compra
                    papeletasSeleccionadas.get(i).setFechaCompra(fecha);
                    papeletasEjb.edit(papeletasSeleccionadas.get(i));
                    //Luego creamos el registro de la compra
                    UsuarioCompraPapeletas usuCompraPape = new UsuarioCompraPapeletas();
                    usuCompraPape.setIdPapeleta(papeletasSeleccionadas.get(i));
                    usuCompraPape.setFecha(fecha);
                    usuCompraPape.setIdUsuario(usuario);
                    //lista.add(usuCompraPape);
                    usuarioCompraPapeletasEjb.create(usuCompraPape);
                    i = i + 1;
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha completado la compra", null));
            
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No hay saldo disponible para comprar las papeletas", null));
            }
            
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha elegido ninguna papeleta para comprar", null));
        }
        
    }
    
    public UsuarioCompraPapeletasFacadeLocal getUsuarioCompraPapeletasEjb() {
        return usuarioCompraPapeletasEjb;
    }

    public void setUsuarioCompraPapeletasEjb(UsuarioCompraPapeletasFacadeLocal usuarioCompraPapeletasEjb) {
        this.usuarioCompraPapeletasEjb = usuarioCompraPapeletasEjb;
    }

    public UsuarioCompraPapeletas getUsuarioCompraPapeletas() {
        return usuarioCompraPapeletas;
    }

    public void setUsuarioCompraPapeletas(UsuarioCompraPapeletas usuarioCompraPapeletas) {
        this.usuarioCompraPapeletas = usuarioCompraPapeletas;
    }

    public List<Papeleta> getListaPapeletasEventoDisponibles() {
        return listaPapeletasEventoDisponibles;
    }

    public void setListaPapeletasEventoDisponibles(List<Papeleta> listaPapeletasEventoDisponibles) {
        this.listaPapeletasEventoDisponibles = listaPapeletasEventoDisponibles;
    }

    public List<Papeleta> getPapeletasSeleccionadas() {
        return papeletasSeleccionadas;
    }

    public void setPapeletasSeleccionadas(List<Papeleta> papeletasSeleccionadas) {
        this.papeletasSeleccionadas = papeletasSeleccionadas;
    }

    public Evento getEventoSeleccionado() {
        return eventoSeleccionado;
    }

    public void setEventoSeleccionado(Evento eventoSeleccionado) {
        this.eventoSeleccionado = eventoSeleccionado;
    }
    
    

}
