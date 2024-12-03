package CalculatorUnitTests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import edu.TestingQA.Calculator.CalculatorApplication;

@SpringBootTest(classes = edu.TestingQA.Calculator.CalculatorApplication.class)
public class CalculatorApplicationTest {

    @Test
    void mainMethodTest() {
        // Explicitly call the main method
        CalculatorApplication.main(new String[]{});
    }
}
