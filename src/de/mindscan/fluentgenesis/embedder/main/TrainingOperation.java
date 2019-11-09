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

import org.deeplearning4j.models.word2vec.Word2Vec;

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

        Word2Vec embedderModel = embedderTraining.embed( embeddingDataPath, windowSize, epochs, dimensions, minWordFrequency );

        embedderTraining.saveModel( embedderModel, "embeddings_" + modelName + "_w" + windowSize + "_" + dimensions + "d.zip" );
    }

    private Word2Vec embed( String embeddingDataPath, int windowSize, int epochs, int dimensions, int minWordFrequency ) {
        // TODO Auto-generated method stub
        return null;
    }

    private void saveModel( Word2Vec embedderModel, String string ) {
        // TODO Auto-generated method stub
    }

}
