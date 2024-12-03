package CalculatorUnitTests;

import edu.TestingQA.Calculator.CalculatorController;
import edu.TestingQA.Calculator.CalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;


import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceControllerTests {

    private final CalculatorService calculatorService = new CalculatorService();
    private final CalculatorController calculatorController = new CalculatorController(calculatorService);

    public CalculatorServiceControllerTests() {
        // Enable testing mode
        calculatorController.enableTestingMode();
    }

    // Test for computeMean
    @Test
    void computeMean_ValidInput_ReturnsCorrectMean() {
        // Arrange
        String input = "1\n2\n3\n4\n5";

        // Act
        double result = calculatorService.computeMean(input);

        // Assert
        assertEquals(3.0, result, 0.01);
    }

    @Test
    void computeMean_SingleValue_ReturnsThatValue() {
        // Arrange
        String input = "42";

        // Act
        double result = calculatorService.computeMean(input);

        // Assert
        assertEquals(42.0, result, 0.01);
    }

    @Test
    void computeMean_EmptyInput_ThrowsException() {
        // Arrange
        String input = "";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.computeMean(input);
        });

        // Assert the exception message
        assertEquals("Input is empty. Please enter one value per line.", exception.getMessage());
    }

    @Test
    void computeMean_InvalidFormat_ThrowsException() {
        // Arrange
        String input = "1 2 3";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculatorService.computeMean(input));
        assertEquals("Values must be separated by newlines, not spaces.", exception.getMessage());
    }

    // Test for computeSampleStandardDeviation
    @Test
    void computeSampleStandardDeviation_ValidInput_ReturnsCorrectStdDev() {
        // Arrange
        String input = "1\n2\n3\n4\n5";

        // Act
        double result = calculatorService.computeSampleStandardDeviation(input);

        // Assert
        assertEquals(1.5811, result, 0.03); // Sample Std Dev of {1,2,3,4,5}
    }

    @Test
    void calculate_SampleStdDev_ValidInput_ReturnsCorrectResult() {
        // Arrange
        String values = "1\n2\n3\n4\n5"; // Valid input for sample standard deviation
        String operation = "sampleStdDev"; // Operation for sample standard deviation
        Model model = new ConcurrentModel();

        // Act
        String resultView = calculatorController.calculate(values, operation, null, model);

        // Assert
        assertEquals("index", resultView); // Ensure it returns to the index page
        assertEquals(1.58, (double) model.getAttribute("result"), 0.03); // Check the result matches expected sample standard deviation with delta
    }

    @Test
    void computeSampleStandardDeviation_SingleValue_ThrowsException() {
        // Arrange
        String input = "1";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculatorService.computeSampleStandardDeviation(input));
        assertEquals("At least two numbers are required to calculate standard deviation.", exception.getMessage());
    }

    @Test
    void computeSampleStandardDeviation_EmptyInput_ThrowsException() {
        // Arrange
        String input = "";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculatorService.computeSampleStandardDeviation(input));
        assertEquals("Input is empty. Please enter one value per line.", exception.getMessage());
    }

    // Test for computePopulationStandardDeviation
    @Test
    void calculate_PopulationStdDev_ValidInput_ReturnsCorrectResult() {
        // Arrange
        String values = "1\n2\n3\n4\n5"; // Valid input
        String operation = "populationStdDev";
        Model model = new ConcurrentModel();

        // Act
        String resultView = calculatorController.calculate(values, operation, null, model);

        // Assert
        assertEquals("index", resultView); // Ensure it returns to the index page
        assertEquals(1.41, (double) model.getAttribute("result"), 0.01); // Cast result to double
    }

    @Test
    void computePopulationStandardDeviation_SingleNumber_ThrowsException() {
        // Arrange
        String input = "1";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculatorService.computePopulationStandardDeviation(input));
        assertEquals("At least two numbers are required to calculate standard deviation.", exception.getMessage());
    }

    @Test
    void computePopulationStandardDeviation_InvalidFormat_ThrowsException() {
        // Arrange
        String input = "1 2 3";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculatorService.computePopulationStandardDeviation(input));
        assertEquals("Values must be separated by newlines, not spaces.", exception.getMessage());
    }

    @Test
    void computePopulationStandardDeviation_EmptyInput_ThrowsException() {
        // Arrange
        String input = "";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculatorService.computePopulationStandardDeviation(input));
        assertEquals("Input is empty. Please enter one value per line.", exception.getMessage());
    }

    // Test for computeZScore
    @Test
    void computeZScore_ValidInput_ReturnsCorrectZScore() {
        // Arrange
        double value = 10.0;
        double mean = 5.0;
        double stdDev = 2.0;

        // Act
        double result = calculatorService.computeZScore(value, mean, stdDev);

        // Assert
        assertEquals(2.5, result, 0.01);
    }

    @Test
    void computeZScore_ValueEqualsMean_ReturnsZero() {
        // Arrange
        double value = 5.0;
        double mean = 5.0;
        double stdDev = 2.0;

        // Act
        double result = calculatorService.computeZScore(value, mean, stdDev);

        // Assert
        assertEquals(0.0, result, 0.01);
    }

    @Test
    void computeZScore_StdDevZero_ThrowsException() {
        // Arrange
        double value = 10.0;
        double mean = 5.0;
        double stdDev = 0.0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculatorService.computeZScore(value, mean, stdDev));
        assertEquals("Standard deviation cannot be zero.", exception.getMessage());
    }

    @Test
    void calculate_ZScore_MissingExtraParams_ThrowsException() {
        // Arrange
        String values = "10,5";
        String operation = "zScore";
        String extraParams = "10,5"; // Missing the stdDev parameter
        Model model = new ConcurrentModel();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculatorController.calculate(values, operation, extraParams, model);
        });

        assertEquals("Z-score format must be: value,mean,stdDev", exception.getMessage());
    }

    @Test
    void calculate_ZScore_ExtraParamsNotNumbers_ThrowsException() {
        // Arrange
        String values = "10,abc,2";
        String operation = "zScore";
        String extraParams = "10,abc,2"; // Non-numeric mean
        Model model = new ConcurrentModel();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculatorController.calculate(values, operation, extraParams, model);
        });

        assertEquals("Z-score format must contain only numeric values: value,mean,stdDev", exception.getMessage());
    }

    @Test
    void calculate_ZScore_MultipleLines_ThrowsException() {
        // Arrange
        String values = "10,5,\n2"; // Multiline input for Z-score
        String operation = "zScore";
        Model model = new ConcurrentModel();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculatorController.calculate(values, operation, null, model);
        });

        // Assert the exception message
        assertEquals("Input must be on one line in the format: value,mean,stdDev", exception.getMessage());
    }

    @Test
    void calculate_ZScore_ValidInput_ReturnsCorrectResult() {
        // Arrange
        String values = "1"; // The value to calculate the Z-score for
        String operation = "zScore";
        String extraParams = "10,5,2"; // mean and stdDev passed here
        Model model = new ConcurrentModel();

        // Act
        String resultView = calculatorController.calculate(values, operation, extraParams, model);

        // Assert
        assertEquals("index", resultView); // Ensure it returns to the index page
        assertEquals(2.5, (double) model.getAttribute("result"), 0.01); // Check the result matches expected Z-score with delta
    }

    @Test
    void calculate_ZScore_InvalidInput_ThrowsIllegalArgumentException() {
        // Arrange
        String values = "10,5,abc"; // Non-numeric stdDev
        String operation = "zScore";
        Model model = new ConcurrentModel();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculatorController.calculate(values, operation, null, model);
        });

        // Assert Exception Message
        assertEquals("Z-score format must be: value,mean,stdDev", exception.getMessage());
    }

    // Test for computeLinearRegression
    @Test
    void computeLinearRegression_ValidInput_ReturnsSlopeAndIntercept() {
        // Arrange
        String input = "1,2\n2,3\n3,5\n4,7";

        // Act
        double[] result = calculatorService.computeLinearRegression(input);

        // Assert
        assertEquals(1.7, result[0], 0.01); // Slope (m)
        assertEquals(0.0, result[1], 0.01); // Intercept (b)
    }

    @Test
    void computeLinearRegression_SinglePoint_ThrowsException() {
        // Arrange
        String input = "1,2";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculatorService.computeLinearRegression(input));
        assertEquals("At least two x,y pairs are required for linear regression.", exception.getMessage());
    }

    @Test
    void computeLinearRegression_InvalidInput_ThrowsException() {
        // Arrange
        String input = "1,2\n2\n3,5\n4,7";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculatorService.computeLinearRegression(input));
        assertEquals("Each line must contain exactly one x,y pair separated by a comma.", exception.getMessage());
    }

    // Test for predictYFromRegression
    @Test
    void predictYFromRegression_ValidInput_ReturnsCorrectY() {
        // Arrange
        double x = 2.0;
        double m = 1.4;
        double b = 0.6;

        // Act
        double result = calculatorService.predictYFromRegression(x, m, b);

        // Assert
        assertEquals(3.4, result, 0.01);
    }

    @Test
    void predictYFromRegression_NegativeSlope_ReturnsCorrectY() {
        // Arrange
        double x = 3.0;
        double m = -1.5;
        double b = 2.0;

        // Act
        double result = calculatorService.predictYFromRegression(x, m, b);

        // Assert
        assertEquals(-2.5, result, 0.01);
    }

    @Test
    void calculate_PredictY_MultipleLines_ThrowsException() {
        // Arrange
        String values = "2,\n1.4,\n0.6"; // Multiline input for Predict Y
        String operation = "predictY";
        Model model = new ConcurrentModel();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculatorController.calculate(values, operation, null, model);
        });

        // Assert the exception message
        assertEquals("Input must be on one line in the format: x,m,b", exception.getMessage());
    }

    @Test
    void calculate_PredictY_InvalidFormat_ThrowsIllegalArgumentException() {
        // Arrange
        String values = "10,5"; // Missing one parameter
        String operation = "predictY";
        Model model = new ConcurrentModel();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculatorController.calculate(values, operation, null, model);
        });

        // Assert Exception Message
        assertEquals("Predict Y format must be: x,m,b on one line separated by commas.", exception.getMessage());
    }

    @Test
    void calculate_PredictY_ValidInput_ReturnsCorrectResult() {
        // Arrange
        String values = "10,2,5"; // Valid input
        String operation = "predictY";
        Model model = new ConcurrentModel();

        // Act
        String resultView = calculatorController.calculate(values, operation, null, model);

        // Assert
        assertEquals("index", resultView); // Ensure it returns to the index page
        assertEquals(25.0, model.getAttribute("result")); // Check the result
    }

    @Test
    void calculate_PredictY_NonNumericInput_ThrowsIllegalArgumentException() {
        // Arrange
        String values = "10,abc,5"; // Invalid input with non-numeric value
        String operation = "predictY";
        Model model = new ConcurrentModel();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculatorController.calculate(values, operation, null, model);
        });

        // Assert Exception Message
        assertEquals("Predict Y format must contain only numeric values: x,m,b.", exception.getMessage());
    }

    @Test
    void showForm_ReturnsIndexView() {
        // Act
        String viewName = calculatorController.showForm();

        // Assert
        assertEquals("index", viewName);
    }

    @Test
    void handleMissingParams_ReturnsErrorMessage() {
        // Arrange
        MissingServletRequestParameterException exception = new MissingServletRequestParameterException("values", "String");
        Model model = new ConcurrentModel();

        // Act
        String viewName = calculatorController.handleMissingParams(exception, model);

        // Assert
        assertEquals("index", viewName);
        assertEquals("Invalid Input: The 'values' parameter is missing.", model.getAttribute("error"));
    }

    @Test
    void calculate_InvalidNumberInput_ThrowsNumberFormatException() {
        // Arrange
        String values = "abc\n2\n3"; // Invalid input (non-numeric value)
        String operation = "mean"; // Any operation
        Model model = new ConcurrentModel();

        // Act & Assert
        NumberFormatException exception = assertThrows(NumberFormatException.class, () -> {
            calculatorController.calculate(values, operation, null, model);
        });

        // Assert Exception Message
        assertEquals("For input string: \"abc\"", exception.getMessage());
    }


    @Test
    void calculate_MissingValues_ThrowsIllegalArgumentException() {
        // Arrange
        String values = ""; // Missing values
        String operation = "mean"; // Any operation
        Model model = new ConcurrentModel();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculatorController.calculate(values, operation, null, model);
        });

        // Assert Exception Message
        assertEquals("The 'values' parameter is required but missing.", exception.getMessage());
    }




    @Test
    void calculate_InvalidExtraParams_ThrowsIllegalArgumentException() {
        // Arrange
        String values = "10,5,abc"; // Invalid input for Z-score
        String operation = "zScore";
        String extraParams = "10,abc,2"; // Non-numeric mean
        Model model = new ConcurrentModel();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculatorController.calculate(values, operation, extraParams, model);
        });

        // Assert Exception Message
        assertEquals("Z-score format must contain only numeric values: value,mean,stdDev", exception.getMessage());
    }

    @Test
    void calculate_ZScore_InvalidNumericInputNTestingModeOff_AddsErrorToModel() {
        // Arrange
        CalculatorController controllerWithoutTestingMode = new CalculatorController(calculatorService); // Reset testingMode to false
        String values = "10,abc,2"; // Invalid input (non-numeric stdDev)
        String operation = "zScore";
        String extraParams = "10,abc,2";
        Model model = new ConcurrentModel();

        // Act
        String resultView = controllerWithoutTestingMode.calculate(values, operation, extraParams, model);

        // Assert
        assertEquals("index", resultView); // Ensure it returns to the index page
        assertEquals("Invalid Input: Z-score format must contain only numeric values: value,mean,stdDev", model.getAttribute("error"));
    }

    @Test
    void calculate_LinearRegression_ValidInput_ReturnsCorrectResult() {
        // Arrange
        String values = "1,2\n2,3\n3,5\n4,7"; // Valid input for linear regression (x,y pairs)
        String operation = "linearRegression"; // Specify the operation
        Model model = new ConcurrentModel();

        // Act
        String resultView = calculatorController.calculate(values, operation, null, model);

        // Assert
        assertEquals("index", resultView); // Ensure it returns to the index page

        // Cast attributes to Double and assert with delta
        assertEquals(1.7, (Double) model.getAttribute("slope"), 0.01); // Check that the slope is correct with a delta
        assertEquals(0.0, (Double) model.getAttribute("intercept"), 0.01); // Check that the intercept is correct with a delta
    }

    @Test
    void calculate_InvalidOperation_ThrowsIllegalArgumentException() {
        // Arrange
        String values = "1,2,3"; // Valid input format for testing
        String operation = "invalidOperation"; // Invalid operation to trigger the default case
        Model model = new ConcurrentModel();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculatorController.calculate(values, operation, null, model);
        });

        // Assert Exception Message
        assertEquals("Invalid operation.", exception.getMessage());
    }

    @Test
    void validateLinearRegressionInput_NullInput_ThrowsIllegalArgumentException() {
        // Arrange
        String values = null; // Null input

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.validateLinearRegressionInput(values);
        });

        // Assert Exception Message
        assertEquals("Input is empty. Please provide x,y pairs.", exception.getMessage());
    }

    @Test
    void validateLinearRegressionInput_EmptyInput_ThrowsIllegalArgumentException() {
        // Arrange
        String values = ""; // Empty input

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.validateLinearRegressionInput(values);
        });

        // Assert Exception Message
        assertEquals("Input is empty. Please provide x,y pairs.", exception.getMessage());
    }

    @Test
    void validateSingleLineInput_NullInput_ThrowsIllegalArgumentException() {
        // Arrange
        String values = null; // Null input
        String format = "value,mean,stdDev";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CalculatorService.validateSingleLineInput(values, format);
        });

        // Assert Exception Message
        assertEquals("Input is empty. Please provide values in the format: value,mean,stdDev", exception.getMessage());
    }

    @Test
    void validateSingleLineInput_EmptyInput_ThrowsIllegalArgumentException() {
        // Arrange
        String values = ""; // Empty input
        String format = "value,mean,stdDev";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CalculatorService.validateSingleLineInput(values, format);
        });

        // Assert Exception Message
        assertEquals("Input is empty. Please provide values in the format: value,mean,stdDev", exception.getMessage());
    }

    @Test
    void calculate_PredictY_InvalidNumericInput_AddsErrorToModel() {
        // Arrange
        CalculatorController controller = new CalculatorController(calculatorService); // Create a new controller instance
        Model model = new ConcurrentModel();
        String values = "1,2,abc"; // Invalid numeric input for PredictY
        String operation = "predictY";

        // Ensure testingMode is false by NOT enabling it

        // Act
        String resultView = controller.calculate(values, operation, null, model);

        // Assert
        assertEquals("index", resultView); // Ensure it returns to the index page
        assertEquals("Invalid Input: Predict Y format must contain only numeric values: x,m,b.",
                model.getAttribute("error")); // Verify error message is added to the model
    }


    @Test
    void calculate_InvalidInputFormat_AddsErrorToModel() {
        // Arrange
        CalculatorController controller = new CalculatorController(calculatorService); // Create a new controller instance
        Model model = new ConcurrentModel();
        String values = "abc,123"; // Invalid numeric input (non-numeric value causes NumberFormatException)
        String operation = "mean"; // Any operation that processes numeric input

        // Act
        String resultView = controller.calculate(values, operation, null, model);

        // Assert
        assertEquals("index", resultView); // Ensure it returns to the index page
        assertEquals("Invalid Input: Invalid input format. Please enter numbers in the correct format.",
                model.getAttribute("error")); // Verify the error message
    }

    @Test
    void calculate_Mean_ValidInputToCoverBreakStatement_ReturnsCorrectResult() {
        // Arrange
        CalculatorController controller = new CalculatorController(calculatorService); // Create a new controller instance
        Model model = new ConcurrentModel();
        String values = "1\n2\n3\n4\n5"; // Valid input for mean
        String operation = "mean"; // Operation for mean calculation

        // Act
        String resultView = controller.calculate(values, operation, null, model);

        // Assert
        assertEquals("index", resultView); // Ensure it returns to the index page
        assertEquals(3.0, model.getAttribute("result")); // Check that the mean is calculated correctly
    }

}
