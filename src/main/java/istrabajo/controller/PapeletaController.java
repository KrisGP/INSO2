/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.controller;

import istrabajo.ejb.PapeletaFacadeLocal;
import istrabajo.model.Papeleta;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author cris9
 */
@Named
@ViewScoped
public class PapeletaController implements Serializable{
    @EJB
    private PapeletaFacadeLocal papeletaEjb;
    private Papeleta papeleta;
}
