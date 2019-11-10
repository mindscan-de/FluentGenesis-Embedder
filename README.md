# FluentGenesis-Embedder

FluentGenesis-Embedder is a companion tool for the FluentGenesis-Classifier. As always, 
this is at the moment a completely private educational project and is not fit for any 
purpose whatsoever.

## The Idea

This project implements a tool for calculating the embeddings of the BPE vocabulary 
derived from a "large" corpus of source code. The derived BPE vocabulary is then 
applied while encoding the source files. After that step it is necessary to vectorize
these byte-"pairs". Those byte pairs aren't really byte pairs but in some cases even 
represent complete words. Technically speaking they are the most frequent n-tuples
of the source code.

Source code has some domain specific features, which ordinary text doesn't have, or 
expose (think about the syntax, which is not very natural). Thus transfer learning 
through using an existing embedding and BPE vocabulary was not an option.  

The embedder will be implemented using deeplearning4j. I couldn't come up with a simpler
solution. The tool is implemented because of the FluentGenesis-Classifier project. But I
see that there might be a demand for calculating such embeddings using a ready setup. At
least I had this demand, which led to this tool.

## The Goal

This tool is part of my effort for reaching SCLU (Source Code Language Understanding). 

Together with all these FuriosIron projects, this will hopefully evolve to a code search 
engine capable of understanding source code, so the search results can be either more 
precise or more semantic related than a simple matching search.
 
Search engines are the most important tool for a programmer right now, because everything
we are working on, was already implemented by someone else for 98% of all cases. It is 
important to be able to find the right code, in time, all the time.

That said, please remember, this is a private educational project. 

## The MVP

* load model description (hparams.json) __[done]__
* walk all files of the corpus and read them as json and use the encoded bpe indexes 
  * read one ".java.json"-file and extract information and prepare that for the sentence iterator __[done]__
  * read all ".java.json" files  __[done]__
  * set/add additional vocabulary __TODO__
* train cbow / skip-gram for these bpe encodings (fitting) __[done]__
* export the calculated embeddings (saving) __[done]__


## Nice to have

## Next steps

## License

Usage is provided under the [MIT License](http://opensource.org/licenses/mit-license.php). See LICENSE for the full details.