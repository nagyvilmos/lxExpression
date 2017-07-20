/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * TestExpressionScript.java (lxExpression)
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: July 2017
 *==============================================================================
 */
package lxexpression;

import lexa.core.data.ArrayDataArray;
import lexa.core.data.ArrayDataSet;
import lexa.core.data.DataSet;
import lexa.core.expression.ExpressionException;
import lexa.core.expression.map.ExpressionMap;
import lexa.core.expression.map.MapDataSet;
import lexa.test.TestAnnotation;
import lexa.test.TestClass;
import lexa.test.TestResult;

/**
 * Test the {@link lexa.core.expression.map} package
 * @author  williamnw
 * @since   2017-07
 */
public class TestMappedData extends TestClass
{

    public Object[] testCases()
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

        return new TestCase[] {
            new TestCase(
                    "Simple",
                    new ArrayDataSet()
                        .put("a", "2")
                        .put("b", "4"),
                    data,
                    new ArrayDataSet()
                        .put("a", 2)
                        .put("b", 4)
            )
       };
    }

    public TestResult setUpExpressionMap(Object arg)
            throws ExpressionException
    {
        TestCase testCase = (TestCase)arg;
        testCase.expressionMap = new ExpressionMap(
                testCase.definition
        );
        testCase.mapDataSet = new MapDataSet(
                testCase.expressionMap,
                testCase.input
        );
        return TestResult.all(
                TestResult.isClass(
                        "lexa.core.expression.map.ExpressionMap",
                        testCase.expressionMap),
                TestResult.isClass(
                        "lexa.core.expression.map.MapDataSet",
                        testCase.mapDataSet),
                TestResult.assignableTo(
                        "lexa.core.data.DataSet",
                        testCase.mapDataSet)
        );
    }

    @TestAnnotation(arguments = "testCases",
            setUp = "setUpExpressionMap")
    public TestResult evaluateMap(Object arg)
            throws ExpressionException
    {
        TestCase testCase = (TestCase)arg;
        DataSet result = testCase.mapDataSet.evaluate();
        return TestResult.result(testCase.result, result);
    }


    /**
     * A test case for the expression map
     */
    class TestCase
    {
        final String  description;
        final DataSet definition;
        final DataSet input;
        final DataSet result;

        ExpressionMap expressionMap;
        MapDataSet mapDataSet;

        /**
         * Create a test case for the expression map
         * @param   description
         *          description for the test
         * @param   definition
         *          define the map
         * @param   input
         *          input data for the map
         * @param   result
         *          expected result of full evaluation
         */
        TestCase(String description,
                DataSet definition,
                DataSet input,
                DataSet result)
        {
            this.description    = description;
            this.definition     = definition;
            this.input          = input.factory().clone(input);
            this.result         = result;
        }

        @Override
        public String toString()
        {
            return this.description;
        }
    }
}
