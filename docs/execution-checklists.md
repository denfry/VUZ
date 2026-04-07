# Execution Checklists

All checklists are mandatory for relevant task types.

## PRE-CODE

- Requirements and expected behavior clarified.
- Impacted modules/files identified.
- Risk level assigned.
- Rollback path drafted (for medium/high risk).
- Relevant workflow selected from `.ai/workflows`.

## PRE-MERGE

- No unrelated file changes.
- Build/tests executed for touched modules.
- Docs updated (README/docs/changelog).
- Compatibility impact reviewed.
- Security-sensitive changes reviewed.

## PRE-RELEASE

- Release notes and changelog complete.
- Migration and rollback guides updated.
- Critical user flows validated.
- Blast radius estimation completed.
- Owner sign-off recorded.

## POST-INCIDENT

- Incident timeline documented.
- Root cause validated.
- Immediate mitigation completed.
- Regression guard added.
- Governance/checklist update applied.

## POST-MIGRATION

- Data integrity checks passed.
- Application startup/critical flows verified.
- Rollback viability re-checked.
- Migration notes finalized.

## FILESYSTEM-REORG

- ASCII naming validated for active working trees.
- Legacy path references in docs/scripts updated.
- Build artifacts isolated from source paths.
- Top-level folder taxonomy remains consistent (`people/`, `shared/`, `workspace/`).

## PERFORMANCE-REVIEW

- Baseline metrics captured.
- Hot paths identified and measured.
- No severe regression introduced.
- Optimization changes documented.

## SECURITY-REVIEW

- Secrets handling reviewed.
- Input validation paths reviewed.
- Dependency risks reviewed.
- High-risk findings have remediation plan.

## ARCHITECTURE-DRIFT

- Current vs intended boundaries compared.
- New coupling violations identified.
- Mitigation tasks added to roadmap.
- Architecture docs updated.
