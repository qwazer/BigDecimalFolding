package com.intellij.bigdecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Operation extends Expression {
    protected String character;
    private int priority;
    protected List<Expression> operands;

    public Operation(String character, int priority, List<Expression> operands) {
        this.character = character;
        this.priority = priority;
        this.operands = operands;
    }

    public List<Expression> getOperands() {
        return operands;
    }

    @Override
    public Operation simplify() {
        List<Expression> simplifiedOperands = null;
        for (int i = 0; i < operands.size(); i++) {
            boolean toSimplify = false;
            Expression operand = operands.get(i);
            if (operand instanceof Operation) {
                Operation simplifiedOperand = (Operation) operand;
                Operation s = simplifiedOperand.simplify();
                if (s != simplifiedOperand) {
                    simplifiedOperand = s;
                    toSimplify = true;
                    if (simplifiedOperands == null) {
                        simplifiedOperands = new ArrayList<>();
                        if (i > 0) {
                            simplifiedOperands.addAll(operands.subList(0, i));
                        }
                    }
                }
                if (Objects.equals(this.getCharacter(), simplifiedOperand.getCharacter())) {
                    if (simplifiedOperands == null) {
                        simplifiedOperands = new ArrayList<>();
                        if (i > 0) {
                            simplifiedOperands.addAll(operands.subList(0, i));
                        }
                    }
                    simplifiedOperands.addAll(simplifiedOperand.operands);
                    toSimplify = true;
                } else if (toSimplify) {
                    simplifiedOperands.add(operand);
                }
            }
            if (!toSimplify && simplifiedOperands != null) {
                simplifiedOperands.add(operand);
            }
        }
        if (simplifiedOperands != null) {
            return (Operation) copy(simplifiedOperands);
        }
        return this;
    }

    private int compareTo(Operation operation) {
        return this.getPriority() == operation.getPriority()
                ? 0
                : this.getPriority() > operation.getPriority()
                ? 1
                : -1;
    }

    @Override
    public String format() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < operands.size(); i++) {
            Expression operand = operands.get(i);
            String f = operand.format();
            boolean toSimplify = false;
            if (operand instanceof Operation) {
                Operation o = (Operation) operand;
                toSimplify = (i == 0 || (o.isAssociative() && this.isAssociative())) && o.compareTo(this) >= 0
                        || o.compareTo(this) > 0;
            }
            if (operand instanceof Operation && !toSimplify) {
                sb.append("(");
            }
            sb.append(f);
            if (operand instanceof Operation && !toSimplify) {
                sb.append(")");
            }
            if (i < operands.size() - 1) {
                sb.append(" ");
            }
            if (i < operands.size() - 1) {
                sb.append(character).append(" ");
            }
        }
        return sb.toString();
    }

    public String getCharacter() {
        return character;
    }

    public boolean isAssociative() {
        return true;
    }

    public int getPriority() {
        return priority;
    }
}
