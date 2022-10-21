package core.client.view;

import java.util.*;

public class TextInputView extends View {
	private String[] keys;
	private List<String> values;
	private StringActor stringActor;
	public TextInputView(String name, StringActor stringActor, String... keys) {
		super(name);
		this.keys = keys;
		this.stringActor= stringActor;
		values = new ArrayList<>();
		ViewMap.getInstance().add(this);
	}
	
	@Override
	public View act() {
		Scanner scanner = new Scanner(System.in);
		Arrays.asList(keys).stream().forEach(key -> {
			System.out.print(key + ": ");
			values.add(scanner.nextLine());
		});
		View nextView = ViewMap.getInstance().getView(stringActor.act(values));
		values.clear();
		return nextView;
	}
}
