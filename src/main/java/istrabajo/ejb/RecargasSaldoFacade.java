/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

import istrabajo.model.RecargasSaldo;
import istrabajo.model.Tarjeta;
import istrabajo.model.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

/**
 *
 * @author LuisPC
 */
@Stateless
public class RecargasSaldoFacade extends AbstractFacade<RecargasSaldo> implements RecargasSaldoFacadeLocal {

    @PersistenceContext(unitName = "Istrabajo")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RecargasSaldoFacade() {
        super(RecargasSaldo.class);
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
    
    public List<RecargasSaldo> getRecargasSaldoUsuario(int idUsuario) {
        List<RecargasSaldo> result;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<RecargasSaldo> criteriaQuery = criteriaBuilder.createQuery(RecargasSaldo.class);
            Root<RecargasSaldo> root = criteriaQuery.from(RecargasSaldo.class);
            Join<RecargasSaldo, Usuario> join = root.join("idUsuario");

            criteriaQuery.where(criteriaBuilder.equal(join.get("idUsuario"), idUsuario));
            criteriaQuery.select(root);
            result = em.createQuery(criteriaQuery).getResultList();
        } catch(NoResultException e) {
            result = null;
        }
        
        return result;
    }
  
    
}
