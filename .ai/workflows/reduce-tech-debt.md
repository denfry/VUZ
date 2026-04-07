# Workflow: Reduce Tech Debt

## Discovery
- Select debt item with severity and owner.

## Impact Graph
- Identify modules and flows affected by repayment.

## Risk Analysis
- Prevent debt repayment from causing regressions.

## Compatibility Verification
- Confirm external behavior remains stable unless intended.

## Rollback Path
- Keep refactors incremental and revertible.

## Docs Updates
- Update `docs/tech-debt-system.md` and relevant module docs.

## Test Expansion
- Add regression tests around previously fragile areas.

## Telemetry Validation
- Validate reduced error frequency or clearer diagnostics.

## Post-Change Governance Updates
- Convert repeated debt patterns into hard repository rules.

