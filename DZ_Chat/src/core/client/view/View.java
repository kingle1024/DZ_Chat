package core.client.view;

import java.util.ArrayList;

public abstract class View {
	private final String name;
	protected final ArrayList<View> next;
	
	public View(String name) {
		this.name = name;
		this.next = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public void addSubView(View view) {
		next.add(view);
		ViewController.getInstance().add(view);
	}
	
	public abstract View act();

	@Override
	public String toString() {
		return name;
	}
}
