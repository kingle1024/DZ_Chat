package message.chat;

//import message.ftp.ClientToServer;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import static message.ftp.FtpClient.messageSend;

public class FileMessage {
    public boolean run(HashMap<String, Object> map) throws IOException {
        String chat = (String) map.get("chat");

        ThreadGroup threadGroup = (ThreadGroup) map.get("threadGroup");
        String chatRoomName = (String) map.get("chatRoomName");

        // FTP Client
        Socket socket = new Socket("localhost", 55_552);
        StringBuilder input = new StringBuilder();
        input.append(chat)
                .append(" ")
                .append("room/")
                .append(chatRoomName);

        messageSend(input.toString(), socket);

        HashMap<String, Object> threadMap = new HashMap<>();
        String[] message = input.toString().split(" ");
        System.out.println("FileMessage > run");
        String fileName = message[1];

        threadMap.put("fileName", input.toString());
        threadMap.put("socket", socket);

//        ClientToServer clientToServer =
//                new ClientToServer(threadGroup, fileName, threadMap);
//        clientToServer.start();

        // #fileStop aa
        if(chat.startsWith("#fileStop")) {
            threadGroup.interrupt();
            return false;
        }
        return true;
    }
}
