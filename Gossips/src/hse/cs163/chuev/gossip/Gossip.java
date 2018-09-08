package hse.cs163.chuev.gossip;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

/**
 * Класс реализующий объект "сплетница"
 * Реализует интерфейс hse.cs163.chuev.gossip.MessageListener,
 * тем самым позволяя передавать сообщения медду объектами hse.cs163.chuev.gossip.Gossip
 */
public class Gossip implements MessageListener, Comparable<Gossip> {

    /**
     * Содержит имя сплетницы
     */
    String _name;
    /**
     * Коллекция слушателей сплетницы
     */
    ArrayList<Gossip> _listeners;
    /**
     * Сообщения, которые рассказывает сплетница
     */
    ArrayList<String> _messages;

    /**
     * Создает объект "сплетница"
     * @param name имя сплетницы
     */
    public Gossip(String name) {
        _name = name;
        _listeners = new ArrayList<Gossip>();
        _messages = new ArrayList<String>();
    }

    /**
     * Возвращает имя сплетницы
     * @return имя сплетницы
     */
    public String name() {
        return _name;
    }

    /**
     * Возвращает коллекцию слушателей
     * @return коллекция слушателей
     */
    public ArrayList<Gossip> listeners() {
        return _listeners;
    }

    /**
     * Добавляет слушателя
     * @param listener слушатель
     */
    public void addListener(Gossip listener) {
        boolean isAdded = false;
        for(Gossip l : _listeners)
            if(l.equals(listener))
                isAdded = true;
        if(!isAdded)
            _listeners.add(listener);
    }

    /**
     * Убирает слушателя
     * @param listener слушатель
     */
    public void removeListener(Gossip listener) {
        _listeners.remove(listener);
    }


    @Override
    public String toString() {
        int listenersCount = _listeners.size();
        String sListeners = "";
        if(listenersCount != 0) {
            for(int i = 0; i < _listeners.size(); i++)
                sListeners += "\'" + _listeners.get(i).name() + "\'; ";
            sListeners = "I tell gossips to: " + sListeners;
        } else {
            sListeners = "I don't tell stories";
        }

        return String.format("I am \'%s\'. %s", _name, sListeners);
    }

    /**
     * Проверяет говорила ли сплетница это сообщение
     * @param message сообщение
     * @return true, если сплетница говорияла сообщение; false, если нет
     */
    boolean isTold(String message) {
        for(int i = 0; i < _messages.size(); i++)
            if(_messages.get(i) == message)
                return true;
        return false;
    }

    @Override
    public void getMessage(String message, final int number) {
        if(isTold(message))
            return;
        _messages.add(message);
        System.out.printf("I am \'%s\'. My message number %d: %s%n", _name, number, message);

        // когда я рассказываю историю я рассказываю ее сразу нескольким людям, а не каждому по очереди
        for(MessageListener listener : _listeners) {
            Thread thread = new Thread(()-> listener.getMessage(message, number + 1));
            thread.start();
        }

        // Через секунду забываю сообщение и позже могу услышать эту исторю как новую
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            //do nothing
        } finally {
            _messages.remove(message);
        }
    }

    @Override
    public int compareTo(@NotNull Gossip o) {
        return _name.compareTo(o.name());
    }
}
