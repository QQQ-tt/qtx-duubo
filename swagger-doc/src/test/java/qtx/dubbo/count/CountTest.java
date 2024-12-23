package qtx.dubbo.count;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author qtx
 * @since 2024/5/21 13:45
 */
public class CountTest {

    @Test
    public void test() {
        String formula = "3 + 5 * (2 - 8)";
        List<String> rpn = FormulaParser.parseToRPN(formula);
        System.out.println("RPN: " + rpn);
        double result = FormulaParser.evaluateRPN(rpn);
        System.out.println("Result: " + result);
    }
}
