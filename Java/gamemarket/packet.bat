@echo off
setlocal enabledelayedexpansion
set name=%2
set outpath=%1
set dirpath=%3
set javapath=E:\Development\Java1.6
set androidpath=E:\Development\Android
set charset=UTF-8
set outclasses=%outpath%\classes
set outdex=%outpath%\classes.dex
set outapk=%outpath%\%name%
set tempapk=%outpath%\temp.apk
set src=%dirpath%\src
set srcpath=%dirpath%\src\com\apps\game
set genpath=%dirpath%\gen
set libspath=%dirpath%\libs
set manifest=%dirpath%\AndroidManifest.xml
set keystore=gamemarket.keystore
set mykeystore=myandroid.keystore						 
set storepass=12345678
set keypass=12345678
set res=%dirpath%\res
set assets=%dirpath%\assets
set java=%javapath%\bin\javac
set jarsigner=%javapath%\bin\jarsigner
set dx=%androidpath%\build-tools\18.0.1\dx.bat
set aapt=%androidpath%\build-tools\18.0.1\aapt.exe
set builder=%androidpath%\tools\apkbuilder.bat
set android=%androidpath%\platforms\android-8\android.jar
set ext=\*.java
set sp=;
set space= 
set libs=%android%

if not exist %outpath% (
md %outpath%
)

if not exist %outclasses% (
md %outclasses%
)

%aapt% package -f -m -J %genpath% -S %res% -M %manifest% -I %android%

for /R %libspath% %%i in (*.jar) do (
set libs=!libs!%sp%%%i
)

for /f "delims=" %%i in ('dir /s /ad /b %srcpath%') do (
set srcs=!srcs!%%i%ext%%space%
)

for /R %genpath% %%i in (*.java) do (
set srcs=!srcs!%%i%space%
)

%java% -encoding %charset% -bootclasspath %libs% -d %outclasses% %srcs%

set libs=

for /R %libspath% %%i in (*.jar) do (
set libs=!libs!%%i%space%
)

call %dx% --dex --output=%outdex% %outclasses% %libs%

%aapt% package -f -M %manifest% -S %res% -A %assets% -I %android% -F %outpath%\res

call %builder% %tempapk% -v -u -rf %src% -f %outdex% -z %outpath%\res

%jarsigner% -keystore %dirpath%\%keystore% -storepass %storepass% -keypass %keypass% -signedjar %outapk% %tempapk% %mykeystore%