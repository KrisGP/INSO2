/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

import istrabajo.model.Premio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LuisPC
 */
@Local
public interface PremioFacadeLocal {

    void create(Premio premio);

    void edit(Premio premio);

    void remove(Premio premio);

    Premio find(Object id);

    List<Premio> findAll();

    List<Premio> findRange(int[] range);

    int count();
    
}
