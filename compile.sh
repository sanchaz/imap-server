#!/bin/sh

reset
echo "updating list of files to compile..."
ls -R main/*.java debug/*.java connections/*.java > compile.txt
echo "update done."
echo "now compiling..."
javac @compile.txt
echo "compiling done."
