#!/bin/bash

# Runs stream parser on given corpus, and evaluates resulting parses against given GS using
# github.com/singnet/language-learning parse-evaluator
#
# Usage:  stream_evaluate.sh <winObserve> <winParse> <vocabFilePath> <corpusPath> <parsesPath> <GSPath>

STREAMPATH='/home/andres/IdeaProjects/stream-parser';
winObserve=$1;
winParse=$2;
calculateScores=true;
exportScores=false;
vocabFilePath=$3;
scoresFilePath="/home/andres/Documents/CAiN/trash.scores";
observeCorpusPath=$4;
corpusPath=$4;
parsesPath=$5$1_$2;
GSPath=$6;

java -classpath $STREAMPATH/out/production/stream-parser:/home/andres/src/ojalgo-47.1.1.jar mstparser.RunParser $winObserve $winParse $calculateScores $exportScores $vocabFilePath $scoresFilePath $observeCorpusPath $corpusPath $parsesPath

parse-evaluator -i -r $GSPath -t $parsesPath
