API de Gerenciamento de Delivery<br>
Projeto desenvolvido durante a Especialização em Arquitetura de Software na Fundação FAT.<br>

API REST desenvolvida para gerenciar ecossistema de restaurantes e entregas. O foco central do projeto é a aplicação de padrões arquiteturais modernos, escalabilidade, segurança e automação completa de infraestrutura.

Tecnologias e Ferramentas<br>
Java 21 & Spring Boot (v3.5.12): Core da aplicação (Web, JPA, Security e Bean Validation).<br>
Maven: Gestão de dependências e automação de builds.<br>
Docker: Conteinerização para garantir paridade entre ambientes.<br>
Aiven (MySQL): Banco de dados relacional gerenciado em nuvem.<br>
Azure App Service: Hospedagem escalável da aplicação em produção.<br>

Arquitetura e Design de Software<br>
O projeto foi construído sobre os pilares da engenharia de software para garantir baixo acoplamento e alta coesão.<br>

Arquitetura em Camadas (Layered Architecture): Organização lógica dividida em Controller, Service, Repository e Entity, onde cada camada possui responsabilidade única (SoC - Separation of Concerns).<br>
Padrão MVC (Model-View-Controller): Desacoplamento entre a lógica de exposição de endpoints e as regras de negócio.<br>
Injeção de Dependência: Utilização do container do Spring para gerenciar o ciclo de vida dos componentes, facilitando a modularização e testabilidade.<br>

Persistência e Dados<br>
Abordagem Code First: A estrutura do banco de dados é gerada e evoluída a partir das classes Java (Entities) via JPA/Hibernate.<br>
Padrão DTO (Data Transfer Object): Camada de transporte de dados que protege as entidades de negócio, evitando a exposição direta do modelo de dados e permitindo validações rigorosas na entrada da API.<br>

Engenharia de Confiabilidade (CI/CD) e QA<br>
A aplicação conta com um pipeline de Integração e Entrega Contínua via GitHub Actions, garantindo que nenhum código chegue à produção sem passar por critérios rigorosos.<br>
Testes de Integração e Regressão: Uso de JUnit 5 e MockMvc para simular requisições HTTP e validar o fluxo completo (Controller -> Service -> Database). Os testes de regressão garantem que novas atualizações não quebrem funcionalidades legadas.<br>
Automação de Build: Geração automática da imagem Docker a cada commit na branch principal.<br>
Deploy Automatizado: Publicação direta no Azure App Service somente após a aprovação de todos os testes unitários e de integração.
