/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

import istrabajo.model.Evento;
import istrabajo.model.Papeleta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LuisPC
 */
@Local
public interface EventoFacadeLocal {

    void create(Evento evento);

    void edit(Evento evento);

    void remove(Evento evento);

    Evento find(Object id);

    List<Evento> findAll();

    List<Evento> findRange(int[] range);

    int count();
    
    public Evento nombreEvento(int idEvento);

    public int calculaNumeroEventos();    
    
    public List<Evento> obtenerEventos();
    
    public List<Papeleta> obtenerPapeletasBaseDatos(int idEvento);
    Evento getEvento(String nombre);
}
