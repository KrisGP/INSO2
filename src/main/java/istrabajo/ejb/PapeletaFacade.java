/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

import istrabajo.model.Evento;
import istrabajo.model.Papeleta;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

/**
 *
 * @author LuisPC
 */
@Stateless
public class PapeletaFacade extends AbstractFacade<Papeleta> implements PapeletaFacadeLocal {

    @PersistenceContext(unitName = "Istrabajo")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PapeletaFacade() {
        super(Papeleta.class);
    }
    
    public List<Papeleta> getPapeletasDisponiblesEvento(int idEvento) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Papeleta> criteriaQuery = criteriaBuilder.createQuery(Papeleta.class);
        Root<Papeleta> papeleta = criteriaQuery.from(Papeleta.class);
        Join<Papeleta, Evento> join = papeleta.join("evento");
        criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(join.get("idEvento"), idEvento), (papeleta.get("fechaCompra").isNull())));
        criteriaQuery.orderBy(criteriaBuilder.asc(papeleta.get("combinacionPapeleta").as(Integer.class)));
        List<Papeleta> result = em.createQuery(criteriaQuery).getResultList();

        
        return result;
    }
    
    /*
        public List<Papeleta> getPapeletasDisponiblesEvento(int idEvento) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Papeleta> criteriaQuery = criteriaBuilder.createQuery(Papeleta.class);
        Root<Papeleta> papeleta = criteriaQuery.from(Papeleta.class);
        Join<Papeleta, Evento> join = papeleta.join("evento");
        criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(join.get("idEvento"), idEvento), (papeleta.get("fechaCompra").isNull())));
        
        List<Papeleta> result = em.createQuery(criteriaQuery).getResultList();

        return result;
    }
    */
    
    /*
        public List<Papeleta> getPapeletasDisponiblesEvento(int idEvento) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Papeleta> criteriaQuery = criteriaBuilder.createQuery(Papeleta.class);
        Root<Papeleta> papeleta = criteriaQuery.from(Papeleta.class);
        Join<Papeleta, Evento> join = papeleta.join("evento");
        criteriaQuery.where(criteriaBuilder.equal(join.get("idEvento"), idEvento));
        
        criteriaQuery.where(papeleta.get("fechaCompra").isNull());
        List<Papeleta> result = em.createQuery(criteriaQuery).getResultList();

        return result;
    }
    */
    
        
    
    

    
}
