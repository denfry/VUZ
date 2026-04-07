# Engineering Laws

These laws are mandatory for all future development in this repository.

## 1) Simplicity Threshold
- Prefer the simplest design that satisfies current requirements and known next-step growth.
- Reject abstractions with only one consumer unless they remove concrete duplication.

## 2) Complexity Budget
- Any file over ~300 logical lines requires explicit justification in review notes.
- Any method over ~40 logical lines should be split unless readability degrades.
- Cyclomatic complexity target: <= 10 per method for non-UI orchestration code.

## 3) Abstraction Constraints
- Abstractions must model stable domain behavior, not temporary implementation detail.
- New shared utilities require at least two real call sites.

## 4) Coupling Ceiling
- UI modules must not directly embed DB credentials, SQL, or environment assumptions.
- Controllers orchestrate; data access layers execute DB interactions.

## 5) Duplication Tolerance
- Accept small local duplication to avoid premature frameworking.
- Remove repeated patterns once they appear in 3+ modules or create defect recurrence.

## 6) Naming Law
- Names must encode intent and unit semantics (e.g., `heightCm`, `volumeLiters`).
- Avoid ambiguous names: `data`, `tmp`, `manager2`, `util`.

## 7) Error Propagation Law
- User-facing apps must display actionable errors and preserve debug details for logs.
- Never swallow exceptions silently.

## 8) Logging Law
- Log state transitions and failures for DB connection, auth, and persistence actions.
- Avoid noisy logs inside tight UI event loops.

## 9) Observability Law
- Every critical flow should expose at least:
  - start event,
  - success event,
  - failure event with reason.

## 10) Testability Law
- Business logic must be separable from UI layer where feasible.
- Behavior-changing changes require tests or executable verification notes.

## 11) Maintainability Scoring (Lightweight)
- Score each module quarterly (1-5) on:
  - readability,
  - test coverage,
  - coupling,
  - doc freshness,
  - defect recurrence.
- Any module averaging <3 enters debt remediation queue.

