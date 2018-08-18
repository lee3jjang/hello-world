@echo off
color 0a
:main
title 3rd Lesson
echo Press 1 to make me say Hi
echo Press 2 to make me say Bye
set /p car=Press Something:

if %car%==1 goto hi
if %car%==2 goto bye

:hi
echo Hi
pause
goto :main

:bye
echo Bye
pause
goto :main
