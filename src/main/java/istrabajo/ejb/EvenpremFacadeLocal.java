/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

import istrabajo.model.Evenprem;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LuisPC
 */
@Local
public interface EvenpremFacadeLocal {

    void create(Evenprem evenprem);

    void edit(Evenprem evenprem);

    void remove(Evenprem evenprem);

    Evenprem find(Object id);

    List<Evenprem> findAll();

    List<Evenprem> findRange(int[] range);

    int count();
    
}
