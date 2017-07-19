/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxexpression;

import lexa.core.expression.ExpressionException;
import lexa.core.expression.Expression;
import lexa.core.data.DataSet;
import lexa.core.data.ArrayDataSet;
import lexa.test.TestClass;
import lexa.test.TestAnnotation;
import lexa.test.TestResult;

/**
 * Test general expression parsing
 * @author william
 */
@TestAnnotation(
        arguments = "expressions")
public class TestExpressionParsing
        extends TestClass
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
            new TestCase("numeric literal",
                    "4",
                    4,
                    null),
            new TestCase("string literal",
                    "\"a\"",
                    "a",
                    null),
            new TestCase("Addition",
                    "1 + 2",
                    3,
                    null),
            new TestCase("String format",
                    "[string.format \"I have %1$d horses for gin\" 4]",
                    "I have 4 horses for gin",
                    null),
            new TestCase("numeric evaluation",
                    "(a * b) / c",
                    6,
                    new ArrayDataSet()
                            .put("a",3)
                            .put("b",4)
                            .put("c",2)),
            new TestCase("all data",
                    "~",
                    new ArrayDataSet()
                            .put("a",new ArrayDataSet()
                                .put("b",4)),
                    new ArrayDataSet()
                            .put("a",new ArrayDataSet()
                                .put("b",4))),
            new TestCase("dataset element",
                    "a",
                    new ArrayDataSet()
                            .put("b",4),
                    new ArrayDataSet()
                            .put("a",new ArrayDataSet()
                                .put("b",4))),
            new TestCase("indexed element",
                    "a.b",
                    4,
                    new ArrayDataSet()
                            .put("a",new ArrayDataSet()
                                .put("b",4)))
        };
    }

    /**
     *
     * @param arg
     * @return
     */
    public TestResult setUpTest(Object arg)
    {
        TestCase testCase = (TestCase)arg;
        this.input = testCase.expression;
        this.expression = null;
        this.result = null;
        this.expected = testCase.result;
        this.data = testCase.data;

        return TestResult.all(
                TestResult.notNull(this.data),
                TestResult.notNull(this.expected),
                TestResult.notNull(this.input),
                TestResult.isNull(this.expression),
                TestResult.isNull(this.result)
        );
    }

    public TestResult tearDownTest(Object arg)
    {
        this.data = null;
        this.expected = null;
        this.expression = null;
        this.input = null;
        this.result = null;

        return TestResult.all(
                TestResult.isNull(this.data),
                TestResult.isNull(this.expected),
                TestResult.isNull(this.expression),
                TestResult.isNull(this.input),
                TestResult.isNull(this.result)
        );
    }
    /**
     *
     * @param arg
     * @return
     * @throws ExpressionException
     */
    @TestAnnotation(order = 0, setUp = "setUpTest")
    public TestResult create(Object arg) throws ExpressionException
    {
        this.expression = Expression.parse(this.input);
        return TestResult.assignableTo("lexa.core.expression.Expression",
                this.expression);
    }

    /**
     *
     * @param arg
     * @return
     * @throws ExpressionException
     */
    @TestAnnotation(order = 10)
    public TestResult evaluate(Object arg) throws ExpressionException
    {
        this.result = this.expression.evaluate(this.data);
        if (this.expected == null)
        {
            return TestResult.isNull(this.result);
        }
        return TestResult.assignableTo(this.expected.getClass().getCanonicalName(),
                this.result);
    }

    /**
     *
     * @param arg
     * @return
     */
    @TestAnnotation(order = 20, tearDown = "tearDownTest")
    public TestResult confirm(Object arg)
    {
        return TestResult.result(this.expected, this.result);
    }

    private class TestCase
    {

        private final DataSet data;

        private final String expression;
        private final String name;
        private final Object result;

        TestCase(String name, String expression, Object result, DataSet data)
        {
            this.name = name;
            this.expression = expression;
            this.result = result;
            this.data = (data != null) ? data :
                    new ArrayDataSet();
        }

        @Override
        public String toString()
        {
            return this.name;
        }


    }
}
