from __future__ import annotations

import argparse
import time
from collections.abc import Sequence


def parse_input_array(raw: str) -> list[int]:
    """Parse integers separated by spaces or commas."""
    tokens = [token for token in raw.replace(",", " ").split() if token]
    if not tokens:
        raise ValueError("Массив не должен быть пустым")
    return [int(token) for token in tokens]


def interpolation_search_recursive(values: Sequence[int], key: int, low: int | None = None, high: int | None = None) -> int:
    """Recursive interpolation search over a sorted integer sequence.

    The public call only needs values and key. Boundaries are kept as optional
    parameters so recursive calls do not allocate additional slices.
    """
    if not values:
        return -1

    left = 0 if low is None else low
    right = len(values) - 1 if high is None else high

    if left > right or key < values[left] or key > values[right]:
        return -1

    if values[left] == values[right]:
        return left if values[left] == key else -1

    position = left + (key - values[left]) * (right - left) // (values[right] - values[left])
    if position < left or position > right:
        return -1

    if values[position] == key:
        return position
    if values[position] < key:
        return interpolation_search_recursive(values, key, position + 1, right)
    return interpolation_search_recursive(values, key, left, position - 1)


def recursive_format_array(values: Sequence[int], index: int = 0) -> str:
    """Format an array recursively for the report and console output."""
    if index >= len(values):
        return ""

    prefix = f"{index}: {values[index]}"
    suffix = recursive_format_array(values, index + 1)
    return prefix if not suffix else prefix + "\n" + suffix


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Лабораторная работа 3, вариант 4: рекурсивный интерполирующий поиск."
    )
    parser.add_argument(
        "--values",
        default="7 1 3 9 3 11 15 21",
        help="Элементы массива через пробел или запятую.",
    )
    parser.add_argument("--key", type=int, default=9, help="Искомый ключ.")
    return parser.parse_args()


def main() -> None:
    args = parse_args()
    original_values = parse_input_array(args.values)
    sorted_values = sorted(original_values)

    started = time.perf_counter()
    index = interpolation_search_recursive(sorted_values, args.key)
    elapsed = time.perf_counter() - started

    print("Лабораторная работа 3, вариант 4")
    print("Задача: рекурсивный интерполирующий поиск целочисленного ключа и вывод массива.")
    print("Исходный массив:")
    print(original_values)
    print("Отсортированный массив:")
    print(recursive_format_array(sorted_values))
    print(f"Искомый ключ: {args.key}")
    print(f"Индекс в отсортированном массиве: {index}")
    print(f"Время поиска: {elapsed:.6f} сек")


if __name__ == "__main__":
    main()
