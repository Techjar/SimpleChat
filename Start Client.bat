@echo off
:ip
set /P IP=Enter IP: %=%
if "%IP%"=="" goto ip
:port
set /P PORT=Enter port: %=%
if "%PORT%"=="" goto port
:name
set /P NAME=Enter username: %=%
if "%NAME%"=="" goto name
java -Xms128M -Xmx512M -jar dist/SimpleChat.jar client %IP% %PORT% %NAME%
pause
