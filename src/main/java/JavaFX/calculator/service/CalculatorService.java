package JavaFX.calculator.service;

public class CalculatorService {
    private String currentInput = "";
    private String firstOperand = "";
    private String operator = "";
    private boolean startNewInput = true;
    private String errorMessage = "";

    public void processInput(String input) {
        if (errorMessage.isEmpty() && input.matches("[0-9]") || (input.equals(".") && !currentInput.contains("."))) {
            handleDigitOrDecimal(input);
        } else if (input.matches("[+\\-*/]")) {
            handleOperator(input);
        } else if (input.equals("=")) {
            handleEquals();
        } else if (input.equals("C")) {
            clear();
        } else if (input.equals("⌫")) {
            handleBackspace();
        } else if (input.equals("±")) {
            handleSignChange();
        }
    }

    private void handleDigitOrDecimal(String input) {
        if (startNewInput) {
            currentInput = input.equals(".") ? "0." : input;
            startNewInput = false;
        } else {
            // Если текущий ввод "0" и вводится цифра (не точка), заменяем "0" на цифру
            if (currentInput.equals("0") && input.matches("[1-9]")) {
                currentInput = input;
            } else if (input.equals(".")) {
                if (currentInput.contains(".")) {
                    return; // Не допускаем две точки
                }
                currentInput += input;
            } else {
                currentInput += input;
            }
        }
        errorMessage = "";
    }

    private void handleOperator(String newOperator) {
        if (!currentInput.isEmpty() && errorMessage.isEmpty()) {
            if (!firstOperand.isEmpty() && !operator.isEmpty() && !startNewInput) {
                calculate();
            }
            firstOperand = currentInput;
            operator = newOperator;
            startNewInput = true;
        }
    }

    private void handleEquals() {
        if (!firstOperand.isEmpty() && !operator.isEmpty() && !startNewInput && errorMessage.isEmpty()) {
            calculate();
            operator = "";
        }
    }

    private void calculate() {
        try {
            double first = Double.parseDouble(firstOperand);
            double second = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+": result = first + second; break;
                case "-": result = first - second; break;
                case "*": result = first * second; break;
                case "/":
                    if (second == 0) {
                        errorMessage = "Ошибка: деление на 0";
                        return;
                    }
                    result = first / second;
                    break;
            }

            // Форматируем результат чтобы убрать лишние нули
            currentInput = formatResult(result);
            firstOperand = "";
            startNewInput = true;

        } catch (NumberFormatException e) {
            errorMessage = "Ошибка вычисления";
        }
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            return String.format("%.10f", result).replaceAll("0*$", "").replaceAll("\\.$", "");
        }
    }

    private void clear() {
        currentInput = "";
        firstOperand = "";
        operator = "";
        startNewInput = true;
        errorMessage = "";
    }

    private void handleBackspace() {
        if (!currentInput.isEmpty() && !startNewInput) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            if (currentInput.isEmpty()) {
                startNewInput = true;
            }
        }
    }

    private void handleSignChange() {
        if (!currentInput.isEmpty() && !startNewInput && !errorMessage.isEmpty()) {
            if (currentInput.startsWith("-")) {
                currentInput = currentInput.substring(1);
            } else {
                currentInput = "-" + currentInput;
            }
        }
    }

    public String getDisplayValue() {
        if (!errorMessage.isEmpty()) {
            return errorMessage;
        }
        return currentInput.isEmpty() ? "0" : currentInput;
    }
}
