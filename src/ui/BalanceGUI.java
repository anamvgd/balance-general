package ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.EventListener;
import java.util.logging.SimpleFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.BalanceGeneral;
import model.Cuenta;

public class BalanceGUI {

	public final static String PATH_EMPRESAS = "Data/empresas.txt";

	@FXML
	private TextField nuevaCompaniaTextField;

	@FXML
	private ComboBox<String> listaEmpresasComboBox;

	@FXML
	private TextField nombreCuentaTextField;

	@FXML
	private ComboBox<String> tipoCuentaComboBox;

	@FXML
	private ComboBox<String> clasificacionComboBox;

	@FXML
	private TextField valorCuentaTextField;

	@FXML
	private CheckBox contraCuentaCheckBox;

	@FXML
	private Label infoLabelRegistro;

	@FXML
	private Label nombreEmpresaLabel;

	@FXML
	private Label fechaLabel;

	@FXML
	private Label infoLabelInicio;

	@FXML
	private Label totalActivosLabel;

	@FXML
	private Label totalPasivosPatrimonioLabel;

	@FXML
	private ScrollPane activosPane;

	@FXML
	private ScrollPane pasivosPane;

	@FXML
	private ScrollPane patrimonioPane;

	private BalanceGeneral bg;

	public void initialize() throws IOException {

		FileReader fr = new FileReader(new File(PATH_EMPRESAS));
		BufferedReader br = new BufferedReader(fr);

		String line = br.readLine();

		ObservableList<String> list = FXCollections.observableArrayList();

		while(line != null){

			list.add(line);
			line = br.readLine();

		}

		listaEmpresasComboBox.setItems(list);

		br.close();
		fr.close();

		ObservableList<String> list2 = FXCollections.observableArrayList("Activo", "Pasivo", "Patrimonio");
		ObservableList<String> list3 = FXCollections.observableArrayList("Corriente", "No_corriente", "No_aplica");

		tipoCuentaComboBox.setItems(list2);
		clasificacionComboBox.setItems(list3);



	}

	@FXML
	void registrarCuentaButton(ActionEvent event) throws IOException {


		if (bg != null) {

			String nombre = nombreCuentaTextField.getText();
			String tipo = tipoCuentaComboBox.getValue();
			String clasificacion;

			if (tipo.equalsIgnoreCase("Patrimonio")) {
				clasificacion = "No aplica";
			}else {
				clasificacion = clasificacionComboBox.getValue();
			}

			int valor = Integer.valueOf(valorCuentaTextField.getText());
			boolean contra = contraCuentaCheckBox.isSelected();

			if (contra) {
				valor = Math.negateExact(valor);
			}


			if (!bg.cuentaExistente(nombre, tipo)) {

				Cuenta nuevaCuenta = new Cuenta(nombre, tipo, valor, clasificacion, contra);

				switch (tipo) {

				case "Activo":

					bg.getActivos().add(nuevaCuenta);
					bg.escribirInfo();

					break;

				case "Pasivo":

					bg.getPasivos().add(nuevaCuenta);
					bg.escribirInfo();

					break;

				default:

					bg.getPatrimonio().add(nuevaCuenta);
					bg.escribirInfo();

					break;
				}

			}else {

				boolean added = false;

				if (tipo.equalsIgnoreCase("Activo")) {

					for (int i = 0; i < bg.getActivos().size() && !added; i++) {
						if (bg.getActivos().get(i).getNombre().equalsIgnoreCase(nombre)) {
							bg.getActivos().get(i).aumentarValor(valor);
							added = true;
						}

					}


					bg.escribirInfo();

				}else if (tipo.equalsIgnoreCase("Pasivo")) {

					for (int i = 0; i < bg.getPasivos().size() && !added; i++) {
						if (bg.getPasivos().get(i).getNombre().equalsIgnoreCase(nombre)) {
							bg.getPasivos().get(i).aumentarValor(valor);
							added = true;
						}
					}

					bg.escribirInfo();

				}else {

					for (int i = 0; i < bg.getPatrimonio().size() && !added; i++) {
						if (bg.getPatrimonio().get(i).getNombre().equalsIgnoreCase(nombre)) {
							bg.getPatrimonio().get(i).aumentarValor(valor);
							added = true;
						}
					}

					bg.escribirInfo();

				}

			}

		}else {
			infoLabelRegistro.setText("Primero debe seleccionar una empresa");
		}


	}

