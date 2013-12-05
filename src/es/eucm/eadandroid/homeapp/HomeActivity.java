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
package es.eucm.eadandroid.homeapp;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.markupartist.android.widget.ActionBar;

import es.eucm.eadandroid.R;
import es.eucm.eadandroid.ecore.ECoreActivity;
import es.eucm.eadandroid.homeapp.preferences.PreferencesActivity;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.RepoResourceHandler;
import es.eucm.eadandroid.res.pathdirectory.Paths;

/**
 * The home menu of eAdventure Mobile. The several icons displayed allow navigating through 
 * the different screens, preferences included. A link to the eAdventure website is displayed
 * at the bottom. 
 * 
 * @author Roberto Tornero
 */
public class HomeActivity extends Activity {
	
	/**
     * The path of the data (game) included with the Intent
     */
    private String path_from = null;
    /**
     * Id of the installation dialog
     */
    static final int DIALOG_INSTALL_ID = 0;
	
	/**
	 * Creation of the grid of icons 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.home_grid);
	    
	    final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
	    actionBar.setHomeLogo(R.drawable.logo_home);

	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter());

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	
	        	Intent i = createIntent(HomeActivity.this, WorkspaceActivity.class);
	            
	        	switch(position){
	        		case 0: i.putExtra("Tab", 0);
	        				break;
	        		case 1: i.putExtra("Tab", 1);
    						break;
	        		case 2: i.putExtra("Tab", 2);
    						break;
	        		case 3: i = createIntent(HomeActivity.this, PreferencesActivity.class);
    						break;
	        	}
	        	
	        	startActivity(i);
	        }
	    });
	    
	    ImageView imview = (ImageView) findViewById(R.id.web_image);
	    imview.setImageResource(R.drawable.header);
	    
	    imview.setOnClickListener(new OnClickListener() {
	    	
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i= new Intent().setAction(Intent.ACTION_VIEW).setData(Uri.parse("http://e-adventure.e-ucm.es/android"));
				startActivity(i);
			}
	    });
	    
	    Intent i = this.getIntent();
		
		if (i.getData() != null){
			String data = this.getIntent().getData().getPath();
			installEadGame(data);
		} 
		
		checkRockPlayer();
	    
	    overridePendingTransition(R.anim.fade_in, R.anim.hold);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
    protected void onStart() {
    	
    	super.onStart();
   		overridePendingTransition(R.anim.fade_in, R.anim.hold);
    } 
	
	/**
	 * If the back key is pressed, ends the application
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	this.finish();
	        return true;
	    }
	    else return false;
	}
	
	/**
	 * Static method for creating intents to start other activities
	 */
	public static Intent createIntent(Context context, Class<?> c) {
        Intent i = new Intent(context, c);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return i;
    }
	
	/**
	 * Install games in the proper folder
	 */
	private void installEadGame(String path_from) {
		
		this.path_from = path_from;		
		this.showDialog(DIALOG_INSTALL_ID);
		
		Thread t = new Thread(new Runnable() {
			public void run()
			{					
				String path_from = getPathFrom();
				int last = path_from.lastIndexOf("/");
				String gameFileName = path_from.substring(last + 1);
				path_from= path_from.substring(0, last+1);
				RepoResourceHandler.unzip(path_from,Paths.eaddirectory.GAMES_PATH,gameFileName,false);
				dismissDialog(DIALOG_INSTALL_ID);
			}
		});
		
		t.start();					
	}
	
	/**
     * Returns the path of the data in the Intent
     */
	private String getPathFrom() {
		return path_from;
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case DIALOG_INSTALL_ID:
			ProgressDialog progressDialog = new ProgressDialog(this);
			progressDialog.setCancelable(false);		
			progressDialog.setTitle(getString(R.string.wait));
			progressDialog.setIcon(R.drawable.dialog_icon);
			progressDialog.setMessage(getString(R.string.installing));
			progressDialog.setIndeterminate(true);
			progressDialog.show();
			dialog = progressDialog;			
			break;
		default:
			dialog = null;
		}
		return dialog;
	}
	
	/**
	 * Checks if RockPlayer Lite app is installed on the device
	 */
	private void checkRockPlayer() {
		
		final boolean appAvailable = isInstalled("com.redirectin.rockplayer.android.unified.lite");
		
		if (!appAvailable)
			showRockPlayerDialog();
	}
	
	/**
	 * Shows an alert dialog to notify the absence of RockPlayer Lite app in system, and suggests the download
	 */
	private void showRockPlayerDialog() {
		new AlertDialog.Builder(this)
				.setTitle(getString(R.string.app_name))
				.setIcon(R.drawable.dialog_icon)
				.setMessage(getString(R.string.video_message))
				.setPositiveButton(getString(R.string.install_app),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								Intent i = new Intent(
									Intent.ACTION_VIEW,
									Uri.parse("market://search?q=pname:com.redirectin.rockplayer.android.unified.lite"));
														
								startActivity(i);						
							}
						}).setNeutralButton(getString(R.string.skip_app),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.dismiss();
							}
						}).show();

	}
	
	private boolean isInstalled(String packageName){
		
		boolean found = false;
		int i = 0;
		List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
		
		while (!found && i < packs.size()){
			PackageInfo p = packs.get(i);
			if (p.packageName.equals(packageName))
				found = true;
			else i++;
		}
		
		return found;
	}
	
	/**
	 * An adapter for displaying images with a text below them on the grid
	 * 
	 * @author Roberto Tornero
	 */
	public class ImageAdapter extends BaseAdapter {
		
		/**
		 * The resources for the icons
		 */
	    private Integer[] mThumbIds = {R.drawable.preferences_desktop_gaming, R.drawable.kde_folder_games,
	            R.drawable.connect_to_network, R.drawable.settings1};	    
	    /**
	     * The text to display under each icon
	     */
	    private String[] mThumbStrings = {getString(R.string.installed_games), getString(R.string.saved_games), 
	    		getString(R.string.games_repository), getString(R.string.preferences)};

		/**
		 * Constructor
		 */
	    public ImageAdapter() {
	        super();
	    }

	    /*
	     * (non-Javadoc)
	     * @see android.widget.Adapter#getCount()
	     */
	    public int getCount() {
	        return mThumbIds.length;
	    }

	    /*
	     * (non-Javadoc)
	     * @see android.widget.Adapter#getItem(int)
	     */
	    public Object getItem(int position) {
	        return null;
	    }

	    /*
	     * (non-Javadoc)
	     * @see android.widget.Adapter#getItemId(int)
	     */
	    public long getItemId(int position) {
	        return position;
	    }

	    /**
	     * A grid view with the icons and their text
	     */
	    public View getView(int position, View convertView, ViewGroup parent) {
	        
	        View myView = convertView;
	        
	        //if (convertView == null){
	            
	        	//Inflate the layout
	        	LayoutInflater li = getLayoutInflater();
	        	myView = li.inflate(R.layout.home_grid_item, null);
	            
	        	// Add The Image          
	        	ImageView iv = (ImageView)myView.findViewById(R.id.grid_item_image);
	        	iv.setImageResource(mThumbIds[position]);
	            
	        	// Add The Text
	        	TextView tv = (TextView)myView.findViewById(R.id.grid_item_text);
	        	tv.setText(mThumbStrings[position]);
	        //}
	         
	        return myView;

	    }
	    
	}
}
