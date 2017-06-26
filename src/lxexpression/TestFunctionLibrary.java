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
                .put("num", 1.0)
                .put("string", "the cat sat on the mat");

        return new TestCase[] {
            // Data library:
            new TestCase(null, "data.clone data", data, data.getDataSet("data")),
            new TestCase(null, "data.contains data \"a\"", data, true),
            new TestCase(null, "data.contains data \"c\"", data, false),
            new TestCase(null, "data.key data 1", data, "b"),
            new TestCase(null, "data.value data 0", data, 1),
            new TestCase(null, "data.remove data \"b\"", data, 2),
            new TestCase(null, "data.map data map", data, null),
            // Maths library:
			new TestCase(null, "maths.cos num", data, 0.5403023058681398),
			new TestCase(null, "maths.pi num", data, 3.141592653589793),
			new TestCase(null, "maths.sin num", data, 0.8414709848078965),
			new TestCase(null, "maths.tan num", data, 1.5574077246549023),
            new TestCase(null, "isNull data.a", data, false),
            new TestCase(null, "isNull data.c", data, true),
            // null handling
			new TestCase(null, "null", data, null),
			new TestCase(null, "nullValue data.b 4", data, 2),
            new TestCase(null, "nullValue data.c 4", data, 4),
            // strings
            new TestCase(null, "string.ends string \"mat\"", data, true),
            new TestCase(null, "string.ends string \"cat\"", data, false),
			new TestCase(null, "string.find string \"cat\"", data, 4),
			new TestCase(null, "string.find string \"dog\"", data, -1),
			new TestCase(null, "string.findAfter string \"cat\" 3", data, 4),
			new TestCase(null, "string.findAfter string \"cat\" 5", data, -1),
            new TestCase(null, "string.findAfter string \"dog\" 5", data, -1),
			new TestCase(null, "string.findBefore string \"cat\" 3", data, -1),
			new TestCase(null, "string.findBefore string \"cat\" 8", data, 4),
            new TestCase(null, "string.findBefore string \"dog\" 5", data, -1),
			new TestCase(null, "string.findLast string \"the\" ", data, 15),
			new TestCase(null, "string.findLast string \"dog\" ", data, -1),
			new TestCase(null, "string.format \"%1$d\" 5", data, "5"),
			new TestCase(null, "string.length string", data, 22),
			new TestCase(null, "string.lower \"MAT\"", data, "mat"),
			new TestCase(null, "string.matches string \"[thecasonm ]*\"", data, true),
			new TestCase(null, "string.matches string \"x*\"", data, false),
			new TestCase(null, "string.replace string \"at\" \"is\"", data, "the cis sis on the mis"),
			new TestCase(null, "string.replaceFirst string \"at\" \"is\"", data, "the cis sat on the mat"),
			new TestCase(null, "string.starts string \"the\"", data, true),
			new TestCase(null, "string.starts string \"thy\"", data, false),
			new TestCase(null, "string.sub string 4 7", data, "cat"),
			new TestCase(null, "string.upper string", data, "THE CAT SAT ON THE MAT"),
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
