@echo off
title SimpleChat Client
:ip
set /P IP=Enter IP: %=%
if "%IP%"=="" goto ip
:port
set /P PORT=Enter port: %=%
if "%PORT%"=="" goto port
:name
set /P NAME=Enter username: %=%
if "%NAME%"=="" goto name
set /P PASS=Enter password (optional): %=%
java -Xms64M -Xmx256M -jar dist/SimpleChat.jar client %IP% %PORT% %NAME% %PASS%
pause
