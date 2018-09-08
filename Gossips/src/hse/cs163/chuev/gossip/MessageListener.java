package hse.cs163.chuev.gossip;

/**
 * Объект способный слушать сообщения
 */
public interface MessageListener {

    /**
     * Метод прослушки сообщения
     * @param message - сообщение
     * @param number - номер сообщения
     */
    void getMessage(String message, final int number);

}
