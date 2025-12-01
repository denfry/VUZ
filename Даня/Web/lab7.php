<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ЛР 7 – Работа с массивами на PHP</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="styles.css">
</head>
<body>

<header>
    <nav>
        <ul>
            <li><a href="index.html" class="nav-link">Главная</a></li>
            <li><a href="lab1.html" class="nav-link">Тренажерный зал</a></li>
            <li><a href="lab3.html" class="nav-link">Транспортный налог</a></li>
            <li><a href="lab5.html" class="nav-link">Таблица степеней</a></li>
            <li><a href="lab7.php" class="nav-link active">ЛР 7 – PHP массивы</a></li>
        </ul>
    </nav>
</header>

<main>
    <h1>Лабораторная работа №7</h1>
    <p class="author">Юрков Д.А., гр. МВА-122</p>

    <?php
    // Исходные массивы
    $array1_original = [12, 45, 7, 23, 56, 34, 89, 3, 67, 41];
    $array2_original = [23, 8, 56, 91, 34, 12, 77, 45, 5];

    // Копии для работы
    $array1 = $array1_original;
    $array2 = $array2_original;

    $set1 = $array1;
    $set2 = $array2;

    // Операции с множествами
    $union          = array_unique(array_merge($set1, $set2));       // объединение
    $intersection   = array_intersect($set1, $set2);                 // пересечение
    $difference     = array_diff($set1, $set2);                      // разность (есть в 1, но нет во 2)
    $symmetricDiff  = array_merge(array_diff($set1, $set2), array_diff($set2, $set1)); // симметричная разность

    // Сортировка
    sort($union);
    rsort($array1);
    rsort($array2);

    // Статистика по массивам
    $max1 = max($set1);
    $min1 = min($set1);
    $sum1 = array_sum($set1);
    $avg1 = round($sum1 / count($set1), 2);

    $max2 = max($set2);
    $min2 = min($set2);
    $sum2 = array_sum($set2);
    $avg2 = round($sum2 / count($set2), 2);
    ?>

    <section>
        <h2>Исходные массивы</h2>
        <table class="result-table">
            <tr><th>Массив 1</th><td><?php echo implode(', ', $array1_original); ?></td></tr>
            <tr><th>Массив 2</th><td><?php echo implode(', ', $array2_original); ?></td></tr>
        </table>
    </section>

    <section>
        <h2>Результаты операций с массивами</h2>

        <table class="result-table">
            <thead>
                <tr>
                    <th>Операция</th>
                    <th>Результат</th>
                    <th>Количество элементов</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Объединение (union)</td>
                    <td><?php echo implode(', ', $union); ?></td>
                    <td><?php echo count($union); ?></td>
                </tr>
                <tr>
                    <td>Пересечение (intersection)</td>
                    <td><?php echo (count($intersection) > 0) ? implode(', ', $intersection) : '—'; ?></td>
                    <td><?php echo count($intersection); ?></td>
                </tr>
                <tr>
                    <td>Разность (A \ B)</td>
                    <td><?php echo (count($difference) > 0) ? implode(', ', $difference) : '—'; ?></td>
                    <td><?php echo count($difference); ?></td>
                </tr>
                <tr>
                    <td>Симметричная разность</td>
                    <td><?php echo (count($symmetricDiff) > 0) ? implode(', ', $symmetricDiff) : '—'; ?></td>
                    <td><?php echo count($symmetricDiff); ?></td>
                </tr>
            </tbody>
        </table>
    </section>

    <section>
        <h2>Статистика по массивам</h2>
        <table class="result-table">
            <thead>
                <tr>
                    <th>Параметр</th>
                    <th>Массив 1</th>
                    <th>Массив 2</th>
                </tr>
            </thead>
            <tbody>
                <tr><td>Максимум</td><td><?php echo $max1; ?></td><td><?php echo $max2; ?></td></tr>
                <tr><td>Минимум</td><td><?php echo $min1; ?></td><td><?php echo $min2; ?></td></tr>
                <tr><td>Сумма</td><td><?php echo $sum1; ?></td><td><?php echo $sum2; ?></td></tr>
                <tr><td>Среднее</td><td><?php echo $avg1; ?></td><td><?php echo $avg2; ?></td></tr>
                <tr><td>Количество элементов</td><td><?php echo count($set1); ?></td><td><?php echo count($set2); ?></td></tr>
            </tbody>
        </table>
    </section>

    <section style="margin-top: 2em;">
        <button class="btn" onclick="location.reload()">Обновить массивы</button>
    </section>

</main>

<footer>
    <p>© 2025 Юрков Д.А. Все права защищены.</p>
</footer>

</body>
</html>