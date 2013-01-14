#!/bin/bash

echo "Copying over latest stuff..."

echo "Copying index.html"
cp -R /Users/roblevin/Desktop/Programming/the-learn-d-developer-design-patterns/index.html ./index.html
echo "Copying img directory"
cp -R /Users/roblevin/Desktop/Programming/the-learn-d-developer-design-patterns/chapters/img ./img/
echo "Copying stylesheet to where generated index.html page expects"
cp ./stylesheets/styles.css ./style.css

echo "Done."

