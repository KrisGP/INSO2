/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

import istrabajo.model.UsuarioCompraPapeletas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author LuisPC
 */
@Stateless
public class UsuarioCompraPapeletasFacade extends AbstractFacade<UsuarioCompraPapeletas> implements UsuarioCompraPapeletasFacadeLocal {

    @PersistenceContext(unitName = "Istrabajo")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioCompraPapeletasFacade() {
        super(UsuarioCompraPapeletas.class);
    }
    
}
