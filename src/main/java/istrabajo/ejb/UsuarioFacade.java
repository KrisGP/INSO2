/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

import istrabajo.SesionUsuario;
import istrabajo.model.Usuario;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
    public boolean nombreUserValido(String nombreUser){
        boolean result = true;
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root usuario = criteriaQuery.from(Usuario.class);

        criteriaQuery.where(criteriaBuilder.equal(usuario.get("nombreUsuario"), nombreUser));
        
        Query query = em.createQuery(criteriaQuery);
        Usuario resultado = (Usuario) query.getSingleResult();

        if (resultado != null) {
            result = false;
        }
        return result;
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
    
    /**
     * Busca un usuario en la base de datos que tenga el mismo dni
     * @param dni
     * @return usuario con ese dni
     */
    public Usuario getUsuario(String dni) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root usuario = criteriaQuery.from(Usuario.class);
        criteriaQuery.where(criteriaBuilder.equal(usuario.get("dni"), dni));
        Query query = em.createQuery(criteriaQuery);
        Usuario result = (Usuario) query.getSingleResult();
        return result;
    }
    
    /**
     * Actualiza el saldo tanto de la sesión abierta del usuario (SesionUsuario)
     * como en la base de datos para las operaciones de retirar o de ingresar dinero
     * 
     * @param cantidad
     * @return true si se ha podido llevar a cabo la operaicon
     *         false si no se ha podido llevar a cabo la operacion (el usuario ha intentado 
     *               retirar dinero y el saldo habría terminado en negativo)
     */
    public boolean actualizarSaldo(BigDecimal cantidad) {
        Usuario usuario = this.find(SesionUsuario.getInstance().getIdUsuario());
        int decision = cantidad.compareTo(BigDecimal.ZERO);
        
        BigDecimal saldoActual = SesionUsuario.getInstance().getSaldo();
        BigDecimal saldoDespuesOperacion = saldoActual.add(cantidad);
        
        if(decision == -1){
            if(saldoDespuesOperacion.compareTo(BigDecimal.ZERO) == -1) {
                return false;
            } 
        } 
        
        SesionUsuario.getInstance().setSaldo(saldoDespuesOperacion);
        usuario.setSaldo(saldoDespuesOperacion);
        this.edit(usuario);
        
        return true;
    }
    
    
    public void persist(Usuario usuario) {
        em.persist(usuario);
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
