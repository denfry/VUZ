from __future__ import annotations

import argparse
import random
import time
from dataclasses import dataclass
from collections.abc import Sequence


@dataclass(frozen=True)
class EvenOddCount:
    """Result of one linear pass over an integer sequence."""

    even: int
    odd: int

    @property
    def total(self) -> int:
        return self.even + self.odd


def generate_numbers(size: int, lower: int, upper: int, seed: int | None = None) -> list[int]:
    """Generate a reproducible integer array for the experiment."""
    if size < 0:
        raise ValueError("Размер массива не может быть отрицательным")
    if lower > upper:
        raise ValueError("Нижняя граница диапазона не может быть больше верхней")

    rng = random.Random(seed)
    return [rng.randint(lower, upper) for _ in range(size)]


def count_even_odd(values: Sequence[int]) -> EvenOddCount:
    """Count even and odd integers in O(n) time and O(1) extra memory."""
    even = 0
    odd = 0

    for value in values:
        if value % 2 == 0:
            even += 1
        else:
            odd += 1

    return EvenOddCount(even=even, odd=odd)


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Лабораторная работа 1, вариант 4: подсчет четных и нечетных чисел."
    )
    parser.add_argument("--size", type=int, default=100_000, help="Размер массива n.")
    parser.add_argument("--min", type=int, default=-1_000_000, dest="lower", help="Минимальное значение.")
    parser.add_argument("--max", type=int, default=1_000_000, dest="upper", help="Максимальное значение.")
    parser.add_argument("--seed", type=int, default=None, help="Seed для воспроизводимого запуска.")
    return parser.parse_args()


def main() -> None:
    args = parse_args()

    started = time.perf_counter()
    values = generate_numbers(args.size, args.lower, args.upper, args.seed)
    generated_at = time.perf_counter()
    result = count_even_odd(values)
    finished = time.perf_counter()

    print("Лабораторная работа 1, вариант 4")
    print("Задача: сформировать одномерный массив случайных чисел и определить количество четных и нечетных элементов.")
    print(f"Размер массива: {result.total}")
    print(f"Количество четных чисел: {result.even}")
    print(f"Количество нечетных чисел: {result.odd}")
    print(f"Контроль суммы: {result.total}")
    print(f"Пример данных: {values[:10]}")
    print(f"Время генерации: {generated_at - started:.6f} сек")
    print(f"Время подсчета: {finished - generated_at:.6f} сек")


if __name__ == "__main__":
    main()
