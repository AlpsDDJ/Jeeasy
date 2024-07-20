@echo off
setlocal EnableDelayedExpansion

:: 设置标题
title @project.name@-@project.version@

:: 检查JAVA_HOME环境变量
if not defined JAVA_HOME (
    echo JAVA_HOME is not set. Please set JAVA_HOME to the root of your JDK installation.
    pause
    exit /b 1
)

:: 定义Java可执行文件路径
set JAVA_EXE=%JAVA_HOME%\bin\java.exe

:: 检查Java可执行文件是否存在
if not exist "%JAVA_EXE%" (
    echo Java executable not found at %JAVA_EXE%
    pause
    exit /b 1
)

:: 定义JVM选项
set JVM_OPTS=-Xms1024m -Xmx2048m -XX:+UseG1GC -Dfile.encoding=UTF-8

:: 定义主类
set MAIN_CLASS=@application.main.class@

:: 定义类路径
set CLASS_PATH=../lib/*;../conf/

:: 显示配置信息
echo.
echo Starting Spring Boot Application...
echo JAVA: %JAVA_EXE%
echo JVM_OPTS: %JVM_OPTS%
echo MAIN_CLASS: !MAIN_CLASS!
echo CLASS_PATH: !CLASS_PATH!

:: 启动应用
"%JAVA_EXE%" %JVM_OPTS% -cp "!CLASS_PATH!" !MAIN_CLASS!

:: 暂停等待用户操作
pause