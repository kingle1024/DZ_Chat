package dto.chat;

import dto.Transfer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatInfo {
    String command;
    String filePath;
    String roomName;

    public ChatInfo(String command, String filePath, String roomName) {
        this.command = command;
        this.filePath = filePath;
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return Transfer.toJSON(this).toString();
    }
}
