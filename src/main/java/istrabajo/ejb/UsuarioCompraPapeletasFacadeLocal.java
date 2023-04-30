/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

import istrabajo.model.UsuarioCompraPapeletas;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LuisPC
 */
@Local
public interface UsuarioCompraPapeletasFacadeLocal {

    void create(UsuarioCompraPapeletas usuarioCompraPapeletas);

    void edit(UsuarioCompraPapeletas usuarioCompraPapeletas);

    void remove(UsuarioCompraPapeletas usuarioCompraPapeletas);

    UsuarioCompraPapeletas find(Object id);

    List<UsuarioCompraPapeletas> findAll();

    List<UsuarioCompraPapeletas> findRange(int[] range);

    int count();
    
}
