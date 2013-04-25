package il.ac.huji.todolist;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class TodoPrefsActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}
