from __future__ import annotations

import argparse
import random
import time
from collections.abc import Callable, Sequence
from dataclasses import dataclass


@dataclass(frozen=True)
class SortMeasurement:
    algorithm: str
    elapsed: float
    is_correct: bool


def generate_numbers(size: int, lower: int = 0, upper: int = 100, seed: int | None = None) -> list[int]:
    if size <= 0:
        raise ValueError("Размер массива должен быть положительным")
    if lower > upper:
        raise ValueError("Нижняя граница диапазона не может быть больше верхней")

    rng = random.Random(seed)
    return [rng.randint(lower, upper) for _ in range(size)]


def bubble_sort(values: Sequence[int]) -> list[int]:
    """Optimized bubble sort with early exit for already ordered data."""
    result = list(values)
    length = len(result)

    for pass_index in range(length - 1):
        swapped = False
        for index in range(length - pass_index - 1):
            if result[index] > result[index + 1]:
                result[index], result[index + 1] = result[index + 1], result[index]
                swapped = True
        if not swapped:
            break

    return result


def measure_against_sorted(name: str, sorter: Callable[[Sequence[int]], list[int]], values: Sequence[int]) -> SortMeasurement:
    expected = sorted(values)
    started = time.perf_counter()
    actual = sorter(values)
    elapsed = time.perf_counter() - started
    return SortMeasurement(algorithm=name, elapsed=elapsed, is_correct=actual == expected)


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Лабораторная работа 4, вариант 4: пузырьковая сортировка с проверкой через sorted()."
    )
    parser.add_argument("--size", type=int, default=2000, help="Размер массива.")
    parser.add_argument("--seed", type=int, default=None, help="Seed для воспроизводимого запуска.")
    return parser.parse_args()


def main() -> None:
    args = parse_args()
    values = generate_numbers(args.size, seed=args.seed)
    measurement = measure_against_sorted("bubble_sort", bubble_sort, values)

    print("Лабораторная работа 4, вариант 4")
    print("Программа 1: пузырьковая сортировка и сравнение со стандартной sorted().")
    print(f"Размер массива: {len(values)}")
    print(f"Пример исходных данных: {values[:15]}")
    print(f"Время bubble_sort(): {measurement.elapsed:.6f} сек")
    print(f"Проверка с sorted(): {'OK' if measurement.is_correct else 'Ошибка'}")


if __name__ == "__main__":
    main()
