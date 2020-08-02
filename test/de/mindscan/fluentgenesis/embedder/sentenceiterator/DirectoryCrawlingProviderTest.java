package de.mindscan.fluentgenesis.embedder.sentenceiterator;

import java.io.File;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class DirectoryCrawlingProviderTest {

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
