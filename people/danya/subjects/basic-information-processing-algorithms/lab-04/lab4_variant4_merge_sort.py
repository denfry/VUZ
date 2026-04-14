import random
import time


def merge(left: list[int], right: list[int]) -> list[int]:
    merged: list[int] = []
    i = 0
    j = 0

    while i < len(left) and j < len(right):
        if left[i] <= right[j]:
            merged.append(left[i])
            i += 1
        else:
            merged.append(right[j])
            j += 1

    if i < len(left):
        merged.extend(left[i:])
    if j < len(right):
        merged.extend(right[j:])
    return merged


def merge_sort(values: list[int]) -> list[int]:
    if len(values) <= 1:
        return values[:]
    mid = len(values) // 2
    left = merge_sort(values[:mid])
    right = merge_sort(values[mid:])
    return merge(left, right)


def main() -> None:
    print("Лабораторная работа 4, вариант 4")
    print("Программа 2: сортировка слиянием.")

    raw_n = input("Введите размер массива (по умолчанию 20): ").strip()
    n = int(raw_n) if raw_n else 20
    if n <= 0:
        raise ValueError("Размер массива должен быть положительным")

    values = [random.randint(0, 100) for _ in range(n)]
    print("Исходный массив:")
    print(values)

    start = time.perf_counter()
    sorted_values = merge_sort(values)
    elapsed = time.perf_counter() - start

    print("Отсортированный массив:")
    print(sorted_values)
    print(f"Время сортировки: {elapsed:.6f} сек")
    print(f"Проверка: {'OK' if sorted_values == sorted(values) else 'Ошибка'}")


if __name__ == "__main__":
    main()
