# stream-parser
Unlabeled dependency parser for streams of tokens

Java implementation of the Maximum Spanning Tree (MST) parser specified by Deniz Yuret in his PhD thesis: Yuret, Deniz. "Discovery of linguistic relations using lexical attraction." arXiv preprint cmp-lg/9805009 (1998).

Code available in the `src` folder:

The `micalculator` package includes classes to estimate the pointwise mutual information (PMI) of word-pairs from observations  of a given corpus.

The `scripts` folder contains shell scripts to lowercase the given corpus, as well as to generate a dictionary file.

The `mstparser` package is the parser itself, which consumes scores calculated beforehand.
