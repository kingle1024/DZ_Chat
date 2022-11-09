package client.view;

import static client.Main.getScanner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;;

public class MenuChoose {
	private List<String> keys;
	private int values;
	
	public MenuChoose(String...keys) {
		this.keys = Arrays.asList(keys);
	}

	public View choose() {
		IntStream.range(1, keys.size()+1).forEach(idx -> {
			System.out.println("[" + idx + "]" + ViewMap.getView(keys.get(idx-1)).getViewName());
		});
		values = Integer.parseInt(getScanner().nextLine());
		return ViewMap.getView(keys.get(values-1));
	}
}
