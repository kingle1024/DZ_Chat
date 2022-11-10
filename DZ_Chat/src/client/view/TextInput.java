package client.view;

import static client.Main.getScanner;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TextInput {
	private List<String> keys;
	private Iterator<String> iter;

	public TextInput(String... keys) {
		this.keys = Arrays.asList(keys);
		this.iter = this.keys.iterator();
	}
	
	public void init() {
		this.iter = this.keys.iterator();
	}
	
	public String next() {
		System.out.println(iter.next() + ":");
		return getScanner().nextLine();
	}
}
