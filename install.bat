@echo off
echo mvn clean install -Denforcer.skip=true -Dmaven.test.skip=true -U
call mvn clean install -Denforcer.skip=true -Dmaven.test.skip=true -U
call pause