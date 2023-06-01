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
    private Evento eventoSeleccionado;

    public Evento getEventoSeleccionado() {
        return eventoSeleccionado;
    }

    public void setEventoSeleccionado(Evento eventoSeleccionado) {
        this.eventoSeleccionado = eventoSeleccionado;
    } 

    public List<Evento> obtenerEventos() {
        return eventoEjb.obtenerEventos();
    }

    public List<Papeleta> obtenerPapeletas() {
        return eventoEjb.obtenerPapeletasBaseDatos(evento);
    }

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

    /*public String redirigirAPapeletas(Evento evento) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.getSessionMap().put("eventoSeleccionado", evento);
        return "papeletas.xhtml?faces-redirect=true";
    }*/
}
