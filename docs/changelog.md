# Changelog

## Unreleased

### Added
- Repository-wide governance system (`AGENTS.md`, `docs/*`, `.ai/workflows/*`).

### Changed
- Established mandatory engineering, release, and AI learning policies.
- Reorganized repository filesystem for ASCII-first navigation and practical grouping (`people/`, `shared/`, `workspace/`).
- Migrated Russian/spaced names inside active working trees to normalized ASCII slugs.
- Consolidated build/IDE artifacts into per-owner `build/` folders.

### Fixed
- N/A

### Deprecated
- N/A

### Removed
- N/A

### Migration Notes
- Existing manual paths/scripts that referenced old Russian root folders must be updated to new locations under `people/*`.
- Root-level report/archive files were moved into author folders (`people/<author>/docs` and `people/<author>/archives`).

### Rollback Notes
- Remove added governance files if full rollback is necessary.
