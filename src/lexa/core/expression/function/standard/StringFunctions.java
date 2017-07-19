/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * InternalFunction.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2013
 *================================================================================
 */
package lexa.core.expression.function.standard;

import lexa.core.data.DataSet;
import lexa.core.expression.function.Function;

/**
 * Internal string handling functions.
 * @author william
 * @since 2013-09
 */
public class StringFunctions
{
	private StringFunctions() { }

    /**
     * Get the internal string handling functions
     * @return an array of functions.
     */
    public static Function[] getFunctions()
	{
		Function[] functions =
		{
			ends(),
			findIn(),
			findAfter(),
			findBefore(),
			findLast(),
			format(),
			length(),
			toLower(),
			matches(),
			replace(),
			replaceFirst(),
			starts(),
			substr(),
			toUpper()
		};

		return functions;
	}

	private static Function ends()
	{
		return new InternalFunction("ends", "string", "end")
		{
			@Override
			public String describe()
			{
				return "check if a string ends with a value";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").endsWith(arguments.getString("end"));
			}
		};
	}

	private static Function findIn()
	{
		return new InternalFunction("findIn", "string", "find")
		{
			@Override
			public String describe()
			{
				return "check if a string contains a value";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").indexOf(arguments.getString("find"));
			}
		};
	}
	private static Function findAfter()
	{
		return new InternalFunction("findAfter", "string", "find", "start")
		{
			@Override
			public String describe()
			{
				return "check if a string contains a value after a given postion";
			}
			@Override
			public Object execute(DataSet arguments)
			{
					return arguments.getString("string").indexOf(arguments.getString("find"),
							arguments.getInteger("start"));
			}
		};
	}

	private static Function findBefore()
	{
		return new InternalFunction("findBefore", "string", "find", "before")
		{
			@Override
			public String describe()
			{
				return "check if a string contains a value before a given postion";
			}
			@Override
			public Object execute(DataSet arguments)
			{
					return arguments.getString("string").lastIndexOf(arguments.getString("find"),
							arguments.getInteger("before"));
			}
		};
	}

	private static Function findLast()
	{
		return new InternalFunction("findLast", "string", "find")
		{
			@Override
			public String describe()
			{
				return "check if a string contains a value from the back";
			}
			@Override
			public Object execute(DataSet arguments)
			{
					return arguments.getString("string").lastIndexOf(arguments.getString("find"));
			}
		};
	}

	private static Function format()
	{

		return new InternalFunction("format", "string", "~")
					// ~ the asteriks means 0 or many more args
		{
			@Override
			public String describe()
			{
				return "create a formated string";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				Object[] args = new Object[arguments.size()-1];
				for (int a = 0;
						a < args.length;
						a++) {
					Object o = arguments.getObject("~" + a);
					args[a] = o == null ? ":NULL:" : o;
				}
				String fs = arguments.getString("string");
				return String.format(fs, args);
			}
		};
	}

	private static Function length()
	{
		return new InternalFunction("length", "string")
		{
			@Override
			public String describe()
			{
				return "get the length of a string";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").length();
			}
		};
	}

	private static Function toLower()
	{
		return new InternalFunction("toLower", "string")
		{
			@Override
			public String describe()
			{
				return "convert a string to lower case";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").toLowerCase();
			}
		};
	}

	private static Function matches()
	{
		return new InternalFunction("matches", "string", "regrxp")
		{
			@Override
			public String describe()
			{
				return "check if a string matches a regular expression";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").matches(arguments.getString("regrxp"));
			}
		};
	}

	private static Function replace()
	{
		return new InternalFunction("replace", "string", "search", "replace")
		{
			@Override
			public String describe()
			{
				return "replace all values in a string";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").replaceAll(
						arguments.getString("search"), arguments.getString("replace"));
			}
		};
	}
	private static Function replaceFirst()
	{
		return new InternalFunction("replaceFirst", "string", "search", "replace")
		{
			@Override
			public String describe()
			{
				return "replace the first occurance of a value in a string";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").replaceFirst(
						arguments.getString("search"), arguments.getString("replace"));
			}
		};
	}

	private static Function starts()
	{
		return new InternalFunction("starts", "string", "search")
		{
			@Override
			public String describe()
			{
				return "check if a string starts with a value";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").
						startsWith(arguments.getString("search"));
			}
		};
	}

	private static Function substr()
	{
		return new InternalFunction("substr", "string", "start", "end")
		{
			@Override
			public String describe()
			{
				return "create a substring from positions start to end";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").substring(
						arguments.getInteger("start"), arguments.getInteger("end"));
			}
		};
	}

	private static Function toUpper()
	{
		return new InternalFunction("toUpper", "string")
		{
			@Override
			public String describe()
			{
				return "convert a string to upper case";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").toUpperCase();
			}
		};
	}
}
