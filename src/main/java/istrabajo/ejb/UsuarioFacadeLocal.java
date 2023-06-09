/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

import istrabajo.model.Usuario;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UsuarioFacadeLocal {

    void create(Usuario usuario);

    void edit(Usuario usuario);

    void remove(Usuario usuario);

    Usuario find(Object id);

    List<Usuario> findAll();

    List<Usuario> findRange(int[] range);

    int count();
    
    Usuario getUsuario(String dni);
    
    void persist(Usuario usuario);
    
    boolean actualizarSaldo(BigDecimal cantidad);
    
    boolean validaCredenciales(String user, String password);
    
    boolean nombreUserValido(String nombreUser);
    
    String tipoUser(String nombreUsuario);
    
    public Usuario getUsuarioIn(String nombreUsuario);

}
