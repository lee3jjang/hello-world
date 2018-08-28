@echo off
color 0a
setlocal enabledelayedexpansion
goto :main

:main
setlocal
    
    :: 시간설정(t)
    if "%1"=="" (
        set /a t0=20
    ) else (
        set /a t0=%1
    )
    set /a t=5
    set tsk="notepad.exe"
    set /a killed=0
    
    :: 일정 시간 간격으로 taskkill 실행
    :loop

        :: 지금까지 몇 번 강제종료했는지 출력
        echo.
        echo           ###########################################################
        echo           ###                                                     ###
        echo           ###              ANTI-SHUTDOWN IS RUNNING               ###
        echo           ###                     Ver 1.0                         ###
        echo           ###                                                     ###
        echo           ###########################################################
        echo.
        echo Task is killed for !killed! times...
        echo.

        :: tasklist에서 notepad.exe가 있는지 확인하여, x에 프로세스 개수 입력
        for /f %%a in ('tasklist ^| find /c !tsk!') do (
            set x=%%a
        )

        :: 프로세스 개수(x)가 1개 이상인지 검사
        if !x! gtr 0 (
            :: 1개 이상이면 taskkill
            echo Task is killed...
            taskkill /f /t /im !tsk!
            set /a killed=!killed!+1
            set /a t=!t0!
        ) else (
            :: 0개면 pass
            set /a t=5
        )

        :: 일정 시간(t) 동안 휴식
        timeout !t!
        cls
    goto :loop

endlocal
goto :eof