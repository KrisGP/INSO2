/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

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
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
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
    
    public List<Tarjeta> getTarjetasUsuario(int idUsuario) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Tarjeta> criteriaQuery = criteriaBuilder.createQuery(Tarjeta.class);
        Root<Tarjeta> root = criteriaQuery.from(Tarjeta.class);
        Join<Tarjeta, Usuario> join = root.join("idUsuario");
        
        criteriaQuery.where(criteriaBuilder.equal(join.get("idUsuario"), idUsuario));
        criteriaQuery.select(root);
        List<Tarjeta> result = em.createQuery(criteriaQuery).getResultList();
        
        /**
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Tarjeta> criteriaQuery = criteriaBuilder.createQuery(Tarjeta.class);
        Root<Usuario> usuario = criteriaQuery.from(Usuario.class);
        Path<Tarjeta> tarjetaPath = usuario.get("tarjetas");
        criteriaQuery.select(tarjetaPath);
        criteriaQuery.where(criteriaBuilder.equal(usuario.get("idUsuario"), idUsuario));
        Query query = em.createQuery(criteriaQuery);
        List<Tarjeta> result = query.getResultList();
        
        */
        /**
         * CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Tarjeta> criteriaQuery = criteriaBuilder.createQuery(Tarjeta.class);
        Root<Tarjeta> root = criteriaQuery.from(Tarjeta.class);
        Join<Tarjeta, Usuario> usuario = root.join("idUsuario", JoinType.INNER);
        criteriaQuery.where(criteriaBuilder.equal(usuario.get("idUsuario"), idUsuario));
        Query query = em.createQuery(criteriaQuery);
        List<Tarjeta> result = query.getResultList();
         */
        
        return result;
    }


}
