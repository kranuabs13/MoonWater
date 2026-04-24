@echo off
setlocal

set APP_HOME=%~dp0
set WRAPPER_JAR=%APP_HOME%gradle\wrapper\gradle-wrapper.jar

if not exist "%WRAPPER_JAR%" (
    echo Warning: gradle-wrapper.jar missing.
    gradle %*
    goto :eof
)

java -Xmx64m -cp "%WRAPPER_JAR%" org.gradle.wrapper.GradleWrapperMain %*
