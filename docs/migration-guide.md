# Migration Guide

This guide applies when future changes affect runtime behavior, configuration, or data.

## Standard Migration Steps

1. Identify impacted module(s) and interfaces.
2. Backup relevant config/data.
3. Apply code and schema/config changes.
4. Run module build/tests and critical flow checks.
5. Validate telemetry/log outputs.
6. Update changelog and release notes.

## JDBC/DB-Specific Guidance

- Validate `conf.prop` keys and connection targets.
- Run smoke query checks (`SELECT VERSION()` and core table query).
- Confirm UI login and query workflow after migration.

## Documentation Requirement

Any migration must update:
- `docs/release-notes.md`
- `docs/rollback-guide.md`
- `docs/upgrade-path.md`
- module `README.md` if run instructions changed.

## Filesystem Naming/Structure Migration (2026-04-07)

This repository now uses ASCII-first paths for daily navigation and scripting.

### Top-Level Structure

- `people/` - per-student or per-author workspaces
- `shared/` - shared educational modules and common projects
- `workspace/` - local helper scripts/prompts and operational assets

### Renamed Person Roots

- `Витос` -> `people/vitos`
- `Даня` -> `people/danya`
- `Натся` -> `people/natsya`
- `Сёма` -> `people/sema`
- `Сэм джонсон` -> `people/sem-johnson`
- `Тима` -> `people/tima`

### Additional Rules Applied

- Non-ASCII and spaced file/folder names under `people/`, `shared/`, and `workspace/` were normalized to ASCII slugs.
- Build/IDE/cache artifacts are grouped under per-owner `build/` trees.
- Root loose reports/archives were moved to author-owned `docs/` and `archives/` folders.
- Legacy shared folder `program/` was migrated to `shared/program-interface/`.
