import random
from bisect import bisect_left


def binary_search_first(sorted_values: list[int], key: int) -> int:
    left = 0
    right = len(sorted_values) - 1
    result = -1

    while left <= right:
        mid = (left + right) // 2
        if sorted_values[mid] == key:
            result = mid
            right = mid - 1
        elif sorted_values[mid] < key:
            left = mid + 1
        else:
            right = mid - 1

    return result


def main() -> None:
    print("Лабораторная работа 2, вариант 4")
    print("Задача: дихотомический поиск числа в массиве случайных целых 0..100.")

    raw_n = input("Введите размер массива (по умолчанию 30): ").strip()
    n = int(raw_n) if raw_n else 30
    if n <= 0:
        raise ValueError("Размер массива должен быть положительным")

    values = [random.randint(0, 100) for _ in range(n)]
    values.sort()
    print("Отсортированный массив:")
    print(values)

    key = int(input("Введите искомое число: ").strip())

    index_custom = binary_search_first(values, key)
    index_bisect = bisect_left(values, key)

    if index_custom == -1:
        print(f"Число {key} не найдено.")
    else:
        print(f"Число {key} найдено, индекс первого вхождения: {index_custom}")

    # Контроль результата стандартным модулем.
    check = (
        index_bisect < len(values) and values[index_bisect] == key and index_bisect == index_custom
    ) or (index_custom == -1 and (index_bisect == len(values) or values[index_bisect] != key))
    print(f"Проверка корректности: {'OK' if check else 'Ошибка'}")


if __name__ == "__main__":
    main()
