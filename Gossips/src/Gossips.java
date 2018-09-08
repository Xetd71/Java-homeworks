import hse.cs163.chuev.gossip.Gossip;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Метод иллюстрирующий работу класса hse.cs163.chuev.gossip.Gossip
 */
public class Gossips {

    /**
     * Создает объект Gossips с именем
     * @param name имя
     * @return созданный объект
     */
    public static Gossip create(@NotNull String name) {
        return new Gossip(name);
    }

    /**
     * Подписывает слушателя на сообщения оратора
     * @param talker - оратор
     * @param listener - слушатель
     */
    public static void link(Gossip talker, Gossip listener) {
        talker.addListener(listener);
    }

    /**
     * Отписывает слушателя от сообщений оратора
     * @param talker - оратор
     * @param listener - слушатель
     */
    public static void unlink(Gossip talker, Gossip listener) {
        talker.removeListener(listener);
    }

    /**
     * Посылает сообщение слушателю
     * @param message - сообщение
     * @param listener - слушетель
     */
    public static void message(String message, Gossip listener) {
        listener.getMessage(message, 1);
    }

    /**
     * Выводит список слушателей в констоль
     * @param gossips - коллекция слушателей
     */
    public static void gossips(ArrayList<Gossip> gossips) {
        gossips.sort((p1, p2) -> p1.compareTo(p2));
        for(Gossip g : gossips)
            System.out.println(g.toString());
    }

    /**
     * Выводит слушаетелей сплетницы
     * @param gossip - сплетница
     */
    public static void listeners(Gossip gossip) {
        ArrayList<Gossip> listeners = gossip.listeners();
        listeners.sort((p1, p2) -> p1.compareTo(p2));
        for(Gossip g : listeners)
            System.out.println(g.toString());
    }

    private static void printDescription() {
        System.out.println("Commands:");
        System.out.println("\r(create)( *- *)(<name>) - create");
        System.out.println("\r(link)( *- *)(<name1>)( *- *)(<name2>)");
        System.out.println("\r(unlink)( *- *)(<name1>)( *- *)(<name2>)");
        System.out.println("\r(message)( *- *)(<message>)( *- *)(<name>)");
        System.out.println("\r(gossips)");
        System.out.println("\r(listeners)( *- *)(<name>)");
        System.out.println("\r(quit)\r\n");
    }

    public static void main(String[] args) {
        printDescription();
        HashMap<String, Gossip> gossipsMap = new HashMap<String, Gossip>();
        Scanner in = new Scanner(System.in);
        boolean isWorking = true;
        while(isWorking) {
            String[] cmd = in.nextLine().split("( *- *)");
            switch(cmd[0]) {
                case "create":
                    if(cmd.length != 2)
                        System.out.println("Wrong command");
                    else {
                        if(gossipsMap.getOrDefault(cmd[1], null) == null) {
                            gossipsMap.put(cmd[1], create(cmd[1]));
                            System.out.printf("\tgossip \'%s\' created%n", cmd[1]);
                        } else {
                            System.out.printf("\tgossip \'%s\' already exist%n", cmd[1]);
                        }
                    }
                    break;
                case "link":
                case "unlink":
                    if(cmd.length != 3)
                        System.out.println("Wrong command");
                    else {
                        Gossip talker = gossipsMap.getOrDefault(cmd[1], null);
                        Gossip listener = gossipsMap.getOrDefault(cmd[2], null);
                        if(talker == null || listener == null) {
                            System.out.println("Can't recognize gossip");
                        }
                        if(cmd[0].equals("link")) {
                            link(talker, listener);
                            System.out.printf("\tlink \'%s\'->\'%s\' created%n", cmd[1], cmd[2]);
                        }
                        else {
                            unlink(talker, listener);
                            System.out.printf("\tlink \'%s\'->\'%s\' deleted%n", cmd[1], cmd[2]);
                        }
                    }
                    break;
                case "message":
                    if(cmd.length != 3)
                        System.out.println("Wrong command");
                    else {
                        Gossip listener = gossipsMap.getOrDefault(cmd[2], null);
                        if(listener == null) {
                            System.out.println("Can't recognize gossip");
                        }
                        message(cmd[1], listener);
                    }
                    break;
                case "gossips":
                    gossips(new ArrayList<Gossip>(gossipsMap.values()));
                    break;
                case "listeners":
                    if(cmd.length != 2)
                        System.out.println("Wrong command");
                    else {
                        Gossip gossip = gossipsMap.getOrDefault(cmd[1], null);
                        if(gossip == null) {
                            System.out.println("Can't recognize gossip");
                        }
                        listeners(gossip);
                    }
                    break;
                case "quit":
                    isWorking = false;
                    break;
                    default:
                        System.out.println("Wrong command");
                        break;
            }
        }
    }
}
