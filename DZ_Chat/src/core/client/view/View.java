package core.client.view;

import java.util.ArrayList;

import core.client.Client;

public abstract class View {
	private final String name;
	protected final ArrayList<View> next;
	protected Client client;
	
	public View(String name) {
		this.name = name;
		this.next = new ArrayList<>();
	}
	
	public void addSubView(View view) {
		next.add(view);
		ViewController.getInstance().add(view);
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract View act();

	@Override
	public String toString() {
		return name;
	}
}
