#!/bin/sh
#-------------------------------------------------------------------------------------------------------------
#该脚本的使用方式为-->[sh startup.sh]
#该脚本可在服务器上的任意目录下执行,不会影响到日志的输出位置等
#-------------------------------------------------------------------------------------------------------------
if [ ! -n "$JAVA_HOME" ]; then
	export JAVA_HOME=@java-home@
fi

# spring.profiles.active
active=""

# 解析参数
while [[ $# -gt 0 ]]; do
    key="$1"

    case $key in
        --e)
            #active=" --spring.profiles.active=$2 "
            active=$2
            export SPRING_PROFILES_ACTIVE=$active
            shift # 过滤掉参数名
            ;;
        --r)
			      sh ./shutdown.sh
            #shift # 过滤掉参数名
            ;;
        *)
            # 不认识的参数
            echo "Unknown option: $key"
            ;;
    esac

    shift # 过滤掉参数或参数值
done

#-------------------------------------------------------------------------------------------------------------
#       系统运行参数
#-------------------------------------------------------------------------------------------------------------
DIR=$(cd "$(dirname "$0")"; pwd)
APP_HOME=${DIR}/..
CLASSPATH=$APP_HOME/conf
APP_LOG=${APP_HOME}/logs
APP_CONFIG=${APP_HOME}/conf/application.yml
APP_MAIN=@application.main.class@

JAVA_OPTS="$JAVA_OPTS -server -Xms1024m -Xmx1024m -Xmn128m -XX:ParallelGCThreads=20 -XX:+UseG1GC -XX:MaxGCPauseMillis=850 -Xlog:gc* -Xlog:gc:$APP_LOG/gc.log -Dfile.encoding=UTF-8 -Djava.util.Arrays.useLegacyMergeSort=true"
JAVA_OPTS="$JAVA_OPTS -DlogPath=$APP_LOG"
JAVA_OPTS="$JAVA_OPTS -Dconf.config=file:${APP_CONFIG}"
JAVA_OPENS="--add-opens
java.base/java.lang=ALL-UNNAMED
--add-opens
java.base/java.lang.reflect=ALL-UNNAMED
--add-opens
java.base/java.lang.invoke=ALL-UNNAMED
--add-opens
java.base/java.math=ALL-UNNAMED
--add-opens
java.base/sun.net.util=ALL-UNNAMED
--add-opens
java.base/java.io=ALL-UNNAMED
--add-opens
java.base/java.net=ALL-UNNAMED
--add-opens
java.base/java.nio=ALL-UNNAMED
--add-opens
java.base/java.security=ALL-UNNAMED
--add-opens
java.base/java.text=ALL-UNNAMED
--add-opens
java.base/java.time=ALL-UNNAMED
--add-opens
java.base/java.util=ALL-UNNAMED"

#remote debug
#JAVA_OPTS="$JAVA_OPTS"

echo "JAVA_HOME="$JAVA_HOME
echo "CLASSPATH="$CLASSPATH
echo "JAVA_OPTS="$JAVA_OPTS

#-------------------------------------------------------------------------------------------------------------
#   程序开始
#-------------------------------------------------------------------------------------------------------------
for appJar in "$APP_HOME"/lib/*.jar;
do
   CLASSPATH="$CLASSPATH":"$appJar"
done
PID=0

getPID(){
    javaps=`$JAVA_HOME/bin/jps -l | grep $APP_MAIN`
    if [ -n "$javaps" ]; then
        PID=`echo $javaps | awk '{print $1}'`
    else
        PID=0
    fi
}

startup(){
    getPID
    echo "================================================================================================================"
    if [ $PID -ne 0 ]; then
        echo "$APP_MAIN already started(PID=$PID)"
        echo "================================================================================================================"
    else
        echo -n "Starting $APP_MAIN"
         if [ ! -d "$APP_LOG" ]; then
            mkdir "$APP_LOG"
         fi
        nohup $JAVA_HOME/bin/java $JAVA_OPENS $JAVA_OPTS -classpath $CLASSPATH $APP_MAIN > $APP_LOG/nohup.log 2>&1 &
        for i in $(seq 5)
        do
        sleep 0.8
        echo -e  ".\c"
        done
        getPID
        if [ $PID -ne 0 ]; then
            echo "(PID=$PID)...[Start $active Success]"
            echo "================================================================================================================"
        else
            echo "[Start $active Failed]"
            echo "================================================================================================================"
        fi
    fi
}

startup
