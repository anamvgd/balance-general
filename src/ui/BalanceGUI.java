package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import model.Cuenta;

public class BalanceGUI {

	@FXML
	private ScrollPane scrollPane;
	
	@FXML
    private TextField nuevaCompania;
	
	@FXML
	private TextField nombreCuenta;

	@FXML
	private MenuButton tipoCuenta;

	@FXML
	private TextField valorCuenta;

	@FXML
	private CheckBox contraCuenta;

	@FXML
	private Label infoLabel;

	@FXML
	void agregarCuenta(ActionEvent event) {

		String nombre = nombreCuenta.getText();
		String tipo = tipoCuenta.getText();
		int valor = Integer.valueOf(valorCuenta.getText());
		boolean contra = contraCuenta.isSelected();

		Cuenta nuevaCuenta = new Cuenta(nombre, tipo, valor, contra);

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
    void agregarCompania(ActionEvent event) {

    }
	
	@FXML
    void seleccionarCompania(ActionEvent event) {

    }

}














