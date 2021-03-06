package com.intellij.bigdecimal;

import java.util.List;

public class DivideAssign extends Operation {
    public DivideAssign(List<Expression> operands) {
        super("/=", 300, operands);
    }

    @Override
    protected Operation copy(List<Expression> newOperands) {
        return new DivideAssign(newOperands);
    }
}
