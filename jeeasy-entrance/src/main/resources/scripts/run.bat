title ${project.name}-${project.version}
REM 声明采用UTF-8编码
chcp 65001

@echo off 
rem ##############设置延迟环境变量扩充，即感叹号间的值不会因跳出循环而为空值。################
setlocal enabledelayedexpansion 

rem ###############java命令######################
set JAVA=%JAVA_HOME%\bin\java.exe 

rem ###############jvm参数######################
set OPTS=-Xms1024M -Xmx1024M -XX:+AggressiveOpts -XX:+UseParallelGC -XX:NewSize=64M -Dfile.encoding=UTF-8

rem ###############agent启动类参数######################
set serverMain=${application.main.class}

echo JAVA: %JAVA% 
echo CLASSPATH: %CP% 
echo OPTS: %OPTS%
java %OPTS%  -cp "../lib/*;../conf" %serverMain% 
PAUSE