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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
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
		ObservableList<String> list3 = FXCollections.observableArrayList("Corriente", "No corriente", "No aplica");

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
	void seleccionarCompaniaButton(ActionEvent event) {

		if (listaEmpresasComboBox.getValue() == null || listaEmpresasComboBox.getValue().equals("")) {
			infoLabelInicio.setText("Seleccione una empresa para continuar");
		}else {
			bg = new BalanceGeneral(listaEmpresasComboBox.getValue());

			nombreEmpresaLabel.setText(bg.getCompania());

			DateFormat formato = new SimpleDateFormat("dd/MMMM/YYYY");
			String fecha = formato.format(bg.getFecha());
			fechaLabel.setText(fecha);
		}



	}


}














