package com.intellij.bigdecimal;

import java.util.List;

public class Negate extends Function {
    public Negate(List<Expression> operands) {
        super("negate", operands);
    }

    @Override
    protected Negate copy(List<Expression> newOperands) {
        return new Negate(newOperands);
    }

    @Override
    public String format() {
        Expression expression = operands.get(0);
        if (expression instanceof Operation) {
            return "-(" + expression.format() + ")";
        } else {
            return "-" + expression.format();
        }
    }
}
