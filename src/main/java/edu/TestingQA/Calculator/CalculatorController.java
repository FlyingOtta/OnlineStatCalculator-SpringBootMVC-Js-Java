package edu.TestingQA.Calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.MissingServletRequestParameterException;

@Controller
public class CalculatorController {

    private final CalculatorService calculatorService;
    private boolean testingMode = false;

    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    public void enableTestingMode() {
        this.testingMode = true;
    }

    @GetMapping("/")
    public String showForm() {
        return "index";
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam(value = "values", required = false) String values,
                            @RequestParam("operation") String operation,
                            @RequestParam(value = "extraParams", required = false) String extraParams,
                            Model model) {

        try {
            if (values == null || values.isEmpty()) {
                throw new IllegalArgumentException("The 'values' parameter is required but missing.");
            }

            double result = 0.0;

            switch (operation) {
                case "sampleStdDev":
                    result = calculatorService.computeSampleStandardDeviation(values);
                    break;
                case "populationStdDev":
                    result = calculatorService.computePopulationStandardDeviation(values);
                    break;
                case "mean":
                    result = calculatorService.computeMean(values);
                    break;
                case "zScore":
                    CalculatorService.validateSingleLineInput(values, "value,mean,stdDev");

                    if (extraParams == null || extraParams.split(",").length != 3) {
                        throw new IllegalArgumentException("Z-score format must be: value,mean,stdDev");
                    }
                    String[] params = extraParams.split(",");
                    try {
                        double value = Double.parseDouble(params[0].trim());
                        double mean = Double.parseDouble(params[1].trim());
                        double stdDev = Double.parseDouble(params[2].trim());
                        result = calculatorService.computeZScore(value, mean, stdDev);
                    } catch (NumberFormatException e) {
                        if (testingMode) throw new IllegalArgumentException("Z-score format must contain only numeric values: value,mean,stdDev");
                        model.addAttribute("error", formatErrorMessage("Z-score format must contain only numeric values: value,mean,stdDev"));
                        return "index";
                    }
                    break;
                case "linearRegression":
                    double[] regressionResult = calculatorService.computeLinearRegression(values);
                    model.addAttribute("slope", regressionResult[0]);
                    model.addAttribute("intercept", regressionResult[1]);
                    return "index";
                case "predictY":
                    CalculatorService.validateSingleLineInput(values, "x,m,b");

                    if (values.split(",").length != 3) {
                        throw new IllegalArgumentException("Predict Y format must be: x,m,b on one line separated by commas.");
                    }
                    String[] predictParams = values.split(",");
                    try {
                        double x = Double.parseDouble(predictParams[0].trim());
                        double m = Double.parseDouble(predictParams[1].trim());
                        double b = Double.parseDouble(predictParams[2].trim());
                        result = calculatorService.predictYFromRegression(x, m, b);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Predict Y format must contain only numeric values: x,m,b.");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operation.");
            }

            model.addAttribute("result", result);
        } catch (NumberFormatException e) {
            if (testingMode) throw e;
            model.addAttribute("error", formatErrorMessage("Invalid input format. Please enter numbers in the correct format."));
        } catch (IllegalArgumentException e) {
            if (testingMode) throw e;
            model.addAttribute("error", formatErrorMessage(e.getMessage()));
        }

        return "index";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParams(MissingServletRequestParameterException ex, Model model) {
        model.addAttribute("error", formatErrorMessage("The '" + ex.getParameterName() + "' parameter is missing."));
        return "index";
    }

    private String formatErrorMessage(String reason) {
        return "Invalid Input: " + reason;
    }
}
