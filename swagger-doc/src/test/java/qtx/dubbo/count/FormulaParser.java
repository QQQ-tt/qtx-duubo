package qtx.dubbo.count;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author qtx
 * @since 2024/5/21 13:44
 */
public class FormulaParser {

    public static List<String> parseToRPN(String formula) {
        List<String> output = new ArrayList<>();
        Stack<Operator> operators = new Stack<>();

        for (int i = 0; i < formula.length(); i++) {
            char c = formula.charAt(i);
            if (Character.isDigit(c)) {
                StringBuilder number = new StringBuilder();
                while (i < formula.length() && (Character.isDigit(formula.charAt(i)) || formula.charAt(i) == '.')) {
                    number.append(formula.charAt(i++));
                }
                i--;
                output.add(number.toString());
            } else if (c == '(') {
                operators.push(null); // Use null as a marker for '('
            } else if (c == ')') {
                while (operators.peek() != null) {
                    output.add(String.valueOf(operators.pop().getSymbol()));
                }
                operators.pop(); // Remove the '('
            } else {
                Operator op = Operator.fromSymbol(c);
                while (!operators.isEmpty() && operators.peek() != null && operators.peek().getPrecedence() >= op.getPrecedence()) {
                    output.add(String.valueOf(operators.pop().getSymbol()));
                }
                operators.push(op);
            }
        }

        while (!operators.isEmpty()) {
            output.add(String.valueOf(operators.pop().getSymbol()));
        }

        return output;
    }

    public static double evaluateRPN(List<String> rpn) {
        Stack<Double> stack = new Stack<>();

        for (String token : rpn) {
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(token));
            } else {
                Operator op = Operator.fromSymbol(token.charAt(0));
                if (op == Operator.NOT) {
                    double value = stack.pop();
                    stack.push(value == 0 ? 1.0 : 0.0);
                } else {
                    double b = stack.pop();
                    double a = stack.pop();
                    switch (op) {
                        case ADD:
                            stack.push(a + b);
                            break;
                        case SUBTRACT:
                            stack.push(a - b);
                            break;
                        case MULTIPLY:
                            stack.push(a * b);
                            break;
                        case DIVIDE:
                            stack.push(a / b);
                            break;
                        case AND:
                            stack.push((a != 0 && b != 0) ? 1.0 : 0.0);
                            break;
                        case OR:
                            stack.push((a != 0 || b != 0) ? 1.0 : 0.0);
                            break;
                    }
                }
            }
        }

        return stack.pop();
    }

}
