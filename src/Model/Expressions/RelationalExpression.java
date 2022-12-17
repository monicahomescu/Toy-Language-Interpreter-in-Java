package Model.Expressions;

import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Model.ProgramState.InterfaceDictionary;
import Model.ProgramState.InterfaceHeap;
import Model.Types.BooleanType;
import Model.Types.IntegerType;
import Model.Types.InterfaceType;
import Model.Values.BooleanValue;
import Model.Values.IntegerValue;
import Model.Values.InterfaceValue;

public class RelationalExpression implements InterfaceExpression {

    String op;
    InterfaceExpression exp1;
    InterfaceExpression exp2;

    public RelationalExpression(String op, InterfaceExpression exp1, InterfaceExpression exp2) {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public InterfaceValue evaluate(InterfaceDictionary<String, InterfaceValue> symTable, InterfaceHeap heap) throws ADTException, ExpressionEvaluationException {
        InterfaceValue val1;
        InterfaceValue val2;
        val1 = this.exp1.evaluate(symTable, heap);
        if (val1.getType().equals(new IntegerType())) {
            val2 = this.exp2.evaluate(symTable, heap);
            if (val2.getType().equals(new IntegerType())) {
                IntegerValue i1 = (IntegerValue) val1;
                IntegerValue i2 = (IntegerValue) val2;
                int n1;
                int n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                if (this.op.equals("<"))
                    return new BooleanValue(n1 < n2);
                else if (this.op.equals("<="))
                    return new BooleanValue(n1 <= n2);
                else if (this.op.equals("=="))
                    return new BooleanValue(n1 == n2);
                else if (this.op.equals("!="))
                    return new BooleanValue(n1 != n2);
                else if (this.op.equals(">"))
                    return new BooleanValue(n1 > n2);
                else if (this.op.equals(">="))
                    return new BooleanValue(n1 >= n2);
            }
            else throw new ExpressionEvaluationException("Second operand is not an integer!");
        }
        else throw new ExpressionEvaluationException("First operand is not an integer!");
        return null;
    }

    @Override
    public InterfaceType typeCheck(InterfaceDictionary<String, InterfaceType> typeEnv) throws ADTException, ExpressionEvaluationException {
        InterfaceType typ1, typ2;
        typ1 = this.exp1.typeCheck(typeEnv);
        typ2 = this.exp2.typeCheck(typeEnv);
        if (typ1.equals(new IntegerType())) {
            if (typ2.equals(new IntegerType()))
                return new BooleanType();
            else
                throw new ExpressionEvaluationException("Second operand is not an integer!");
        }
        else
            throw new ExpressionEvaluationException("First operand is not an integer!");
    }

    @Override
    public InterfaceExpression deepCopy() {
        return new RelationalExpression(this.op, this.exp1.deepCopy(), this.exp2.deepCopy());
    }

    @Override
    public String toString() {
        return this.exp1.toString() + this.op + this.exp2.toString();
    }

}
