package server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import member.Member;
import message.chat.ChatRoom;
import server.service.Service;
import server.service.ServiceMap;
import server.service.serviceimpl.MapperService;

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
      threadPool.execute(() -> {
         try {
            while (true) {
               Socket socket = serverSocket.accept();
               System.out.println("New Socket Accept");
               ServiceMap.getService("MapperService", socket).request();
            }
         } catch (IOException e) {
            e.printStackTrace();
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