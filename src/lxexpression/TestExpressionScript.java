/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * TestExpressionScript.java (lxExpression)
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: April 2017
 *==============================================================================
 */
package lxexpression;

import java.io.File;
import java.io.IOException;
import lexa.core.data.DataSet;
import lexa.core.data.io.DataReader;
import lexa.test.TestClass;
import lexa.test.TestAnnotation;
import lexa.test.TestResult;
import lexa.core.expression.function.FunctionLibrary;


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
 * @author  william
 * @since   2017-04
 */
@TestAnnotation(setUp = "loadTestFile")
public class TestExpressionScript extends TestClass
{

    private boolean silent;

    private boolean stopOnError;

    private final File testFile;
    private DataSet testData;
    private String[] testList;
    TestExpressionScript(String testFile)
    {
        this.testFile = new File(testFile);
    }

    /**
     * Load the test file
     * This just reads in the file, ie the data set containing the test
     * script to perform
     * @return
     * @throws java.io.FileNotFoundException
     */
    public TestResult loadTestFile()
            throws IOException
    {
        if (!this.testFile.exists())
        {
            return new TestResult(false, "File does not exist : " + this.testFile.getAbsolutePath());
        }
        this.testData = new DataReader(this.testFile).read();

        this.stopOnError = this.testData.contains("stopOnError") ?
				this.testData.getBoolean("stopOnError") :
				false;
        this.silent = this.testData.contains("silent") ?
				this.testData.getBoolean("silent") :
				true;

        this.testList = this.testData.contains("testList") ?
                this.testData.getString("testList").split(" ") :
                this.testData.getDataSet("test").keys();

        // we have results, that's all we need
        return new TestResult(true);
    }

    public Object[] testList()
    {
        return this.testList;
    }

    public TestResult setUpTest(Object test)
    {
        String testName = (String)test;

        return TestResult.all(
                this.setUpEnvironment(testName),
                this.setUpFunctions(testName),
                this.setUpData(testName)
        );
    }

    private TestResult setUpEnvironment(String testName)
    {
        return TestResult.result(false);
    }

    private TestResult setUpFunctions(String testName)
    {
        return TestResult.result(false);
    }

    private TestResult setUpData(String testName)
    {
        return TestResult.result(false);
    }

//    /**
//     *
//     * @return
//     */
//    public Object[] testList()
//    {
//        return this.testData.contains("function") ?
//                this.testData.getString("testList").split(" ") :
//                this.testData.getDataSet("test").keys();
//    }
//
////        return this.testData.contains("function") ?
////				new FunctionLibrary(testData.getDataSet("function")) :
////				FunctionLibrary.base();
////		if (!this.silent && testData.contains("function"))
////		{
////			System.out.println("---Global Library:\n");
////			this.library.allFunctions().stream().forEach((String function) ->
////			{
////				try
////				{
////					System.out.println(
////							this.library.getFunction(function).help());
////				}
////				catch (ExpressionException ex)
////				{
////					ex.printStackTrace();
////				}
////			});
////			System.out.println("---\n");
////		}
////
////		this.map =  testData.contains("map") ?
////				testData.getDataSet("map") :
////				null;
////        this.data =  testData.contains("data") ?
////				testData.getDataSet("data") :
////				new ArrayDataSet();
////        this.tests = testData.getDataSet("test");
////    }
}
