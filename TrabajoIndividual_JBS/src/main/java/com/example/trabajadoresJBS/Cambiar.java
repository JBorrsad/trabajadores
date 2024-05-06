package com.example.trabajadoresJBS;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class Cambiar {
    @FXML
    private TextField modificarID;
    @FXML
    private TextField modificarNombre;
    @FXML
    private TextField modificarPuesto;
    @FXML
    private TextField modificarSalario;
    @FXML
    private Button botonCancelar;
    @FXML
    private Button botonEditar;

    private Trabajador trabajadorSeleccionado;
    private Stage stage;

    public void init(Trabajador trabajador) {
        this.trabajadorSeleccionado = trabajador;
        this.stage = stage;


        modificarNombre.setText(trabajador.getNombre());
        modificarPuesto.setText(trabajador.getPuesto());
        modificarSalario.setText(String.valueOf(trabajador.getSalario()));
    }

    @FXML
    private void cancelar() {
        Stage stage = (Stage) botonCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void guardarCambios() {

        String nuevoNombre = modificarNombre.getText();
        String nuevoPuesto = modificarPuesto.getText();
        int nuevoSalario = Integer.parseInt(modificarSalario.getText());


        trabajadorSeleccionado.setNombre(nuevoNombre);
        trabajadorSeleccionado.setPuesto(nuevoPuesto);
        trabajadorSeleccionado.setSalario(nuevoSalario);


        MYSQL.actualizarTrabajador(trabajadorSeleccionado);


        stage.close();
    }

    @FXML
    private void editar() {

        if (trabajadorSeleccionado != null) {
            String nuevoNombre = modificarNombre.getText();
            String nuevoPuesto = modificarPuesto.getText();
            int nuevoSalario = Integer.parseInt(modificarSalario.getText());
            trabajadorSeleccionado.setNombre(nuevoNombre);
            trabajadorSeleccionado.setPuesto(nuevoPuesto);
            trabajadorSeleccionado.setSalario(nuevoSalario);

            //
            Stage stage = (Stage) botonEditar.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Tienes que seleccionar un trabajador para editar.");
        }
    }

    @FXML
    private void modificarNombre() {
        String nuevoNombre = obtenerNuevoNombre();
        modificarNombre.setText(nuevoNombre);
    }

    private String obtenerNuevoNombre() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Cambiar nombre");
        dialog.setHeaderText("Ingresa nuevo nombre:");
        dialog.setContentText("Nombre:");
        dialog.initOwner(stage);
        dialog.initModality(Modality.WINDOW_MODAL);
        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }

    @FXML
    private void modificarSalario() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Modificar salario");
        dialog.setHeaderText("Nuevo salario:");
        dialog.setContentText("Salario:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nuevoSalario -> modificarSalario.setText(nuevoSalario));
    }

    @FXML
    private void cambiarPuesto() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Puesto actual", "Puesto 1", "Puesto 2", "Puesto 3");
        dialog.setTitle("Modificar puesto");
        dialog.setHeaderText("Ingresa el nuevo puesto:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nuevoPuesto -> modificarPuesto.setText(nuevoPuesto));
    }
}
