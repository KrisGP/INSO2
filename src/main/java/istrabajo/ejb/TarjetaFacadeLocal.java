/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

import istrabajo.model.Tarjeta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LuisPC
 */
@Local
public interface TarjetaFacadeLocal {

    void create(Tarjeta tarjeta);

    void edit(Tarjeta tarjeta);

    void remove(Tarjeta tarjeta);

    Tarjeta find(Object id);

    List<Tarjeta> findAll();

    List<Tarjeta> findRange(int[] range);

    int count();

    public Object getTarjeta(String tarjetasCreditoCol);

    public List<Tarjeta> getTarjetasUsuario(int idUsuario);
    
}
