document.addEventListener('DOMContentLoaded', function() {
    const orderForm = document.querySelector('#water-meter-form');
    if (orderForm) {
        orderForm.addEventListener('submit', function(e) {
            e.preventDefault();
            alert('Order submitted! Thank you.');
        });
    }

    const matrixForm = document.querySelector('#matrix-form');
    if (matrixForm) {
        matrixForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const n = parseInt(document.querySelector('#number').value);
            const resultTable = document.querySelector('#result-table');
            resultTable.innerHTML = '<tr><th>Множитель</th><th>Произведение</th></tr>';
            for (let i = 1; i <= 9; i++) {
                const row = document.createElement('tr');
                row.innerHTML = `<td>${i}</td><td>${n * i}</td>`;
                resultTable.appendChild(row);
            }
        });
    }
});