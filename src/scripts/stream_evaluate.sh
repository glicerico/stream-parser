#!/bin/bash

# Runs stream parser on given corpus, and evaluates resulting parses against given GS using SingNet's parse-evaluator
# at github.com/singnet/language-learning
#
# NOTE: language-learning conda environment needs to be active before running this script
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

echo "# Parse evaluations" > results.dat # write header
echo "# winObserve winParse Recall Precision F1" > results.dat # write header

# Loop to parse and evaluate with range of windows

for ((winObserve=1; winObserve<=maxWinObserve; winObserve++))
do
  for ((winParse=1; winParse<=maxWinParse; winParse++))
  do
    echo "Evaluating: winObserve=${winObserve} winParse=${winParse}"
    parsesPath=$3${winObserve}_${winParse};
    scoresFilePath=""; # Only used if exportScores is true

    # Parse the corpus with current window sizes
    java -classpath $STREAMPATH/out/production/stream-parser:/home/andres/src/ojalgo-47.1.1.jar mstparser.RunParser "$winObserve" "$winParse" $calculateScores $exportScores "$vocabFilePath" "$scoresFilePath" "$observeCorpusPath" "$corpusPath" "$parsesPath"

    # Evaluate current parses against the gold standard
    parse-evaluator -i -r "$GSPath" -t "$parsesPath"

    # Obtain scores from evaluation
    scores=() # scores array
    grep -E 'Recall|Precision|F1' < "${parsesPath}.stat"  > tmp.file # tmp.file used to avoid subshell from piping
    while read -r f1 f2
    do
      scores+=("$f2")
    done < tmp.file
    rm tmp.file

    # Store results in file
    echo "$winObserve" "$winParse" "${scores[@]}" >> results.dat
  done
  echo "" >> results.dat # newline, useful for splot in gnuplot
done

mkdir plots
gnuplot -persist << -EOFMarker
  reset
  set terminal png
  set xlabel "winObserve"
  set ylabel "winParse"
  set title "Parse results"
  set output "plots/recall.png"
  splot 'results.dat' using 1:2:3 with lines title "Recall"
  set output "plots/precision.png"
  splot 'results.dat' using 1:2:4 with lines title "Precision"
  set output "plots/f1score.png"
  splot 'results.dat' using 1:2:5 with lines title "F1"
-EOFMarker

