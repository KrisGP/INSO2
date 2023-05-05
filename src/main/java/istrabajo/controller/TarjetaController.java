/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.controller;

import istrabajo.ejb.TarjetaFacadeLocal;
import istrabajo.model.Tarjeta;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author cris9
 */
@Named
@ViewScoped
public class TarjetaController implements Serializable{
    @EJB
    private TarjetaFacadeLocal tarjetaEjb;
    private Tarjeta tarjeta;

    @PostConstruct
    public void init() {
        tarjeta = new Tarjeta();
    }

    public boolean existeTarjeta(Tarjeta tarjeta) {
        if(tarjetaEjb.getTarjeta(tarjeta.getTarjetasCreditoCol()) == null) {
            return false;
        } else {
            return true;
        }
    }


}