package edu.TestingQA.Calculator;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalculatorService {

    public double computeMean(String values) {
        validateSingleValuePerLine(values);

        double[] numbers = Arrays.stream(values.split("\\s+"))
                .mapToDouble(Double::parseDouble)
                .toArray();
        return Arrays.stream(numbers).average().orElse(0.0);
    }

    public double computeSampleStandardDeviation(String values) {
        validateSingleValuePerLine(values);
        validateMinimumInputSizeForStandardDeviation(values);

        double[] numbers = Arrays.stream(values.split("\\s+"))
                .mapToDouble(Double::parseDouble)
                .toArray();
        double mean = Arrays.stream(numbers).average().orElse(0.0);
        return Math.sqrt(Arrays.stream(numbers)
                .map(n -> Math.pow(n - mean, 2))
                .sum() / (numbers.length - 1));
    }

    public double computePopulationStandardDeviation(String values) {
        validateSingleValuePerLine(values);
        validateMinimumInputSizeForStandardDeviation(values);

        double[] numbers = Arrays.stream(values.split("\\s+"))
                .mapToDouble(Double::parseDouble)
                .toArray();
        double mean = Arrays.stream(numbers).average().orElse(0.0);
        return Math.sqrt(Arrays.stream(numbers)
                .map(n -> Math.pow(n - mean, 2))
                .average()
                .orElse(0.0));
    }

    private void validateMinimumInputSizeForStandardDeviation(String values) {
        String[] lines = values.split("\\s+");
        if (lines.length < 2) {
            throw new IllegalArgumentException("At least two numbers are required to calculate standard deviation.");
        }
    }

    public double computeZScore(double value, double mean, double stdDev) {
        if (stdDev == 0) {
            throw new IllegalArgumentException("Standard deviation cannot be zero.");
        }
        return (value - mean) / stdDev;
    }

    public double[] computeLinearRegression(String values) {
        validateLinearRegressionInput(values);

        List<double[]> pairs = Arrays.stream(values.split("\\n"))
                .map(line -> line.split(","))
                .map(pair -> new double[]{Double.parseDouble(pair[0].trim()), Double.parseDouble(pair[1].trim())})
                .collect(Collectors.toList());

        double meanX = pairs.stream().mapToDouble(pair -> pair[0]).average().orElse(0.0);
        double meanY = pairs.stream().mapToDouble(pair -> pair[1]).average().orElse(0.0);

        double numerator = pairs.stream().mapToDouble(pair -> (pair[0] - meanX) * (pair[1] - meanY)).sum();
        double denominator = pairs.stream().mapToDouble(pair -> Math.pow(pair[0] - meanX, 2)).sum();
        double slope = numerator / denominator;

        double intercept = meanY - slope * meanX;

        return new double[]{slope, intercept};
    }

    public double predictYFromRegression(double x, double m, double b) {
        return m * x + b;
    }

    private void validateSingleValuePerLine(String values) {
        if (values == null || values.trim().isEmpty()) {
            throw new IllegalArgumentException("Input is empty. Please enter one value per line.");
        }
        if (values.contains(" ") && !values.contains("\n")) {
            throw new IllegalArgumentException("Values must be separated by newlines, not spaces.");
        }
    }

    public void validateLinearRegressionInput(String values) {
        if (values == null || values.trim().isEmpty()) {
            throw new IllegalArgumentException("Input is empty. Please provide x,y pairs.");
        }
        String[] lines = values.split("\\n");
        if (lines.length < 2) {
            throw new IllegalArgumentException("At least two x,y pairs are required for linear regression.");
        }
        for (String line : lines) {
            String[] pair = line.split(",");
            if (pair.length != 2 || !isNumeric(pair[0].trim()) || !isNumeric(pair[1].trim())) {
                throw new IllegalArgumentException("Each line must contain exactly one x,y pair separated by a comma.");
            }
        }
    }

    public static void validateSingleLineInput(String values, String format) {
        if (values == null || values.trim().isEmpty()) {
            throw new IllegalArgumentException("Input is empty. Please provide values in the format: " + format);
        }
        if (values.contains("\n")) {
            throw new IllegalArgumentException("Input must be on one line in the format: " + format);
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
