package de.mindscan.fluentgenesis.embedder.sentenceiterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.deeplearning4j.text.sentenceiterator.BaseSentenceIterator;
import org.junit.jupiter.api.Test;

public class MultipleCorpusFilesLineSentenceIteratorTest {

    @Test
    public void testCreateCorpusIterator() throws Exception {
        // arrange
        BaseSentenceIterator iterator = MultipleCorpusFilesLineSentenceIterator.createCorpusIterator( "D:\\Downloads\\Big-Code-excerpt\\1datapoint_1",
                        "(\\.java.json)" );

        boolean result = iterator.hasNext();

        assertThat( result, equalTo( true ) );
    }

    @Test
    public void testCreateCorpusIterator_1() throws Exception {
        // arrange
        BaseSentenceIterator iterator = MultipleCorpusFilesLineSentenceIterator.createCorpusIterator( "D:\\Downloads\\Big-Code-excerpt\\1datapoint_1",
                        "(\\.java.json)" );

        iterator.hasNext();
        String result = iterator.nextSentence();

        System.out.println( result );
    }

    @Test
    public void testHasNext_iterateAllFiles_numberOfIteratedFilesIsNotZero() throws Exception {
        // arrange
        BaseSentenceIterator iterator = MultipleCorpusFilesLineSentenceIterator.createCorpusIterator( "D:\\Downloads\\Big-Code-excerpt\\1datapoint_1",
                        "(\\.java.json)" );

        int numberOfFiles = 0;
        while (iterator.hasNext()) {
            iterator.nextSentence();
            numberOfFiles++;
        }

        assertThat( numberOfFiles, is( not( equalTo( 0 ) ) ) );
    }

    @Test
    public void testReset_iterateAllFilesOnceThenResetThenIterateAllAgain_expectNumbersOfIteratedFilesIsSame() throws Exception {
        // arrange
        BaseSentenceIterator iterator = MultipleCorpusFilesLineSentenceIterator.createCorpusIterator( "D:\\Downloads\\Big-Code-excerpt\\1datapoint_1",
                        "(\\.java.json)" );

        int firstRun = 0;

        while (iterator.hasNext()) {
            // must not be skipped... (yet)
            iterator.nextSentence();
            firstRun++;
        }

        iterator.reset();

        int secondRunAfterReset = 0;
        while (iterator.hasNext()) {
            // must not be skipped... (yet)
            iterator.nextSentence();
            secondRunAfterReset++;
        }

        assertThat( secondRunAfterReset, is( equalTo( firstRun ) ) );
    }

}
