package com.project.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Course implements EntryPoint {
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final AuthenticationAsync greetingService = GWT.create(Authentication.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Use RootPanel.get() to get the entire body element
		RootPanel rootPanel = RootPanel.get("baseContainer");
		
		
		
		final TextBox txtbxLogin = new TextBox();
		txtbxLogin.setText("Login");
		rootPanel.add(txtbxLogin, 147, 87);
		txtbxLogin.setSize("155px", "18px");
		
		final PasswordTextBox pswrdtxtbxPassword = new PasswordTextBox();
		rootPanel.add(pswrdtxtbxPassword, 147, 127);
		pswrdtxtbxPassword.setSize("155px", "18px");
		
		final Button btnLogin = new Button("Login");

		rootPanel.add(btnLogin, 192, 163);
		btnLogin.setSize("74px", "30px");		
		
		final Label label = new Label("");
		rootPanel.add(label, 147, 48);
	
		final class LoginClickHandler implements ClickHandler {
			
			@Override
			public void onClick(ClickEvent event) {
				greetingService.login(txtbxLogin.getText(), pswrdtxtbxPassword.getText().hashCode(), new AsyncCallback<Boolean>() {
					public void onSuccess(Boolean result) {
						label.setText(result.toString());													
					}
					public void onFailure(Throwable caught) {
						label.setText("Error!!!");
					}

					
				});			
			}			
			
			
		}
		
		btnLogin.addClickHandler(new LoginClickHandler());
		
	
	}
}
