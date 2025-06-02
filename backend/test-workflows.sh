#!/bin/bash

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== Testando Workflows Localmente ===${NC}\n"

# 1. Testar SonarQube
echo -e "${BLUE}1. Executando análise SonarQube...${NC}"
mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
  -Dsonar.projectKey=FranciscoAlbergaria_ChargeMate \
  -Dsonar.organization=franciscoalbergaria \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.login=${SONAR_TOKEN}

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Análise SonarQube concluída com sucesso!${NC}\n"
else
    echo -e "${RED}✗ Falha na análise SonarQube${NC}\n"
fi

# 2. Executar testes
echo -e "${BLUE}2. Executando testes...${NC}"
mvn clean test -f backend/pom.xml

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Testes executados com sucesso!${NC}\n"
else
    echo -e "${RED}✗ Alguns testes falharam${NC}\n"
fi

# 3. Listar relatórios
echo -e "${BLUE}3. Listando relatórios gerados...${NC}"
ls -l backend/reports

# 4. Enviar para Xray
echo -e "${BLUE}4. Enviando resultados para Xray...${NC}"
mvn -f backend/pom.xml \
  -Dxray.clientId=${XRAYID} \
  -Dxray.clientSecret=${XRAYSECRET} \
  -Dxray.testEnvironment=java17 \
  -Dxray.revision=local-test \
  -Dxray.projectKey=${XRAY_PROJECT_KEY} \
  -Dxray.reportFile=backend/reports/TEST-*.xml \
  xray:import-results

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Resultados enviados para Xray com sucesso!${NC}\n"
else
    echo -e "${RED}✗ Falha ao enviar resultados para Xray${NC}\n"
fi

echo -e "${BLUE}=== Teste dos Workflows Concluído ===${NC}" 