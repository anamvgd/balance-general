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

	public BalanceGeneral(String compania) {

		this.compania = compania;
		this.fecha = new Date();
		this.activos = new ArrayList<Cuenta>();
		this.pasivos = new ArrayList<Cuenta>();
		this.patrimonio = new ArrayList<Cuenta>();

	}

	public int sumarActivosCorrientes() {

		int activo = 0;

		for (int i = 0; i < activos.size(); i++) {

			if (activos.get(i).getClasificacion().equalsIgnoreCase("Corriente")) {
				
					activo += activos.get(i).getValor();
				

			}


		}

		return activo;

	}

	public int sumarActivosNoCorrientes() {

		int activo = 0;

		for (int i = 0; i < activos.size(); i++) {

			if (activos.get(i).getClasificacion().equalsIgnoreCase("No_Corriente")) {

				
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

			if (pasivos.get(i).getClasificacion().equalsIgnoreCase("Corriente")) {
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

			if (pasivos.get(i).getClasificacion().equalsIgnoreCase("No_Corriente")) {
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

	public boolean cuentaExistente(String nombre, String tipo) {

		boolean existe = false;

		switch (tipo) {

		case "Activo":

			for (int i = 0; i < activos.size() && !existe; i++) {

				if (activos.get(i).getNombre().equalsIgnoreCase(nombre)) {
					existe = true;
				}

			}

			break;

		case "Pasivo":

			for (int i = 0; i < pasivos.size() && !existe; i++) {

				if (pasivos.get(i).getNombre().equalsIgnoreCase(nombre)) {
					existe = true;
				}

			}

			break;

		default:

			for (int i = 0; i < patrimonio.size() && !existe; i++) {

				if (patrimonio.get(i).getNombre().equalsIgnoreCase(nombre)) {
					existe = true;
				}

			}

			break;
		}

		return existe;

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

	public ArrayList<Cuenta> getActivos() {
		return activos;
	}

	public ArrayList<Cuenta> getPasivos() {
		return pasivos;
	}

	public ArrayList<Cuenta> getPatrimonio() {
		return patrimonio;
	}

	public void escribirInfo() throws IOException {

		FileWriter fileWriter = new FileWriter("Data/" + compania + ".txt", false);
		BufferedWriter bw = new BufferedWriter(fileWriter);
		PrintWriter out = new PrintWriter(bw);

		for (int i = 0; i < activos.size(); i++) {
			out.print(activos.get(i).getNombre() + " " + activos.get(i).getValor() + " " + activos.get(i).getClasificacion() + ",");
		}

		out.println();

		for (int i = 0; i < pasivos.size(); i++) {
			out.print(pasivos.get(i).getNombre() + " " + pasivos.get(i).getValor() + " " + pasivos.get(i).getClasificacion() + ",");
		}

		out.println();

		for (int i = 0; i < patrimonio.size(); i++) {
			out.print(patrimonio.get(i).getNombre() + " " + patrimonio.get(i).getValor() + " " + "No_Aplica" + ",");
		}

		out.println();

		out.close();
	}

	public void leerInfo() throws IOException {

		FileReader fr = new FileReader(new File("Data/" + compania + ".txt"));
		BufferedReader br = new BufferedReader(fr);

		String line = br.readLine();

		if (line != null) {

			String[] parts = line.split(",");
			StringTokenizer st;

			for (int i = 0; i < parts.length; i++) {

				st = new StringTokenizer(parts[i]);

				String nombreCuenta = st.nextToken();
				int valorCuenta = Integer.valueOf(st.nextToken());
				String clasificacionCuenta = st.nextToken();
				boolean contra = false;
				if (valorCuenta < 0) {
					contra = true;
				}

				Cuenta nuevaCuenta = new Cuenta(nombreCuenta, "Activo", valorCuenta, clasificacionCuenta, contra);

				activos.add(nuevaCuenta);

			}

		}

		line = br.readLine();
		

		if (line != null) {
			
			String[] parts = line.split(",");
			StringTokenizer st;
			
			for (int i = 0; i < parts.length; i++) {

				st = new StringTokenizer(parts[i]);

				String nombreCuenta = st.nextToken();
				int valorCuenta = Integer.valueOf(st.nextToken());
				String clasificacionCuenta = st.nextToken();
				boolean contra = false;
				if (valorCuenta < 0) {
					contra = true;
				}

				Cuenta nuevaCuenta = new Cuenta(nombreCuenta, "Activo", valorCuenta, clasificacionCuenta, contra);

				pasivos.add(nuevaCuenta);

			}
		}



		line = br.readLine();

		if (line != null) {

			String[] parts = line.split(",");
			StringTokenizer st;
			
			for (int i = 0; i < parts.length; i++) {

				st = new StringTokenizer(parts[i]);

				String nombreCuenta = st.nextToken();
				int valorCuenta = Integer.valueOf(st.nextToken());
				String clasificacionCuenta = st.nextToken();
				boolean contra = false;
				if (valorCuenta < 0) {
					contra = true;
				}

				Cuenta nuevaCuenta = new Cuenta(nombreCuenta, "Activo", valorCuenta, clasificacionCuenta, contra);

				patrimonio.add(nuevaCuenta);

			}

		}

		br.close();
		fr.close();

	}

}



















