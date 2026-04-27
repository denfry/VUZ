import argparse
import re
from typing import NamedTuple


class SearchResult(NamedTuple):
    index: int
    checked: int
    words: list[str]


def extract_words(text: str) -> list[str]:
    return re.findall(r"\w+", text, flags=re.UNICODE)


def accelerated_linear_search(text: str, target: str, ignore_case: bool) -> SearchResult:
    words = extract_words(text)
    search_target = target.casefold() if ignore_case else target

    for index, word in enumerate(words):
        current_word = word.casefold() if ignore_case else word
        if current_word == search_target:
            return SearchResult(index=index, checked=index + 1, words=words)

    return SearchResult(index=-1, checked=len(words), words=words)


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Lab 02, variant 2: accelerated linear word search in text."
    )
    parser.add_argument(
        "--text",
        default="alpha beta gamma beta",
        help="Source text. Words are extracted with a regular expression.",
    )
    parser.add_argument("--word", default="gamma", help="Search word.")
    parser.add_argument("--ignore-case", action="store_true", help="Ignore letter case.")
    return parser.parse_args()


def main() -> None:
    args = parse_args()
    result = accelerated_linear_search(args.text, args.word, args.ignore_case)

    print("Lab 02, variant 2")
    print(f"Words: {result.words}")
    print(f"Search word: {args.word}")
    print(f"Found index: {result.index}")
    print(f"Checked words: {result.checked}")
    if result.index == -1:
        print("Result: word was not found")
    else:
        print(f"Result: word was found at index {result.index}")


if __name__ == "__main__":
    main()
