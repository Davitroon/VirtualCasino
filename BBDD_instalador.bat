@echo off
echo Instalando la base de datos...
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p < .\files\CasinoBDDD_Scripts.sql
echo Base de datos instalada
pause