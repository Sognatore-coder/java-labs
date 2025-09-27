public class Table {
    private int[][] data; // Двумерный массив
    private int rows;     // Количество строк
    private int cols;     // Количество столбцов

    // Проверка корректности индексов
    private void checkIndices(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IllegalArgumentException("Неверные индексы: row=" + row + ", col=" + col);
        }
    }


    public Table(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Размеры таблицы должны быть положительными");
        }

        this.rows = rows;
        this.cols = cols;
        this.data = new int[rows][cols];
    }

    // Получить значение по индексам
    public int getValue(int row, int col) {
        checkIndices(row, col);
        return data[row][col];
    }

    // Установить значение по индексам
    public void setValue(int row, int col, int value) {
        checkIndices(row, col);
        data[row][col] = value;
    }

    // Количество строк
    public int rows() {
        return rows;
    }

    // Количество столбцов
    public int cols() {
        return cols;
    }

    // Среднее арифметическое всех значений
    public double average() {
        if (rows == 0 || cols == 0) {
            return 0.0;
        }

        int sum = 0;
        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum += data[i][j];
                count++;
            }
        }

        return (double) sum / count;
    }

    // Представление таблицы в виде строки
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(String.format("%4d", data[i][j]));
            }
            sb.append("\n");
        }

        return sb.toString();
    }


    public static void main(String[] args) {

        Table table = new Table(3, 4);


        table.setValue(0, 0, 1);
        table.setValue(0, 1, 2);
        table.setValue(0, 2, 3);
        table.setValue(0, 3, 4);

        table.setValue(1, 0, 5);
        table.setValue(1, 1, 6);
        table.setValue(1, 2, 7);
        table.setValue(1, 3, 8);

        table.setValue(2, 0, 9);
        table.setValue(2, 1, 10);
        table.setValue(2, 2, 11);
        table.setValue(2, 3, 12);


        System.out.println("Таблица:");
        System.out.println(table.toString());

        System.out.println("Количество строк: " + table.rows());
        System.out.println("Количество столбцов: " + table.cols());
        System.out.println("Значение в [1,2]: " + table.getValue(1, 2));
        System.out.println("Среднее значение: " + table.average());
    }
}