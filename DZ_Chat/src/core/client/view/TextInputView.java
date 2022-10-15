package core.client.view;

import java.util.*;

public class TextInputView extends View {
	private String[] keys;
	private List<String> values;
	private ViewMapper viewMapper;
	public TextInputView(String name, ViewMapper viewMapper, String... keys) {
		super(name);
		this.keys = keys;
		this.viewMapper = viewMapper;
		values = new ArrayList<>();
		ViewController.getInstance().add(this);
	}
	
	@Override
	public View act() {
		Scanner scanner = new Scanner(System.in);
		Arrays.asList(keys).stream().forEach(key -> {
			System.out.print(key + ": ");
			values.add(scanner.nextLine());
		});
		return ViewController.getInstance().getView(viewMapper.act(values));
	}
}