	@FXML
	void agregarCompaniaButton(ActionEvent event) throws IOException {

		String nuevaCompania = nuevaCompaniaTextField.getText();

		String ruta = "Data/" + nuevaCompania + ".txt";
		File nuevoArchivo = new File(ruta);

		if (!nuevoArchivo.exists()) {
			nuevoArchivo.createNewFile();

			FileWriter fw = new FileWriter(PATH_EMPRESAS, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			out.println();
			out.print(nuevaCompania);

			out.close();

			initialize();

			nuevaCompaniaTextField.setText("");
		}else {
			infoLabelInicio.setText("Empresa existente");
		}



	}

	@FXML
	void seleccionarCompaniaButton(ActionEvent event) throws IOException {

		if (listaEmpresasComboBox.getValue() == null || listaEmpresasComboBox.getValue().equals("")) {
			infoLabelInicio.setText("Seleccione una empresa para continuar");
		}else {
			bg = new BalanceGeneral(listaEmpresasComboBox.getValue());

			nombreEmpresaLabel.setText(bg.getCompania());

			DateFormat formato = new SimpleDateFormat("dd/MMMM/YYYY");
			String fecha = formato.format(bg.getFecha());
			fechaLabel.setText(fecha);

			bg.leerInfo();

			String act = String.valueOf(bg.totalActivos());
			System.out.println();
			String pas = String.valueOf(bg.totalPasivos()+bg.sumarPatrimonio());

			generarBalanceActivos();
			generarBalancePasivos();
			generarBalancePatrimonio();

			totalActivosLabel.setText(act);
			totalPasivosPatrimonioLabel.setText(pas);
		}



	}

	public void generarBalanceActivos() {

		GridPane gp1 = new GridPane();	
		activosPane.setContent(gp1);;

		int i = 0;
		Label l1 = new Label();
		l1.setText("ACTIVOS CORRIENTES");
		gp1.add(l1, 0, i);



		for (int j = 0; j < bg.getActivos().size(); j++) {


			if (bg.getActivos().get(j).getClasificacion().equalsIgnoreCase("Corriente")) {

				Label label = new Label();
				label.setText("\t\t");
				Label label1 = new Label();
				Label label2 = new Label();

				label1.setText(bg.getActivos().get(j).getNombre());
				String val = String.valueOf(bg.getActivos().get(j).getValor());
				label2.setText(val);

				gp1.add(label1, 0, i+1);
				gp1.add(label, 1, i+1);
				gp1.add(label2, 2, i+1);

				i++;

			}

		}

		gp1.add(new Label(String.valueOf(bg.sumarActivosCorrientes())), 2, i+1);

		i+=2;
		l1 = new Label();
		l1.setText("ACTIVOS NO CORRIENTES");
		gp1.add(l1, 0, i);
		i++;


		for (int j = 0; j < bg.getActivos().size(); j++) {


			if (bg.getActivos().get(j).getClasificacion().equalsIgnoreCase("No_corriente")) {

				Label label = new Label();
				label.setText("\t\t\t\t\t\t");
				Label label1 = new Label();
				Label label2 = new Label();

				label1.setText(bg.getActivos().get(j).getNombre());
				String val = String.valueOf(bg.getActivos().get(j).getValor());
				label2.setText(val);

				gp1.add(label1, 0, i+1);
				gp1.add(label, 1, i+1);
				gp1.add(label2, 2, i+1);

				i++;

			}

		}

		gp1.add(new Label(String.valueOf(bg.sumarActivosNoCorrientes())), 2, i+1);

	}

	public void generarBalancePasivos() {

		
		GridPane gp2 = new GridPane();
		pasivosPane.setContent(gp2);;
		
		int i = 0;
		Label l1 = new Label();
		l1.setText("PASIVOS CORRIENTES");
		gp2.add(l1, 0, i);



		for (int j = 0; j < bg.getPasivos().size(); j++) {


			if (bg.getPasivos().get(j).getClasificacion().equalsIgnoreCase("Corriente")) {

				Label label = new Label();
				label.setText("\t\t");
				Label label1 = new Label();
				Label label2 = new Label();
				
				
				
				label1.setText(bg.getPasivos().get(j).getNombre());
				String val = String.valueOf(bg.getPasivos().get(j).getValor());
				label2.setText(val);

				gp2.add(label1, 0, i+1);
				gp2.add(label, 1, i+1);
				gp2.add(label2, 2, i+1);

				i++;

			}

		}

		gp2.add(new Label(String.valueOf(bg.sumarPasivosCorrientes())), 2, i+1);

		i+=2;
		l1 = new Label();
		l1.setText("PASIVOS NO CORRIENTES");
		gp2.add(l1, 0, i);
		i++;


		for (int j = 0; j < bg.getPasivos().size(); j++) {


			if (bg.getPasivos().get(j).getClasificacion().equalsIgnoreCase("No_corriente")) {

				Label label = new Label();
				label.setText("\t\t\t\t\t\t");
				Label label1 = new Label();
				Label label2 = new Label();

				label1.setText(bg.getPasivos().get(j).getNombre());
				String val = String.valueOf(bg.getPasivos().get(j).getValor());
				label2.setText(val);

				gp2.add(label1, 0, i+1);
				gp2.add(label, 1, i+1);
				gp2.add(label2, 2, i+1);

				i++;

			}

		}

		gp2.add(new Label(String.valueOf(bg.sumarPasivosNoCorrientes())), 2, i+1);

	}

	public void generarBalancePatrimonio() {

		GridPane gp3 = new GridPane();
		
		patrimonioPane.setContent(gp3);
		int i = 0;

		for (int j = 0; j < bg.getPatrimonio().size(); j++) {

			Label label = new Label();
			label.setText("\t\t\t\t\t\t\t");
			Label label1 = new Label();
			Label label2 = new Label();

			label1.setText(bg.getActivos().get(j).getNombre());
			String val = String.valueOf(bg.getActivos().get(j).getValor());
			label2.setText(val);

			gp3.add(label1, 0, j);
			gp3.add(label, 1, j);
			gp3.add(label2, 2, j);

			i = j;
		}

		gp3.add(new Label(String.valueOf(bg.sumarPatrimonio())), 2, i+1);


	}


}














