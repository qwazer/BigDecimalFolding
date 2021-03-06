package com.intellij.bigdecimal;

import java.util.List;

public class Equal extends Operation {
    public Equal(List<Expression> operands) {
        super("≡", 18, operands);
    }

    @Override
    protected Operation copy(List<Expression> newOperands) {
        return new Equal(operands);
    }
}
