package core.client.view;

import java.util.Scanner;

import core.client.Client;

public class MenuChooseView extends View {
	private Client client;
	
	public MenuChooseView(String name) {
		super(name);
		ViewController.getInstance().add(this);
	}
	
	private void printMenu() {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= next.size(); i++) {
			sb.append("[")
			.append(i)
			.append("]")
			.append(next.get(i-1));
		}
		System.out.println(sb.toString());
	}
	
	@Override
	public View act() {
		printMenu();
		Scanner scanner = new Scanner(System.in);
		int choose = Integer.parseInt(scanner.nextLine());
		return next.get(choose-1);
	}
	
	public void run() {
		client.run();
	}
}
