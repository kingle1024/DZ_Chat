package message.chat;

import message.ftp.ClientToServerThread;
import message.ftp.FtpClient;
import property.Property;

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
        Socket socket = new Socket(
                Property.server().get("IP"),
                Integer.parseInt(Property.server().get("FTP_PORT")));
        StringBuilder input = new StringBuilder();
        input.append(chat)
                .append(" ")
                .append("room/")
                .append(chatRoomName);

        messageSend(input.toString(), socket);

        HashMap<String, Object> threadMap = new HashMap<>();
        String[] message = input.toString().split(" ");
        String fileName = message[1];

        threadMap.put("fileName", input.toString());
        threadMap.put("socket", socket);
        threadMap.put("chatRoomName", chatRoomName);

        if(chat.startsWith("#fileSend")) {
            ClientToServerThread clientToServerThread = new ClientToServerThread(threadGroup, fileName, threadMap);
            clientToServerThread.start();
        }else if(chat.startsWith("#fileSave")){
            threadMap.put("chat", chat);
            FtpClient ftpClient = new FtpClient(threadGroup, fileName, threadMap);
            ftpClient.start();
        }else if(chat.startsWith("#fileStop")) {
            threadGroup.interrupt();
            return false;
        }
        return true;
    }
}
