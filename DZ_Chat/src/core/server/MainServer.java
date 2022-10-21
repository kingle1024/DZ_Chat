package core.server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import core.service.MapperService;
import member.Member;
import message.chat.ChatRoom;

public class MainServer extends Server {
   public MainServer(int port) throws UnknownHostException {
      super(port);         
   }

   public static final Map<String, ChatRoom> chatRoomMap = Collections.synchronizedMap(new HashMap<>()); 
   public static final Map<String, Member> memberMap = Collections.synchronizedMap(new TreeMap<>());
   public static final ExecutorService threadPool = Executors.newFixedThreadPool(16);

   private ServerSocket serverSocket;
   
   @Override
   public void start() throws IOException {
      serverSocket = new ServerSocket(PORT_NUMBER);
      System.out.println("[Main Server] Start " + HOST + ":" + PORT_NUMBER);
//      System.out.println("안녕");
      threadPool.execute(() -> {
    	  Thread.currentThread().setDaemon(true);
         try {
            while (true) {
               Socket socket = serverSocket.accept();
               System.out.println("New Socket Accept");
               ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream()); 
               ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
               new MapperService(is, os).request();
            }
         } catch (IOException e) {
//            e.printStackTrace();
         }
      });
   }
   
   @Override
   public void stop() throws IOException {
      serverSocket.close();
      threadPool.shutdown();
      System.out.println("[서버] 종료");
   }
}