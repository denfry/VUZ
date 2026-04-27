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


def merge(left: Sequence[int], right: Sequence[int]) -> list[int]:
    """Merge two sorted sequences into one sorted list."""
    merged: list[int] = []
    left_index = 0
    right_index = 0

    while left_index < len(left) and right_index < len(right):
        if left[left_index] <= right[right_index]:
            merged.append(left[left_index])
            left_index += 1
        else:
            merged.append(right[right_index])
            right_index += 1

    merged.extend(left[left_index:])
    merged.extend(right[right_index:])
    return merged


def merge_sort(values: Sequence[int]) -> list[int]:
    """Stable merge sort implemented with divide-and-conquer recursion."""
    if len(values) <= 1:
        return list(values)

    middle = len(values) // 2
    left = merge_sort(values[:middle])
    right = merge_sort(values[middle:])
    return merge(left, right)


def measure_against_sorted(name: str, sorter: Callable[[Sequence[int]], list[int]], values: Sequence[int]) -> SortMeasurement:
    expected = sorted(values)
    started = time.perf_counter()
    actual = sorter(values)
    elapsed = time.perf_counter() - started
    return SortMeasurement(algorithm=name, elapsed=elapsed, is_correct=actual == expected)


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Лабораторная работа 4, вариант 4: сортировка слиянием с проверкой через sorted()."
    )
    parser.add_argument("--size", type=int, default=2000, help="Размер массива.")
    parser.add_argument("--seed", type=int, default=None, help="Seed для воспроизводимого запуска.")
    return parser.parse_args()


def main() -> None:
    args = parse_args()
    values = generate_numbers(args.size, seed=args.seed)
    measurement = measure_against_sorted("merge_sort", merge_sort, values)

    print("Лабораторная работа 4, вариант 4")
    print("Программа 2: сортировка слиянием и сравнение со стандартной sorted().")
    print(f"Размер массива: {len(values)}")
    print(f"Пример исходных данных: {values[:15]}")
    print(f"Время merge_sort(): {measurement.elapsed:.6f} сек")
    print(f"Проверка с sorted(): {'OK' if measurement.is_correct else 'Ошибка'}")


if __name__ == "__main__":
    main()
