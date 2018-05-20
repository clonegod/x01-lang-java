package io.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class FileUpload {

    public static void main(String[] args) throws Exception {

        String serverIp = InetAddress.getLocalHost().getHostAddress();
        int serverPort = 10010;

        new FTPServer(serverPort).listen();

        new Client(serverIp, serverPort, 3000, new File("README.txt")).start();
    }

    /**
     * 服务端提供接收数据服务
     *
     * @author Administrator
     *
     */
    static class FTPServer implements Runnable {

        ServerSocket serverSocket;

        public FTPServer(int port) {
            try {
                this.serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void listen() {
            new Thread(this).start();
        }

        @Override
        public void run() {
            for (;;) {
                try {
                    Socket socket = serverSocket.accept(); // block until one client arrive

                    System.out.println(socket.getInetAddress().getHostAddress() + "," + socket.getPort());

                    saveFile(socket);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void saveFile(Socket socket) throws Exception {

            // save upload data to file
            File file = new File("fileupload.txt");
            file.createNewFile();
            PrintWriter fw = new PrintWriter(file);

            BufferedReader lnr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = null;
            while ((line = lnr.readLine()) != null) {
                System.out.println("line:" + line);
                fw.println(line);
            }
            fw.close();

            // response result to client
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("上传成功");

            // release resource
            System.out.println("server close socket: " + socket.hashCode());
            socket.close();
        }

    }

    static class Client implements Runnable {

        Socket socket;

        String serverIp;
        int serverPort;

        File targetFile;

        public Client(String serverIp, int serverPort, int timeout, File file) {
            this.serverIp = serverIp;
            this.serverPort = serverPort;

            this.targetFile = file;

            socket = new Socket();
            SocketAddress endpoint = new InetSocketAddress(serverIp, serverPort);
            try {
                socket.connect(endpoint, timeout);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void start() {
            new Thread(this).start();
        }

        @Override
        public void run() {
            upload();
            System.out.println("client exit");
        }

        public void close() {
            try {
                System.out.println("client close socket: " + socket.hashCode());
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void upload() {

            try {
                // send data to server
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

                BufferedReader bufr = new BufferedReader(
                        new InputStreamReader(new FileInputStream(targetFile)));
                String line = null;
                while ((line = bufr.readLine()) != null) {
                    pw.println(line);
                }

                bufr.close();

                socket.shutdownOutput(); // 发送流结束标记给服务器

                // try to read data from server
                InputStream in = socket.getInputStream();
                BufferedReader lnr = new BufferedReader(new InputStreamReader(in));

                String reply = lnr.readLine();
                System.out.println("server reply: " + reply);

                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
