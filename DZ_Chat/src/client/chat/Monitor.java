package client.chat;

public class Monitor {
	private String status;
	
	public Monitor(String status) {
		this.status = status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public boolean equalsStatus(String status) {
		return this.status.equals(status);
	}
}
