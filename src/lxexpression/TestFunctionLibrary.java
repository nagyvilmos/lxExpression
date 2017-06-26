/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxexpression;

import lexa.core.data.ArrayDataSet;
import lexa.core.data.DataSet;
import lexa.core.expression.Expression;
import lexa.core.expression.ExpressionException;
import lexa.core.expression.function.FunctionLibrary;
import lexa.test.TestAnnotation;
import lexa.test.TestClass;
import lexa.test.TestResult;

/**
 *
 * @author william
 */
public class TestFunctionLibrary extends TestClass
{

    public TestFunctionLibrary()
    {
    }

    public Object[] argumentsFunctionCall()
    {
        DataSet data = new ArrayDataSet()
                .put("data",
                        new ArrayDataSet()
                            .put("a", 1)
                            .put("b", 2))
                .put("num", 1.0);

        return new TestCase[] {
            new TestCase(null, "data.clone data", data, data.getDataSet("data")),
            new TestCase(null, "data.contains data \"a\"", data, true),
            new TestCase(null, "data.contains data \"c\"", data, false),
            new TestCase(null, "data.key data 1", data, "b"),
            new TestCase(null, "data.value data 0", data, 1),
            new TestCase(null, "data.remove data \"b\"", data, 2),
            new TestCase(null, "data.map data map", data, null),
			new TestCase(null, "maths.cos num", data, 0.5403023058681398),
			new TestCase(null, "maths.pi num", data, 3.141592653589793),
			new TestCase(null, "maths.sin num", data, 0.8414709848078965),
			new TestCase(null, "maths.tan num", data, 1.5574077246549023),
            new TestCase(null, "isNull data.a", data, false),
            new TestCase(null, "isNull data.c", data, true),
			new TestCase(null, "null", data, null),
			new TestCase(null, "nullValue data.b 4", data, 2),
            new TestCase(null, "nullValue data.c 4", data, 4)
       };
    }

    public TestResult setUpFunctionCall(Object arg)
            throws ExpressionException
    {
        TestCase testCase = (TestCase)arg;
        // Load the library
        testCase.functionLibrary = FunctionLibrary.base();
        if (testCase.definition != null)
        {
            testCase.functionLibrary.addFunctions(testCase.definition);
        }
        testCase.expression = Expression.parse(
                '[' + testCase.call + ']',
                testCase.functionLibrary
        );
        return TestResult.all(
                TestResult.isClass(
                        "lexa.core.expression.function.FunctionLibrary",
                        testCase.functionLibrary),
                TestResult.isClass(
                        "lexa.core.expression.FunctionCall",
                        testCase.expression)
        );
    }

    @TestAnnotation(arguments = "argumentsFunctionCall",
            setUp = "setUpFunctionCall")
    public TestResult functionCall(Object arg)
            throws ExpressionException
    {
        TestCase testCase = (TestCase)arg;
        System.out.println(testCase.call);
        return TestResult.result(testCase.result,
                testCase.expression.evaluate(testCase.data));
    }

    class TestCase
    {
        final DataSet definition; // don't need for internal function tests;
        final String call;
        final DataSet data;
        final Object result;

        FunctionLibrary functionLibrary;
        Expression expression;

        TestCase(DataSet definition,
                String call,
                DataSet data,
                Object result)
        {
            this.definition = definition;
            this.call = call;
            // copy so it doesn't affect others:
            this.data = data.factory().clone(data);
            this.result = result;
        }

        @Override
        public String toString()
        {
            return this.call;
        }
    }
}
