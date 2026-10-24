package cl.isidora.heroes.controller;

import cl.isidora.heroes.dto.HeroeDto;
import cl.isidora.heroes.service.HeroesServiceArchivo;
import cl.isidora.heroes.service.IHeroesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class HeroesController {
    @FXML private TextField txtId;
    @FXML private TextField txtNombre;

    @FXML private Button btnCrear;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    @FXML private TableView<HeroeDto> tableHeroes;
    @FXML private TableColumn<HeroeDto, Integer> colId;
    @FXML private TableColumn<HeroeDto, String> colNombre;

    private IHeroesService heroesService;
    private ObservableList<HeroeDto> heroes;
    private HeroeDto heroeSeleccionado;

    @FXML
    public void initialize() {
        heroesService = new HeroesServiceArchivo();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        heroes = FXCollections.observableArrayList();
        tableHeroes.setItems(heroes);

        tableHeroes.getSelectionModel().selectedItemProperty()
                .addListener((obs, anterior, nuevo) -> seleccionarHeroe(nuevo));

        refrescarTabla();
        limpiarFormulario();
    }

    @FXML
    public void handleCrear(ActionEvent event) {
        try {
            String textoId = txtId.getText().trim();
            String nombre = txtNombre.getText().trim();

            if (textoId.isEmpty() || nombre.isEmpty()) {
                mostrarError("El ID y el nombre son obligatorios.");
                return;
            }

            Integer id = Integer.parseInt(textoId);

            if (existeId(id)) {
                mostrarError("Ya existe un héroe con el ID " + id + ".");
                return;
            }

            heroesService.crearHeroe(new HeroeDto(id, nombre));
            refrescarTabla();
            limpiarFormulario();
            mostrarInfo("Héroe \"" + nombre + "\" creado.");
        } catch (NumberFormatException e) {
            mostrarError("El ID debe ser un número.");
        }
    }

    @FXML
    public void handleActualizar(ActionEvent event) {
        if (heroeSeleccionado == null) {
            mostrarError("Primero selecciona un héroe de la tabla.");
            return;
        }

        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            mostrarError("El nombre es obligatorio.");
            return;
        }

        HeroeDto editado = new HeroeDto(heroeSeleccionado.getId(), nombre);
        heroesService.actualizarHeroe(editado);
        refrescarTabla();
        limpiarFormulario();
        mostrarInfo("Héroe actualizado.");
    }

    @FXML
    public void handleEliminar(ActionEvent event) {
        if (heroeSeleccionado == null) {
            mostrarError("Primero selecciona un héroe de la tabla.");
            return;
        }

        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
        confirmar.setTitle("Confirmar");
        confirmar.setHeaderText("Eliminar héroe");
        confirmar.setContentText("¿Seguro que quieres eliminar a \"" + heroeSeleccionado.getNombre() + "\"?");

        confirmar.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                heroesService.eliminarHeroe(heroeSeleccionado.getId());
                refrescarTabla();
                limpiarFormulario();
            }
        });
    }

    @FXML
    public void handleLimpiar(ActionEvent event) {
        tableHeroes.getSelectionModel().clearSelection();
        limpiarFormulario();
    }

    private void seleccionarHeroe(HeroeDto heroe) {
        heroeSeleccionado = heroe;
        if (heroe == null) {
            return;
        }
        txtId.setText(String.valueOf(heroe.getId()));
        txtNombre.setText(heroe.getNombre());

        txtId.setDisable(true);
        btnCrear.setDisable(true);
        btnActualizar.setDisable(false);
        btnEliminar.setDisable(false);
    }

    private void limpiarFormulario() {
        heroeSeleccionado = null;
        txtId.clear();
        txtNombre.clear();

        txtId.setDisable(false);
        btnCrear.setDisable(false);
        btnActualizar.setDisable(true);
        btnEliminar.setDisable(true);
    }

    private void refrescarTabla() {
        heroes.setAll(heroesService.obtenerHeroes());
    }

    private boolean existeId(Integer id) {
        for (HeroeDto h : heroesService.obtenerHeroes()) {
            if (h.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Listo");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}