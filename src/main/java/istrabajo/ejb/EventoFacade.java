/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

import istrabajo.model.Evento;
import istrabajo.model.Papeleta;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

/**
 *
 * @author LuisPC
 */
@Stateless
public class EventoFacade extends AbstractFacade<Evento> implements EventoFacadeLocal {

    @PersistenceContext(unitName = "Istrabajo")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventoFacade() {
        super(Evento.class);
    }

    public Evento nombreEvento(int idEvento) {
        boolean result = true;
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Evento.class);
        Root usuario = criteriaQuery.from(Evento.class);

        criteriaQuery.where(criteriaBuilder.equal(usuario.get("idEvento"), idEvento));

        Query query = em.createQuery(criteriaQuery);
        Evento resultado = (Evento) query.getSingleResult();

        return resultado;
    }

    public int calculaNumeroEventos() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Evento> eventoRoot = countQuery.from(Evento.class);

        countQuery.select(criteriaBuilder.count(eventoRoot));

        TypedQuery<Long> typedQuery = em.createQuery(countQuery);
        Long totalEventos = typedQuery.getSingleResult();

        return totalEventos.intValue();
    }

    public List<Evento> obtenerEventos() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Evento> criteriaQuery = criteriaBuilder.createQuery(Evento.class);
        Root<Evento> eventoRoot = criteriaQuery.from(Evento.class);

        criteriaQuery.select(eventoRoot);

        TypedQuery<Evento> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Papeleta> obtenerPapeletasBaseDatos(int idEvento) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Papeleta> criteriaQuery = criteriaBuilder.createQuery(Papeleta.class);
        Root<Papeleta> papeletaRoot = criteriaQuery.from(Papeleta.class);
        Join<Papeleta, Evento> join = papeletaRoot.join("idEvento");
        
        criteriaQuery.where(criteriaBuilder.equal(join.get("idEvento"), idEvento));
        criteriaQuery.select(papeletaRoot);
        
        List<Papeleta> result = em.createQuery(criteriaQuery).getResultList();

        return result;
    }
    

}
