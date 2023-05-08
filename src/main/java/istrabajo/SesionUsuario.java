package istrabajo;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Clase que sigue el patrón Singleton para almacenar los datos de la sesión del usuario que la inicie.
 */
public class SesionUsuario {
        
	private static SesionUsuario instanciaSesion;
	
	private Integer idUsuario;
	private String nombreUsuario;
	private String nombrePersona;
	private String tipoDeUsuario;
	private BigDecimal saldo;
	
	/**
         * Utilizado para almacenar los datos de la sesión del usuario.
         * Utilizado cuando el usuario inicia la sesión
	 */
	public void iniciarSesion(int idUsuario, String nombreUsuario, String nombrePersona, String tipoUsuario, BigDecimal saldo) {
		this.setIdUsuario(idUsuario);
		this.setNombrePersona(nombrePersona);
		this.setNombreUsuario(nombreUsuario);
		this.setTipoDeUsuario(tipoDeUsuario);
		this.setSaldo(saldo);
	}
	
        /**
         * Utilizado para borrar los datos del usuario y la instancia de esta clase.
         * Utilizado cuando el usuario cierra la sesión.
         */
	public void cerrarSesion() {
		this.idUsuario = null;
		this.nombrePersona = null;
		this.nombreUsuario = null;
		this.tipoDeUsuario = null;
                this.saldo = null;
		instanciaSesion = null;
	}
	
	public static SesionUsuario getInstance() {
   	 if (instanciaSesion == null) {
   		instanciaSesion = new SesionUsuario();
   	 }
   	 return instanciaSesion;
   }

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNombrePersona() {
		return nombrePersona;
	}

	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}

	public String getTipoDeUsuario() {
		return tipoDeUsuario;
	}

	public void setTipoDeUsuario(String tipoDeUsuario) {
		this.tipoDeUsuario = tipoDeUsuario;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	
	

	


}