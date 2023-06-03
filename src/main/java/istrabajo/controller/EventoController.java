/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.controller;

import istrabajo.ejb.EventoFacadeLocal;
import istrabajo.ejb.PapeletaFacadeLocal;
import istrabajo.model.Evento;
import istrabajo.model.Papeleta;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import istrabajo.model.Premio;
import istrabajo.model.RecargasSaldo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author cris9
 * @author Luis
 */
@Named
@ViewScoped
public class EventoController implements Serializable {
    /**
     * Cadena que representa el primer premio
     */
    public static final String PRIMERPREMIO = "PRIMERPREMIO";
    /**
     * Cadena que representa el segundo premio
     */
    public static final String SEGUNDOPREMIO = "SEGUNDOPREMIO";
    /**
     * Cadena que representa el tercer premio
     */
    public static final String TERCERPREMIO = "TERCERPREMIO";

    /**
     * Cadena con el porcentaje que se utiliza de IVA en España
     */
    public static final String PORCENTAJEIVA = "0.21";

    @EJB
    private EventoFacadeLocal eventoEjb;
    @EJB
    private PapeletaFacadeLocal papeletaEjb;

    private Evento evento;
    private Evento eventoSeleccionado; //este es el evento que seleciona el usuario
    private List<Evento> eventos;
    private List<Papeleta> papeletas;
    private boolean mostrarEvento = false;

    public boolean isMostrarEvento() {
        return mostrarEvento;
    }

    public void setMostrarEvento(boolean mostrarEvento) {
        this.mostrarEvento = mostrarEvento;
    }

    public List<Papeleta> getPapeletas() {
        return papeletas;
    }

    public void setPapeletas(List<Papeleta> papeletas) {
        this.papeletas = papeletas;
    }

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
    
    private Premio premio1;
    private Premio premio2;
    private Premio premio3;
    
    @PostConstruct
    public void init() {
        evento = new Evento();
        
        premio1 = new Premio();
        premio1.setPuestoObtenerPremio(PRIMERPREMIO);
        premio2 = new Premio();
        premio2.setPuestoObtenerPremio(SEGUNDOPREMIO);
        premio3 = new Premio();
        premio3.setPuestoObtenerPremio(TERCERPREMIO);
    }
    
    /**
     * Registra un evento en la base de datos con sus correspondientes papeletas y premios creados
     * Crea las papeletas según el atributo del evento numPapeletas
     * Siempre crea 3 premios (los que estan declarados en esta clase)
     */
    public void registrarEvento() {
        if(existeEvento(this.evento) == false) {
            //calcular iva de la papeleta, y asignarle el atributo al evento
            BigDecimal costeIvaPapeleta = BigDecimal.ZERO;
            BigDecimal iva = new BigDecimal(PORCENTAJEIVA);
            costeIvaPapeleta = evento.getCostePapeletasTotal().multiply(iva);
            BigDecimal costeSinIva = evento.getCostePapeletasTotal().subtract(costeIvaPapeleta);
            
            evento.setCostePapeletasSinIva(costeSinIva);
            //comprobar que la fecha de finalizacion no sea antes que la de empezado
            
            //Comprobacion de los datos de los premios
            List<Premio> listaPremios = new ArrayList();
            listaPremios.add(premio1);
            listaPremios.add(premio2);
            listaPremios.add(premio3);
            
            int i = 0;
            do {
                listaPremios.get(i).setEvento(evento);
                i++;
            } while(i<listaPremios.size());
            
            this.evento.setPremios(listaPremios);
            this.creacionPapeletas(evento);
            
            //Lo metemos en la base de datos
            eventoEjb.create(evento);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento añadido correctamente", null));

            
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un evento con este nombre. No pueden haber 2 eventos con el mismo nombre", null));

        }
    }
    
    /**
     * Comprueba si existe un evento en la base de datos
     * @param evento
     * @return true si existe el evento en la base de datos
     *         false si noe xiste en la base de datos
     */
    public boolean existeEvento(Evento evento) {
        if(eventoEjb.getEvento(evento.getNombreEvento()) == null) {
            return false;
        } else {
            return true;
        }
    }
    
    public void creacionPapeletas(Evento evento) {
        if(evento != null) {
            int numeroPapeletas = evento.getNumeroPapeletas();
            List<Papeleta> papeletas = new ArrayList();
            int i=0;
            while(i<numeroPapeletas){
                Papeleta papeleta = new Papeleta();
                papeleta.setCombinacionPapeleta(Integer.toString(i+1));
                papeleta.setEvento(evento);
                papeletas.add(papeleta);
                i++;
            }
            evento.setPapeletas(papeletas);
        }
        
    }

    public EventoFacadeLocal getEventoEjb() {
        return eventoEjb;
    }

    public void setEventoEjb(EventoFacadeLocal eventoEjb) {
        this.eventoEjb = eventoEjb;
    }

    public PapeletaFacadeLocal getPapeletaEjb() {
        return papeletaEjb;
    }

    public void setPapeletaEjb(PapeletaFacadeLocal papeletaEjb) {
        this.papeletaEjb = papeletaEjb;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }


     public List<Evento> obtenerEventos() {
        if(eventos == null) {
            setEventos(eventoEjb.obtenerEventos());
        } else {
            if(eventos.size() == 0) {
                setEventos(eventoEjb.obtenerEventos());
            }
        }
        
        return eventos;
    }

    public List<Papeleta> obtenerPapeletas() {
        setPapeletas(eventoEjb.getPapeletasDisponiblesEvento(eventoSeleccionado.getIdEvento()));
        return eventoEjb.getPapeletasDisponiblesEvento(eventoSeleccionado.getIdEvento());
    }

    @Named
    @ViewScoped
    public void cargarPapeletas(Evento evento) {
        setEventoSeleccionado(evento);
        setPapeletas(eventoEjb.getPapeletasDisponiblesEvento(evento.getIdEvento()));
        setMostrarEvento(true);
    }

    public List<Papeleta> obtenerPapelas() {
        return eventoEjb.getPapeletasDisponiblesEvento(eventoSeleccionado.getIdEvento());
    }

    public void redirigirCreacionEventos() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext, null, "registroEventos?faces-redirect=true");
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
    
    public Premio getPremio1() {
        return premio1;
    }

    public void setPremio1(Premio premio1) {
        this.premio1 = premio1;
    }

    public Premio getPremio2() {
        return premio2;
    }

    public void setPremio2(Premio premio2) {
        this.premio2 = premio2;
    }

    public Premio getPremio3() {
        return premio3;
    }

    public void setPremio3(Premio premio3) {
        this.premio3 = premio3;
    }
}
