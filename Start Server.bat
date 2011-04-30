@echo off
title SimpleChat Server
:port
set /P PORT=Enter port: %=%
if "%PORT%"=="" goto port
java -Xms128M -Xmx512M -jar dist/SimpleChat.jar server %PORT%
pause
