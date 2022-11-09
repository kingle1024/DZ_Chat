package client.view;

import java.io.File;
import java.util.*;

public class ViewMap {
	private static final Map<String, View> viewMap = new HashMap<>();
	
	static {
		// ViewClass Loading
		File viewPath = new File("./src/client/view/viewimpl");
		Arrays.asList(viewPath.list())
			.stream()
			.map(x -> x.replaceAll("\\..*", ""))
			.forEach(x -> {
				try {
					String key = x.replace("View", "");
					View view = (View) Class.forName("client.view.viewimpl." + x).getConstructor().newInstance();
					viewMap.put(key, view);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
	}
	
	private ViewMap() { }

	public static View getView(String viewName) {
		return viewMap.get(viewName);
	}
	
	public static void add(View view) {
		viewMap.put(view.getViewName(), view);
	}
}
