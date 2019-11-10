/**
 * 
 * MIT License
 *
 * Copyright (c) 2019 Maxim Gansert, Mindscan
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 */
package de.mindscan.fluentgenesis.embedder.sentenceiterator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.deeplearning4j.text.sentenceiterator.BaseSentenceIterator;

import com.google.gson.Gson;

import de.mindscan.fluentgenesis.embedder.types.ModelBPEContent;

/**
 * We need a SentenceIterator for training the Word2Vec Model.
 * 
 * The thing is, that this SentenceIterator will iterate over all corpus files, 
 * instead of one corpus file containing all training data.
 */
public class MultipleCorpusFilesLineSentenceIterator extends BaseSentenceIterator {
    private final String basePath;
    private final String fileExtension;
    private Iterator<File> corpusFileIterator;

    private File currentFile;
    private int currentTokenLine = 0;

    /**
     * 
     */
    public MultipleCorpusFilesLineSentenceIterator( String basePath ) {
        this( basePath, "(\\.java.json)" );
    }

    public MultipleCorpusFilesLineSentenceIterator( String basePath, String allowedExtension ) {
        this.basePath = basePath;
        this.fileExtension = allowedExtension;
        this.currentTokenLine = 0;

        this.corpusFileIterator = createFileIterator( this.basePath, this.fileExtension );

    }

    public static BaseSentenceIterator createCorpusIterator( String basePath, String allowedExtension ) {
        MultipleCorpusFilesLineSentenceIterator corpusIterator = new MultipleCorpusFilesLineSentenceIterator( basePath, allowedExtension );

        corpusIterator.initFirstFile();

        return corpusIterator;
    }

    protected Iterator<File> createFileIterator( String basePath, String allowedExtension ) {
        return DirectoryCrawlingProvider.getDirectoryIterator( basePath, allowedExtension );
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public String nextSentence() {
        // read content from the current corpus json file and convert the array of bpe encoded data into whitespace separated "words"
        String line = "";
        try (BufferedReader jsonBufferedReader = Files.newBufferedReader( Paths.get( this.currentFile.getAbsolutePath() ), StandardCharsets.UTF_8 )) {
            Gson gson = new Gson();
            ModelBPEContent content = gson.fromJson( jsonBufferedReader, ModelBPEContent.class );
            line = String.join( " ", content.getTokenData().stream().map( x -> encodeToWord( x ) ).collect( Collectors.toList() ) );

            currentTokenLine++;
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (preProcessor != null) {
            return preProcessor.preProcess( line );
        }

        return line;
    }

    // fucking workaround for word2vec, because word2vec won't accept numbers as words, so each number is vconverted into a string
    // and then '0' is replaced by lowercase 'a', '1' by 'b' and so on ...
    private String encodeToWord( Integer i ) {
        byte[] bytes = i.toString().getBytes();
        for (int j = 0; j < bytes.length; j++) {
            bytes[j] += 49;
        }

        return new String( bytes );
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        if (currentFileHasNext()) {
            return true;
        }

        // find next useful file, and then have a look into the file...
        while (cleanupAndThenPrepareNextFile()) {
            if (currentFileHasNext()) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return
     */
    private boolean currentFileHasNext() {
        return currentTokenLine == 0;
    }

    /**
     * @return
     */
    private boolean cleanupAndThenPrepareNextFile() {
        try {

            if (!corpusFileIterator.hasNext()) {
                // if there is no next file in corpus, there is not next file to prepare
                return false;
            }

            initNextFile();

            return true;
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException( "Error while cleanup and prepare next file to read " );
        }

    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        try {
            corpusFileIterator = createFileIterator( basePath, fileExtension );

            initNextFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initFirstFile() {
        try {
            if (this.corpusFileIterator.hasNext()) {
                initNextFile();
            }
            else {
                this.currentFile = null;
            }
        }
        catch (IOException ioe) {

        }
    }

    private void initNextFile() throws FileNotFoundException, IOException {
        currentFile = corpusFileIterator.next();
        currentTokenLine = 0;

        System.out.println( "Processing file: " + currentFile.getAbsolutePath() );
    }

}
