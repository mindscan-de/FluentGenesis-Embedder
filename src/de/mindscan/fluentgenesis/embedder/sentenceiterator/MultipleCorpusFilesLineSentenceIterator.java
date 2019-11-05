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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.deeplearning4j.text.sentenceiterator.BaseSentenceIterator;

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

    /**
     * 
     */
    public MultipleCorpusFilesLineSentenceIterator( String basePath ) {
        this( basePath, "(\\.java.json)" );
    }

    public MultipleCorpusFilesLineSentenceIterator( String basePath, String allowedExtension ) {
        this.basePath = basePath;
        this.fileExtension = allowedExtension;

        this.corpusFileIterator = createFileIterator( this.basePath, this.fileExtension );

        initFirstFile();
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

        if (preProcessor != null) {
            return preProcessor.preProcess( line );
        }

        return line;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        // TODO Auto-generated method stub
        // check if there is more in corpus file or in next corpus file.
        return false;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        // TODO Auto-generated method stub

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
        // fileStream = new BufferedInputStream( new FileInputStream( currentFile ) );

        System.out.println( "Processing file: " + currentFile.getAbsolutePath() );
    }

}
