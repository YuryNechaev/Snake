import java.util.ArrayList;
import java.util.List;

public class Snake {

    private List<SnakeSection> sections;
    private boolean isAlive;
    private SnakeDirection direction;

    public List<SnakeSection> getSections() {
        return sections;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    Snake(int x, int y){

        SnakeSection head = new SnakeSection(x, y);
        sections = new ArrayList<>();
        sections.add(head);
        isAlive=true;

        }
    int getX(){
        return sections.get(0).getX();
    }
    int getY(){
        return sections.get(0).getY();
    }
    void move(){
        if (!isAlive)
        return;
        if(direction == SnakeDirection.UP)
            move(0, -1);
        if(direction == SnakeDirection.DOWN)
            move(0, 1);
        if(direction == SnakeDirection.LEFT)
            move(-1,0);
        if(direction == SnakeDirection.RIGHT)
            move(1,0);

    }
    void move(int i, int j){
        // создаем новую голову
        SnakeSection head = sections.get(0);
        head = new SnakeSection(head.getX() + i, head.getY() + j);
        //Проверяем - не вылезла ли голова за границу комнаты
        checkBorders(head);
        if (!isAlive) return;
        //Проверяем - не пересекает ли змея  саму себя
        checkBody(head);
        if (!isAlive) return;
        //Проверяем - не съела ли змея мышь.
        Mouse mouse = Room.game.getMouse();
        if (head.getX() == mouse.getX() && head.getY() == mouse.getY()) //съела
        {
            sections.add(0, head);                  //Добавили новую голову
            Room.game.eatMouse();                   //Хвост не удаляем, но создаем новую мышь.
        } else //просто движется
        {
            sections.add(0, head);                  //добавили новую голову
            sections.remove(sections.size() - 1);   //удалили последний элемент с хвоста
        }

    }
    /**
     * Метод проверяет - находится ли новая голова в пределах комнаты
     */
    void checkBorders(SnakeSection head) {
        if ((head.getX() < 0 || head.getX() >= Room.game.getWidth()) || head.getY() < 0 || head.getY() >= Room.game.getHeight()) {
            isAlive = false;
        }
    }

    /**
     * Метод проверяет - не совпадает ли голова с каким-нибудь участком тела змеи.
     */
    void checkBody(SnakeSection head) {
        if (sections.contains(head)) {
            isAlive = false;
        }
    }
}
