@echo off

if not ""%1"" == ""generate-sources"" if not ""%1"" == ""local-test"" if not ""%1"" == ""package"" goto help
if ""%1"" == ""generate-sources"" goto generate-sources
if ""%1"" == ""local-test"" goto local-test
if ""%1"" == ""package"" goto package
goto end

:generate-sources
mvn clean ^
    swagger-codegen:generate@spring-front ^
    swagger-codegen:generate@spring-service ^
    swagger-codegen:generate@spring-open ^
    swagger-codegen:generate@html-front ^
    swagger-codegen:generate@html-service ^
    swagger-codegen:generate@html-open ^
    swagger-codegen:generate@javascript ^
    antrun:run@process-src ^
    frontend:install-node-and-npm@install-node-npm ^
    frontend:npm@npm-run-install
goto end

:local-test
cd src/main/frontend
start npm start
cd ../../../
mvn clean spring-boot:run -Dmaven.test.skip=true
goto end

:package
mvn clean package -Dmaven.test.skip=true -PdisableNpmInstall
goto

:help
echo "usage: umvn {generate-sources|local-test|package}"
goto end

:end
