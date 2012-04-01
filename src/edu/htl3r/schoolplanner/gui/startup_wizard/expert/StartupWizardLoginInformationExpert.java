package edu.htl3r.schoolplanner.gui.startup_wizard.expert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import edu.htl3r.schoolplanner.R;
import edu.htl3r.schoolplanner.backend.preferences.loginSets.LoginSetConstants;
import edu.htl3r.schoolplanner.gui.LoginInformationForm;
import edu.htl3r.schoolplanner.gui.startup_wizard.StartupWizardLoginInformationCheck;

/**
 * Startup-Assistent Seite 2, welche den Benutzer auffordert, Login-Informationen anzugeben.
 */
public class StartupWizardLoginInformationExpert extends LoginInformationForm {
	
	private Activity thisActivity;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setOnButtonClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(requiredDataEntered()) {
					Intent nextScreen = new Intent(thisActivity, StartupWizardLoginInformationCheck.class);
					putEnteredLoginInformation(nextScreen);
					
					startActivity(nextScreen);
				}
				else {
					showToastMessage(getString(R.string.error_login_set_information_missing));
				}
			}
			
		});
	}
	
	/**
	 * Speichert die eingegebenen Login-Daten im uebergebenen Intent.<br />
	 * Verwendet die Keys aus der Klasse {@link LoginSetConstants}.
	 * @param intent {@link Intent}, in das die Daten gespeichert werden sollen
	 * @see LoginSetConstants
	 */
	private void putEnteredLoginInformation(Intent intent) {
		intent.putExtra(LoginSetConstants.nameKey, getNameInput());
		intent.putExtra(LoginSetConstants.serverUrlKey, getServerUrlInput());
		intent.putExtra(LoginSetConstants.schoolKey, getSchoolInput());
		intent.putExtra(LoginSetConstants.usernameKey, getUsernameInput());
		intent.putExtra(LoginSetConstants.passwordKey, getPasswordInput());
		intent.putExtra(LoginSetConstants.sslOnlyKey, isSslOnly());
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		thisActivity = this;
	}
}