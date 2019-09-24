#!/bin/bash

# Runs stream parser on given corpus, and evaluates resulting parses against given GS
#
# Usage:  stream_evaluate.sh <vocabFilePath> <corpusPath> <parsesPath> <GSPath>

STREAMPATH='/home/andres/IdeaProjects/stream-parser';
winObserve=6;
winParse=30;
calculateScores=true;
exportScores=false;
vocabFilePath=$1;
scoresFilePath="/home/andres/Documents/CAiN/trash.scores";
observeCorpusPath=$2;
corpusPath=$2;
parsesPath=$3;
GSPath=$4;

java -classpath $STREAMPATH/out/production/stream-parser:/home/andres/src/ojalgo-47.1.1.jar mstparser.RunParser $winObserve $winParse $calculateScores $exportScores $vocabFilePath $scoresFilePath $observeCorpusPath $corpusPath $parsesPath

parse-evaluator -i -r $GSPath -t $parsesPath
