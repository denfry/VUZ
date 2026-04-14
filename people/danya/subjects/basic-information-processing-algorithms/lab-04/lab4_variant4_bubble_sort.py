import random
import time


def bubble_sort(values: list[int]) -> list[int]:
    arr = values[:]
    n = len(arr)
    for i in range(n - 1):
        swapped = False
        for j in range(0, n - 1 - i):
            if arr[j] > arr[j + 1]:
                arr[j], arr[j + 1] = arr[j + 1], arr[j]
                swapped = True
        if not swapped:
            break
    return arr


def main() -> None:
    print("Лабораторная работа 4, вариант 4")
    print("Сравнение встроенной сортировки Python и кастомной сортировки пузырьком.")

    raw_n = input("Введите размер массива (по умолчанию 2000): ").strip()
    n = int(raw_n) if raw_n else 2000
    if n <= 0:
        raise ValueError("Размер массива должен быть положительным")

    values = [random.randint(0, 100) for _ in range(n)]
    print(f"Размер массива: {n}")
    if n <= 50:
        print("Исходный массив:")
        print(values)

    start_builtin = time.perf_counter()
    builtin_sorted = sorted(values)
    elapsed_builtin = time.perf_counter() - start_builtin

    start_bubble = time.perf_counter()
    bubble_sorted = bubble_sort(values)
    elapsed_bubble = time.perf_counter() - start_bubble

    if n <= 50:
        print("Отсортированный массив (bubble):")
        print(bubble_sorted)

    is_equal = bubble_sorted == builtin_sorted
    ratio = (elapsed_bubble / elapsed_builtin) if elapsed_builtin > 0 else float("inf")

    print(f"Время sorted(): {elapsed_builtin:.6f} сек")
    print(f"Время bubble_sort(): {elapsed_bubble:.6f} сек")
    print(f"bubble / sorted = {ratio:.2f}x")
    print(f"Проверка одинакового результата: {'OK' if is_equal else 'Ошибка'}")


if __name__ == "__main__":
    main()
