@echo off

rem -- Lipsion
color 1f
:menu
echo   ________________________________________________________________
echo  ^|                                                                ^|
echo  ^|                     Maven  -  �������                         ^|
echo  ^|                                                                ^|
echo  ^|  0 - clean package -D...skip=true -Ptest                       ^|
echo  ^|  1 - clean package -D...skip=true -Pmirror                     ^|
echo  ^|  2 - clean package -D...skip=true -Ponline                     ^|
echo  ^|________________________________________________________________^|
:input
set /p input=-^> ��ѡ��������: 

if "%input%"== "0" goto test
if "%input%"== "1" goto mirror
if "%input%"== "2" goto online
goto end

:test
echo  # ������Ի����� #
mvn clean package -Dmaven.test.skip=true -Ptest&&pause&&cls&& call compile.bat

:mirror
echo  # ���뾵�񻷾��� #
mvn clean package -Dmaven.test.skip=true -Pmirror&&pause&&cls&& call compile.bat

:online
echo  # �������������� #
mvn clean package -Dmaven.test.skip=true -Ponline&&pause&&cls&& call compile.bat

rem for /d %%d in (*) do (
rem  if exist %%d\POM.xml set dao_dir=%%d
rem )
rem echo ��ǰĿ¼��:%dao_dir%
rem &&pause 
:end
echo ����
prompt
popd