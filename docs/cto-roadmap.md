# CTO Roadmap

## Strategic Summary

Product direction: evolve this repository from disconnected lab submissions into a governed educational engineering platform with consistent quality, release safety, and reusable technical foundations.

## Maturity Stages

1. Stage 1 (0-30 days): Governance baseline
- Enforce AGENTS + checklists + workflows.
- Standardize module run/build documentation.

2. Stage 2 (31-60 days): Technical consistency
- Align config handling across JDBC/JavaFX modules.
- Establish shared validation/error patterns.

3. Stage 3 (61-90 days): Reliability and scale-up
- Add CI build matrix and smoke tests.
- Track debt hotspots and recurrence.

## Roadmaps by Capability

Architecture:
- Consolidate reusable domain/util packages.
- Reduce duplicated controller/data-access patterns.

Team scaling:
- Define module ownership by folder.
- Require checklist completion in every merge.

Repo modularization:
- Group common components into `shared/` once duplication threshold is met.

Tech debt:
- Prioritize secrets handling, generated artifact cleanup, and duplicate code paths.

Performance:
- Add query latency and UI responsiveness checkpoints for DB modules.

Security:
- Remove plaintext secrets from committed configs over time.
- Introduce local `.env` + sample config model.

Testing:
- Add unit tests for domain logic and DB integration smoke tests.

CI/CD:
- Start with CI build/test checks on pull requests.
- Add release-tag validation for packaging modules.

Documentation:
- Keep runbooks and migration docs synchronized with behavior changes.

Migration modernization:
- Version DB setup scripts and seed data routines.

Cost optimization:
- Favor local reproducible toolchains and minimal dependencies.

## 90-Day Priorities

1. Standardize DB config and credential handling in Lab7/Lab8.
2. Introduce minimal automated checks (build + smoke run per active module).
3. Extract shared Java utilities where duplication is measurable.
4. Establish changelog and release note habit for meaningful changes.

