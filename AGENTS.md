# Repository Operating Rules (Mandatory)

This file is the mandatory operating contract for all future tasks in this repository.

## 1) Mission and Product Direction

Inferred product direction from repository scan:
- Current state: multi-author educational monorepo with Java labs, HTML/CSS assignments, and report artifacts.
- Dominant runtime: local desktop Java (Swing/JavaFX) plus PostgreSQL-backed JavaFX/JDBC apps.
- Emerging product vector: transition from isolated labs into a reusable educational engineering platform with:
  - reusable UI/data modules,
  - repeatable database environments,
  - stronger testing and release discipline,
  - consistent documentation/report generation.

Core capability clusters:
- Java fundamentals and OOP exercises.
- Desktop GUI data-entry and validation flows.
- Database auth/connect/query flows in JDBC/JavaFX.
- Academic report/document outputs (DOCX/PDF/HTML).

## 2) AI Edit Behavior (Hard Rules)

- Preserve semantics first; optimize second.
- Do not perform speculative rewrites.
- No unrelated edits in the same change.
- Keep changes deterministic, minimal, and reviewable.
- Respect existing language and folder conventions.
- Do not remove student artifacts unless explicitly requested.
- If a rule conflicts with user intent, ask for explicit override.

## 3) Change Safety Policy

Before changes:
- Identify impacted module(s), consumer flows, and documentation links.
- Classify risk: `low` / `medium` / `high`.
- Define rollback path before editing high-risk paths.

During changes:
- Keep backward compatibility unless task explicitly allows breaking change.
- Preserve config contracts (`conf.prop`, DB URL/user expectations, run scripts).
- Add/adjust tests when behavior changes.

After changes:
- Update linked docs/checklists/changelog entries.
- Verify build/run commands for touched module(s).
- Record governance updates if the same issue pattern recurs.

## 4) Repo Grounding Rules

- Primary sources for truth:
  - module `README.md`,
  - `pom.xml` and module descriptors,
  - runtime configs (`conf.prop`, launch scripts),
  - this `AGENTS.md`,
  - `docs/*.md`,
  - `.ai/workflows/*.md`.
- Ignore generated outputs (`target/`, `.class`, temporary binaries) for design decisions.

## 5) Compatibility and Dependency Discipline

- Pin or document dependency versions in Maven modules.
- Avoid introducing new frameworks without written justification in docs.
- Keep Java version assumptions explicit in module docs.
- Never break existing launch scripts without migration notes.

## 6) Review and Release Standards

Every non-trivial change must pass:
- scope check (only intended files),
- docs sync check,
- tests/build check for touched module(s),
- rollback feasibility check.

Commit message standard:
- `type(scope): summary`
- Types: `feat`, `fix`, `refactor`, `docs`, `test`, `chore`.

Branch naming standard:
- `feature/*`, `fix/*`, `refactor/*`, `hotfix/*`, `release/*`, `docs/*`.

## 7) Document and Git Intelligence Persistence

Mandatory doc-to-code sync:
- Code changes update corresponding README/docs/checklists.
- DB or schema behavior changes update migration and rollback docs.
- GUI flow changes update usage/run instructions.

Mandatory release artifacts (for release-level changes):
- `docs/release-notes.md`
- `docs/migration-guide.md`
- `docs/rollback-guide.md`
- `docs/upgrade-path.md`
- `docs/changelog.md`

## 8) Anti-Regression Operating Mode

- Prefer smallest safe patch.
- Add regression tests for previously failing behavior.
- Convert repeated failures into new rule/checklist items in:
  - `docs/ai-learning-loop.md`
  - `docs/execution-checklists.md`
  - `docs/tech-debt-system.md`

## 9) Mandatory Workflow Selection

For every future task, choose and follow one workflow in `.ai/workflows/`.
If multiple apply, execute in this order:
1. `design-feature.md`
2. task-specific workflow
3. `prepare-release.md` (for release-affecting changes)

No task is complete until workflow checkpoints are closed.

