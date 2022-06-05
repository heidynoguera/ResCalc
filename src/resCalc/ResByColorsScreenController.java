package resCalc;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class ResByColorsScreenController implements Initializable {

    @FXML
    private RadioButton fourBandsChoice, fiveBandsChoice, sixBandsChoice;

    @FXML
    private Spinner<String> band1Choice, band2Choice, band3Choice,
            multChoice, toleranceChoice, ppmChoice;

    @FXML
    private Label resTheoValue, toleranceValue, ppmValue, ppmValueLabel;

    @FXML
    private TextField resExpValue;

    @FXML
    private Button detailsBtn, goToInitialScreenBtn, calcResTheoValue;

    private ValidateInputs validateInputs;

    private Calculate calculate;
    private Resistencia Res;
    @FXML
    private Label titleLabel;
    @FXML
    private TilePane tilePane;
    @FXML
    private ToggleGroup Group1;
    @FXML
    private Label colorsLabel;
    @FXML
    private Label band1Label;
    @FXML
    private Label band2Label;
    @FXML
    private Label band3Label;
    @FXML
    private Label multLabel;
    @FXML
    private Label toleranceLabel;
    @FXML
    private Label ppmLabel;
    @FXML
    private Label resTheoValueLabel;
    @FXML
    private Label toleranceValueLabel;
    @FXML
    private Label resExpValueLabel;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        if (fourBandsChoice.isSelected()) {
            SetSpinners setSpinners = new SetSpinners();
            validateInputs = new ValidateInputs();
            calculate = new Calculate();
            setSpinners.setBandsSpinner(band1Choice);
            setSpinners.setBandsSpinner(band2Choice);
            setSpinners.setBandsSpinner(band3Choice);
            setSpinners.setMultSpinner(multChoice);
            setSpinners.setToleranceSpinner(toleranceChoice);
            setSpinners.setPPMSpinner(ppmChoice);
            band3Choice.setDisable(true);
            ppmChoice.setDisable(true);
            ppmValueLabel.setVisible(false);

        }
    }

    @FXML
    public void checkBandsSelected(ActionEvent event) {
        if (fourBandsChoice.isSelected()) {
            setFourBandsCalc();
        } else if (fiveBandsChoice.isSelected()) {
            setFiveBandsCalc();
        } else if (sixBandsChoice.isSelected()) {
            setSixBandsCalc();
        }
    }

    @FXML
    public void calculate(ActionEvent event) {
        if (fourBandsChoice.isSelected()) {
            String band1 = band1Choice.getValue();
            String band2 = band2Choice.getValue();
            String mult = multChoice.getValue();
            String tolerancia = toleranceChoice.getValue();
            if (validateInputs.checkEmptyInputs(band1, band2, mult, tolerancia)) {
                Resistencia resistencia
                        = calculate.calculateFourBandsResistor(band1, band2, mult, tolerancia);
                Res = resistencia;
                resTheoValue.setText(resistencia.getValorResistencia() + " ohms");
                toleranceValue.setText(resistencia.getValorTolerancia() + " %");
            } else {
                Alert a = new Alert(AlertType.NONE);
                a.setAlertType(AlertType.ERROR);
                a.setContentText("Hay datos vacios!");
                a.show();
            }
        } else if (fiveBandsChoice.isSelected()) {
            String band1 = band1Choice.getValue();
            String band2 = band2Choice.getValue();
            String band3 = band2Choice.getValue();
            String mult = multChoice.getValue();
            String tolerancia = toleranceChoice.getValue();
            if (validateInputs.checkEmptyInputs(band1, band2,
                    band3, mult, tolerancia)) {
                Resistencia resistencia = calculate.calculateFiveBandsResistor(
                        band1, band2, band3, mult, tolerancia);
                Res = resistencia;
                resTheoValue.setText(resistencia.getValorResistencia() + " ohms");
                toleranceValue.setText(resistencia.getValorTolerancia() + " %");
            } else {
                Alert a = new Alert(AlertType.NONE);
                a.setAlertType(AlertType.ERROR);
                a.setContentText("Hay datos vacios!");
                a.show();
            }
        } else if (sixBandsChoice.isSelected()) {
            String band1 = band1Choice.getValue();
            String band2 = band2Choice.getValue();
            String band3 = band2Choice.getValue();
            String mult = multChoice.getValue();
            String tolerancia = toleranceChoice.getValue();
            String ppm = ppmChoice.getValue();
            if (validateInputs.checkEmptyInputs(band1, band2,
                    band3, mult, tolerancia, ppm)) {
                Resistencia resistencia
                        = calculate.calculateSixBandsResistor(
                                band1, band2, band3, mult, tolerancia, ppm);
                Res = resistencia;
                resTheoValue.setText(resistencia.getValorResistencia() + " ohms");
                toleranceValue.setText(resistencia.getValorTolerancia() + " %");
                ppmValue.setText(resistencia.getValorPPM());
            } else {
                Alert a = new Alert(AlertType.NONE);
                a.setAlertType(AlertType.ERROR);
                a.setContentText("Hay datos vacios!");
                a.show();
            }
        }
    }

    @FXML
    public void goToInitialScreen(ActionEvent event) {
        SingleStage initialStage = SingleStage.getSingleStage(new Stage());
        initialStage.stage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("InitialScreen.fxml"));
            Scene scene = new Scene(root);
            initialStage.stage.setScene(scene);
            initialStage.stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSixBandsCalc() {
        band3Choice.setDisable(false);
        ppmChoice.setDisable(false);
        ppmValueLabel.setVisible(true);
    }

    private void setFiveBandsCalc() {
        band3Choice.setDisable(false);
        ppmChoice.setDisable(true);
        ppmValueLabel.setVisible(false);
    }

    private void setFourBandsCalc() {
        band3Choice.setDisable(true);
        ppmChoice.setDisable(true);
        ppmValueLabel.setVisible(false);
    }

    private void validateDouble() {
        try {
            Double doubleRes = Double.parseDouble(resExpValue.getText());
        } catch (NumberFormatException excep) {
            Alert a = new Alert(AlertType.NONE);
            a.setAlertType(AlertType.ERROR);
            a.setContentText("Por favor ingrese un Numero");
            a.show();
        }
    }

    @FXML
    private void details(ActionEvent event) {
        SingleStage initialStage = SingleStage.getSingleStage(new Stage());
        initialStage.stage.close();
        try {
            validateDouble();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UsableRes.fxml"));
            loader.load();
            UsableResController usableRes = loader.getController();
            usableRes.RecibirParametros(resExpValue.getText(), Res.getValorResistencia(), Res.getValorTolerancia());
            Scene scene = new Scene(loader.getRoot());
            initialStage.stage.setScene(scene);
            initialStage.stage.show();
        } catch (IOException ex) {
            System.out.println("IO Exception: " + ex.getMessage());
        }
    }
}
//        FXMLLoader carga = new FXMLLoader();
//            carga.setLocation(getClass().getResource("UsableRes.fxml"));
//            Scene scene = new Scene(carga.load());
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.initModality(Modality.APPLICATION_MODAL);
