@echo off
cd /d "%~dp0"
echo Starting Dojo Manager...
echo Database / test data folder: %cd%\dojo_data
java -jar DojoManager.jar
if errorlevel 1 (
  echo.
  echo Could not start. Make sure Java is installed, or use App\DojoManager\DojoManager.exe instead.
  pause
)
