#!/bin/bash

# Runs stream parser on given corpus, and evaluates resulting parses against given GS using
# github.com/singnet/language-learning parse-evaluator
#
# Usage:  stream_evaluate.sh <maxWinObserve> <maxWinParse> <vocabFilePath> <corpusPath> <parsesPath> <GSPath>

STREAMPATH='/home/andres/IdeaProjects/stream-parser';
maxWinObserve=$5;
maxWinParse=$6;
calculateScores=true;
exportScores=false;
vocabFilePath=$1;
observeCorpusPath=$2;
corpusPath=$2;
GSPath=$4;

echo "winObserve winParse Recall Precision F1" > results.dat # write header

# Loop to parse and evaluate with range of windows

for ((winObserve=1; winObserve<=maxWinObserve; winObserve++))
do
  for ((winParse=1; winParse<=maxWinObserve; winParse++))
  do
    parsesPath=$3${winObserve}_${winParse};
    scoresFilePath=""; # Only used if exportScores is true

    # Parse the corpus with current window sizes
    java -classpath $STREAMPATH/out/production/stream-parser:/home/andres/src/ojalgo-47.1.1.jar mstparser.RunParser "$winObserve" $winParse $calculateScores $exportScores $vocabFilePath $scoresFilePath $observeCorpusPath $corpusPath $parsesPath

    # Evaluate current parses against the gold standard
    parse-evaluator -i -r $GSPath -t $parsesPath

    # Obtain scores from evaluation
    scores=() # scores array
    cat ${parsesPath}.stat | grep -E 'Recall|Precision|F1' > tmp.file # tmp.file used to avoid subshell from piping
    while read f1 f2
    do
      scores+=($f2)
    done < tmp.file
    rm tmp.file

    echo "$winObserve" "$winParse" "${scores[@]}" >> results.dat
  done
done
