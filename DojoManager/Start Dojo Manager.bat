@echo off
title Dojo Manager
cd /d "%~dp0"

where java >nul 2>&1
if errorlevel 1 (
  echo Java was not found. Install JDK 8+ and add it to PATH, then try again.
  pause
  exit /b 1
)

if not exist "build\classes" mkdir "build\classes"

echo Compiling Dojo Manager...
javac -encoding UTF-8 -d "build\classes" -sourcepath src ^
  src\dojomanager\*.java ^
  src\dojomanager\model\*.java ^
  src\dojomanager\data\*.java ^
  src\dojomanager\util\*.java ^
  src\dojomanager\gui\*.java

if errorlevel 1 (
  echo Compile failed.
  pause
  exit /b 1
)

echo Starting Dojo Manager...
java -cp "build\classes" dojomanager.DojoManagerApp
if errorlevel 1 pause
