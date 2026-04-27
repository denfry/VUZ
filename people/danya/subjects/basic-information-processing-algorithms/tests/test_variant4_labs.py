import importlib.util
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]


def load_module(relative_path: str, module_name: str):
    spec = importlib.util.spec_from_file_location(module_name, ROOT / relative_path)
    module = importlib.util.module_from_spec(spec)
    sys.modules[module_name] = module
    spec.loader.exec_module(module)
    return module


def test_lab01_counts_even_and_odd_values():
    lab01 = load_module('lab-01/lab1_variant4_even_odd_count.py', 'lab01_variant4')

    result = lab01.count_even_odd([1, 2, 0, -3, -4, 7])

    assert result.even == 3
    assert result.odd == 3
    assert result.total == 6


def test_lab02_binary_search_returns_first_match():
    lab02 = load_module('lab-02/lab2_variant4_binary_search.py', 'lab02_variant4')

    assert lab02.binary_search_first([1, 2, 2, 2, 5], 2) == 1
    assert lab02.binary_search_first([1, 2, 5], 4) == -1


def test_lab03_recursive_interpolation_search_finds_value():
    lab03 = load_module('lab-03/lab3_variant4_recursive_interpolation_search.py', 'lab03_variant4')

    values = [1, 4, 7, 10, 13, 16]

    assert lab03.interpolation_search_recursive(values, 10) == 3
    assert lab03.interpolation_search_recursive(values, 11) == -1


def test_lab04_custom_sorts_match_python_sorted():
    bubble = load_module('lab-04/lab4_variant4_bubble_sort.py', 'lab04_bubble_variant4')
    merge = load_module('lab-04/lab4_variant4_merge_sort.py', 'lab04_merge_variant4')
    values = [5, -1, 3, 3, 0]

    assert bubble.bubble_sort(values) == sorted(values)
    assert merge.merge_sort(values) == sorted(values)
    assert values == [5, -1, 3, 3, 0]
