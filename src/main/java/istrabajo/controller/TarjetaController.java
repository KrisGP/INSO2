/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.controller;

import istrabajo.ejb.TarjetaFacadeLocal;
import istrabajo.model.Tarjeta;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Luis
 */
@Named
@ViewScoped
public class TarjetaController implements Serializable{
    @EJB
    private TarjetaFacadeLocal tarjetaEjb;
    private Tarjeta tarjeta;
    
    public static final Character NUMEROVISA = '4';
    public static final Character NUMEROMASTERCARD = '5';

    @PostConstruct
    public void init() {
        tarjeta = new Tarjeta();
    }
    
    /**
     * Comprueba que el parámetro "mes" esté entre 1 y 12.
     * Reformatea "mes" para que sea una cadena de texto de longitud 2, añadiendole un 
     * 0 a la izquierda cuando sea necesario.
     * Ejemplo: 
     * mes = 9 
     * Devolvería: "09"
     * 
     * @param mes
     * @return
     * @throws NumberFormatException Si "mes" no está entre 1 y 12
     *                               Si "mes" no es un número Integer válido
     */
    public static String validarYFormatearMesCaducidad(String mes) throws NumberFormatException {
        int mesInt = Integer.parseInt(mes);
        String mesFormateado = String.valueOf(mesInt);
        if(mesInt > 12 || mesInt < 0) {
            throw new NumberFormatException();
        }
        if(mesInt < 10) {
            mesFormateado = "0" + mesFormateado;
        }
        return mesFormateado;
    }
    
    public static String getTipoTarjeta(Tarjeta tarjeta) {
        if(tarjeta.getTarjetasCreditoCol().charAt(0) == TarjetaController.NUMEROVISA) {
            return "Visa";
        } else if(tarjeta.getTarjetasCreditoCol().charAt(0) == TarjetaController.NUMEROMASTERCARD) {
            return "Mastercard";
        } else {
            return "No registrado";
        }
    }

    /**
     * Comprueba que exista la tarjeta en la base de datos
     * @param tarjeta
     * @return true si existe la tarjeta en la abse de datos
     *         false si no existe
     */
    public boolean existeTarjeta(Tarjeta tarjeta) {
        if(tarjetaEjb.getTarjeta(tarjeta.getTarjetasCreditoCol()) == null) {
            return false;
        } else {
            return true;
        }
    }


}