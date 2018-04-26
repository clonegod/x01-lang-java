package com.asynclife.basic.advanced.methods.java7;

public class JavadocMethod {

    public static void main(String[] args) {
        new JavadocMethod().parse("20");
    }

    /**
     * The method parses the string argument as a signed decimal integer. The
     * characters in the string must all be decimal digits, except that the
     * first character may be a minus sign {@code '-'} or plus sign {@code '+'}.
     *
     * <p>
     * An exception of type {@code NumberFormatException} is thrown if string is
     * {@code null} or has length of zero.
     *
     * <p>
     * Examples:
     * <blockquote><pre>
     * parse( "0" ) returns 0
     * parse( "+42") returns 42
     * parse( "-2" ) returns -2
     * parse( "string" ) throws a NumberFormatException
     * </pre></blockquote>
     *
     * @param str a {@code String} containing the {@code int} representation to
     * be parsed
     * @return the integer value represented by the string
     * @exception NumberFormatException if the string does not contain a valid
     * integer value
     */
    public int parse(String str) throws NumberFormatException {
        return Integer.parseInt(str);
    }
}
