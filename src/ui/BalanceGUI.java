package ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	private Label infoLabel;
	
	@FXML
    private Label nombreEmpresaLabel;

    @FXML
    private Label fechaLabel;
	
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
	void registrarCuentaButton(ActionEvent event) {

		String nombre = nombreCuentaTextField.getText();
		String tipo = tipoCuentaComboBox.getValue();
		int valor = Integer.valueOf(valorCuentaTextField.getText());
		String clasificacion = clasificacionComboBox.getValue();
		boolean contra = contraCuentaCheckBox.isSelected();

		Cuenta nuevaCuenta = new Cuenta(nombre, tipo, valor, clasificacion, contra);

		switch (tipo) {

		case "Activo":



			break;

		case "Pasivo":
			
			
			
			break;

		default:

			break;
		}

	}

	@FXML
	void agregarCompaniaButton(ActionEvent event) throws IOException {

		String nuevaCompania = nuevaCompaniaTextField.getText();

		FileWriter fw = new FileWriter(PATH_EMPRESAS, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter out = new PrintWriter(bw);
		out.println();
		out.print(nuevaCompania);

		out.close();
		
		initialize();
		
		nuevaCompaniaTextField.setText("");

	}

	@FXML
	void seleccionarCompaniaButton(ActionEvent event) {
		
		bg = new BalanceGeneral(listaEmpresasComboBox.getValue());
		
		nombreEmpresaLabel.setText(bg.getCompania());

		DateFormat formato = new SimpleDateFormat("dd/MMMM/YYYY");
		String fecha = formato.format(bg.getFecha());
		fechaLabel.setText(fecha);
		
		
	}
	

}














