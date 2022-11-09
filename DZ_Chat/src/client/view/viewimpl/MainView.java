package client.view.viewimpl;

import client.view.MenuChoose;
import client.view.View;

public class MainView implements View {
	private MenuChoose menuChoose;
	
	public MainView() {
		this.menuChoose = new MenuChoose("Login", "Register", "FindPW");
	}

	@Override
	public View nextView() {
		return menuChoose.choose();
	}

	@Override
	public String getViewName() {
		return "메인 화면";
	}
}
