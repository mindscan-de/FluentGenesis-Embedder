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
package de.mindscan.fluentgenesis.embedder.main;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentencePreProcessor;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import de.mindscan.fluentgenesis.embedder.sentenceiterator.MultipleCorpusFilesLineSentenceIterator;

/**
 * 
 */
public class TrainingOperation {

    public static void main( String[] args ) {

        // TODO: load these information from the hparams files
        int windowSize = 10;
        int epochs = 1;
        int dimensions = 1280;
        int minWordFrequency = 1;
        String modelName = "excerpt-10k";
        String embeddingDataPath = "D:\\Downloads\\Big-Code-excerpt";

        TrainingOperation embedderTraining = new TrainingOperation();

        Word2Vec embeddedTokens = embedderTraining.fitEmbeddings( embeddingDataPath, windowSize, epochs, dimensions, minWordFrequency );

        embedderTraining.saveModel( embeddedTokens, "embeddings_" + modelName + "_w" + windowSize + "_" + dimensions + "d.zip" );
    }

    private Word2Vec fitEmbeddings( String embeddingDataPath, int windowSize, int epochs, int dimensions, int minWordFrequency ) {
        System.out.println( "Load and vectorize bpe encodings" );

        TokenizerFactory tFactory = new DefaultTokenizerFactory();
        tFactory.setTokenPreProcessor( new CommonPreprocessor() );

        @SuppressWarnings( "serial" )
        SentencePreProcessor sPreProcessor = new SentencePreProcessor() {
            @Override
            public String preProcess( String sentence ) {
                // since we only have numbers as words, we do not have to care about this. 
                return sentence.toLowerCase();
            }
        };

        SentenceIterator sentenceIterator = new MultipleCorpusFilesLineSentenceIterator( embeddingDataPath );
        sentenceIterator.setPreProcessor( sPreProcessor );

        Word2Vec embeddings = new Word2Vec.Builder() //
                        .useUnknown( true ) // Use placeholder for the unknown word - bpe encodings should be fine and be able to encode unknown tokens
                        .iterations( epochs ) // Number of time to fit this model
                        .layerSize( dimensions ) // Number of dimensions
                        .windowSize( windowSize ) // number of tokens left and right to the element
                        .minWordFrequency( minWordFrequency ) // number of minmal word occurence
                        .seed( 1337 ) //
                        .tokenizerFactory( tFactory ) //
                        .iterate( sentenceIterator ) //
                        .build();

        embeddings.fit();

        return embeddings;
    }

    private void saveModel( Word2Vec embeddedTokens, String targetFilename ) {
        WordVectorSerializer.writeWord2VecModel( embeddedTokens, targetFilename );
    }

}
