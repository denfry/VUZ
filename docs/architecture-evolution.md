# Architecture Evolution

## Current Architecture Map

Repository is a multi-module educational monorepo with four dominant zones:
- `Даня/*`, `Витос/*`, `Тима/*`, etc.: student-owned lab implementations.
- Java console and Swing modules: foundational OOP/UI exercises.
- JavaFX + Maven modules (`Lab6`, `Lab7`, `Lab8`): richer desktop app architecture.
- Documentation/report artifacts: DOCX/PDF/HTML plus prompt/governance markdown.

## Domain Boundaries

- `core-models`: domain entities (e.g., refrigerator, user records).
- `ui-desktop`: Swing/JavaFX forms and controllers.
- `data-access`: JDBC/database manager classes and SQL orchestration.
- `ops-docs`: runbooks, reports, checklists, release and migration instructions.

## Target Architecture (12-18 months)

- Shared `common` library for validation, config loading, and error handling.
- Clear layering for JavaFX apps:
  - presentation (FXML/controllers),
  - application services,
  - repository/data access.
- Standardized config and secrets strategy (env + local override).
- Build/test pipelines per module with uniform quality gates.

## Evolution Milestones

1. Baseline governance and checklists (done in this change set).
2. Normalize build/run and config contracts across Lab6/Lab7/Lab8.
3. Extract shared utilities for DB and validation.
4. Add regression tests for critical DB/UI flows.
5. Introduce CI validation matrix (Java version + module build checks).

## Service Decomposition Rules

- Do not split into services until:
  - module has independent deploy cadence,
  - clear ownership exists,
  - measurable coupling pain is present.
- Prefer modular monolith with strict package boundaries first.

## Plugin/Extension Opportunities

- Assignment template generator for new labs.
- Common report pipeline for DOCX/PDF outputs.
- Data seed and DB reset tooling for JDBC labs.
- Reusable GUI form validation and telemetry hooks.

## State Ownership

- UI state: controller/view model scope.
- Domain state: model/service scope.
- Persistence state: database only through data-access layer.

## Versioning Contracts

- Use semantic intent even in educational modules:
  - behavior-preserving refactor: patch,
  - feature additions: minor,
  - breaking run/config expectations: major note in upgrade docs.

