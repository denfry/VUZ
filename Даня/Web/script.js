// === ЛР 5: Таблица степеней (a^i от 1 до n) ===
function generatePowerTable(event) {
    event.preventDefault();
    const a = parseFloat(document.getElementById('base').value);
    const n = parseInt(document.getElementById('exponent').value);
    const tbody = document.getElementById('power-table-body');
    const resultSection = document.getElementById('result');
    
    tbody.innerHTML = '';
    
    for (let i = 1; i <= n; i++) {
        const row = document.createElement('tr');
        row.classList.add('fade-in');
        const powerValue = Math.pow(a, i);
        row.innerHTML = `<td>${i}</td><td>${powerValue.toFixed(4)}</td>`;
        tbody.appendChild(row);
    }
    
    resultSection.style.display = 'block';
    showPowerModal();
}

function showPowerModal() {
    const modal = document.getElementById('powerModal');
    if (modal) {
        modal.style.display = 'flex';
        setTimeout(() => closePowerModal(), 1500);
    }
}

function closePowerModal() {
    const modal = document.getElementById('powerModal');
    if (modal) {
        modal.style.display = 'none';
    }
}

// === ЛР 6: Валидация формы транспортного налога (lab3.html) ===
function submitServiceForm(event) {
    event.preventDefault();

    // Сброс предыдущих ошибок
    clearErrors();

    let hasError = false;

    // Получаем значения
    const name = document.getElementById('name')?.value.trim();
    const inn = document.getElementById('inn')?.value.trim();
    const passport = document.getElementById('passport')?.value.trim();
    const vehicle = document.getElementById('vehicle')?.value.trim();
    const regNumber = document.getElementById('regNumber')?.value.trim();
    const power = document.getElementById('power')?.value;
    const period = document.getElementById('period')?.value;
    const amount = document.getElementById('amount')?.value;
    const date = document.getElementById('date')?.value;

    // Валидация ФИО (только кириллица, пробелы и дефисы)
    if (name !== undefined) {
        const nameRegex = /^[А-ЯЁ][а-яё]+(?: [А-ЯЁ][а-яё]+){1,2}$/;
        if (!name) {
            showError('name', 'Введите ФИО налогоплательщика');
            hasError = true;
        } else if (!nameRegex.test(name)) {
            showError('name', 'ФИО должно содержать только русские буквы, начинаться с заглавной');
            hasError = true;
        }
    }

    // Валидация ИНН (10 или 12 цифр)
    if (inn !== undefined) {
        const innRegex = /^\d{10}$|^\d{12}$/;
        if (!inn) {
            showError('inn', 'Введите ИНН');
            hasError = true;
        } else if (!innRegex.test(inn)) {
            showError('inn', 'ИНН должен содержать 10 или 12 цифр');
            hasError = true;
        }
    }

    // Валидация паспорта
    if (passport !== undefined) {
        const passportRegex = /^\d{4} \d{6}$|^\d{2} \d{2} \d{6}$/;
        if (!passport) {
            showError('passport', 'Введите паспортные данные');
            hasError = true;
        } else if (!passportRegex.test(passport.replace(/\s+/g, ' '))) {
            showError('passport', 'Неверный формат паспорта (пример: 1234 567890)');
            hasError = true;
        }
    }

    // Валидация марки и модели
    if (vehicle !== undefined && !vehicle) {
        showError('vehicle', 'Введите марку и модель транспортного средства');
        hasError = true;
    }

    // Валидация госномера (пример: А123БВ777)
    if (regNumber !== undefined) {
        const regNumberRegex = /^[АВЕКМНОРСТУХ]\d{3}[АВЕКМНОРСТУХ]{2}\d{2,3}$/i;
        if (!regNumber) {
            showError('regNumber', 'Введите государственный номер');
            hasError = true;
        } else if (!regNumberRegex.test(regNumber.toUpperCase())) {
            showError('regNumber', 'Неверный формат номера (пример: А123БВ777)');
            hasError = true;
        }
    }

    // Валидация мощности
    if (power !== undefined) {
        if (!power || power <= 0) {
            showError('power', 'Мощность должна быть больше 0');
            hasError = true;
        }
    }

    // Валидация периода
    if (period !== undefined && !period) {
        showError('period', 'Выберите период уплаты налога');
        hasError = true;
    }

    // Валидация суммы
    if (amount !== undefined) {
        if (!amount || amount < 0) {
            showError('amount', 'Сумма налога должна быть неотрицательной');
            hasError = true;
        }
    }

    // Валидация даты
    if (date !== undefined) {
        if (!date) {
            showError('date', 'Выберите дату подачи декларации');
            hasError = true;
        } else {
            const selectedDate = new Date(date);
            const today = new Date();
            today.setHours(0, 0, 0, 0);
            if (selectedDate > today) {
                showError('date', 'Дата не может быть в будущем');
                hasError = true;
            }
        }
    }

    // Если ошибок нет — успех
    if (!hasError) {
        const periodText = document.getElementById('period')?.options[
            document.getElementById('period')?.selectedIndex
        ]?.text || period;

        const confirmation = document.getElementById('serviceConfirmation');
        if (confirmation) {
            confirmation.innerHTML = `
                <strong>Декларация успешно отправлена!</strong><br><br>
                ФИО: ${name || '—'}<br>
                ИНН: ${inn || '—'}<br>
                Паспорт: ${passport || '—'}<br>
                ТС: ${vehicle || '—'}<br>
                Гос. номер: ${regNumber || '—'}<br>
                Мощность: ${power || '—'} л.с.<br>
                Период: ${periodText || '—'}<br>
                Сумма: ${amount || '—'} руб.<br>
                Дата подачи: ${date ? new Date(date).toLocaleDateString('ru-RU') : '—'}
            `;
        }
        const modal = document.getElementById('serviceModal');
        if (modal) {
            modal.style.display = 'flex';
        }
        const form = document.getElementById('serviceForm');
        if (form) {
            form.reset();
        }
        setTimeout(closeServiceModal, 5000);
    }
}

// Показать ошибку под полем
function showError(fieldId, message) {
    const field = document.getElementById(fieldId);
    const error = document.createElement('div');
    error.className = 'error-message';
    error.style.color = '#d32f2f';
    error.style.fontSize = '0.9em';
    error.style.marginTop = '5px';
    error.textContent = message;

    // Вставляем ошибку после поля
    field.parentElement.appendChild(error);
    field.style.borderColor = '#d32f2f';
}

// Очистить все ошибки
function clearErrors() {
    document.querySelectorAll('.error-message').forEach(el => el.remove());
    document.querySelectorAll('input, select').forEach(el => {
        el.style.borderColor = '#80cbc4';
    });
}

function closeServiceModal() {
    document.getElementById('serviceModal').style.display = 'none';
}

// === Остальные функции (модалка записи в зал) ===
function showContactModal() {
    document.getElementById('contactModal').style.display = 'flex';
}

function closeContactModal() {
    document.getElementById('contactModal').style.display = 'none';
}

document.getElementById('contactForm')?.addEventListener('submit', function(event) {
    event.preventDefault();
    alert('Заявка на консультацию отправлена!');
    this.reset();
    closeContactModal();
});