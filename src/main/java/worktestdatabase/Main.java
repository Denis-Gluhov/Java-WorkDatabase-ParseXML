package worktestdatabase;

public class Main {

    private static final String HOST = "jdbc:mysql://localhost:3306/testdatabase";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final int COUNT = 100;

    public static void main(String[] args) {

        DBWorker dbWorker = new DBWorker();

        //инициализация параметров
        dbWorker.setHost(HOST);
        dbWorker.setUsername(USERNAME);
        dbWorker.setPassword(PASSWORD);
        dbWorker.setCount(COUNT);

        //подключение к БД и заполнение таблицы
        dbWorker.connect();

        XMLParse xmlParse = new XMLParse();

        //создание и заполнение xml.1
        xmlParse.createXmlFile(dbWorker.get());
        //создание и заполнение xml.2
        xmlParse.transformXmlFile();

        System.out.println("Арифметическая сумма значений всех атрибутов = " +xmlParse.getSum());
        //отключение от БД
        dbWorker.disconnect();
    }

}
