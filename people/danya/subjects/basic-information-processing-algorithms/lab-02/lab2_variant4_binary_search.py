from __future__ import annotations

import argparse
import bisect
import random
import time
from collections.abc import Sequence
from dataclasses import dataclass


@dataclass(frozen=True)
class SearchReport:
    key: int
    index: int
    checked_by_bisect: int

    @property
    def found(self) -> bool:
        return self.index != -1


def generate_sorted_numbers(size: int, lower: int = 0, upper: int = 100, seed: int | None = None) -> list[int]:
    """Generate and sort values because binary search requires ordered input."""
    if size <= 0:
        raise ValueError("Размер массива должен быть положительным")
    if lower > upper:
        raise ValueError("Нижняя граница диапазона не может быть больше верхней")

    rng = random.Random(seed)
    values = [rng.randint(lower, upper) for _ in range(size)]
    values.sort()
    return values


def binary_search_first(sorted_values: Sequence[int], key: int) -> int:
    """Return the first index of key in a sorted sequence, or -1 when absent."""
    left = 0
    right = len(sorted_values) - 1
    result = -1

    # After a match, continue searching on the left side to get the first occurrence.
    while left <= right:
        middle = (left + right) // 2
        middle_value = sorted_values[middle]

        if middle_value == key:
            result = middle
            right = middle - 1
        elif middle_value < key:
            left = middle + 1
        else:
            right = middle - 1

    return result


def compare_with_standard_bisect(sorted_values: Sequence[int], key: int) -> SearchReport:
    """Compare the custom binary search with Python's bisect module."""
    custom_index = binary_search_first(sorted_values, key)
    standard_index = bisect.bisect_left(sorted_values, key)
    standard_found = standard_index < len(sorted_values) and sorted_values[standard_index] == key
    expected_index = standard_index if standard_found else -1

    if custom_index != expected_index:
        raise AssertionError("Пользовательский бинарный поиск не совпал с bisect_left")

    return SearchReport(key=key, index=custom_index, checked_by_bisect=expected_index)


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Лабораторная работа 2, вариант 4: дихотомический поиск в случайном массиве 0..100."
    )
    parser.add_argument("--size", type=int, default=30, help="Размер массива.")
    parser.add_argument("--key", type=int, default=50, help="Искомое число.")
    parser.add_argument("--seed", type=int, default=None, help="Seed для воспроизводимого запуска.")
    return parser.parse_args()


def main() -> None:
    args = parse_args()
    values = generate_sorted_numbers(args.size, seed=args.seed)

    started = time.perf_counter()
    report = compare_with_standard_bisect(values, args.key)
    elapsed = time.perf_counter() - started

    print("Лабораторная работа 2, вариант 4")
    print("Задача: выполнить дихотомический поиск числа в отсортированном массиве случайных целых чисел 0..100.")
    print(f"Размер массива: {len(values)}")
    print(f"Отсортированный массив: {values}")
    print(f"Искомое число: {report.key}")
    print(f"Индекс custom binary search: {report.index}")
    print(f"Индекс bisect_left: {report.checked_by_bisect}")
    print(f"Проверка корректности: {'OK' if report.found or report.index == -1 else 'Ошибка'}")
    print(f"Время поиска: {elapsed:.6f} сек")


if __name__ == "__main__":
    main()
