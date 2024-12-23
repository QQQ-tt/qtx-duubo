package qtx.dubbo.count;

import lombok.Getter;

/**
 * @author qtx
 * @since 2024/5/21 13:44
 */
@Getter
public enum Operator {
    ADD('+', 1),
    SUBTRACT('-', 1),
    MULTIPLY('*', 2),
    DIVIDE('/', 2),
    AND('&', 3),
    OR('|', 3),
    NOT('!', 4);

    private final char symbol;
    private final int precedence;

    Operator(char symbol, int precedence) {
        this.symbol = symbol;
        this.precedence = precedence;
    }

    public static Operator fromSymbol(char symbol) {
        for (Operator op : values()) {
            if (op.symbol == symbol) {
                return op;
            }
        }
        throw new IllegalArgumentException("Unknown operator: " + symbol);
    }
}
