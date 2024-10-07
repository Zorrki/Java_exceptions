class DateValidator {
    public static String validateDate(String date) {
        // Проверка длины строки
        if (date == null || date.length() != 10) {
            return "Формат даты неверен";
        }

        // Проверка формата через символы '-'
        if (date.charAt(4) != '-' || date.charAt(7) != '-') {
            return "Формат даты неверен";
        }

        try {
            // Извлечение года, месяца и дня
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(5, 7));
            int day = Integer.parseInt(date.substring(8, 10));

            // Проверки год, месяц, день
            if (year < 1 || year > 9999) {
                return "Некорректный год";
            }

            if (month < 1 || month > 12) {
                return "Некорректный месяц";
            }

            // Количество дней в каждом месяце, февраль с учетом високосного года
            int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

            if (month == 2 && isLeapYear(year)) {
                daysInMonth[1] = 29;  // Високосный год
            }

            if (day < 1 || day > daysInMonth[month - 1]) {
                return "Некорректный день";
            }

            return date;  // Дата корректна
        } catch (NumberFormatException e) {
            return "Ошибка при преобразовании даты";
        }
    }

    private static boolean isLeapYear(int year) {
        // Логика високосного года
        if (year % 4 != 0) {
            return false;
        } else if (year % 100 != 0) {
            return true;
        } else if (year % 400 != 0) {
            return false;
        } else {
            return true;
        }
    }
}

public class exception_2 {
    public static void main(String[] args) {
        String date;
        if (args.length > 0) {
            date = args[0];
        } else {
            date = "24-09-32";  // Значение по умолчанию
        }
        String result = DateValidator.validateDate(date);
        System.out.println(result);
    }
}