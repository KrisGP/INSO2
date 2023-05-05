/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

import istrabajo.model.Tarjeta;
import istrabajo.model.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author LuisPC
 */
@Stateless
public class TarjetaFacade extends AbstractFacade<Tarjeta> implements TarjetaFacadeLocal {

    @PersistenceContext(unitName = "Istrabajo")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TarjetaFacade() {
        super(Tarjeta.class);
    }
    
    public Tarjeta getTarjeta(String numeroTarjeta) {
        Tarjeta result;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Tarjeta.class);
            Root tarjeta = criteriaQuery.from(Tarjeta.class);
            criteriaQuery.where(criteriaBuilder.equal(tarjeta.get("tarjetasCreditoCol"), numeroTarjeta));
            Query query = em.createQuery(criteriaQuery);
            result = (Tarjeta) query.getSingleResult();
        } catch(NoResultException e) {
            result = null;
        }
        
        return result;
        
 
    }
    
}
