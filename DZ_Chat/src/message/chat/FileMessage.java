package message.chat;

import message.ftp.FileSaveThread;
import message.ftp.FileSendThread;
import property.Property;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;

public class FileMessage {
    public boolean client(HashMap<String, Object> map) throws IOException {
        ThreadGroup threadGroup = (ThreadGroup) map.get("threadGroup");
        String chat = (String) map.get("chat");
        if(chat.startsWith("#fileStop")) {
            threadGroup.interrupt();
            return false;
        }

        // FTP Client
        Socket socket = getSocket();
        String chatAndRoomName = (String)map.get("chatAndRoomName");
        String chatRoomName = (String) map.get("chatRoomName");

        // FTP 서버에다가 메시지를 전달해줌
        sendMessageFtpServer(chatAndRoomName, socket);

        HashMap<String, Object> threadMap = new HashMap<>();
        String[] message = chatAndRoomName.split(" ");
        System.out.println("message:"+message[1]);

        threadMap.put("chat", chat);
        threadMap.put("threadName", message[1]);
        threadMap.put("socket", socket);
        threadMap.put("chatRoomName", chatRoomName);
        // common
        threadMap.put("command", map.get("command"));
        // common
        threadMap.put("fileAndPath", map.get("fileAndPath"));

        if(chat.startsWith("#fileSend")) {
            new FileSendThread(threadGroup, threadMap).start();
        }else if(chat.startsWith("#fileSave")){
            new FileSaveThread(threadGroup, threadMap).start();
        }

        return true;
    }
    public Socket getSocket(){
        try {
            return new Socket(
                    Property.server().get("IP"),
                    Integer.parseInt(Property.server().get("FTP_PORT")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendMessageFtpServer(String input, Socket socket) throws IOException {
        BufferedWriter bufferedWriter =
                new BufferedWriter(
                        new OutputStreamWriter(
                                new ObjectOutputStream(socket.getOutputStream())
                        )
                );
        bufferedWriter.write(input);
        bufferedWriter.newLine();
        bufferedWriter.flush();
//		System.out.println("메시지 전송 완료");
    }
}
