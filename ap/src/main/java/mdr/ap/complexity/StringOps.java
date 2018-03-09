package mdr.ap.complexity;

import mdr.ap.util.NotNull;

/**
 *  Study the complexity of various string operations
 *  @author mross
 */
public class StringOps {
    
    /**
     *  @param inN number of times to perform the specified operation
     */
    public StringOps(final int inN) {
        _n = inN;
    }

    @NotNull
    public String appendViaPlusEquals() {
        String accumulator = "";
        for (int i = 0; i < _n; i++) {
            // NOTE: string concatenation like this is generally a "no-no"
            accumulator += WORD;
        }
        return accumulator;
    }

    @NotNull
    public String appendViaStringBuilder() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < _n; i++) {
            stringBuilder.append(WORD);
        }
        return stringBuilder.toString();
    }

    private final int _n;

    private static final String WORD = "word ";

}
