package formatfa.craw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
    private AndCrawServer server;
    RequestHandler handler;

    public ServerThread(AndCrawServer server, RequestHandler handler) {
        this.server = server;
        this.handler = handler;
    }

    public ServerThread(AndCrawServer server) {
        this.server = server;
    }

    private boolean running=true;
    @Override
    public void run() {
        try {
            ServerSocket socket = new ServerSocket(server.getPort());
            String host=socket.getInetAddress().toString();
            System.out.println("start server at "+host+":"+socket.getLocalPort()+" 等待客户端连接..");
            while(running)
            {
                Socket client = socket.accept();
                ClientThread thread = new ClientThread(client,handler);
                thread.start();

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
