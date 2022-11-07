package core.client.view;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import static core.client.Main.getScanner;;

public class MenuChooseView implements View {
	private List<String> keys;
	private String name;
	private int values;
	
	public MenuChooseView(String name, String...keys) {
		this.keys = Arrays.asList(keys);
		this.name = name;
	}

	public void inputNumber() {
		IntStream.range(1, keys.size()+1).forEach(idx -> {
			System.out.println("[" + idx + "]" + ViewMap.getView(keys.get(idx-1)).getViewName());
		});
		values = Integer.parseInt(getScanner().nextLine());
	}
	
	@Override
	public View nextView() {
		inputNumber();
		return ViewMap.getView(keys.get(values-1));
	}
	
	@Override
	public String getViewName() {
		return this.name;
	}
}
