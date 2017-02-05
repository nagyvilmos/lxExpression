/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxExpression;

import lexa.core.data.DataSet;
import lexa.core.data.SimpleDataSet;
import lexa.core.expression.Expression;
import lexa.core.expression.ExpressionException;
import lexa.test.TestClass;
import lexa.test.TestAnnotation;

/**
 *
 * @author william
 */
@TestAnnotation(
        arguments = "expressions")
public class TestExpressionParsing extends TestClass
{

    private DataSet data;

    private Object expected;

    private Expression expression;
    private String input;
    private Object result;

    public Object[] expressions()
    {
        return new Object[]{
            new Object[]{"1 + 2", 3},
            new Object[]{"'I have ' + (1+ 3) + ' horses for gin'", "I have 4 horses for gin"},
            new Object[]{"a * c / b", 6, new SimpleDataSet().put("a",3).put("b",4).put("c",2)}
        };
    }
    
    public Boolean setUpTest(Object arg) 
    {
        Object[] args = (Object[])arg;
        this.input = (String)args[0];
        this.expression = null;
        this.result = null;
        this.expected = args[1];
        this.data = args.length>2 ?
                (DataSet)args[2] :
                new SimpleDataSet();        
        return true;
    }

    @TestAnnotation(order = 0, setUp = "setUpTest")
    public Boolean create(Object arg) throws ExpressionException
    {
        this.expression = Expression.parse(this.input);
        return true;
    }
            
   @TestAnnotation(order = 10)
    public Boolean evaluate(Object arg) throws ExpressionException
    {
        this.result = this.expression.evaluate(this.data);
        return true;
    }
    
   @TestAnnotation(order = 20)  
    public Boolean confirm(Object arg)
    {
        if (this.expected == null)
            return (this.result == null);
        return this.expected.equals(this.result);
    }
}
