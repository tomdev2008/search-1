@echo off

rem -- Lipsion
color 1f
:menu
echo   ________________________________________________________________
echo  ^|                                                                ^|
echo  ^|                     Maven  -  �������                         ^|
echo  ^|                                                                ^|
echo  ^|  0 - eclipse:clean clean       1 - clean package -D...skip=true^|
echo  ^|  2 - eclipse:e.. -Ddown..      3 - package -D...skip=true      ^|
echo  ^|      -Dwtp..                                                   ^|
echo  ^|  4 - ���벿���                                                  ^|
echo  ^|________________________________________________________________^|
:input
set /p input=-^> ��ѡ��: 

if "%input%"== "0" goto clean
if "%input%"== "1" goto clean-package
if "%input%"== "2" goto eclipse
if "%input%"== "3" goto package
if "%input%"== "4" goto deploy-zip
goto end

:clean
echo  # �������̱��� #
mvn eclipse:clean clean&&pause&&cls&& call compile.bat

:clean-package
echo  # �������벢��� #
mvn clean package -Dmaven.test.skip=true &&pause&&cls&& call compile.bat

:eclipse
echo  # ת��Eclipse���� #
mvn eclipse:clean eclipse:eclipse -DdownloadSources=true &&pause&&cls&& call compile.bat

:package
echo  # ��� #
mvn package -Dmaven.test.skip=true &&pause&&cls&& call compile.bat

:deploy-zip
cls && call deploy.bat

rem for /d %%d in (*) do (
rem  if exist %%d\POM.xml set dao_dir=%%d
rem )
rem echo ��ǰĿ¼��:%dao_dir%
rem &&pause 
:end
echo ����
prompt
popd