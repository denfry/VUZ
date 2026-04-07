# Rollback Guide

## Rollback Triggers

- Critical user flow failure.
- Data integrity risk.
- Security regression.
- Unrecoverable runtime instability.

## Standard Rollback Procedure

1. Stop further rollout.
2. Revert to last known-good commit/tag.
3. Restore previous config and DB state if changed.
4. Verify startup and critical flows.
5. Record incident and prevention actions.

## Verification After Rollback

- Build/start succeeds for impacted module(s).
- DB connection and baseline queries succeed.
- Core UI actions complete without errors.

## Documentation Updates

After rollback, update:
- `docs/changelog.md`
- `docs/release-notes.md`
- `docs/ai-learning-loop.md`

