package model;

public class Cuenta {
	
	private String nombre;
	private String tipo;
	private int valor;
	private String clasificacion;
	private boolean contraCuenta;
	
	
	public Cuenta(String nombre, String tipo, int valor, String clasificacion, boolean contraCuenta) {
		
		this.nombre = nombre;
		this.tipo = tipo;
		this.valor = valor;
		this.contraCuenta = contraCuenta;
		this.clasificacion = clasificacion;
		
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public boolean isContraCuenta() {
		return contraCuenta;
	}

	public void setContraCuenta(boolean contraCuenta) {
		this.contraCuenta = contraCuenta;
	}
	
	public String getClasificacion() {
		
		return clasificacion;
		
	}
	
}
