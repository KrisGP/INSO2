/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

import istrabajo.model.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author cris9
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "Istrabajo")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public boolean validaCredenciales(String user, String password){
        boolean result = false;
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root usuario = criteriaQuery.from(Usuario.class);

        criteriaQuery.where(
            criteriaBuilder.equal(usuario.get("nombreUsuario"), user),
            criteriaBuilder.equal(usuario.get("contrasena"), password)
        );
        
        Query query = em.createQuery(criteriaQuery);
        Usuario resultado = (Usuario) query.getSingleResult();

        if (resultado != null) {
            result = true;
        }
        return result;
    }
    
    public String tipoUser(String nombreUsuario){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root usuario = criteriaQuery.from(Usuario.class);
        
        criteriaQuery.where(criteriaBuilder.equal(usuario.get("nombreUsuario"), nombreUsuario));
        
        Query query = em.createQuery(criteriaQuery);
        Usuario result = (Usuario) query.getSingleResult();
        return result.getRol();
    }
    
}
