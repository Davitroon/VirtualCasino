@echo off
title Casino Virtual - Lanzador
echo ===============================
echo ¿Has instalado la base de datos?
echo [S]í / [N]o
set /p bd_instalada=Respuesta:

if /I "%bd_instalada%"=="N" (
    echo Ejecutando instalador de base de datos...
    call files\CasinoBDDD_Instalador.sql
    pause
    exit /b
)

if /I "%bd_instalada%"=="S" (
    echo Iniciando CasinoVirtual.jar...
    java -cp "lib/*;files/CasinoVirtual.jar" logica.Lanzador
    pause
    exit /b
)

echo Opcion no válida. Intenta de nuevo con S o N.
pause