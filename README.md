# FluentGenesis-Embedder

FluentGenesis-Embedder is a companion tool for the FluentGenesis-Classifier. As always, 
this is at the moment a completely private educational project and is not fir for any 
purpose whatsoever.

## The Idea

This project implements a tool for calculating the embeddings of the bpe-vocabulary. I 
will implement this tool using deeplearning4j. Currently there is only one reason for 
this tool to exist: calculate the embeddings for the FluentGenesis-Classifier. No fancy 
things. 

This tool is part of my effort for reaching SCLU (Source Code Language Understanding). 

Together with all these FuriosIron projects, this will hopefully evolve to a code search 
engine capable of understanding the source code, so the results can be much more precise. 
Search engines are the most important tool for a programmer right now, because everything
we are working on, was already implemented by someone else for 98% of all cases. It is 
important to be able to find the right code in time all the time.

That said, please remember, this is a private educational project. 

## The MVP

* load model description (hparams.json)
* walk all files of the corpus and read them as json and use the bpe indexes
  * read files, prepare bpe them
  * set vocabulary
* train cbow / skip-gram for these bpe encodings
* export the calculated embeddings



## Nice to have

## What needs to be done next

## License

Usage is provided under the [MIT License](http://opensource.org/licenses/mit-license.php). See LICENSE for the full details.