Java: 21; 
Database: MySQL; 
Скріншоти проєкту: https://imgur.com/a/dSjnnIa



Перенесено проєкт оренди фільмів на Spring.
Розроблено:
1) Архітектуру проєкту, репозиторії, сервіси + Rest Controllers + UI Controllers.
2) Формулу для обрахунку вартості оренди фільмів для вказаного користувача.

Користувач може змінювати жанри та ціни, що й вимагалося замовником.

Формула рахує таким чином:
rentWithoutPenalty нараховує відразу.
priceWithoutPenalty нараховує за кожен день аренди(максимум днів 7).
priceForPenalDays нараховує за кожен день просрочки. 
Виводить дату початку оренди, дату кінця оренди, ціну за кожну оренду, включаючи всі значення вище, та загальну ціну за все.
Фільми, які користувач ще не повернув система до розрахунку не бере.

Приклад виводу:
Rental statement for Arthur Netrebin:

Arthur Netrebin has rented 'Inception' from 2024-11-09 to 2024-12-07. Total cost: 139,50

Arthur Netrebin has rented 'Cartoon' but not yet returned.

Arthur Netrebin has rented 'Kingdome Come Deliverence' from 2024-11-07 to 2024-11-10. Total cost: 27,00

Total cost for all returned rentals: 166.5


