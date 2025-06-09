# ChargeMate

## Members

- **Francisco Albergaria** - 114646
- **Gonçalo Sousa** - 108133
- **Regina Tavares** - 114129

## Roles

| Role            | Member                    |
|-----------------|---------------------------|
| Team Manager    | Francisco Albergaria      |
| Product Owner   | Francisco Albergaria      |
| QA Engineer     | Regina Tavares            |
| DevOps          | Gonçalo Sousa             |



## Alterações significativas desde a apresentação

- Dockerização completa do frontend com Vite + Nginx.
- Integração de variáveis de ambiente (.env) para configuração entre o frontend e o backend.
- Ajuste da configuração CORS no backend para permitir a integração com o frontend em container.
- O frontend passou a estar acessível diretamente através do endereço IP (sem necessidade da porta :3000), com exposição da porta 80 via Docker.
- Conclusão do processo de deployment: o sistema encontra-se funcional e acessível através do IP da máquina virtual.

