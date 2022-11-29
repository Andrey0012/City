
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(
                "Выберете задачу" + "\n"+
                        "1.Вывести полученный список объектов City в консоль" + "\n"+
                        "2. Сортировка списка городов по наименованию в алфавитном порядке по убыванию без учета регистра" + "\n"+
                        "3. Сортировка списка городов по федеральному округу и наименованию города внутри каждого федерального округа в алфавитном порядке по убыванию с учетом регистра" + "\n"+
                        "4.Найти индекс элемента и значение с наибольшим количеством жителей города." + "\n"+
                        "5.определить количество городов в разрезе регионов"
        );
        Scanner in = new Scanner(System.in);
        while (true) {
            int a = in.nextInt();
            List<City> cities = database();
            switch (a) {
                case 1:
                    cities.forEach(System.out::println);
                    break;
                case 2:
                    sortListName(cities);
                    cities.forEach(System.out::println);
                    break;
                case 3:
                    sortListDistrict(cities);
                    cities.forEach(System.out::println);
                    break;
                case 4:
                    sortMaxPopulation(cities);
                    break;
                case 5:
                    sortListNumberCity(cities);
                    break;
                default:
                    break;
            }
        }
    }
    //Вывести полученный список объектов City в консоль
    public static List<City> database() {
        List<City> cities = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/Guide.txt"));
            scanner.useDelimiter("[;|\\n|\\r]");
            while (scanner.hasNext()) {
                cities.add(new City(scanner.nextInt(), scanner.next(), scanner.next(), scanner.next(), scanner.nextInt(), scanner.next()));
                if (scanner.hasNext()) scanner.next();
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return cities;
    }

    //сортировка списка городов по наименованию в алфавитном порядке по убыванию без учета регистра;
    public static void sortListName(List<City> cities) {
        cities.sort((o1, o2) -> o2.getName().compareToIgnoreCase(o1.getName()));
    }

    // Сортировка списка городов по федеральному округу и наименованию города внутри каждого федерального округа
    // в алфавитном порядке по убыванию с учетом регистра;
    public static void sortListDistrict (List<City> cities) {
        cities.sort(Comparator.comparing(City::getDistrict).thenComparing(City::getName));
    }

    //     город с наибольшим количеством жителей города.
    public static void sortMaxPopulation(List<City> cities) {
        Optional<City> city = cities.stream().max(Comparator.comparing(City::getPopulation));
        System.out.println(city.isPresent() ? String.format("[%d] - %d", city.get().getId(), city.get().getPopulation()): "не найдено");

    }
    //потребуется реализовать поиск количества городов в разрезе регионов
    private static void sortListNumberCity(List<City> cities) {
        Map<String, Integer> regions = new HashMap<>();
        cities.forEach(city -> regions.merge(city.getRegion(), 1, Integer::sum));
        regions.forEach((k, v) -> System.out.printf("[%s] - %d%n", k, v));
    }

}