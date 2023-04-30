/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

import istrabajo.model.Papeevent;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LuisPC
 */
@Local
public interface PapeeventFacadeLocal {

    void create(Papeevent papeevent);

    void edit(Papeevent papeevent);

    void remove(Papeevent papeevent);

    Papeevent find(Object id);

    List<Papeevent> findAll();

    List<Papeevent> findRange(int[] range);

    int count();
    
}
