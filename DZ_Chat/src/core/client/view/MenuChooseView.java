package core.client.view;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import static core.client.Main.getScanner;;

public class MenuChooseView extends View {
	private List<String> keys;
	private int values;
	
	public MenuChooseView(String name, String...keys) {
		super(name);
		this.keys = Arrays.asList(keys);
	}

	public void inputNumber() {
		IntStream.range(1, keys.size()+1).forEach(idx -> {
			System.out.println("[" + idx + "]" + keys.get(idx-1));
		});
		values = Integer.parseInt(getScanner().nextLine());
	}
	
	@Override
	public View nextView() {
		inputNumber();
		return ViewMap.getView(keys.get(values-1));
	}
}
