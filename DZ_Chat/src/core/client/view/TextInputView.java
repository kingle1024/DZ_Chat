package core.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import static core.client.Main.getScanner;

public abstract class TextInputView extends View {
	private List<String> keys;
	private List<String> values;
	public TextInputView(String name, String... keys) {
		super(name);
		values = new ArrayList<>();
		this.keys = Arrays.asList(keys);
	}
	
	public void inputText() {
		keys.stream().forEach(key -> {
			System.out.println(key + ": ");
			values.add(getScanner().nextLine());
		});
	}
	
	public Iterator<String> answerIterator() {
		values.clear();
		inputText();
		Iterator<String> iter = values.iterator();
		return iter;
	}
}
