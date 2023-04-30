/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.ejb;

import istrabajo.model.Papeleta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LuisPC
 */
@Local
public interface PapeletaFacadeLocal {

    void create(Papeleta papeleta);

    void edit(Papeleta papeleta);

    void remove(Papeleta papeleta);

    Papeleta find(Object id);

    List<Papeleta> findAll();

    List<Papeleta> findRange(int[] range);

    int count();
    
}
