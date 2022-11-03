package message.chat;

import message.ftp.ClientToServerThread;
import message.ftp.FtpClient;
import property.Property;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;

public class FileMessage {
    public boolean run(HashMap<String, Object> map) throws IOException {
        String chat = (String) map.get("chat");
        ThreadGroup threadGroup = (ThreadGroup) map.get("threadGroup");
        if(chat.startsWith("#fileStop")) {
            threadGroup.interrupt();
            return false;
        }

        String chatRoomName = (String) map.get("chatRoomName");

        // FTP Client
        Socket socket = getSocket();
        StringBuilder input = new StringBuilder();
        input.append(chat)
                .append(" ")
                .append("room/")
                .append(chatRoomName);

        // 서버에다가 메시지를 전달해줌
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

    public void messageSend(String input, Socket socket) throws IOException {
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
