/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxExpression;

import java.io.File;
import java.io.IOException;
import lexa.core.data.DataSet;
import lexa.core.data.io.DataReader;
import lexa.core.expression.function.FunctionLibrary;
import lexa.test.TestClass;
import lexa.test.TestAnnotation;

/**
 * Test the expression evaluator.
 * Expects a file in the runtime folder, formated as:
 * <pre>
 * [silent ? &lt;run silent&gt;]
 * [testList - &lt;list of tests&gt;]
 * [function {
 *   &lt;global functions&gt;
 * }]
 * [data {
 *   &lt;global data&gt;
 * }]
 * test {
 *   &lt;name&gt; {
 *     expression - &lt;expression&gt;
 *     expected &lt;expected result&gt;
 *     [data {
 *       &lt;test data&gt;
 *     }]
 *     [map {
 *       &lt;expression map&gt;
 *     }]
 *     [function {
 *       &lt;test functions&gt;
 *     }]
 *   }
 *   [...]
 * }
 * </pre>
 * Where:
 * <dl>
 * <dt>&lt;run silent&gt;</dt><dd>indicate if output is suppressed when the expected results
 *		are returned; defaults to {@code true}.</dd>
 * <dt>&lt;list of tests&gt;</dt><dd>a space separated list of tests to perform;
 *		defaults to all the tests listed.</dd>
 * <dt>&lt;global functions&gt;</dt><dd>a set of {@link FunctionLibrary functions} available
 *		to all the tests.</dd>
 * <dt>&lt;global data&gt;</dt><dd>data to be used by all the tests.</dd>
 * <dt>&lt;name&gt;</dt><dd>the name for a test.</dd>
 * <dt>&lt;expression&gt;</dt><dd>an expression to test.</dd>
 * <dt>&lt;expected result&gt;</dt><dd>the expected result of the test.</dd>
 * <dt>&lt;test functions&gt;</dt><dd>a set of {@link FunctionLibrary functions} for this test.</dd>
 * <dt>&lt;test data&gt;</dt><dd>data to be used by this test.</dd>
 * <dt>&lt;expression map&gt;</dt><dd>Expression map to use to generate the test data; the map is evaluated
 *		from the source data and the result passed through the test expression.</dd>
 * </dl>
 * 
 * @author william
 */
@TestAnnotation(setUp = "loadTestFile")        
public class TestExpressionScript extends TestClass
{

    private final String testFile;
    private DataSet testData;
    TestExpressionScript(String testFile)
    {
        this.testFile = testFile;
    }
    
    /**
     *
     * @return
     * @throws IOException
     */
    public Boolean loadTestFile()
            throws IOException
    {
        this.testData = new DataReader(new File(this.testFile)).read();
//        this.stopOnError = testData.contains("stopOnError") ?
//				testData.getBoolean("stopOnError") :
//				false;
//        this.silent = testData.contains("silent") ?
//				testData.getBoolean("silent") :
//				true;
    return true;
    }
    
    /**
     *
     * @return
     */
    public Object[] testList()
    {
        return this.testData.contains("function") ?
                this.testData.getString("testList").split(" ") :
                this.testData.getDataSet("test").keys();
    }
    
//        return this.testData.contains("function") ?
//				new FunctionLibrary(testData.getDataSet("function")) :
//				FunctionLibrary.base();
//		if (!this.silent && testData.contains("function"))
//		{
//			System.out.println("---Global Library:\n");
//			this.library.allFunctions().stream().forEach((String function) ->
//			{
//				try
//				{
//					System.out.println(
//							this.library.getFunction(function).help());
//				}
//				catch (ExpressionException ex)
//				{
//					ex.printStackTrace();
//				}
//			});
//			System.out.println("---\n");
//		}
//
//		this.map =  testData.contains("map") ?
//				testData.getDataSet("map") :
//				null;
//        this.data =  testData.contains("data") ?
//				testData.getDataSet("data") :
//				new SimpleDataSet();
//        this.tests = testData.getDataSet("test");
//    }
}
