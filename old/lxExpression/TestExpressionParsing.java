/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxExpression;

import lexa.core.data.DataSet;
import lexa.core.data.ArrayDataSet;
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

    /**
     *
     * @return
     */
    public Object[] expressions()
    {
        return new TestCase[]{
            new TestCase("1 + 2", 3, null),
            new TestCase("[string.format \"I have %1$d horses for gin\" 4]", "I have 4 horses for gin", null),
            new TestCase("(a * b) / c", 6, new ArrayDataSet().put("a",3).put("b",4).put("c",2))
        };
    }
    
    /**
     *
     * @param arg
     * @return
     */
    public Boolean setUpTest(Object arg) 
    {
        TestCase testCase = (TestCase)arg;
        this.input = testCase.expression;
        this.expression = null;
        this.result = null;
        this.expected = testCase.result;
        this.data = testCase.data;        
        return true;
    }

    /**
     *
     * @param arg
     * @return
     * @throws ExpressionException
     */
    @TestAnnotation(order = 0, setUp = "setUpTest")
    public Boolean create(Object arg) throws ExpressionException
    {
        this.expression = Expression.parse(this.input);
        return true;
    }
            
    /**
     *
     * @param arg
     * @return
     * @throws ExpressionException
     */
    @TestAnnotation(order = 10)
    public Boolean evaluate(Object arg) throws ExpressionException
    {
        this.result = this.expression.evaluate(this.data);
        return true;
    }
    
    /**
     *
     * @param arg
     * @return
     */
    @TestAnnotation(order = 20)  
    public Boolean confirm(Object arg)
    {
        if (this.expected == null)
            return (this.result == null);
        return this.expected.equals(this.result);
    }
    
    private class TestCase
    {

        private final DataSet data;

        private final String expression;
        private final Object result;
        
        TestCase(String expression, Object result, DataSet data)
        {
            this.expression = expression;
            this.result = result;
            this.data = (data != null) ? data :
                    new ArrayDataSet();
        }

        @Override
        public String toString()
        {
            return "TestCase{" + expression + ", " + result + '}';
        }
        
        
    }
}
