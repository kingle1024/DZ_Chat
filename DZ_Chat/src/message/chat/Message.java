package message.chat;

import log.NeedLog;

public interface Message extends NeedLog {
	public abstract void push();
}
