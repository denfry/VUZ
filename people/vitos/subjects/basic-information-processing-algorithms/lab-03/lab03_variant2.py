import argparse


def normalize_word(word: str, ignore_case: bool) -> str:
    return word.casefold() if ignore_case else word


def recursive_word_search(
    words: list[str],
    target: str,
    index: int = 0,
    ignore_case: bool = False,
) -> int:
    if index >= len(words):
        return -1

    if normalize_word(words[index], ignore_case) == normalize_word(target, ignore_case):
        return index

    return recursive_word_search(words, target, index + 1, ignore_case)


def recursive_format_words(words: list[str], index: int = 0) -> str:
    if index >= len(words):
        return ""

    current = f"{index}: {words[index]}"
    rest = recursive_format_words(words, index + 1)
    if rest:
        return current + "\n" + rest
    return current


def parse_words(raw_words: str) -> list[str]:
    return [word.strip() for word in raw_words.split(",") if word.strip()]


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Lab 03, variant 2: recursive word search and recursive array output."
    )
    parser.add_argument(
        "--words",
        default="alpha,beta,gamma",
        help="Comma-separated word array.",
    )
    parser.add_argument("--word", default="beta", help="Search word.")
    parser.add_argument("--ignore-case", action="store_true", help="Ignore letter case.")
    return parser.parse_args()


def main() -> None:
    args = parse_args()
    words = parse_words(args.words)
    index = recursive_word_search(words, args.word, ignore_case=args.ignore_case)

    print("Lab 03, variant 2")
    print("Array:")
    formatted = recursive_format_words(words)
    print(formatted if formatted else "(empty)")
    print(f"Search word: {args.word}")
    print(f"Found index: {index}")
    if index == -1:
        print("Result: word was not found")
    else:
        print(f"Result: word was found at index {index}")


if __name__ == "__main__":
    main()
