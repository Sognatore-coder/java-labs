package JavaFX.calculator.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import JavaFX.calculator.service.CalculatorService;

public class CalculatorController {

    @FXML private TextField displayField;

    private CalculatorService calculatorService;

    @FXML
    public void initialize() {
        calculatorService = new CalculatorService();
        updateDisplay();
        displayField.requestFocus();

        // Блокируем прямой ввод в TextField
        displayField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(calculatorService.getDisplayValue())) {
                displayField.setText(calculatorService.getDisplayValue());
            }
        });
    }

    @FXML
    private void handleButtonAction(javafx.event.ActionEvent event) {
        Button source = (Button) event.getSource();
        String buttonText = source.getText();
        calculatorService.processInput(buttonText);
        updateDisplay();
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        String keyText = event.getText();
        String code = event.getCode().toString();

        if (isValidInput(keyText, code)) {
            String operation = convertKeyToOperation(keyText, code);
            calculatorService.processInput(operation);
            updateDisplay();
        }

        // Всегда блокируем прямой ввод в TextField
        event.consume();
    }

    private boolean isValidInput(String keyText, String code) {
        return keyText.matches("[0-9+\\-*/.=]") ||
                code.matches("ENTER|BACK_SPACE|DELETE|ESCAPE|DECIMAL|ADD|SUBTRACT|MULTIPLY|DIVIDE");
    }

    private String convertKeyToOperation(String keyText, String code) {
        switch (code) {
            case "ENTER": return "=";
            case "BACK_SPACE": return "⌫";
            case "DELETE": case "ESCAPE": return "C";
            case "DECIMAL": return ".";
            case "ADD": return "+";
            case "SUBTRACT": return "-";
            case "MULTIPLY": return "*";
            case "DIVIDE": return "/";
            default: return keyText;
        }
    }

    private void updateDisplay() {
        String displayValue = calculatorService.getDisplayValue();
        displayField.setText(displayValue);
        displayField.positionCaret(displayValue.length());
    }
}
