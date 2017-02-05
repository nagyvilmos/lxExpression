/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Modulus.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * DD-MON-YY    ??
 *================================================================================
 */
package lexa.core.expression;

import java.util.HashMap;

/**
 * Types of word supported by the expression evaluator.
 * @author William
 * @since 2013-03
 */
public enum WordType {
    /** A null word - indicates end of expression */
    NULL(null),
    /** Any word providing a literal value or id */
    LITERAL(""),
    /** End of expression */
    END_OF_EXPRESSION(";"),
    /** Indicates the value is required */
    VALUE("@"),
    /** In line IF */
    IF("?"),
    /** In line ELSE */
    ELSE(":"),
    /** A unary not */
    NOT("!"),
    /** the open bracket symbol */
    OPEN_BRACKET("("),
    /** the -- symbol */
    CLOSE_BRACKET(")"),
    /** the boolean and symbol */
    AND("&&"),
    /** the boolean or symbol */
    OR("||"),
    /** the addition symbol */
    ADD("+"),
    /** the subtraction symbol */
    SUBTRACT("-"),
    /** the multiplication symbol */
    MULTIPLY("*"),
    /** the division symbol */
    DIVIDE("/"),
    /** the assignment symbol */
    ASSIGN("="),
    /** the less than symbol */
    LESS_THAN("<"),
    /** the less than or equals symbol */
    LESS_THAN_OR_EQUALS("<="),
    /** the equals symbol */
    EQUALS("=="),
    /** the not equals symbol */
    NOT_EQUALS("!="),
    /** the greater than or equals symbol */
    GREATER_THAN_OR_EQUALS(">="),
    /** the greater than symbol */
    GREATER_THAN(">"),
    /** the power symbol */
    POWER ("^"),
    /** the modulus symbol */
    MODULUS ("%"),
    /** start of function */
    START_FUNCTION ("["),
    /** end of function */
    END_FUNCTION ("]");

    /** The string value for this enum */
    private final String value;

    /**
     * Constructor for a {@see WordType}
     * @param value the string value for the enum.
     */
    private WordType(String value) {
        this.value = value;
    }

    /**
     * Returns the string value for the enum.
     * @return the string value for the enum
     */
    public String getValue() {
        if (this.equals(WordType.NULL)) {
            return "[null]";
        }
        if (this.equals(WordType.LITERAL)) {
            return "[literal]";
        }
        return this.value;
    }

    /** A mapping of values to enums. */
    private static final HashMap<String, WordType> backRef = new HashMap<String, WordType>();

	/**
	 * Get the word type for a given string.
	 * 
	 * @param word the word as a string.
	 * @return the type of word the string represents.
	 */
    public static WordType getType(String word) {
        if (word == null) {
            return WordType.NULL;
        }
        if (backRef.isEmpty()) {
            for (int w = 0;
                    w<WordType.values().length;
                    w++) {
                WordType type = WordType.values()[w];
                if (type != WordType.NULL) {
                    backRef.put (type.value ,type);
                }
            }
        }
        WordType type = backRef.get(word);
        if (type == null) {
            return WordType.LITERAL;
        }
        return type;
    }
}
