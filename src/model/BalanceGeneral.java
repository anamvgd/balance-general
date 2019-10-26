package model;

import java.util.ArrayList;
import java.util.Date;

public class BalanceGeneral {

	private String compania;
	private Date fecha;
	private ArrayList<Cuenta> cuentas;
	private int activos;
	private int pasivos;
	private int patrimonio;

	public BalanceGeneral(String compania) {

		this.compania = compania;
		this.fecha = new Date();
		cuentas = new ArrayList<Cuenta>();
		this.activos = 0;
		this.pasivos = 0;
		this.patrimonio = 0;

	}

	public void sumarActivos() {

		int activo = 0;

		for (int i = 0; i < cuentas.size(); i++) {

			if (cuentas.get(i).getTipo().equalsIgnoreCase("Activo")) {

				if (cuentas.get(i).isContraCuenta()) {
					activo -= cuentas.get(i).getValor();
				}else {
					activo += cuentas.get(i).getValor();
				}

			}

		}

		this.activos = activo;

	}

	public void sumarPasivos() {

		int pasivo = 0;

		for (int i = 0; i < cuentas.size(); i++) {

			if (cuentas.get(i).getTipo().equalsIgnoreCase("Pasivo")) {

				if (cuentas.get(i).isContraCuenta()) {
					pasivo -= cuentas.get(i).getValor();
				}else {
					pasivo += cuentas.get(i).getValor();
				}

			}

		}

		this.pasivos = pasivo;

	}

	public void sumarPatrimonio() {

		int patrimonio1 = 0;

		for (int i = 0; i < cuentas.size(); i++) {

			if (cuentas.get(i).getTipo().equalsIgnoreCase("Patrimonio")) {

				if (cuentas.get(i).isContraCuenta()) {
					patrimonio1 -= cuentas.get(i).getValor();
				}else {
					patrimonio1 += cuentas.get(i).getValor();
				}

			}

		}

		this.patrimonio = patrimonio1;

	}

	public String getCompania() {
		return compania;
	}

	public void setCompania(String compania) {
		this.compania = compania;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public ArrayList<Cuenta> getCuentas() {
		return cuentas;
	}

	public int getActivos() {
		return activos;
	}

	public int getPasivos() {
		return pasivos;
	}

	public int getPatrimonio() {
		return patrimonio;
	}


}
