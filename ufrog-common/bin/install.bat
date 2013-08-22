@echo off
cd %~dp0
cd ..

mvn clean source:jar install

cd bin
pause