package de.mindscan.fluentgenesis.embedder.sentenceiterator;

import java.io.File;
import java.util.Iterator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DirectoryCrawlingProviderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGetDirectoryIterator_iterateOverAllFiles_doesNotThrowException() throws Exception {
        // arrange
        Iterator<File> directoryIterator = DirectoryCrawlingProvider.getDirectoryIterator( "D:\\Downloads\\Big-Code-excerpt", "(\\.java.json)" );

        // act
        // assert
        while (directoryIterator.hasNext()) {
            File next = directoryIterator.next();
            System.out.println( next.getAbsolutePath() );
        }
    }

}
