# stream-parser
Unlabeled dependency parser for streams of tokens

Java implementation of the Minimum Spanning Tree (MST) parser specified by Deniz Yuret in his PhD thesis: Yuret, Deniz. "Discovery of linguistic relations using lexical attraction." arXiv preprint cmp-lg/9805009 (1998).

This parser requires scores for possible links as input. Those weights have to be calculated elsewhere and provided as a database. For a way to calculate the scores, check [MI_calculator](broken link).
