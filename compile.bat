@echo off

rem -- Lipsion
color 1f
:menu
echo   ________________________________________________________________
echo  ^|                                                                ^|
echo  ^|                     Maven  -  控制面板                         ^|
echo  ^|                                                                ^|
echo  ^|  0 - eclipse:clean clean       1 - clean package -D...skip=true^|
echo  ^|  2 - eclipse:e.. -Ddown..      3 - package -D...skip=true      ^|
echo  ^|      -Dwtp..                                                   ^|
echo  ^|  4 - 编译部署包                                                  ^|
echo  ^|________________________________________________________________^|
:input
set /p input=-^> 请选择: 

if "%input%"== "0" goto clean
if "%input%"== "1" goto clean-package
if "%input%"== "2" goto eclipse
if "%input%"== "3" goto package
if "%input%"== "4" goto deploy-zip
goto end

:clean
echo  # 消除工程编译 #
mvn eclipse:clean clean&&pause&&cls&& call compile.bat

:clean-package
echo  # 消除编译并打包 #
mvn clean package -Dmaven.test.skip=true &&pause&&cls&& call compile.bat

:eclipse
echo  # 转成Eclipse工程 #
mvn eclipse:clean eclipse:eclipse -DdownloadSources=true &&pause&&cls&& call compile.bat

:package
echo  # 打包 #
mvn package -Dmaven.test.skip=true &&pause&&cls&& call compile.bat

:deploy-zip
cls && call deploy.bat

rem for /d %%d in (*) do (
rem  if exist %%d\POM.xml set dao_dir=%%d
rem )
rem echo 当前目录是:%dao_dir%
rem &&pause 
:end
echo 结束
prompt
popd