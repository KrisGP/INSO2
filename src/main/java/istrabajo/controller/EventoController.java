/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.controller;

import istrabajo.ejb.EventoFacadeLocal;
import istrabajo.model.Evento;
import istrabajo.model.Papeleta;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author cris9
 */
@Named
@ViewScoped
public class EventoController implements Serializable {

    @EJB
    private EventoFacadeLocal eventoEjb;
    private Evento evento;
    private Evento eventoSeleccionado; //este es el evento que seleciona el usuario
    private List<Evento> eventos;
    public Evento getEventoSeleccionado() {
        return eventoSeleccionado;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    
    public void setEventoSeleccionado(Evento eventoSeleccionado) {
        this.eventoSeleccionado = eventoSeleccionado;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

     public List<Evento> obtenerEventos() {
        setEventos(eventoEjb.obtenerEventos());
        return eventoEjb.obtenerEventos();
    }

    public List<Papeleta> obtenerPapeletas() {
        return eventoEjb.obtenerPapeletasBaseDatos(eventoSeleccionado.getIdEvento());
    }

    /*public String redirigirAPapeletas(Evento evento) {
        setEventoSeleccionado(evento);
        String eventoId = String.valueOf(evento.getIdEvento());
        String redirectURL = "papeletas.xhtml?eventoId=" + eventoId + "&faces-redirect=true";

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + "/" + redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/
    public void prueba() {
        this.eventoSeleccionado.getNombreEvento();
    }

    /*public void redirigirAPapeletas(Evento evento) {
        // Aquí puedes realizar cualquier lógica adicional antes de redirigir
        setEventoSeleccionado(evento);
        // Redirigir a la página papeletas.xhtml pasando el ID del evento como parámetro
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext, null, "papeletas?faces-redirect=true");
    }*/
 /*public void redirigirAPapeletas(Evento evento) {
        // Redirigir a la página detalles.xhtml pasando el objeto como parámetro
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + "/papeletas.xhtml?objetoId=" + evento.getIdEvento());
        } catch (IOException e) {
            // Manejar cualquier excepción de redirección
            e.printStackTrace();
        }
    }*/
    public String redirigirAPapeletas(Evento evento) {
        setEventoSeleccionado(evento);
        String eventoId = String.valueOf(evento.getIdEvento());
        String redirectURL = "papeletas.xhtml?eventoId=" + eventoId + "&faces-redirect=true";

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + "/" + redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*public void redirigirAPapeletas(Evento evento){
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext, null, "papeletas?faces-redirect=true");
        Evento newEvento = new Evento();
        newEvento.setNombreEvento(evento.getNombreEvento());
        setEventoSeleccionado(newEvento);
    }*/
 /*public String redirigirAPapeletas(Evento evento) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.getSessionMap().put("eventoSeleccionado", evento);
        return "papeletas.xhtml?faces-redirect=true";
    }*/
}
