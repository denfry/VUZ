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
            <li><a href="lab7.php" class="nav-link active">ЛР 7 – PHP</a></li>
        </ul>
    </nav>
</header>

<main>
    <h1>Лабораторная работа №7</h1>
    <p class="author">Юрков Д.А., гр. МВА-122</p>

    <?php
    // Параметры генерации случайных массивов
    $length1 = 10;
    $length2 = 9;
    $min_val = 1;
    $max_val = 120;

    // Если нажали кнопку генерации — создаём новые случайные массивы
    if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['generate'])) {
        $array1_original = [];
        $array2_original = [];

        for ($i = 0; $i < $length1; $i++) {
            $array1_original[] = mt_rand($min_val, $max_val);
        }
        for ($i = 0; $i < $length2; $i++) {
            $array2_original[] = mt_rand($min_val, $max_val);
        }
    } 
    // Иначе показываем начальные фиксированные массивы
    else {
        $array1_original = [12, 45, 7, 23, 56, 34, 89, 3, 67, 41];
        $array2_original = [23, 8, 56, 91, 34, 12, 77, 45, 5];
    }

    // Рабочие копии массивов
    $array1 = $array1_original;
    $array2 = $array2_original;

    // Операции с множествами
    $union        = array_unique(array_merge($array1, $array2));
    $intersection = array_intersect($array1, $array2);
    $difference   = array_diff($array1, $array2);
    $symmetric    = array_merge(array_diff($array1, $array2), array_diff($array2, $array1));

    // Сортировки (для наглядности)
    sort($union);
    rsort($array1);
    rsort($array2);

    // Статистика
    $max1 = $array1_original ? max($array1_original) : 0;
    $min1 = $array1_original ? min($array1_original) : 0;
    $sum1 = array_sum($array1_original);
    $avg1 = count($array1_original) ? round($sum1 / count($array1_original), 2) : 0;

    $max2 = $array2_original ? max($array2_original) : 0;
    $min2 = $array2_original ? min($array2_original) : 0;
    $sum2 = array_sum($array2_original);
    $avg2 = count($array2_original) ? round($sum2 / count($array2_original), 2) : 0;
    ?>

    <section>
        <h2>Исходные массивы</h2>

        <form method="post" style="margin: 1.5em 0;">
            <button type="submit" name="generate" class="btn">Сгенерировать новые случайные массивы</button>
        </form>

        <table class="result-table">
            <tr>
                <th>Массив 1 (<?= count($array1_original) ?> элементов)</th>
                <td><?= htmlspecialchars(implode(', ', $array1_original)) ?></td>
            </tr>
            <tr>
                <th>Массив 2 (<?= count($array2_original) ?> элементов)</th>
                <td><?= htmlspecialchars(implode(', ', $array2_original)) ?></td>
            </tr>
        </table>
    </section>

    <section>
        <h2>Операции с множествами</h2>
        <table class="result-table">
            <thead>
                <tr>
                    <th>Операция</th>
                    <th>Результат</th>
                    <th>Кол-во элементов</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Объединение (union)</td>
                    <td><?= htmlspecialchars(implode(', ', $union)) ?: '—' ?></td>
                    <td><?= count($union) ?></td>
                </tr>
                <tr>
                    <td>Пересечение</td>
                    <td><?= htmlspecialchars(implode(', ', $intersection)) ?: '—' ?></td>
                    <td><?= count($intersection) ?></td>
                </tr>
                <tr>
                    <td>Разность (1 \\ 2)</td>
                    <td><?= htmlspecialchars(implode(', ', $difference)) ?: '—' ?></td>
                    <td><?= count($difference) ?></td>
                </tr>
                <tr>
                    <td>Симметричная разность</td>
                    <td><?= htmlspecialchars(implode(', ', $symmetric)) ?: '—' ?></td>
                    <td><?= count($symmetric) ?></td>
                </tr>
            </tbody>
        </table>
    </section>

    <section>
        <h2>Статистика массивов</h2>
        <table class="result-table">
            <thead>
                <tr>
                    <th>Параметр</th>
                    <th>Массив 1</th>
                    <th>Массив 2</th>
                </tr>
            </thead>
            <tbody>
                <tr><td>Максимум</td><td><?= $max1 ?></td><td><?= $max2 ?></td></tr>
                <tr><td>Минимум</td><td><?= $min1 ?></td><td><?= $min2 ?></td></tr>
                <tr><td>Сумма</td><td><?= $sum1 ?></td><td><?= $sum2 ?></td></tr>
                <tr><td>Среднее</td><td><?= $avg1 ?></td><td><?= $avg2 ?></td></tr>
                <tr><td>Количество элементов</td><td><?= count($array1_original) ?></td><td><?= count($array2_original) ?></td></tr>
            </tbody>
        </table>
    </section>

</main>

<footer>
    <p>© 2026 Юрков Д.А. Все права защищены.</p>
</footer>

</body>
</html>