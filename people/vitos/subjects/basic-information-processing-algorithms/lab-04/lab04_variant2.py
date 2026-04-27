import argparse
import random
import time


def generate_numbers(size: int, min_value: int, max_value: int, seed: int | None) -> list[int]:
    rng = random.Random(seed)
    return [rng.randint(min_value, max_value) for _ in range(size)]


def bubble_sort(values: list[int]) -> list[int]:
    result = values.copy()
    n = len(result)

    for pass_index in range(n - 1):
        swapped = False
        for index in range(0, n - pass_index - 1):
            if result[index] > result[index + 1]:
                result[index], result[index + 1] = result[index + 1], result[index]
                swapped = True
        if not swapped:
            break

    return result


def measure_sort(sort_function, values: list[int]) -> tuple[list[int], float]:
    start = time.perf_counter()
    result = sort_function(values)
    elapsed = time.perf_counter() - start
    return result, elapsed


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Lab 04, variant 2: compare Python sorted() and custom bubble sort."
    )
    parser.add_argument("--size", type=int, default=3_000, help="Number of generated values.")
    parser.add_argument("--min", type=int, default=-1000, dest="min_value", help="Minimum value.")
    parser.add_argument("--max", type=int, default=1000, dest="max_value", help="Maximum value.")
    parser.add_argument("--seed", type=int, default=None, help="Random seed for repeatable runs.")
    return parser.parse_args()


def main() -> None:
    args = parse_args()
    if args.size < 0:
        raise SystemExit("--size must be non-negative")
    if args.min_value > args.max_value:
        raise SystemExit("--min must be less than or equal to --max")

    numbers = generate_numbers(args.size, args.min_value, args.max_value, args.seed)
    python_sorted, python_time = measure_sort(sorted, numbers)
    bubble_sorted, bubble_time = measure_sort(bubble_sort, numbers)
    outputs_match = python_sorted == bubble_sorted

    print("Lab 04, variant 2")
    print(f"Input size: {len(numbers)}")
    print(f"Input sample: {numbers[:10]}")
    print(f"Python sorted() time: {python_time:.6f} seconds")
    print(f"Custom bubble sort time: {bubble_time:.6f} seconds")
    print(f"Results match: {outputs_match}")
    print(f"Sorted sample: {python_sorted[:10]}")
    if not outputs_match:
        raise SystemExit("Sorting results do not match")


if __name__ == "__main__":
    main()
