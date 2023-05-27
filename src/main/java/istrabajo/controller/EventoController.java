/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.controller;

import istrabajo.ejb.EventoFacadeLocal;
import istrabajo.model.Evento;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
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

    public List<Evento> obtenerEventos() {
        return eventoEjb.obtenerEventos();
    }

    public String redirigirAPapeletas(Evento evento) {
        // Aquí puedes realizar cualquier lógica adicional antes de redirigir a la página de papeletas
        return "papeletas.xhtml?eventoId=" + evento.getIdEvento() + "&faces-redirect=true";
    }
}
