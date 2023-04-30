/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

import istrabajo.model.UsuarioTieneTarjeta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LuisPC
 */
@Local
public interface UsuarioTieneTarjetaFacadeLocal {

    void create(UsuarioTieneTarjeta usuarioTieneTarjeta);

    void edit(UsuarioTieneTarjeta usuarioTieneTarjeta);

    void remove(UsuarioTieneTarjeta usuarioTieneTarjeta);

    UsuarioTieneTarjeta find(Object id);

    List<UsuarioTieneTarjeta> findAll();

    List<UsuarioTieneTarjeta> findRange(int[] range);

    int count();
    
}
