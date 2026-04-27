import argparse
import random
import time


def generate_numbers(size: int, min_value: int, max_value: int, seed: int | None) -> list[int]:
    rng = random.Random(seed)
    return [rng.randint(min_value, max_value) for _ in range(size)]


def split_even_odd(numbers: list[int]) -> tuple[list[int], list[int]]:
    even = []
    odd = []
    for number in numbers:
        if number % 2 == 0:
            even.append(number)
        else:
            odd.append(number)
    return even, odd


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Lab 01, variant 2: split random integers into even and odd arrays."
    )
    parser.add_argument("--size", type=int, default=100_000, help="Number of generated values.")
    parser.add_argument("--min", type=int, default=-100, dest="min_value", help="Minimum value.")
    parser.add_argument("--max", type=int, default=100, dest="max_value", help="Maximum value.")
    parser.add_argument("--seed", type=int, default=None, help="Random seed for repeatable runs.")
    return parser.parse_args()


def main() -> None:
    args = parse_args()
    if args.size < 0:
        raise SystemExit("--size must be non-negative")
    if args.min_value > args.max_value:
        raise SystemExit("--min must be less than or equal to --max")

    numbers = generate_numbers(args.size, args.min_value, args.max_value, args.seed)
    start = time.perf_counter()
    even, odd = split_even_odd(numbers)
    elapsed = time.perf_counter() - start

    print("Lab 01, variant 2")
    print(f"Input size: {len(numbers)}")
    print(f"Even count: {len(even)}")
    print(f"Odd count: {len(odd)}")
    print(f"Check count: {len(even) + len(odd)}")
    print(f"Even sample: {even[:10]}")
    print(f"Odd sample: {odd[:10]}")
    print(f"Processing time: {elapsed:.6f} seconds")


if __name__ == "__main__":
    main()
