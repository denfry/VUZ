import importlib.util
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]


def load_module(relative_path: str, module_name: str):
    spec = importlib.util.spec_from_file_location(module_name, ROOT / relative_path)
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def test_lab01_splits_even_and_odd_numbers():
    lab01 = load_module("lab-01/lab01_variant2.py", "lab01_variant2")

    even, odd = lab01.split_even_odd([5, 2, 0, -3, -4, 7])

    assert even == [2, 0, -4]
    assert odd == [5, -3, 7]


def test_lab02_accelerated_search_stops_after_found_word():
    lab02 = load_module("lab-02/lab02_variant2.py", "lab02_variant2")

    result = lab02.accelerated_linear_search("Alpha beta gamma beta", "gamma", False)

    assert result.index == 2
    assert result.checked == 3
    assert result.words == ["Alpha", "beta", "gamma", "beta"]


def test_lab02_search_can_ignore_case():
    lab02 = load_module("lab-02/lab02_variant2.py", "lab02_variant2")

    result = lab02.accelerated_linear_search("Alpha beta", "alpha", True)

    assert result.index == 0
    assert result.checked == 1


def test_lab03_recursive_word_search_finds_index():
    lab03 = load_module("lab-03/lab03_variant2.py", "lab03_variant2")

    index = lab03.recursive_word_search(["alpha", "beta", "gamma"], "beta")

    assert index == 1


def test_lab03_recursive_word_search_returns_minus_one_when_missing():
    lab03 = load_module("lab-03/lab03_variant2.py", "lab03_variant2")

    index = lab03.recursive_word_search(["alpha", "beta"], "delta")

    assert index == -1


def test_lab04_bubble_sort_matches_python_sorted():
    lab04 = load_module("lab-04/lab04_variant2.py", "lab04_variant2")

    values = [7, -1, 3, 3, 0]

    assert lab04.bubble_sort(values) == sorted(values)
    assert values == [7, -1, 3, 3, 0]
