#!/bin/bash
for file in *.dat 
do
	for i in 1 2 3 4 5 6 7 8 9 10 
	do
		java -jar dist/MolComSim.jar -pfile: $file -batchRun
	done
done
java -jar dist/BatchFileCollator.jar

