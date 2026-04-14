def interpolation_search_recursive(
    arr: list[int], key: int, low: int, high: int
) -> int:
    if low > high or key < arr[low] or key > arr[high]:
        return -1

    if arr[high] == arr[low]:
        return low if arr[low] == key else -1

    pos = low + (key - arr[low]) * (high - low) // (arr[high] - arr[low])
    if pos < low or pos > high:
        return -1

    if arr[pos] == key:
        return pos
    if arr[pos] < key:
        return interpolation_search_recursive(arr, key, pos + 1, high)
    return interpolation_search_recursive(arr, key, low, pos - 1)


def parse_input_array(raw: str) -> list[int]:
    tokens = [t for t in raw.replace(",", " ").split() if t]
    if not tokens:
        raise ValueError("Массив не должен быть пустым")
    return [int(t) for t in tokens]


def main() -> None:
    print("Лабораторная работа 3, вариант 4")
    print("Задача: рекурсивный интерполирующий поиск целочисленного ключа.")

    raw = input(
        "Введите элементы массива через пробел (например: 7 1 3 9 3 11): "
    ).strip()
    values = parse_input_array(raw)
    sorted_values = sorted(values)

    print("Исходный массив:")
    print(values)
    print("Отсортированный массив (для интерполяционного поиска):")
    print(sorted_values)

    key = int(input("Введите искомый ключ: ").strip())
    index = interpolation_search_recursive(sorted_values, key, 0, len(sorted_values) - 1)

    if index == -1:
        print(f"Ключ {key} не найден.")
    else:
        print(f"Ключ {key} найден, индекс в отсортированном массиве: {index}")


if __name__ == "__main__":
    main()
