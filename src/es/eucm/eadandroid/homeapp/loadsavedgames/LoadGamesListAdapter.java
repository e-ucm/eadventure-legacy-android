/*******************************************************************************
 * <e-Adventure> Mobile for Android(TM) is a port of the <e-Adventure> research project to 	the Android(TM) platform.
 *        
 *          Copyright 2009-2012 <e-UCM> research group.
 *        
 *          <e-UCM> is a research group of the Department of Software Engineering
 *           and Artificial Intelligence at the Complutense University of Madrid
 *           (School of Computer Science).
 *        
 *           C Profesor Jose Garcia Santesmases sn,
 *           28040 Madrid (Madrid), Spain.
 *       
 *           For more info please visit:  <http://e-adventure.e-ucm.es/android> or
 *           <http://www.e-ucm.es>
 *        
 *        	 *Android is a trademark of Google Inc.
 *       	
 *        ****************************************************************************
 *     	 This file is part of <e-Adventure> Mobile, version 1.0.
 *     
 *    	 Main contributors - Roberto Tornero
 *     
 *     	 Former contributors - Alvaro Villoria 
 *     						    Juan Manuel de las Cuevas
 *     						    Guillermo Martin 	
 *    
 *     	 Directors - Baltasar Fernandez Manjon
 *     				Eugenio Marchiori
 *     
 *         	 You can access a list of all the contributors to <e-Adventure> Mobile at:
 *                	http://e-adventure.e-ucm.es/contributors
 *        
 *        ****************************************************************************
 *             <e-Adventure> Mobile is free software: you can redistribute it and/or modify
 *            it under the terms of the GNU Lesser General Public License as published by
 *            the Free Software Foundation, either version 3 of the License, or
 *            (at your option) any later version.
 *        
 *            <e-Adventure> Mobile is distributed in the hope that it will be useful,
 *            but WITHOUT ANY WARRANTY; without even the implied warranty of
 *            MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *            GNU Lesser General Public License for more details.
 *        
 *            See <http://www.gnu.org/licenses/>
 ******************************************************************************/
package es.eucm.eadandroid.homeapp.loadsavedgames;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.homeapp.loadsavedgames.LoadGamesArray.InfoLoadGames;

/**
 * An adapter for showing each saved game with its information in the list
 */
public class LoadGamesListAdapter extends BaseAdapter {

	/**
	 * The list of saved games
	 */
	private ArrayList<InfoLoadGames> infoSaved;
	/**
	 * The context of the application to get the ViewInflater
	 */
	private Context con;

	/**
	 * Constructor of the adapter
	 */
	public LoadGamesListAdapter(Context cont, ArrayList<InfoLoadGames> info) {

		super();
		this.con = cont;
		this.infoSaved = info;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return infoSaved.get(position);
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * Create the view by inflating the proper layout
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		InfoLoadGames savedGame = infoSaved.get(position);

		if (v == null) {
			LayoutInflater vi = (LayoutInflater) con
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.load_games_activity_listitem, null);
		}

		TextView gameText = (TextView) v.findViewById(R.id.toptext);
		TextView saveText = (TextView) v.findViewById(R.id.bottomtext);
		ImageView iconV = (ImageView) v.findViewById(R.id.icon);

		if (gameText != null) {
			gameText.setText(savedGame.getGame());
		}

		if (saveText != null) {
			saveText.setText(savedGame.getSaved());
		}

		if (iconV != null) {

			if (savedGame.getScreenShot() != null)

				iconV.setImageBitmap(savedGame.getScreenShot());
		}

		return v;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	public int getCount() {
		// TODO Auto-generated method stub
		return infoSaved.size();
	}



}
