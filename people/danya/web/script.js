// === ЛР 5: Таблица степеней ===
function generatePowerTable(event) {
    event.preventDefault();

    const a = parseFloat(document.getElementById('base')?.value);
    const n = parseInt(document.getElementById('exponent')?.value);
    const tbody = document.getElementById('power-table-body');
    const resultSection = document.getElementById('result');

    if (!tbody || !resultSection) return;

    tbody.innerHTML = '';

    if (isNaN(a) || isNaN(n)) {
        alert('Введите корректные числа');
        return;
    }

    if (n < 1 || n > 100) {
        alert('Количество степеней должно быть от 1 до 100');
        return;
    }

    if (Math.abs(a) > 10000) {
        alert('Слишком большое основание (|a| ≤ 10000)');
        return;
    }

    for (let i = 1; i <= n; i++) {
        const row = document.createElement('tr');
        row.classList.add('fade-in');

        let value = Math.pow(a, i);
        let display = '';

        if (!isFinite(value)) {
            display = '∞';
        } else if (Math.abs(value) > 1e12) {
            display = value.toExponential(4);
        } else {
            display = value.toLocaleString('ru-RU', { maximumFractionDigits: 4 });
        }

        row.innerHTML = `<td>${i}</td><td>${display}</td>`;
        tbody.appendChild(row);
    }

    resultSection.style.display = 'block';
    const modal = document.getElementById('powerModal');
    if (modal) {
        modal.style.display = 'flex';
        // Автозакрытие через 4 секунды
        setTimeout(() => {
            if (modal.style.display === 'flex') modal.style.display = 'none';
        }, 4000);
    }
}

// === ЛР 3-4: Валидация формы транспортного налога ===
function submitServiceForm(event) {
    event.preventDefault();

    clearErrors();

    let hasError = false;

    const fields = {
        name: document.getElementById('name'),
        inn: document.getElementById('inn'),
        passport: document.getElementById('passport'),
        vehicle: document.getElementById('vehicle'),
        regNumber: document.getElementById('regNumber'),
        power: document.getElementById('power'),
        period: document.getElementById('period'),
        amount: document.getElementById('amount'),
        date: document.getElementById('date')
    };

    // ФИО — более мягкая проверка
    if (fields.name) {
        const val = fields.name.value.trim();
        if (!val) {
            showError('name', 'Введите ФИО');
            hasError = true;
        } else if (!/^[А-ЯЁа-яё\s-]{2,70}$/u.test(val)) {
            showError('name', 'ФИО может содержать только русские буквы, пробелы и дефисы');
            hasError = true;
        }
    }

    // ИНН
    if (fields.inn) {
        const val = fields.inn.value.trim();
        if (!/^\d{10}$|^\d{12}$/.test(val)) {
            showError('inn', 'ИНН — 10 или 12 цифр');
            hasError = true;
        }
    }

    // Паспорт (самые популярные форматы)
    if (fields.passport) {
        const val = fields.passport.value.trim().replace(/\s+/g, ' ');
        if (!/^\d{4}\s?\d{6}$|^\d{2}\s\d{2}\s\d{6}$/.test(val)) {
            showError('passport', 'Формат: 1234 567890 или 12 34 567890');
            hasError = true;
        }
    }

    // Госномер (современный российский формат 2025+)
    if (fields.regNumber) {
        const val = fields.regNumber.value.trim().toUpperCase();
        if (!/^[АВЕКМНОРСТУХ]\d{3}[АВЕКМНОРСТУХ]{2}\d{2,3}$/.test(val)) {
            showError('regNumber', 'Пример: А123БВ77 или С777ММ777');
            hasError = true;
        }
    }

    // Мощность
    if (fields.power) {
        const val = Number(fields.power.value);
        if (!val || val <= 0) {
            showError('power', 'Мощность должна быть > 0');
            hasError = true;
        }
    }

    // Остальные обязательные поля
    ['vehicle', 'period', 'amount', 'date'].forEach(id => {
        if (fields[id] && !fields[id].value.trim()) {
            showError(id, 'Поле обязательно');
            hasError = true;
        }
    });

    // Проверка даты
    if (fields.date) {
        const d = new Date(fields.date.value);
        const today = new Date();
        today.setHours(0,0,0,0);
        if (d > today) {
            showError('date', 'Дата не может быть в будущем');
            hasError = true;
        }
    }

    if (!hasError) {
        const confirmation = document.getElementById('serviceConfirmation');
        if (confirmation) {
            confirmation.innerHTML = `
                <strong>Декларация отправлена!</strong><br><br>
                ФИО: ${fields.name?.value || '—'}<br>
                ИНН: ${fields.inn?.value || '—'}<br>
                Паспорт: ${fields.passport?.value || '—'}<br>
                ТС: ${fields.vehicle?.value || '—'}<br>
                Госномер: ${fields.regNumber?.value || '—'}<br>
                Мощность: ${fields.power?.value || '—'} л.с.<br>
                Период: ${fields.period?.options[fields.period.selectedIndex]?.text || '—'}<br>
                Сумма: ${fields.amount?.value || '—'} ₽<br>
                Дата: ${fields.date?.value ? new Date(fields.date.value).toLocaleDateString('ru-RU') : '—'}
            `;
        }

        const modal = document.getElementById('serviceModal');
        if (modal) {
            modal.style.display = 'flex';
            setTimeout(() => modal.style.display = 'none', 6000);
        }

        document.getElementById('serviceForm')?.reset();
    }
}

function showError(id, msg) {
    const field = document.getElementById(id);
    if (!field) return;

    const error = document.createElement('div');
    error.className = 'error-message';
    error.textContent = msg;
    field.parentElement.appendChild(error);
    field.classList.add('error');
}

function clearErrors() {
    document.querySelectorAll('.error-message').forEach(el => el.remove());
    document.querySelectorAll('.error').forEach(el => el.classList.remove('error'));
}

function closeServiceModal() {
    document.getElementById('serviceModal')?.style.display = 'none';
}

// Модальное окно консультации (lab1)
function showContactModal() {
    document.getElementById('contactModal')?.style.display = 'flex';
}

function closeContactModal() {
    document.getElementById('contactModal')?.style.display = 'none';
}

document.getElementById('contactForm')?.addEventListener('submit', e => {
    e.preventDefault();
    alert('Заявка отправлена! (демонстрация)');
    e.target.reset();
    closeContactModal();
});