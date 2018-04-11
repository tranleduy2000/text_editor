package org.gjt.sp.jedit.syntax;

/**
 * A simple text style class. It can specify the color, italic flag,
 * and bold flag of a run of text.
 *
 * @author Slava Pestov
 * @version $Id: SyntaxStyle.java 21831 2012-06-18 22:54:17Z ezust $
 */
public class SyntaxStyle {
    //{{{ SyntaxStyle constructor

    //{{{ Private members
    private int fgColor;

    //{{{ getForegroundColor() method
    private int bgColor;

    //{{{ getBackgroundColor() method

    /**
     * Creates a new SyntaxStyle.
     *
     * @param fgColor The text color
     * @param bgColor The background color
     */
    public SyntaxStyle(int fgColor, int bgColor) {
        this.fgColor = fgColor;
        this.bgColor = bgColor;
    } //}}}

    /**
     * Returns the text color.
     */
    public int getForegroundColor() {
        return fgColor;
    } //}}}

    /**
     * Returns the background color.
     */
    public int getBackgroundColor() {
        return bgColor;
    } //}}}
    //}}}
}
