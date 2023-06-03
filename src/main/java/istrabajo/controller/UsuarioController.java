package istrabajo.controller;

import istrabajo.SesionUsuario;
import istrabajo.ejb.TarjetaFacadeLocal;
import istrabajo.ejb.UsuarioFacadeLocal;
import istrabajo.ejb.UsuarioFacadeLocal;
import istrabajo.model.Tarjeta;
import istrabajo.model.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 */
@Named
@ViewScoped
public class UsuarioController implements Serializable {

    public static final String TIPOUSUARIO = "USUARIO";
    public static final String TIPOADMINISTRADOR = "ADMINISTRADO";
    public static final String TIPODEPENDIENTE = "DEPENDIENTE";

    @EJB
    private UsuarioFacadeLocal usuarioEjb;
    @EJB
    private TarjetaFacadeLocal tarjetaEjb;
    private Usuario usuario;

    private Tarjeta tarjeta;

    @PostConstruct
    public void init() {
        usuario = new Usuario();
        tarjeta = new Tarjeta();
    }
    
    public UsuarioFacadeLocal getUsuarioEjb() {
        return usuarioEjb;
    }

    public void setUsuarioEjb(UsuarioFacadeLocal usuarioEjb) {
        this.usuarioEjb = usuarioEjb;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public Usuario getUserSesion() {
        return SesionUsuario.getInstance().getUsuarioIn();
    }

    public String getNombreUserSesion() {
        return SesionUsuario.getInstance().getNombrePersona();
    }

    public String getSaldoUserSesion() {
        return SesionUsuario.getInstance().getSaldo().toString();
    }

    /**
     * Realiza el inicio de sesión del usuario
     * Primero comprueba que los datos del usuario sean correctos en la base de datos
     * Luego, dependiendo del tipo del usuariom redirige al usuario a su inicio correspondiente
     * 
     * Si los datos del usuario no son correctos, muestra un mensaje de error
     * @throws IOException 
     */
    public void iniciarSesion() throws IOException {
        if (usuarioEjb.validaCredenciales(usuario.getNombreUsuario(), usuario.getContrasena())) {
            setUsuario(usuarioEjb.getUsuarioIn(usuario.getNombreUsuario()));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inicio de sesión exitoso", null));

            if (usuarioEjb.tipoUser(usuario.getNombreUsuario()).equals(TIPOUSUARIO)) {
                SesionUsuario.getInstance().iniciarSesion(usuario.getIdUsuario(), usuario.getNombreUsuario(), usuario.getNombre(), usuario.getRol(), usuario.getSaldo());
                SesionUsuario.getInstance().setUsuarioIn(usuario);
                FacesContext facesContext = FacesContext.getCurrentInstance();
                NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
                navigationHandler.handleNavigation(facesContext, null, "inicioUser?faces-redirect=true");
            } else if (usuarioEjb.tipoUser(usuario.getNombreUsuario()).equals(TIPOADMINISTRADOR)) {
                SesionUsuario.getInstance().iniciarSesion(usuario.getIdUsuario(), usuario.getNombreUsuario(), usuario.getNombre(), usuario.getRol(), usuario.getSaldo());
                FacesContext facesContext = FacesContext.getCurrentInstance();
                NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
                navigationHandler.handleNavigation(facesContext, null, "inicioAdmin?faces-redirect=true");
            } else {
                SesionUsuario.getInstance().iniciarSesion(usuario.getIdUsuario(), usuario.getNombreUsuario(), usuario.getNombre(), usuario.getRol(), usuario.getSaldo());
                FacesContext facesContext = FacesContext.getCurrentInstance();
                NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
                navigationHandler.handleNavigation(facesContext, null, "inicioDepend?faces-redirect=true");
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciales incorrectas", null));
        }
    }

    /**
     * Utilizado para validar un DNI
     * @param dni
     * @return true si dni es válido
     *         false si dni no es válido
     */
    public boolean validarDNI(String dni) {
        // Comprobar que el DNI tenga 9 caracteres
        if (dni.length() != 9) {
            return false;
        }
        // Comprobar que los 8 primeros caracteres sean números
        String numeros = dni.substring(0, 8);
        try {
            Integer.parseInt(numeros);
        } catch (NumberFormatException e) {
            return false;
        }

        // Comprobar que el último caracter sea una letra válida
        char letra = dni.charAt(8);
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int indice = Integer.parseInt(numeros) % 23;
        if (letra != letras.charAt(indice)) {
            return false;
        }

        // Si ha pasado todas las comprobaciones, el DNI es válido
        return true;
    }

    /**
     * Registra la tarjeta introducida a al usuario que tiene la sesión abierta
     * Primero valida los datos introducidos sean correctos
     * Luego, comprueba que no exista una tarjeta ya con el mismo número en la base de datos
     * Finalmente inserta la tarjeta a ese usuario
     *
     */
    public void registrarTarjeta() {
        if (tarjeta.getTarjetasCreditoCol().length() == 16
                && (tarjeta.getTarjetasCreditoCol().charAt(0) == TarjetaController.NUMEROVISA
                || tarjeta.getTarjetasCreditoCol().charAt(0) == TarjetaController.NUMEROMASTERCARD)) {

            //Validar que la tarjeta sea correcta
            String mesFormateado = null;
            try {
                //Validamos que la tarjeta sea correcta
                mesFormateado = TarjetaController.validarYFormatearMesCaducidad(this.tarjeta.getMesCaduca());
                int anyoFormateado = Integer.parseInt(this.tarjeta.getAnyoCaduca());

                //Comprobar la tarjeta. Comprobar de que tipo es. Incluirlo al final
                String tipoTarjeta;
                tipoTarjeta = TarjetaController.getTipoTarjeta(tarjeta);

                //Comprobamos que no exista en la base de datos
                if (tarjetaEjb.getTarjeta(tarjeta.getTarjetasCreditoCol()) != null) {
                    //Si existe, mostramos un mensaje de alerta al usuario
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ya tienes esta tarjeta registrada", null));
                } else {
                    //Si no existe, introducimos la tarjeta al usuario que tenga la sesión abierta
                    usuario = usuarioEjb.find(SesionUsuario.getInstance().getIdUsuario());
                    tarjeta.setUsuario(usuario);
                    usuario.addTarjeta(tarjeta);
                    usuarioEjb.edit(usuario);

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrada la tarjeta de tipo " + tipoTarjeta, null));
                }

            } catch (NumberFormatException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error en el formato de la fecha. Revisela", null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error en el número de la tarjeta. Debe de ser una tarjeta Visa o Mastercard", null));
        }
    }

    /**
     * Utilizado para registrar a un usuario en la base de datos
     * Comprueba que no haya un usuario con el mismo nombre de usuario y que su dni sea válido
     */
    public void guardar() {
        try {
            usuario.setRol(TIPOUSUARIO);
            usuario.setSaldo(BigDecimal.ZERO);

            if (usuarioEjb.nombreUserValido(usuario.getNombreUsuario()) && validarDNI(usuario.getDni())) {
                usuarioEjb.create(usuario);
                FacesContext facesContext = FacesContext.getCurrentInstance();
                NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
                navigationHandler.handleNavigation(facesContext, null, "inicioSesion?faces-redirect=true");
            } else {
                if (!usuarioEjb.nombreUserValido(usuario.getNombreUsuario())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ya existe un usuario con este nombre.", null));
                }
                if (!validarDNI(usuario.getDni())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "DNI no valido.", null));
                }
            }
        } catch (Exception e) {

        }
    }
    
     /**
     * Método para redirigir a la página de recargar saldo
     */
    public void redirigirRecargaSaldoUser() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext, null, "recargasaldo?faces-redirect=true");
    }

    /**
     * Método para redirigir a la pantalla del perfil del usuario
     */
    public void redirigirPerfilUser() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext, null, "perfilUser?faces-redirect=true");
    }

    /**
     * Método para cerrar la sesión y volver al inicio
     */
    public void cerrarSesion() {
        SesionUsuario.getInstance().cerrarSesion();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext, null, "index?faces-redirect=true");
    }
}
