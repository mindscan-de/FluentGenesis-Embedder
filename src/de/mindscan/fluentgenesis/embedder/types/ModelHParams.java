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
package de.mindscan.fluentgenesis.embedder.types;

/**
 * Current model of the hparams file.
 * 
 * TODO: 
 * - add embedding dimensions,
 * - add real vocabulary size to model ?
 * - add windowsize
 * - add epochs
 * - add minWordFrequency
 * 
 */
public class ModelHParams {

    private String global_wordlist;
    private String token_bpefile;
    private String token_filename;
    private String path;
    private int split_if_occurence_less_than;
    private int tokens_to_emit;

    public String getPath() {
        return path;
    }

    public String getGlobal_wordlist() {
        return global_wordlist;
    }

    public int getSplit_if_occurence_less_than() {
        return split_if_occurence_less_than;
    }

    public String getTokenBPEfile() {
        return token_bpefile;
    }

    public String getTokenFilename() {
        return token_filename;
    }

    public int getTokens_to_emit() {
        return tokens_to_emit;
    }
}
