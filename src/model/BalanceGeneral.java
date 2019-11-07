package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class BalanceGeneral {

	private String compania;
	private Date fecha;
	private ArrayList<Cuenta> activos;
	private ArrayList<Cuenta> pasivos;
	private ArrayList<Cuenta> patrimonio;
	private ArrayList<Cuenta> ingresos;
	private ArrayList<Cuenta> gastos;

	public BalanceGeneral(String compania) {

		this.compania = compania;
		this.fecha = new Date();
		this.activos = new ArrayList<Cuenta>();
		this.pasivos = new ArrayList<Cuenta>();
		this.patrimonio = new ArrayList<Cuenta>();
		this.ingresos = new ArrayList<Cuenta>();
		this.gastos = new ArrayList<Cuenta>();

	}

	public int sumarActivosCorrientes() {

		int activo = 0;

		for (int i = 0; i < activos.size(); i++) {

			if (activos.get(i).getTipo().equalsIgnoreCase("Activo Corriente")) {

				activo += activos.get(i).getValor();

			}

		}

		return activo;

	}

	public int sumarActivosNoCorrientes() {

		int activo = 0;

		for (int i = 0; i < activos.size(); i++) {

			if (activos.get(i).getTipo().equalsIgnoreCase("Activo No Corriente")) {

				activo += activos.get(i).getValor();			

			}


		}

		return activo;

	}

	public int totalActivos() {

		return sumarActivosCorrientes() + sumarActivosNoCorrientes();

	}

	public int sumarPasivosCorrientes() {

		int pasivo = 0;

		for (int i = 0; i < pasivos.size(); i++) {

			if (pasivos.get(i).getTipo().equalsIgnoreCase("Pasivo Corriente")) {
				if (pasivos.get(i).isContraCuenta()) {
					pasivo -= pasivos.get(i).getValor();
				}else {
					pasivo += pasivos.get(i).getValor();
				}
			}



		}

		return pasivo;

	}

	public int sumarPasivosNoCorrientes() {

		int pasivo = 0;

		for (int i = 0; i < pasivos.size(); i++) {

			if (pasivos.get(i).getTipo().equalsIgnoreCase("Pasivo No Corriente")) {
				if (pasivos.get(i).isContraCuenta()) {
					pasivo -= pasivos.get(i).getValor();
				}else {
					pasivo += pasivos.get(i).getValor();
				}
			}



		}

		return pasivo;

	}

	public int totalPasivos() {

		return sumarPasivosCorrientes() + sumarPasivosNoCorrientes();

	}

	public int sumarPatrimonio() {

		int patrimonio1 = 0;

		for (int i = 0; i < patrimonio.size(); i++) {

			if (patrimonio.get(i).isContraCuenta()) {
				patrimonio1 -= patrimonio.get(i).getValor();
			}else {
				patrimonio1 += patrimonio.get(i).getValor();
			}

		}

		return patrimonio1;

	}

	public int sumarIngresosOperativos() {

		int ingreso = 0;

		for (int i = 0; i < ingresos.size(); i++) {

			if (ingresos.get(i).getTipo().equalsIgnoreCase("Ingreso Operativo")) {

				ingreso += ingresos.get(i).getValor();

			}

		}

		return ingreso;

	}

	public int sumarIngresosNoOperativos() {

		int ingreso = 0;

		for (int i = 0; i < ingresos.size(); i++) {

			if (ingresos.get(i).getTipo().equalsIgnoreCase("Ingreso No Operativo")) {

				ingreso += ingresos.get(i).getValor();

			}

		}

		return ingreso;

	}

	public int totalIngresos() {
		return sumarIngresosOperativos() + sumarIngresosNoOperativos();
	}

	public int sumarGastosOperativos() {

		int gasto = 0;

		for (int i = 0; i < gastos.size(); i++) {

			if (gastos.get(i).getTipo().equalsIgnoreCase("Gasto Operativo")) {

				gasto += gastos.get(i).getValor();

			}

		}

		return gasto;

	}

	public int sumarGastosNoOperativos() {

		int gasto = 0;

		for (int i = 0; i < gastos.size(); i++) {

			if (gastos.get(i).getTipo().equalsIgnoreCase("Gasto No Operativo")) {

				gasto += gastos.get(i).getValor();

			}

		}

		return gasto;

	}

	public int totalGastos() {

		return sumarGastosOperativos() + sumarGastosNoOperativos();

	}

	public boolean cuentaExistente(String nombre, String tipo) {

		boolean existe = false;
		
		if (tipo.equalsIgnoreCase("Activo Corriente") || tipo.equalsIgnoreCase("Activo No corriente")) {
			
			for (int i = 0; i < activos.size() && !existe; i++) {

				if (activos.get(i).getNombre().equalsIgnoreCase(nombre)) {
					existe = true;
				}

			}
			
		}else if (tipo.equalsIgnoreCase("Pasivo Corriente") || tipo.equalsIgnoreCase("Pasivo No corriente")) {
			
			for (int i = 0; i < pasivos.size() && !existe; i++) {

				if (pasivos.get(i).getNombre().equalsIgnoreCase(nombre)) {
					existe = true;
				}

			}

		}else if (tipo.equalsIgnoreCase("Ingreso Operativo") || tipo.equalsIgnoreCase("Ingreso No Operativo")) {
			
			for (int i = 0; i < ingresos.size() && !existe; i++) {

				if (ingresos.get(i).getNombre().equalsIgnoreCase(nombre)) {
					existe = true;
				}

			}
			
		}else if (tipo.equalsIgnoreCase("Gasto Operativo") || tipo.equalsIgnoreCase("Gasto No Operativo")) {
			
			for (int i = 0; i < gastos.size() && !existe; i++) {

				if (gastos.get(i).getNombre().equalsIgnoreCase(nombre)) {
					existe = true;
				}

			}
			
		}else {
			
			for (int i = 0; i < patrimonio.size() && !existe; i++) {

				if (patrimonio.get(i).getNombre().equalsIgnoreCase(nombre)) {
					existe = true;
				}

			}
			
		}

		return existe;

	}

	public String getCompania() {
		return compania;
	}

	public Date getFecha() {
		return fecha;
	}

	public ArrayList<Cuenta> getActivos() {
		return activos;
	}

	public ArrayList<Cuenta> getPasivos() {
		return pasivos;
	}

	public ArrayList<Cuenta> getPatrimonio() {
		return patrimonio;
	}

	public ArrayList<Cuenta> getIngresos(){
		return ingresos;
	}

	public ArrayList<Cuenta> getGastos(){
		return gastos;
	}

	public void escribirInfo() throws IOException {

		FileWriter fileWriter = new FileWriter("Data/" + compania + ".txt", false);
		BufferedWriter bw = new BufferedWriter(fileWriter);
		PrintWriter out = new PrintWriter(bw);

		if (activos.size() != 0) {

			for (int i = 0; i < activos.size(); i++) {
				out.print(activos.get(i).getNombre() + "," + activos.get(i).getValor() + "," + activos.get(i).getTipo() + ";");
			}

			out.println();

		}

		if (pasivos.size() != 0) {

			for (int i = 0; i < pasivos.size(); i++) {
				out.print(pasivos.get(i).getNombre() + "," + pasivos.get(i).getValor() + "," + pasivos.get(i).getTipo() + ";");
			}

			out.println();

		}

		if (patrimonio.size() != 0) {

			for (int i = 0; i < patrimonio.size(); i++) {
				out.print(patrimonio.get(i).getNombre() + "," + patrimonio.get(i).getValor() + "," + patrimonio.get(i).getTipo() + ";");
			}

			out.println();

		}

		if (ingresos.size() != 0) {

			for (int i = 0; i < ingresos.size(); i++) {
				out.print(ingresos.get(i).getNombre() + "," + ingresos.get(i).getValor() + "," + ingresos.get(i).getTipo() + ";");
			}

			out.println();

		}

		if (gastos.size() != 0) {

			for (int i = 0; i < gastos.size(); i++) {
				out.print(gastos.get(i).getNombre() + "," + gastos.get(i).getValor() + "," + gastos.get(i).getTipo() + ";");
			}


		}		

		out.close();
	}

	public void leerInfo() throws IOException {

		FileReader fr = new FileReader(new File("Data/" + compania + ".txt"));
		BufferedReader br = new BufferedReader(fr);

		String line = br.readLine();

		if (line != null) {

			String[] parts = line.split(";");
			StringTokenizer st;

			for (int i = 0; i < parts.length; i++) {

				st = new StringTokenizer(parts[i], ",");

				String nombreCuenta = st.nextToken();
				int valorCuenta = Integer.valueOf(st.nextToken());
				String tipo = st.nextToken();
				boolean contra = false;
				if (valorCuenta < 0) {
					contra = true;
				}

				Cuenta nuevaCuenta = new Cuenta(nombreCuenta, tipo, valorCuenta, contra);

				activos.add(nuevaCuenta);

			}

		}

		line = br.readLine();


		if (line != null) {

			String[] parts = line.split(";");
			StringTokenizer st;

			for (int i = 0; i < parts.length; i++) {

				st = new StringTokenizer(parts[i], ",");

				String nombreCuenta = st.nextToken();
				int valorCuenta = Integer.valueOf(st.nextToken());
				String tipo = st.nextToken();
				boolean contra = false;
				if (valorCuenta < 0) {
					contra = true;
				}

				Cuenta nuevaCuenta = new Cuenta(nombreCuenta, tipo, valorCuenta, contra);

				pasivos.add(nuevaCuenta);

			}
		}



		line = br.readLine();

		if (line != null) {

			String[] parts = line.split(";");
			StringTokenizer st;

			for (int i = 0; i < parts.length; i++) {

				st = new StringTokenizer(parts[i], ",");

				String nombreCuenta = st.nextToken();
				int valorCuenta = Integer.valueOf(st.nextToken());
				String tipo = st.nextToken();
				boolean contra = false;
				if (valorCuenta < 0) {
					contra = true;
				}

				Cuenta nuevaCuenta = new Cuenta(nombreCuenta, tipo, valorCuenta, contra);

				patrimonio.add(nuevaCuenta);

			}

		}

		line = br.readLine();

		if (line != null) {

			String[] parts = line.split(";");
			StringTokenizer st;

			for (int i = 0; i < parts.length; i++) {

				st = new StringTokenizer(parts[i], ",");

				String nombreCuenta = st.nextToken();
				int valorCuenta = Integer.valueOf(st.nextToken());
				String tipo = st.nextToken();
				boolean contra = false;
				if (valorCuenta < 0) {
					contra = true;
				}

				Cuenta nuevaCuenta = new Cuenta(nombreCuenta, tipo, valorCuenta, contra);

				ingresos.add(nuevaCuenta);

			}

		}

		line = br.readLine();

		if (line != null) {

			String[] parts = line.split(";");
			StringTokenizer st;

			for (int i = 0; i < parts.length; i++) {

				st = new StringTokenizer(parts[i], ",");

				String nombreCuenta = st.nextToken();
				int valorCuenta = Integer.valueOf(st.nextToken());
				String tipo = st.nextToken();
				boolean contra = false;
				if (valorCuenta < 0) {
					contra = true;
				}

				Cuenta nuevaCuenta = new Cuenta(nombreCuenta, tipo, valorCuenta, contra);

				gastos.add(nuevaCuenta);

			}

		}

		br.close();
		fr.close();

	}

}



















