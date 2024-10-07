import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Attestation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Пожалуйста, введите данные в следующем формате:");
            System.out.println("Фамилия Имя Отчество | Дата рождения (дд.мм.гггг) | Номер телефона | Пол (М или Ж)");
            System.out.println("Пример: Иванов Иван Иванович 01.01.1990 1234567890 М");

            String input = scanner.nextLine();

            try {
                String lastName = processInput(input);
                System.out.println("Данные успешно обработаны и сохранены.");
                System.out.println("Содержимое файла " + lastName + ".txt:");
                Files.lines(Paths.get(lastName + ".txt")).forEach(System.out::println);
            } catch (IllegalArgumentException e) {
                System.err.println("Ошибка ввода данных: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Ошибка записи в файл: " + e.getMessage());
                e.printStackTrace();
            }

            System.out.println("Хотите добавить новую информацию? (да/нет): ");
            String continueInput = scanner.nextLine().trim().toLowerCase();
            if (!continueInput.equals("да")) {
                break;
            }
        }

        scanner.close();
        System.out.println("Программа завершена.");
    }

    private static String processInput(String input) throws IOException {
        String[] data = input.split(" ");
        
        if (data.length != 6) {
            throw new IllegalArgumentException(
                "Ожидается 6 элементов: Фамилия, Имя, Отчество, Дата рождения, Номер телефона и Пол.");
        }

        String lastName = data[0];
        String firstName = data[1];
        String middleName = data[2];
        String dateOfBirth = data[3];
        String phoneNumber = data[4];
        String gender = data[5];

        validateDateOfBirth(dateOfBirth);
        validatePhoneNumber(phoneNumber);
        validateGender(gender);

        String formattedData = String.format("%s %s %s %s %s %s", lastName, firstName, middleName, dateOfBirth, phoneNumber, gender);
        writeToFile(lastName, formattedData);

        return lastName;
    }

    private static void validateDateOfBirth(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Некорректный формат даты рождения. Ожидается формат дд.мм.гггг, пример: 01.01.1990");
        }
    }

    private static void validatePhoneNumber(String phoneNumber) {
        try {
            Long.parseLong(phoneNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный номер телефона. Ожидается целое число без пробелов.");
        }
    }

    private static void validateGender(String gender) {
        if (!(gender.equalsIgnoreCase("f") || gender.equalsIgnoreCase("m") || gender.equalsIgnoreCase("ж") || gender.equalsIgnoreCase("м"))) {
            throw new IllegalArgumentException("Некорректный формат пола. Используйте 'М' или 'Ж'.");
        }
    }

    private static void writeToFile(String lastName, String data) throws IOException {
        String fileName = lastName + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(data);
            writer.newLine();
        }
    }
}