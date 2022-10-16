package core.client.view;

import java.util.*;

public class ViewMap {
	private static final Map<String, View> viewMap = new HashMap<>();
	private static ViewMap viewController;
	
	private ViewMap() { }

	public static ViewMap getInstance() {
		if (viewController == null) return viewController = new ViewMap();
		return viewController;
	}
	
	public View getView(String viewName) {
		return viewMap.get(viewName);
	}
	
	public void add(View view) {
		viewMap.put(view.getName(), view);
	}
}
