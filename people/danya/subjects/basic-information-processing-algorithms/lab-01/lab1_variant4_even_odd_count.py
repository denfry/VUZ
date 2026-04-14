import random
import time


def read_n() -> int:
    default_n = 100_000
    raw = input(
        "Введите n (10..50000000), Enter для значения по умолчанию 100000: "
    ).strip()
    if not raw:
        return default_n
    n = int(raw)
    if n < 10 or n > 50_000_000:
        raise ValueError("n должно быть в диапазоне 10..50000000")
    return n


def main() -> None:
    print("Лабораторная работа 1, вариант 4")
    print("Задача: сформировать массив случайных чисел и найти количество четных и нечетных.")

    n = read_n()
    min_value = -1_000_000
    max_value = 1_000_000

    start_gen = time.perf_counter()
    values = [random.randint(min_value, max_value) for _ in range(n)]
    end_gen = time.perf_counter()

    start_count = time.perf_counter()
    even_count = sum(1 for v in values if v % 2 == 0)
    odd_count = n - even_count
    end_count = time.perf_counter()

    print(f"Размер массива: {n}")
    print(f"Количество четных: {even_count}")
    print(f"Количество нечетных: {odd_count}")
    print(f"Время генерации массива: {end_gen - start_gen:.6f} сек")
    print(f"Время подсчета: {end_count - start_count:.6f} сек")


if __name__ == "__main__":
    main()
