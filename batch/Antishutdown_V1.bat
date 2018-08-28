@echo off
color 0a
setlocal enabledelayedexpansion
goto :main

:main
setlocal
    
    :: 시간설정(t)
    if "%1"=="" (
        set /a t=15
    ) else (
        set /a t=%1
    )

    set x=tasklist | find "chrome.exe"
    echo !x!
    
    :: 일정 시간 간격으로 taskkill 실행
    :loop
        echo Antishutdown is running...
        taskkill /f /t /im notepad.exe
        timeout !t!
        
    goto :loop

endlocal
goto :eof