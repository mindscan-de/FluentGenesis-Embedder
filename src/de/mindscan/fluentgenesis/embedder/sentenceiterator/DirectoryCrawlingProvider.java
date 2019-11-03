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
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

/**
 * This class provides an iterator of files which will crawl every directory below a given root path.
 * 
 * This class is just a helper class, which might be implemented more easier, but for the moment it is good enough.
 * 
 * This can probably be replaced by java.nio.Files? Maybe investigate later...
 */
public class DirectoryCrawlingProvider {

    public static Iterator<File> getDirectoryIterator( String rootPath, String allowedExtension ) {
        return getIteratorForRegex( rootPath, new RegexFileFilter( ".*" + allowedExtension ) );
    }

    private static Iterator<File> getIteratorForRegex( String rootPath, RegexFileFilter regexFileFilter ) {
        File rootDirAsFile = new File( rootPath );
        IOFileFilter dirFilter = new IOFileFilter() {

            @Override
            public boolean accept( File file ) {
                try {
                    return isPlainDirectory( file );
                }
                catch (IOException e) {
                    return false;
                }
            }

            @Override
            public boolean accept( File dir, String name ) {
                try {
                    return isPlainDirectory( dir );
                }
                catch (IOException e) {
                    return false;
                }
            }

            private boolean isPlainDirectory( File file ) throws IOException {
                return file.isDirectory() && !isSymbolicLink( file );
            }

            private boolean isSymbolicLink( File file ) throws IOException {
                File canon;
                if (file.getParent() == null) {
                    canon = file;
                }
                else {
                    File canonDir = file.getParentFile().getCanonicalFile();
                    canon = new File( canonDir, file.getName() );
                }
                return !canon.getCanonicalFile().equals( canon.getAbsoluteFile() );
            }
        };

        return FileUtils.iterateFiles( rootDirAsFile, regexFileFilter, dirFilter );
    }

}
