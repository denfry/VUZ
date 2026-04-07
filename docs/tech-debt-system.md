# Tech Debt System

## Debt Hotspot Inventory (Current)

High risk:
- Committed plaintext DB credentials in config files (`conf.prop` variants).
- Mixed source + generated binaries (`.class`, `target/`) inside repository.

Medium risk:
- Duplicated logic across student module variants.
- Inconsistent build/run conventions across labs.
- Limited automated tests for behavior regression.

Low risk:
- IDE/project metadata churn.
- Report/document version drift.

## Severity Model

- `P0`: Security/release blocking.
- `P1`: Frequent defects or major maintenance drag.
- `P2`: Moderate maintainability cost.
- `P3`: Cosmetic or localized cleanup.

## Prioritization Rules

Prioritize by:
1. User/business risk.
2. Security/release impact.
3. Frequency of recurrence.
4. Cost of delayed remediation.

## Repayment Strategy

- P0/P1: schedule immediate or next sprint remediation.
- P2: bundle with touched-module changes.
- P3: batch in maintenance windows.

## Acceptable Debt Policy

Debt is acceptable only when:
- documented with owner and expiry date,
- rollback path exists,
- risk is bounded and visible.

## Recurrence Detection

Trigger recurrence flag if same class of issue appears 2+ times in 30 days.
When triggered, add:
- new engineering law or checklist gate,
- workflow update in `.ai/workflows`,
- preventive test/verification step.

