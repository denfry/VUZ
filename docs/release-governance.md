# Release Governance

## Release Checklist (Mandatory)

- Scope and affected modules confirmed.
- Backward compatibility evaluated.
- DB/config migration readiness evaluated.
- Tests/build checks passed for touched modules.
- Docs/changelog/release notes updated.
- Rollback steps verified.

## Backward Compatibility Gates

- No breaking config key removal without migration docs.
- No behavior change in critical user flows without explicit release note.
- Existing startup/run scripts remain valid or are replaced with compatible alternatives.

## Migration Readiness Checks

- Schema/data changes documented.
- Pre-migration backup instructions present.
- Post-migration verification commands present.

## Feature Flag Rules

- Required for high-risk behavior changes.
- Flags must have:
  - default state,
  - owner,
  - removal plan.

## Rollback Rules

- Every release-level change must define rollback trigger and rollback steps.
- Rollback must not require undocumented manual database surgery.

## Canary Strategy (Local/Small Team)

- First run on dev clone dataset.
- Then run on primary local/dev environment.
- Promote only after smoke checks and critical flow verification.

## Incident Response Workflow

1. Stabilize and contain blast radius.
2. Rollback or mitigate.
3. Collect evidence and timeline.
4. Ship fix with regression guard.
5. Update AI learning loop/checklists.

## Hotfix Workflow

1. Branch `hotfix/*`.
2. Minimal scoped patch.
3. Mandatory verification on impacted module.
4. Update changelog + rollback note.
5. Merge and propagate to mainline branches.

## Postmortem Template

- Incident summary.
- Timeline (UTC).
- Root cause.
- Detection and response gaps.
- Corrective actions.
- Preventive governance updates.

## Blast Radius Estimation

For each release, score impact (1-5) on:
- runtime availability,
- data integrity,
- user workflow continuity,
- recoverability.

Any total >= 14 requires canary and explicit rollback rehearsal.

