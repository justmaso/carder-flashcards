package views;

import javax.swing.*;

/**
 * A flagged text area that fixes a nasty bug with focusing policies.
 */
public class FlaggedJTextArea extends JTextArea {
    private boolean flag;

    public FlaggedJTextArea(String text, boolean flag) {
        super(text, 1, 23);
        this.flag = flag;
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    public FlaggedJTextArea(boolean flag) {
        super(1, 23);
        this.flag = flag;
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    /**
     * Gets the flag.
     * @return the flag.
     */
    public boolean getFlag() {
        return flag;
    }
}
