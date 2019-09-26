#!/bin/bash

# Runs stream parser on given corpus, and evaluates resulting parses against given GS using SingNet's parse-evaluator
# at github.com/singnet/language-learning
#
# NOTE: language-learning conda environment needs to be active before running this script
#
# Usage:  stream_evaluate.sh <vocabFilePath> <corpusPath> <GSPath> <maxWinObserve> <maxWinParse>

STREAMPATH='/home/andres/IdeaProjects/stream-parser';
maxWinObserve=$4;
maxWinParse=$5;
calculateScores=true;
exportScores=false;
vocabFilePath=$1;
observeCorpusPath=$2;
corpusPath=$2;
GSPath=$3;

echo "# Parse evaluations" > results.dat # write header
echo "# winObserve winParse Recall Precision F1" > results.dat # write header

mkdir -p parses
mkdir -p stats
# Loop to parse and evaluate with range of windows
for ((winObserve=1; winObserve<=maxWinObserve; winObserve++))
do
  for ((winParse=1; winParse<=maxWinParse; winParse++))
  do
    echo "Evaluating: winObserve=${winObserve} winParse=${winParse}"
    currParses="wObs${winObserve}_wPar${winParse}";
    parsesPath="parses/${currParses}";
    scoresFilePath="scores/${winObserve}_${winParse}"; # Only used if exportScores is true

    # Parse the corpus with current window sizes
    java -classpath $STREAMPATH/out/production/stream-parser:/home/andres/src/ojalgo-47.1.1.jar mstparser.RunParser "$winObserve" "$winParse" $calculateScores $exportScores "$vocabFilePath" "$scoresFilePath" "$observeCorpusPath" "$corpusPath" "$parsesPath"

    # Evaluate current parses against the gold standard
    parse-evaluator -i -r "$GSPath" -t "$parsesPath"

    # Obtain scores from evaluation
    scores=() # scores array
    grep -E 'Recall|Precision|F1' < "${currParses}.stat"  > tmp.file # tmp.file used to avoid subshell from piping
    while read -r f1 f2
    do
      scores+=("$f2")
    done < tmp.file
    mv ${currParses}.stat stats
    rm tmp.file

    # Store results in file
    echo "$winObserve" "$winParse" "${scores[@]}" >> results.dat
  done
  echo "" >> results.dat # newline, useful for splot in gnuplot
done

# Plotting results with gnuplot
mkdir -p plots
gnuplot -persist << -EOFMarker
  reset
  set terminal png

  # Plotting contour map
  set pm3d
  unset surface
  set view map
  set key outside

  # Preparing three different plots
  set cbrange [0.3:0.9]
  set xlabel "winObserve"
  set ylabel "winParse"
  set title "Recall"
  set output "plots/recall.png"
  splot 'results.dat' using 1:2:3 with lines notitle
  set title "Precision"
  set output "plots/precision.png"
  splot 'results.dat' using 1:2:4 with lines notitle
  set title "F1 score"
  set output "plots/f1score.png"
  splot 'results.dat' using 1:2:5 with lines notitle
-EOFMarker
echo "Results plots stored in 'plots' folder"

