# 🧾 Changelog
Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

O formato segue as recomendações de [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/)
e este projeto adota o [Versionamento Semântico](https://semver.org/lang/pt-BR/).

---

## [0.2.0-ALPHA] - 2025-11-11
### Added
- Nova tabela `gathering.event_fee` para armazenar taxas dinâmicas por quantidade de jogadores.
- Entidade `EventFee` com relacionamento `OneToMany` em `Event`.
- Validação automática de configuração de potes (`loser_fee` e `prize_fee`) no `EventService`.

### Changed
- Removidas as colunas `loserFee4`, `loserFee5` e `loserFee6` da tabela `gathering.event`.
- Atualizado o DTO `EventDTO` e o método `validate()` para refletir o novo modelo.

### Fixed
- Corrigido erro de serialização JSON circular entre `Event` e `EventFee` com `@JsonIgnore`.

---

## [0.1.0-ALPHA] - 2025-11-11
### 🚀 Primeira versão funcional da Gathering API

Esta é a primeira versão **Alpha** da API — inclui todas as principais entidades, 
lógica de cálculo de resultados e dashboards consolidados.

#### 🆕 Adicionado
- Estrutura completa do **banco de dados** com schemas, constraints e comentários documentados.
- Criação das **views de agregação**:
  - `vw_event_*` (dados e estatísticas por evento)
  - `vw_gathering_*` (resumo geral por gathering)
  - `vw_result_final_balance` → consolidada como `vw_gathering_result`
- Repositórios e projections para cada view.
- Camada de **serviços** e **controllers** RESTful padronizados.
- **Endpoint `/actuator/info`** com build version, artifact, group e descrição.
- **Sistema de logs padronizado** com `LogHelper` e `RouteHelper`.
- **Padronização de commits e versionamento** (SemVer adotado).

#### ⚙️ Alterado
- Refatoração do serviço de resultados para utilizar o **summary projection** consolidado.
- Reorganização dos pacotes `projection.gathering` e `projection.event`.
- Adição de índices (`CREATE INDEX`) em colunas de alta frequência em joins e filtros.
- Atualização das dependências no `pom.xml`:
  - `springdoc-openapi-starter-webmvc-ui`
  - `spring-boot-starter-actuator`
- Ajuste do `pom.xml` com nome e descrição do projeto:
  - `name`: `api-gathering`
  - `description`: `Gathering API - Gestão de confras e eventos de TCG modo mesão`

#### 🔧 Correções
- Correção de importações duplicadas (`jakarta.persistence.Transient` vs `org.springframework.data.annotation.Transient`).
- Ajustes nas views SQL (`CHECK`, `JOIN`, `GROUP BY`) para garantir compatibilidade e portabilidade.
- Correção na inicialização do `DashboardRepository` (uso de `ViewPlaceholder`).
- Correção da rota `/actuator/info` (de `actuador` → `actuator` 😄).

#### 🧱 Estrutura e boas práticas
- Reorganização dos pacotes por domínio (`gathering`, `event`, `dashboard`, `transaction`).
- Introdução da interface `SummaryProjection` para reuso entre `EventSummaryProjection` e `GatheringSummaryProjection`.
- Introdução de **validações de negócio** robustas:
  - Proibição de `SAQUE` sem saldo.
  - Proibição de `INSCRIÇÃO` e `RESULTADO` via endpoint.
- Padronização de comentários em português no schema `gathering`.

#### 🧪 Testes e validação
- Todos os endpoints do `DashboardController` testados manualmente via Swagger.
- Log detalhado em cada requisição para rastreabilidade de execuções.
- Teste do endpoint `/actuator/info` confirmando exibição correta dos metadados da build.

---

## 🗓️ Planejado para a próxima versão (0.2.0-BETA)
- Adicionar autenticação de usuários (login e controle por JWT).
- Implementar controle de planos de assinatura (Básico / Premium).
- Início do **frontend** (Quasar ou Flutter, decisão pendente).
- Exibição visual do dashboard e ranking em tempo real.

---

## 📦 Metadados da build
| Campo | Valor |
|-------|--------|
| **Group ID** | br.com.gathering |
| **Artifact ID** | api-gathering |
| **Version** | 0.1.0-ALPHA |
| **Descrição** | Gathering API - Gestão de confras e eventos de jogos em grupo |
| **Data da build** | 2025-11-10 |

---

© 2025 Gabriel Vianna. Todos os direitos reservados.
