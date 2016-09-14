:: set classpath to current directory, src directory within this directory
set CLASSPATH = .\src;

:: go to classes folder, remove old class files from previous compile
cd classes
del *.class

:: navigate to source directory
cd ..\src

:: compile classes to a separate directory, compile main Game class in subdirectory
javac -d ..\classes game\Game.java
PAUSE