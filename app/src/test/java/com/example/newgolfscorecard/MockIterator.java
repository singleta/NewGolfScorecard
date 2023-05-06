package com.example.newgolfscorecard;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Iterator;

public class MockIterator {

    @SafeVarargs
    public static <T> void mockIterable(Iterable<T> iterable, T... values) {
        Iterator<T> mockIterator = mock(Iterator.class);
        when(iterable.iterator()).thenReturn(mockIterator);

        if (values.length == 0) {
            when(mockIterator.hasNext()).thenReturn(false);
        } else if (values.length == 1) {
            when(mockIterator.hasNext()).thenReturn(true, false);
            when(mockIterator.next()).thenReturn(values[0]);
        } else {
            // build boolean array for hasNext()
            Boolean[] hasNextResponses = new Boolean[values.length];
            for (int i = 0; i < hasNextResponses.length -1 ; i++) {
                hasNextResponses[i] = true;
            }
            hasNextResponses[hasNextResponses.length - 1] = false;
            when(mockIterator.hasNext()).thenReturn(true, hasNextResponses);
            T[] valuesMinusTheFirst = Arrays.copyOfRange(values, 1, values.length);
            when(mockIterator.next()).thenReturn(values[0], valuesMinusTheFirst);
        }
    }
}
