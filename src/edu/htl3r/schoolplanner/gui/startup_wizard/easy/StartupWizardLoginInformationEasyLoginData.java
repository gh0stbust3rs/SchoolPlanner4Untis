package edu.htl3r.schoolplanner.gui.startup_wizard.easy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import edu.htl3r.schoolplanner.R;
import edu.htl3r.schoolplanner.backend.preferences.loginSets.LoginSetConstants;
import edu.htl3r.schoolplanner.gui.SchoolPlannerActivity;

public class StartupWizardLoginInformationEasyLoginData extends
		SchoolPlannerActivity {

	private Button back, next;
	private Activity thisActivity;
	private EditText school, user, pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.startup_wizard_login_information_easy_login_data);

		school = (EditText) findViewById(R.id.startup_wizard_login_information_school);
		user = (EditText) findViewById(R.id.startup_wizard_login_information_username);
		pass = (EditText) findViewById(R.id.startup_wizard_login_information_password);

		thisActivity = this;
		initButtons();

		super.onCreate(savedInstanceState);
	}

	private boolean everythigHere() {
		if (school.getText().length() > 1 && user.getText().length() > 1 && pass.getText().length() > 1)
			return true;
		return false;
	}

	private void initButtons() {
		back = (Button) findViewById(R.id.startup_wizard_introduction_back_button);
		next = (Button) findViewById(R.id.startup_wizard_introduction_next_button);

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onBackPressed();
			}
		});

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (everythigHere()) {
					Intent intent = new Intent(thisActivity,StartupWizardLoginInformationEasyLoginData.class);
					intent.putExtra(LoginSetConstants.serverUrlKey,thisActivity.getIntent().getExtras().getString(LoginSetConstants.serverUrlKey));
					intent.putExtra(LoginSetConstants.schoolKey,school.getText());
					intent.putExtra(LoginSetConstants.usernameKey,user.getText());
					intent.putExtra(LoginSetConstants.passwordKey,pass.getText());
					startActivity(intent);
				}
			}
		});
	}
}