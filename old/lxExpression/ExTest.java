/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxExpression;

import java.io.File;
import java.io.IOException;
import lexa.core.data.DataSet;
import lexa.core.data.ArrayDataSet;
import lexa.core.data.io.DataReader;
import lexa.core.expression.Expression;
import lexa.core.expression.ExpressionException;
import lexa.core.expression.function.FunctionLibrary;
import lexa.core.expression.map.ExpressionMap;
import lexa.core.expression.map.MapDataSet;
/**
 *
 * @author william
 */
public class ExTest
{
    private final DataSet tests;
    private final String testList;
    private final DataSet data;
    private final Boolean silent;
	private final FunctionLibrary library;
	private final DataSet map;
	private final boolean stopOnError;

    private ExTest(DataSet testData)
            throws ExpressionException
	{
        this.stopOnError = testData.contains("stopOnError") ?
				testData.getBoolean("stopOnError") :
				false;
        this.silent = testData.contains("silent") ?
				testData.getBoolean("silent") :
				true;
        this.testList = testData.getString("testList");
        this.library = testData.contains("function") ?
				new FunctionLibrary(testData.getDataSet("function")) :
				FunctionLibrary.base();
		if (!this.silent && testData.contains("function"))
		{
			System.out.println("---Global Library:\n");
			this.library.allFunctions().stream().forEach((String function) ->
			{
				try
				{
					System.out.println(
							this.library.getFunction(function).help());
				}
				catch (ExpressionException ex)
				{
					ex.printStackTrace();
				}
			});
			System.out.println("---\n");
		}

		this.map =  testData.contains("map") ?
				testData.getDataSet("map") :
				null;
        this.data =  testData.contains("data") ?
				testData.getDataSet("data") :
				new ArrayDataSet();
        this.tests = testData.getDataSet("test");
    }

    private void testAll() {
        String[] testNames =
                (this.testList != null) ?
                        this.testList.split(" ") :
                        this.tests.keys();
		for (String testName : testNames)
		{
			boolean okay = this.test(testName);
			if (stopOnError && !okay)
			{
				break;
			}
		}
        System.out.println("-- done --");
    }

    private boolean test(String testName)
	{
        StringBuilder results =
                new StringBuilder(testName).append("\n--");
		boolean okay;
		try
		{
			DataSet test = this.tests.getDataSet(testName);
			if (test == null) {
				throw new ExpressionException("Missing test " + testName);
			}

			FunctionLibrary testLibrary = test.contains("function") ?
					new FunctionLibrary(this.library, test.getDataSet("function")) :
					this.library;
			if (test.contains("function"))
			{
				results.append("---Test Library:\n");
				testLibrary.functions().stream().forEach((String function) ->
				{
					try
					{
						results.append(
								testLibrary.getFunction(function).help()).append('\n');
					}
					catch (ExpressionException ex)
					{
						ex.printStackTrace();
					}
				});
				results.append("---\n");
			}

			String expression = test.getString("expression");
			results.append("\nExpression:").append(expression);
			boolean hasResult = test.contains("expected");
			Object expected = test.getValue("expected");
			if (hasResult) {
				results.append("\nExpected:").append(expected);
			}
			DataSet testData = new ArrayDataSet(this.data);
			if (test.contains("data"))
			{
				testData.put(test.getDataSet("data"));
			}

			// is there a map?
			DataSet testMap = this.map != null ?
					new ArrayDataSet(this.map) :
					new ArrayDataSet();
			if (test.contains("map"))
			{
				testMap.put(test.getDataSet("map"));
			}
			if (!testMap.isEmpty())
			{
				results.append("\nMap:").append(testMap);
				ExpressionMap expressionMap = new ExpressionMap(testMap,testLibrary);
				testData = new MapDataSet(expressionMap, testData);
			}

			results.append("\nData:").append(testData);

            Expression ex = Expression.parse(expression, testLibrary);
            results.append("\nEx:").append(ex);
            Object res = ex.evaluate(testData);
            results.append("\nResult:").append(res);
            results.append("\nOut:").append(testData);

            okay = (expected == res ||
                    (expected != null && expected.equals(res)));
            results.append("\nCheck:").append(okay);
        } catch (Exception ex) {
			ex.printStackTrace();
            results.append("\n*** FAILED ***\n").append(ex.getMessage());
            okay = false;
        }
        results.append("\n---\n");
        if (!silent || !okay) {
			System.err.flush();
            System.out.println(results);
        }
		return okay;
    }

    /**
     *
     * @param args
     */
    public static void main(String ... args) {
        DataSet file = null;
        try {
            String fileName = "test.expression.lexa";
            if (args != null && args.length > 0) {
                fileName = args[0];
            }
			System.out.println("loading : " + fileName);
            file = new DataReader(new File(fileName)).read();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (file == null) {
            return;
        }
        try {
            new ExTest(file).testAll();
        } catch (ExpressionException ex) {
            ex.printStackTrace();
        }

    }

}
