package simpleTorrent.client;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    private Socket s;
    private DataInputStream in;
    private DataOutputStream out;
    private List<String> files = null;
    private static final int BUFFER = 1024;

    public Client(String host, int port) throws IOException {
        s = new Socket();
        s.connect(new InetSocketAddress(host, port));
        in = new DataInputStream(s.getInputStream());
        out = new DataOutputStream(s.getOutputStream());
    }

    public void closeConnection() throws IOException {
        out.writeChars("\r\n");
        out.flush();
        in.close();
        out.close();
        s.close();
    }

    private String readLine() throws IOException {
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

    public List<String> loadFiles() throws IOException, NumberFormatException {
        if(files != null)
            return files;

        files = new ArrayList<>();
        String line;
        while(!(line = readLine()).isEmpty()) {
            files.add(line);
        }
        return files;
    }

    public void downloadFile(String filePath, String directoryToSave) throws IOException {
        out.writeChars(filePath + "\r\n");
        out.flush();
        File f = new File(directoryToSave, filePath);
        try(DataOutputStream bw = new DataOutputStream(new FileOutputStream(f))) {
            byte buffer[] = new byte[BUFFER];
            long i = in.readLong();
            for(; i > BUFFER; i -= BUFFER) {
                if(in.read(buffer, 0, BUFFER) < 0)
                    throw new IOException();
                bw.write(buffer, 0, BUFFER);
            }
            if(i != 0) {
                if(in.read(buffer, 0, (int)i) < 0)
                    throw new IOException();
                bw.write(buffer, 0, (int)i);
            }
        }
    }

    public long Ask(String filePath) throws IOException {
        out.writeChars(filePath + "\r\n");
        out.flush();
        return in.readLong();
    }

    public void downloadFile(String filePath, String directoryToSave, DoubleProperty progress, long n) throws IOException {
        if(n < 0) {
            out.writeChars("NO\r\n");
            return;
        }

        out.writeChars("YES\r\n");
        File f = new File(directoryToSave, filePath);
        try(BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(f))) {
            byte buffer[] = new byte[BUFFER];
            long i = n;
            int c;
            progress.setValue(0);
            while(i > 0 && (c = in.read(buffer, 0, (int)Math.min(BUFFER, i))) > 0) {
                bw.write(buffer, 0, c);
                i -= c;
                progress.setValue(1 - (double)i / n);
            }
            progress.setValue(1);
        }
    }

}