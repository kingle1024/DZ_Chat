package core.client;

import java.util.*;

public class View {
	String str;
	ArrayList<View> menu;
	
	public View(String str) {
		this.str = str;
		this.menu = new ArrayList<>();
	}
	
	public void printMenu() {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= menu.size(); i++) {
			sb.append("[")
			.append(i)
			.append("]")
			.append(str);
		}
		System.out.println(sb.toString());
	}
	public static void main(String[]args) {
		View main = new View("main");
		main.menu.add(new View("login"));
		main.menu.add(new View("register"));
		main.menu.add(new View("findpw"));
		
		View ptr = main;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			ptr.printMenu();
			int num = scanner.nextInt();
			ptr = ptr.menu.get(num-1);
		}
	}
}
