import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.KeyEvent;

public class Room {
    static Room game;
    private int width;
    private int height;
    private Snake snake;
    private Mouse mouse;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
    }

    public static void main(String[] args) {
        game = new Room(20, 20, new Snake(10, 10));
        game.snake.setDirection(SnakeDirection.DOWN);
        game.createMouse();
        game.run();

    }

    void sleep() {

        try {
            for (int i = 0; i < snake.getSections().size() && i < 16; i++) {
                Thread.sleep(500 - i * 20);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        //Создаем объект "наблюдатель за клавиатурой" и стартуем его.
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        //пока змея жива
        while (snake.isAlive()) {
            //"наблюдатель" содержит события о нажатии клавиш?
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                //Если равно символу 'q' - выйти из игры.
                if (event.getKeyChar() == 'q') return;

                //Если "стрелка влево" - сдвинуть фигурку влево
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    snake.setDirection(SnakeDirection.LEFT);
                    //Если "стрелка вправо" - сдвинуть фигурку вправо
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    snake.setDirection(SnakeDirection.RIGHT);
                    //Если "стрелка вверх" - сдвинуть фигурку вверх
                else if (event.getKeyCode() == KeyEvent.VK_UP)
                    snake.setDirection(SnakeDirection.UP);
                    //Если "стрелка вниз" - сдвинуть фигурку вниз
                else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                    snake.setDirection(SnakeDirection.DOWN);
            }

            snake.move();   //двигаем змею
            print();        //отображаем текущее состояние игры
            sleep();        //пауза между ходами
        }

        //Выводим сообщение "Game Over"
        System.out.println("Game Over!");
    }

    void print() {
        //Создаем массив, куда будем "рисовать" текущее состояние игры
        int[][] panel = new int[height][width];
        // присваиваем значение телу змеи
        ArrayList<SnakeSection> sections = new ArrayList<SnakeSection>(snake.getSections());
        for (SnakeSection section : sections)
            panel[section.getY()][section.getX()] = 1;
        // присваиваем значение голове змеи
        panel[snake.getY()][snake.getX()] = snake.isAlive() ? 2 : 4;
        // присваиваем значение мыши
        panel[mouse.getY()][mouse.getX()] = 3;

        // Выводим на экран
        String[] symbols = {". ", "x ", "X ", "^ ", "* "};
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(symbols[panel[i][j]]);
            }
            System.out.println();
        }
    }

    void createMouse() {
        mouse = new Mouse((int) (Math.random() * width), (int) (Math.random() * height));
    }

    void eatMouse() {
        createMouse();

    }

}
