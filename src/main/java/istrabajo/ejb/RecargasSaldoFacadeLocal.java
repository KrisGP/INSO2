/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

import istrabajo.model.RecargasSaldo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LuisPC
 */
@Local
public interface RecargasSaldoFacadeLocal {

    void create(RecargasSaldo recargasSaldo);

    void edit(RecargasSaldo recargasSaldo);

    void remove(RecargasSaldo recargasSaldo);

    RecargasSaldo find(Object id);

    List<RecargasSaldo> findAll();

    List<RecargasSaldo> findRange(int[] range);

    int count();
    
}
