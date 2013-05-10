package be.uclouvain.sinf1225.gourmet;

import android.content.Intent;
import android.view.View;

public class ReservationRowListener implements View.OnClickListener{
	private Intent intent;
	
	public ReservationRowListener(Intent intent){
		super();
		this.intent = intent;
	}

	@Override
	public void onClick(View v) {
		ReservationManagerView newRsvMgrView = new ReservationManagerView();
		newRsvMgrView.startActivityForResult(this.intent, 1);
	}

}
