package core.client;

import java.util.ArrayList;

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
			
		}
	}
}
