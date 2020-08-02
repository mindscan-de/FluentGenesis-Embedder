/**
 * 
 * MIT License
 *
 * Copyright (c) 2020 Maxim Gansert, Mindscan
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

import java.nio.file.Path;
import java.util.concurrent.Callable;

import picocli.CommandLine.Option;

/**
 * 
 */
class TrainingOperationParameters implements Callable<Integer> {

    @Option( names = "--hparams", defaultValue = "D:\\Projects\\SinglePageApplication\\Angular\\FluentGenesis-Classifier\\src\\de\\mindscan\\fluentgenesis\\bpe" )
    private Path hparamsPath;

    @Option( names = "--modelName", defaultValue = "16K-full" )
    private String modelName;

    /** 
     * {@inheritDoc}
     */
    @Override
    public Integer call() throws Exception {
        TrainingOperation main = new TrainingOperation( this );
        main.run();
        return 0;
    }

    /**
     * @return the modelName
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * @return the hparamsPath
     */
    public Path getHparamsPath() {
        return hparamsPath;
    }
}
