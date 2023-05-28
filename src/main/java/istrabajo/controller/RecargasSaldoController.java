/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.controller;

import istrabajo.SesionUsuario;
import istrabajo.ejb.RecargasSaldoFacadeLocal;
import istrabajo.ejb.TarjetaFacade;
import istrabajo.ejb.TarjetaFacadeLocal;
import istrabajo.ejb.UsuarioFacadeLocal;
import istrabajo.model.RecargasSaldo;
import istrabajo.model.Tarjeta;
import istrabajo.model.Usuario;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Luis
 */
@Named
@ViewScoped
public class RecargasSaldoController implements Serializable{
    @EJB
    private RecargasSaldoFacadeLocal recargasSaldoEjb;
    @EJB
    private TarjetaFacadeLocal tarjetaEjb;
    @EJB
    private UsuarioFacadeLocal usuarioEjb;
    
    private RecargasSaldo recargasSaldo;
   
    /**
     * Lista de instancias de Tarjeta que el usuario tiene guardadas en la base de datos
     */
    private List<Tarjeta> tarjetasGuardadas;
    /**
     * Lista de los números de las tarjeta del usuario
     */
    private List<String> names; 
    
    /**
     * Opciones de Ingresar o Retirar dinero
     */
    private List<String> opciones;
    /**
     * Opción elegida por el usuario para Ingresar o Retirar dinero
     */
    private String opcionElegida;
    /**
     * Numero de la tarjeta con la que se va a realizar la operacion
     */
    private String numeroTarjetaRealizarOperacion;
    /**
     * String que contiene el BigDecimal que el usuario ha introducido para realizar la operacion
     */
    private BigDecimal numeroOperacion;
    
    @PostConstruct
    public void init() {
        recargasSaldo = new RecargasSaldo();
        names = new ArrayList<String>();
        opciones = new ArrayList<String>();
        opciones.add("Ingresar");
        opciones.add("Retirar");
        numeroTarjetaRealizarOperacion = new String();
    }
    
    /**
     * Utilizado para recoger las tarjetas de un usuario en la base de datos.
     */
    public void iniciarTarjetasUsuarioActivo() {
        this.tarjetasGuardadas = tarjetaEjb.getTarjetasUsuario(SesionUsuario.getInstance().getIdUsuario());
        if(tarjetasGuardadas != null) {
            for(int i=0;i<tarjetasGuardadas.size(); i++) {
                names.add(tarjetasGuardadas.get(i).getTarjetasCreditoCol());
            }
        }
        
    }
    
    /**
     * Método para realizar la operación de Ingresar o Retirar dinero de la cuenta del usuario actual y en la base de datos
     */
    public void realizarTransaccion() {
        try {
            int indexTarjeta = names.indexOf(this.numeroTarjetaRealizarOperacion);
                try {

                    BigDecimal numero = numeroOperacion;
                    if(opcionElegida.equals("Retirar")) {
                        numero = numero.negate();
                        
                    }
                    if(usuarioEjb.actualizarSaldo(numero)) {
                        RecargasSaldo rs = new RecargasSaldo();
                        rs.setIdTarjeta(this.tarjetasGuardadas.get(indexTarjeta));
                        rs.setIdUsuario(usuarioEjb.find(SesionUsuario.getInstance().getIdUsuario()));
                        rs.setValorRecargo(numeroOperacion.toString());
                        rs.setFechaRecarga(Calendar.getInstance());
                        rs.setTipoOperacion(opcionElegida);
                        recargasSaldoEjb.create(rs);
                        
                        //SesionUsuario.getInstance().setSaldo(numero);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha realizado correctamente la operación", null));
                
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se ha podido llevar a cabo la operación. No puede retirar más saldo del que tiene en su cuenta.", null));
                
                    }
                    
                } catch(NumberFormatException e) {
                    //El usuario no ha escrito el saldo en el formato pedido
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Formato de saldo para la operación incorrecto", null));
                }
            } catch(ArrayIndexOutOfBoundsException e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "No tiene una tarjeta seleccionada", null));
            }
    }

    public UsuarioFacadeLocal getUsuarioEjb() {
        return usuarioEjb;
    }

    public void setUsuarioEjb(UsuarioFacadeLocal usuarioEjb) {
        this.usuarioEjb = usuarioEjb;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    public String getOpcionElegida() {
        return opcionElegida;
    }

    public void setOpcionElegida(String opcionElegida) {
        this.opcionElegida = opcionElegida;
    }

    public BigDecimal getNumeroOperacion() {
        return numeroOperacion;
    }

    public void setNumeroOperacion(BigDecimal numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }
    
    

    public String getNumeroTarjetaRealizarOperacion() {
        return numeroTarjetaRealizarOperacion;
    }

    public void setNumeroTarjetaRealizarOperacion(String numeroTarjetaRealizarOperacion) {
        this.numeroTarjetaRealizarOperacion = numeroTarjetaRealizarOperacion;
    }
    
    

    public RecargasSaldoFacadeLocal getRecargasSaldoEjb() {
        return recargasSaldoEjb;
    }

    public void setRecargasSaldoEjb(RecargasSaldoFacadeLocal recargasSaldoEjb) {
        this.recargasSaldoEjb = recargasSaldoEjb;
    }

    public TarjetaFacadeLocal getTarjetaEjb() {
        return tarjetaEjb;
    }

    public void setTarjetaEjb(TarjetaFacadeLocal tarjetaEjb) {
        this.tarjetaEjb = tarjetaEjb;
    }

    public RecargasSaldo getRecargasSaldo() {
        return recargasSaldo;
    }

    public void setRecargasSaldo(RecargasSaldo recargasSaldo) {
        this.recargasSaldo = recargasSaldo;
    }

    public List<Tarjeta> getTarjetasGuardadas() {
        return tarjetasGuardadas;
    }

    public void setTarjetasGuardadas(List<Tarjeta> tarjetasGuardadas) {
        this.tarjetasGuardadas = tarjetasGuardadas;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
    
    
}
    
