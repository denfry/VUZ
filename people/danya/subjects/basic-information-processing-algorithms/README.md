# Лабораторные 1-4 по дисциплине "Алгоритмы базовой обработки информации"

Вариант: `4`.

## Состав решений

- `lab-01/lab1_variant4_even_odd_count.py` — подсчет четных и нечетных чисел в одномерном массиве.
- `lab-02/lab2_variant4_binary_search.py` — дихотомический поиск в отсортированном случайном массиве 0..100 с проверкой через `bisect_left`.
- `lab-03/lab3_variant4_recursive_interpolation_search.py` — рекурсивный интерполирующий поиск и рекурсивный вывод массива.
- `lab-04/lab4_variant4_bubble_sort.py` — пузырьковая сортировка с проверкой результата через `sorted()`.
- `lab-04/lab4_variant4_merge_sort.py` — сортировка слиянием с проверкой результата через `sorted()`.

## Запуск

Из корня проекта Danya:

```powershell
python lab-01/lab1_variant4_even_odd_count.py --size 1000 --seed 4
python lab-02/lab2_variant4_binary_search.py --size 30 --key 50 --seed 4
python lab-03/lab3_variant4_recursive_interpolation_search.py --values "1 3 7 9 11 15" --key 9
python lab-04/lab4_variant4_bubble_sort.py --size 500 --seed 4
python lab-04/lab4_variant4_merge_sort.py --size 500 --seed 4
```
