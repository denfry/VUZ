# Лабораторная работа 1

## Задания

### 1. Вычисление стороны куба по объему (Вариант 19)
**Файл:** `CubeSideCalculator.java`

Программа вычисляет сторону куба по заданному объему.

**Запуск:**
```bash
javac CubeSideCalculator.java
java CubeSideCalculator
```

### 2. Обработка массивов - Задача 19.1
**Файл:** `ArrayDivisibleBy3.java`

Дан одномерный массив Xn. Найти количество элементов массива, делящихся на 3 без остатка.

**Способ задания массива:** Ввод с клавиатуры

**Запуск:**
```bash
javac ArrayDivisibleBy3.java
java ArrayDivisibleBy3
```

**Запуск через jar-файл:**
```bash
javac ArrayDivisibleBy3.java
jar cfm ArrayDivisibleBy3.jar META-INF/MANIFEST.MF ArrayDivisibleBy3.class
java -jar ArrayDivisibleBy3.jar
```

### 3. Обработка массивов - Задача 19.2
**Файл:** `NonZeroEvenOddArray.java`

Дан одномерный массив Zm. Сформировать массив Хк, состоящий из ненулевых элементов массива Zm, сначала с четным, а затем с нечетным индексом.

**Способ задания массива:** Генерация случайных чисел в заданном диапазоне

**Запуск:**
```bash
javac NonZeroEvenOddArray.java
java NonZeroEvenOddArray
```

## Структура программы

Все программы соответствуют требованиям:
- Методы выполняют вычисления и возвращают результат
- Ввод исходных данных и вывод результата выполняется в методе main
- Использованы 2 разных способа задания исходных массивов:
  1. Ввод с клавиатуры (ArrayDivisibleBy3)
  2. Генерация случайных чисел (NonZeroEvenOddArray)

