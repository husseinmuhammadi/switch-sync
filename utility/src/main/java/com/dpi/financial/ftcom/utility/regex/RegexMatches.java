package com.dpi.financial.ftcom.utility.regex;

import com.dpi.financial.ftcom.utility.atm.journal.ATMConstant;
import com.dpi.financial.ftcom.utility.exception.MultipleMatchException;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches {
    public RegexMatches(String input, String regex) {
        super();
        this.regex = regex;
        this.input = input;
    }

    private String regex;
    private String input;

    /**
     * @return the regex
     */
    public String getRegex() {
        return regex;
    }

    /**
     * @param regex the regex to set
     */
    public void setRegex(String regex) {
        this.regex = regex;
    }

    /**
     * @return the input
     */
    public String getInput() {
        return input;
    }

    /**
     * @param input the input to set
     */
    public void setInput(String input) {
        this.input = input;
    }

    public void printAllMatches() {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        int count = 0;

        while (matcher.find()) {
            count++;
            System.out.println("Match number " + count);
            System.out.println("start(): " + matcher.start());
            System.out.println("end(): " + matcher.end());
        }
    }

    public static Iterable<MatchResult> allMatches(final String regex, final CharSequence input) {
        return new Iterable<MatchResult>() {
            @Override
            public Iterator<MatchResult> iterator() {
                return new Iterator<MatchResult>() {
                    // Use a matcher internally.
                    final Matcher matcher = Pattern.compile(regex).matcher(input);
                    // Keep a match around that supports any interleaving of
                    // hasNext/next calls.
                    MatchResult pending;

                    @Override
                    public boolean hasNext() {
                        // Lazily fill pending, and avoid calling find() multiple times if the
                        // clients call hasNext() repeatedly before sampling via next().
                        if (pending == null && matcher.find()) {
                            pending = matcher.toMatchResult();
                        }
                        return pending != null;
                    }

                    @Override
                    public MatchResult next() {
                        // Fill pending if necessary (as when clients call
                        // next() without
                        // checking hasNext()), throw if not possible.
                        if (!hasNext()) {
                            throw new NoSuchElementException();
                        }
                        // Consume pending so next call to hasNext() does a
                        // find().
                        MatchResult next = pending;
                        pending = null;
                        return next;
                    }

                    /** Required to satisfy the interface, but unsupported. */
                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    public static MatchResult getSingleResult(final String regex, final CharSequence input) throws MultipleMatchException {
        // System.out.println("---> look for : "+regex);
        Iterable<MatchResult> iterable = allMatches(regex, input);
        Iterator<MatchResult> iterator = iterable.iterator();
        if (!iterator.hasNext())
            return null;
        MatchResult result = iterator.next();
        if (!regex.equals(ATMConstant.ATM_REGEX_TRANSACTION_ANY) && iterator.hasNext())
            throw new MultipleMatchException();

        return result;
    }

}