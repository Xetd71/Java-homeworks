package simpleTorrent.server;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private volatile ExecutorService pool;
    private volatile ServerSocket server;
    private Map<String, File> files;
    private static final int BUFFER = 1024;

    public Server(int port, int maxClients, final File folder) throws IOException {
        pool = Executors.newFixedThreadPool(maxClients);
        server = new ServerSocket(port);
        files = new HashMap<>();
        Arrays.stream(folder.listFiles()).filter(file->!file.isDirectory()).forEach(file->files.put(file.getName(), file));
    }

    public void start() {
        try {
            while(!Thread.currentThread().isInterrupted()) {
                Socket connection = server.accept();
                Runnable task = new ClientThread(connection, files);
                pool.execute(task);
            }
        } catch(IOException e) {
            System.out.println("Server stopped");
        }

    }

    public void stop() throws IOException {
        pool.shutdownNow();
        server.close();
    }

    private static class ClientThread implements Runnable {
        private Socket connection;
        private Map<String, File> files;

        ClientThread(Socket connection, Map<String, File> files) {
            this.connection = connection;
            this.files = files;
        }

        private String readLine(DataInputStream in) throws IOException {
            StringBuilder s = new StringBuilder();
            Character c1, c2;
            c2 = in.readChar();
            while(true) {
                c1 = c2;
                c2 = in.readChar();
                if(c1.equals('\r') && c2.equals('\n'))
                    break;
                s.append(c1);
            }
            return s.toString();
        }

        @Override
        public void run() {
            DataInputStream in = null;
            DataOutputStream out = null;
            try {
                in = new DataInputStream(connection.getInputStream());
                out = new DataOutputStream(connection.getOutputStream());
                for(String file : files.keySet())
                    out.writeChars(file + "\r\n");
                out.writeChars("\r\n");
                out.flush();

                String line;
                File file;
                while(!Thread.currentThread().isInterrupted()) {
                    if((line = readLine(in)).isEmpty())
                        break;

                    file = files.get(line);
                    out.writeLong(file.length());
                    out.flush();

                    if(!readLine(in).equals("YES"))
                        continue;

                    int c;
                    byte arr[] = new byte[BUFFER];
                    try(BufferedInputStream br = new BufferedInputStream(new FileInputStream(file))) {
                        while((c = br.read(arr)) > 0) {
                            out.write(arr, 0, c);
                            out.flush();
                        }
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Client disconnected");
                try {
                    if(in != null)
                        in.close();
                    if(out != null)
                        out.close();
                    connection.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
