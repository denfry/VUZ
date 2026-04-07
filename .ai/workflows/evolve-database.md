# Workflow: Evolve Database

## Discovery
- Define schema/config/query changes and affected flows.

## Impact Graph
- Map tables, queries, controllers, and config files.

## Risk Analysis
- Evaluate data integrity and downtime risk.

## Compatibility Verification
- Preserve query contracts or provide migration adapters.

## Rollback Path
- Provide schema/data rollback method before applying changes.

## Docs Updates
- Update migration, rollback, release notes, and module README.

## Test Expansion
- Run integration smoke tests for DB connect/auth/query paths.

## Telemetry Validation
- Validate DB errors and latency are observable.

## Post-Change Governance Updates
- Promote recurring DB issues into hard checklist gates.

