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

    public void redirigirRecargaSaldoUser() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //FacesContext context = FacesContext.getCurrentInstance();
        //context.getExternalContext().redirect("recargasaldo.xhtml");
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext, null, "recargasaldo?faces-redirect=true");
    }

    public void redirigirPerfilUser() throws IOException {
        
        //FacesContext context = FacesContext.getCurrentInstance();
        //context.getExternalContext().redirect("recargasaldo.xhtml");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext, null, "perfilUser?faces-redirect=true");
    }

    public void cerrarSesion() {
        SesionUsuario.getInstance().cerrarSesion();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext, null, "index?faces-redirect=true");
    }

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

    @PostConstruct
    public void init() {
        usuario = new Usuario();
        tarjeta = new Tarjeta();
    }

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
}
