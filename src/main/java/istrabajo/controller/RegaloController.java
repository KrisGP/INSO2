/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.controller;

import istrabajo.ejb.RegaloFacadeLocal;
import istrabajo.model.Regalo;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author cris9
 */
public class RegaloController implements Serializable{
    @EJB
    private RegaloFacadeLocal regaloEjb;
    private Regalo regalo;
}
