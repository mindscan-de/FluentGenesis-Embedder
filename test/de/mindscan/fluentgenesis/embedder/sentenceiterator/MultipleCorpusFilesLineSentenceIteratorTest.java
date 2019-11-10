package de.mindscan.fluentgenesis.embedder.sentenceiterator;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.deeplearning4j.text.sentenceiterator.BaseSentenceIterator;
import org.junit.Test;

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
    public void testCreateCorpusIterator_iterateAllFiles() throws Exception {
        // arrange
        BaseSentenceIterator iterator = MultipleCorpusFilesLineSentenceIterator.createCorpusIterator( "D:\\Downloads\\Big-Code-excerpt\\1datapoint_1",
                        "(\\.java.json)" );

        while (iterator.hasNext()) {
            String result = iterator.nextSentence();
            System.out.println( result );
        }

    }

}
