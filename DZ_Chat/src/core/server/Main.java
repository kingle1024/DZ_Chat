package core.server;

import java.io.IOException;
import java.util.Scanner;

import member.MemberDao;
import message.chat.ChatRoom;
import message.ftp.FtpServer;

public class Main {
   public static void main(String[] args) {
//	   private MemberDao memberDao = MemberDao.getInstance();
   	   System.out.println("main: change");
      // Mock ChatRoom
      for (int i = 0; i < 10; i++) {
         MainServer.chatRoomMap.put("TEST ROOM" + i, new ChatRoom("TEST ROOM" + i));         
      }

      Scanner scanner = new Scanner(System.in);
      try {
//    	  dao.readContent();
         Server server = new MainServer(50_001);
         server.start();
//         FtpServer.startServer();
         
         
         while (true) {
        	 String command = scanner.nextLine();
        	 System.out.println("INPUT: " + command);
        	 if ("q".equals(command)) break;
        	 if ("save".equals(command)) save();
         }
         
//         FtpServer.stopServer();
         server.stop();
         
      } catch (IOException e) {
          System.out.println("IOException");
      } catch (Exception e) {
         System.out.println("Exception");
      }
      scanner.close();
   }
   
   public static void save() {
	   // log
//	   dao.writeContent();
	   System.out.println("SAVE COMMAND");
   }
}