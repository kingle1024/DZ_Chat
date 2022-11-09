package client.view;

import static client.Main.getScanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class TextInputView implements View {
	private List<String> keys;
	private List<String> values;
	public TextInputView(String... keys) {
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
