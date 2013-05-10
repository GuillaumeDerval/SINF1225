package be.uclouvain.sinf1225.gourmet;

import android.view.View;

public class ReservationRowListener implements View.OnClickListener{
	private int resvID;
	
	public ReservationRowListener(int resvID){
		super();
		this.resvID = resvID;
	}

	@Override
	public void onClick(View v) {
			
		System.out.println(resvID);
	}

}
