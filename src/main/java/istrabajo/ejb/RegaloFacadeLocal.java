/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

import istrabajo.model.Regalo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LuisPC
 */
@Local
public interface RegaloFacadeLocal {

    void create(Regalo regalo);

    void edit(Regalo regalo);

    void remove(Regalo regalo);

    Regalo find(Object id);

    List<Regalo> findAll();

    List<Regalo> findRange(int[] range);

    int count();
    
}
