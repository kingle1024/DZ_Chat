package core.client.view;

import java.util.*;

public class ViewController {
	private static final Map<String, View> viewMap = new HashMap<>();
	private static ViewController viewController;
	
	private ViewController() { }

	public static ViewController getInstance() {
		if (viewController == null) return viewController = new ViewController();
		return viewController;
	}
	
	public View getView(String viewName) {
		return viewMap.get(viewName);
	}
	
	public void add(View view) {
		viewMap.put(view.getName(), view);
	}
}
