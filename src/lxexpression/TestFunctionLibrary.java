/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * TestInternalFunction.java (lxExpression)
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: April 2017
 *==============================================================================
 */

package lxexpression;

import java.util.*;
import lexa.core.data.ArrayDataArray;
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
 * @since  2017-04
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
                .put("string", "the cat sat on the mat")
                .put("array", new ArrayDataArray()
                    .add(1)
                    .add(2)
                    .add(3)
        );

        TestCase[] tests = new TestCase[] {
            // Data library: [0]
            new TestCase(null, "clone data", data, data.getDataSet("data")),
            new TestCase(null, "contains data \"a\"", data, true),
            new TestCase(null, "contains data \"c\"", data, false),
            new TestCase(null, "key data 1", data, "b"),
            new TestCase(null, "value data 0", data, 1),
            new TestCase(null, "remove data \"b\"", data, 2),
            new TestCase(null, "map data map", data, null),
            new TestCase(null, "size data", data, 2),
            new TestCase(null, "size array", data, 3),
            new TestCase(null, "size string", data, 22),
            // [10]
            new TestCase(null, "array -3 -2 -1", data, new ArrayDataArray(-3, -2, -1)),
            new TestCase(null, "add array -1", data, new ArrayDataArray(1, 2, 3, -1)),
            new TestCase(null, "add array -1 \"nowt\"", data,
                    new ArrayDataArray(1, 2, 3, -1, "nowt")),
            new TestCase(null, "join [array 0 1 2] [array 3 4]", data,
                    new ArrayDataArray(0, 1, 2, 3, 4)),
            // Maths library:
			new TestCase(null, "cos num", data, 0.5403023058681398),
			new TestCase(null, "pi num", data, 3.141592653589793),
			new TestCase(null, "sin num", data, 0.8414709848078965),
			new TestCase(null, "tan num", data, 1.5574077246549023),
            new TestCase(null, "isNull data.a", data, false),
            new TestCase(null, "isNull data.c", data, true),
            // [20]
            // null handling
			new TestCase(null, "null", data, null),
			new TestCase(null, "nullValue data.b 4", data, 2),
            new TestCase(null, "nullValue data.c 4", data, 4),
            // strings
            new TestCase(null, "ends string \"mat\"", data, true),
            new TestCase(null, "ends string \"cat\"", data, false),
			new TestCase(null, "findIn string \"cat\"", data, 4),
			new TestCase(null, "findIn string \"dog\"", data, -1),
			new TestCase(null, "findAfter string \"cat\" 3", data, 4),
			new TestCase(null, "findAfter string \"cat\" 5", data, -1),
            new TestCase(null, "findAfter string \"dog\" 5", data, -1),
            // [30]
			new TestCase(null, "findBefore string \"cat\" 3", data, -1),
			new TestCase(null, "findBefore string \"cat\" 8", data, 4),
            new TestCase(null, "findBefore string \"dog\" 5", data, -1),
			new TestCase(null, "findLast string \"the\" ", data, 15),
			new TestCase(null, "findLast string \"dog\" ", data, -1),
            new TestCase(null, "format \"add %1$d here\" 4", data, "add 4 here"),
			new TestCase(null, "format \"%1$d\" 5", data, "5"),
			new TestCase(null, "length string", data, 22),
			new TestCase(null, "toLower \"MAT\"", data, "mat"),
			new TestCase(null, "matches string \"[thecasonm ]*\"", data, true),
            // [40]
			new TestCase(null, "matches string \"x*\"", data, false),
			new TestCase(null, "replace string \"at\" \"is\"", data, "the cis sis on the mis"),
			new TestCase(null, "replaceFirst string \"at\" \"is\"", data, "the cis sat on the mat"),
			new TestCase(null, "starts string \"the\"", data, true),
			new TestCase(null, "starts string \"thy\"", data, false),
			new TestCase(null, "substr string 4 7", data, "cat"),
			new TestCase(null, "toUpper string", data, "THE CAT SAT ON THE MAT"),
            new TestCase(
                    new ArrayDataSet().put("testFunction", new ArrayDataSet()
                            .put("arguments", "a b")
                            .put("description", "add two numbers")
                            .put("expression", "a + b"))
                    , "testFunction 5 6", data, 11)

       };   // [48]
        return Arrays.copyOfRange(tests, 10, 13);
        //return tests;
    }

    public TestResult setUpFunctionCall(Object arg)
            throws ExpressionException
    {
        TestCase testCase = (TestCase)arg;
        // Load the library
        if (testCase.definition != null)
        {
            testCase.functionLibrary =
                    new FunctionLibrary(testCase.definition);
        }
        else
        {
            testCase.functionLibrary = FunctionLibrary.base();
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
