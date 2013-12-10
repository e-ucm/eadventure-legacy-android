package es.eucm.androidgames.standalonegame;

import android.content.Intent;
import es.eucm.eadandroid.ecore.EcoreVideo;

public class SCoreVideo extends EcoreVideo{
	public void changeActivity() {
		Intent i = new Intent(this, SCoreActivity.class);
		i.putExtra("before_video", true);
		this.startActivity(i);
		this.finish();
	}
}
