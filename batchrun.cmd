FOR %%f IN (*.dat) DO (
FOR /L %%j IN (1,1,100) DO (
FOR /L %%i IN (1,1,10) DO (
	start /B java -jar dist\MolComSim.jar -pfile: %%f -batchRun
)
FOR /L %%k IN (1,1,2) DO (
timeout /t 1 /nobreak
tasklist | find "java.exe"
IF %ERRORLEVEL% EQU 0 SET k=1
)
)
)
start /B java -jar dist/BatchFileCollator.jar
ECHO done.

