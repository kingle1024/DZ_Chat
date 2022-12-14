package message.ftp;

import org.json.JSONObject;

import dto.chat.ChatInfo;
import property.ServerProperties;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class FileMessage {
    private ChatInfo chatInfo;
    private Socket socket;
    public boolean client(JSONObject json) throws IOException {
        ThreadGroup threadGroup = (ThreadGroup) json.get("threadGroup");
        chatInfo = (ChatInfo) json.get("chatInfo");
        String command = chatInfo.getCommand().toLowerCase();
        if(command.startsWith("#filestop")) {
            threadGroup.interrupt();
            return false;
        }

        createSocket();
        sendMessageFtpServer();

        JSONObject threadMap = setJSON(command);
        System.out.println("fileMessage:"+chatInfo.getFilePath());

        if(command.startsWith("#filesend")) fileSend(threadGroup, threadMap);
        else if (command.startsWith("#filezip")) fileZip(threadGroup, threadMap);
        else fileSave(threadGroup, threadMap);

        return true;
    }

    private void fileSave(ThreadGroup threadGroup, JSONObject threadMap) {
        threadMap.put("chatInfo", chatInfo);
        new FileSaveThread(threadGroup, threadMap).start();
    }

    private void fileZip(ThreadGroup threadGroup, JSONObject threadMap) {
        chatInfo.setFilePath("temp.zip");
        fileSave(threadGroup, threadMap);
    }

    private static void fileSend(ThreadGroup threadGroup, JSONObject threadMap) {
        new FileSendThread(threadGroup, threadMap).start();
    }

    private JSONObject setJSON(String command) {
        JSONObject threadMap = new JSONObject();
        threadMap.put("chat", command);
        threadMap.put("threadName", chatInfo.getRoomName());
        threadMap.put("socket", socket);
        threadMap.put("chatInfo", chatInfo);

        return threadMap;
    }

    public void createSocket(){
        try {
            socket = new Socket(
                    ServerProperties.getIP(),
                    ServerProperties.getFTPPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessageFtpServer() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("chatInfo", chatInfo.toString());

        String json = jsonObject.toString();
        DataOutputStream dos = new DataOutputStream((socket.getOutputStream()));

        byte[] sendData =  json.getBytes(StandardCharsets.UTF_8);
        dos.writeInt(sendData.length);

        int remainder = sendData.length;
        int sendBlock = remainder > 4069 ? 4069 : remainder;
        int pos = 0;

        while(remainder > 0){
            dos.write(sendData, pos, sendBlock);
            remainder -= sendBlock;
            pos += sendBlock;
            if(remainder < sendBlock){
                sendBlock = remainder;
            }
//            System.out.println("length = " + sendData.length + "      pos = " + pos + "   sendBlock = " + sendBlock);
        }

        dos.flush();
    }
}
