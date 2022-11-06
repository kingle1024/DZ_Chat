package core.client.view;

public abstract class View {
	private final String name;
	
	public View(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract View nextView();

	@Override
	public String toString() {
		return name;
	}
}
