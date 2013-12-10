package es.eucm.androidgames.damaboba;

import android.content.Intent;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.ecore.ECoreActivity;

public class SCoreActivity extends ECoreActivity{

	protected void startLoadApplication() {
		overridePendingTransition(R.anim.fade_in, R.anim.hold);
		this.finish();
	}
	
	
	protected void finishapplication() {
		
		overridePendingTransition(R.anim.fade_in, R.anim.hold);
		this.finish();
	}
	
	protected void activityvideo() {
		
		Intent i = new Intent(this, SCoreVideo.class);
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.hold);	
	}	
}
