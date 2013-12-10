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
package es.eucm.eadandroid.assessment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import es.eucm.eadandroid.common.auxiliar.ReleaseFolders;
import es.eucm.eadandroid.common.data.assessment.AssessmentProfile;
import es.eucm.eadandroid.common.data.assessment.AssessmentRule;
import es.eucm.eadandroid.common.data.assessment.TimedAssessmentRule;
import es.eucm.eadandroid.common.loader.Loader;
import es.eucm.eadandroid.common.loader.incidences.Incidence;
import es.eucm.eadandroid.ecore.GameThread;
import es.eucm.eadandroid.ecore.ECoreActivity.ActivityHandlerMessages;
import es.eucm.eadandroid.ecore.control.FlagSummary;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.TimerEventListener;
import es.eucm.eadandroid.ecore.control.TimerManager;
import es.eucm.eadandroid.ecore.control.VarSummary;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalConditions;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

/**
 * This engine stores the rules to be processed when the flags change in the
 * game, creating events that tell the process of the player in the game
 */
public class AssessmentEngine implements TimerEventListener {

	public static int STATE_STARTED = 1;

	public static int STATE_NONE = 0;

	public static int STATE_DONE = 2;

	/**
	 * Constants for the colors of the HTML reports
	 */
	private static String HTML_REPORT_COLOR_0 = "794910"; // Old value =#4386CE

	private static String HTML_REPORT_COLOR_1 = "F7D769"; // Old value =#C1D6EA

	private static String HTML_REPORT_COLOR_2 = "FFFFFF"; // Old value =#EEF6FE

	/**
	 * Current assessment profile
	 */
	private AssessmentProfile assessmentProfile;

	/**
	 * List of rules to be checked
	 */
	private List<AssessmentRule> assessmentRules;

	/**
	 * This list sto 
	 */
	private List<AssessmentRule> repeatedRules;

	/**
	 * List of executed rules
	 */
	private List<ProcessedRule> processedRules;

	/**
	 * Structure of timed rules
	 */
	private HashMap<Integer, TimedAssessmentRule> timedRules;

	private String playerName;

	private int state;

	private String lastHTMLReport;

	/**
	 * Constructor
	 */
	public AssessmentEngine() {
		processedRules = new ArrayList<ProcessedRule>();
		repeatedRules = new ArrayList<AssessmentRule>();
		timedRules = new HashMap<Integer, TimedAssessmentRule>();
		state = STATE_NONE;
	}

	/**
	 * Loads a set of assessment rules
	 * 
	 * @param assessmentPath
	 *            Path of the file containing the assessment data
	 */
	public void loadAssessmentRules(AssessmentProfile profile) {

		if (profile!=null){
			assessmentProfile = profile;
			assessmentRules = new ArrayList<AssessmentRule>(assessmentProfile.getRules());

			FlagSummary flags = Game.getInstance().getFlags();
			VarSummary vars = Game.getInstance().getVars();
			for (String flag : assessmentProfile.getFlags()) {
				flags.addFlag(flag);
			}
			for (String var : assessmentProfile.getVars()) {
				vars.addVar(var);
			}


			// Iterate through the rules: those timed add them to the timer manager
			for (AssessmentRule assessmentRule : assessmentRules) {
				if (assessmentRule instanceof TimedAssessmentRule) {
					TimedAssessmentRule tRule = (TimedAssessmentRule) assessmentRule;
					int id = TimerManager.getInstance().addTimer(tRule.getInitConditions(), tRule.getEndConditions(), tRule.isUsesEndConditions(), this);
					timedRules.put(new Integer(id), tRule);
				}
			}
			processRules();
		}
	}

	public static AssessmentProfile loadAssessmentProfile(String assessmentPath) {
		if (assessmentPath != null && !assessmentPath.equals("")) {
			AssessmentProfile assessmentProfile = Loader.loadAssessmentProfile(
					ResourceHandler.getInstance(), assessmentPath,
					new ArrayList<Incidence>());
			return assessmentProfile;
		}
		return null;
	}

	/**
	 * Process the rules, triggering them if necessary
	 */
	public void processRules() {
		int i = 0;
		try {
			if (assessmentRules!=null){
				// check if repeated rules have to be executed again 
				for (AssessmentRule repeatRule: repeatedRules){
					if (isActive(repeatRule)){
						triggerRule(repeatRule);
					}
				}

				// For every rule
				while (i < assessmentRules.size()) {

					// If it was activated, execute the rule
					if (isActive(assessmentRules.get(i))) {
						AssessmentRule oldRule = (AssessmentRule) (assessmentRules.remove(i).clone( ));

						// first time that the repeatRule is executed
						if (oldRule.isRepeatRule( ))
							repeatedRules.add( oldRule );

						triggerRule(oldRule);
					}

					// Else, check the next rule
					else
						i++;
				}


			}
		} catch( CloneNotSupportedException e ) {
			
		}
	}

private void triggerRule(AssessmentRule oldRule){
	    
        oldRule.setConcept( Game.getInstance( ).processText( oldRule.getConcept( )));
        oldRule.setText( Game.getInstance( ).processText( oldRule.getText( )));
        ProcessedRule rule = new ProcessedRule(oldRule, Game
            .getInstance().getTime());

        processedRules.add(rule);
	}

	private static boolean isActive(AssessmentRule rule) {
		return new FunctionalConditions(rule.getConditions()).allConditionsOk();
	}

	/**
	 * Returns the timed rule indexed by key "i".
	 * 
	 * @param i
	 *            the key in the hash map
	 * @return the correct TimedAssessmentRule of timedRules
	 */
	public TimedAssessmentRule getTimedAssessmentRule(int i) {
		return timedRules.get(new Integer(i));
	}

	/**
	 * Generates a report file, in HTML format
	 * 
	 * @param filename
	 *            File name of the report file
	 * @param minImportance
	 *            Importance value for filtering
	 */
	public void generateHTMLReport(String filename, int minImportance) {
		try {
			// Create the file to write
			PrintStream file = new PrintStream(new FileOutputStream(filename));

			// HTML tag
			file.println("<html>");

			// META tag for accents
			file.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");

			// Header
			file.print("<title>");
			file.print(Game.getInstance().processText(Game.getInstance().getGameDescriptor().getTitle()));
			file.println("</title>");

			// Body and content table
			file.println("<body style=\"background: #" + HTML_REPORT_COLOR_0
					+ ";\">");
			file.println("<br/><br/>");
			file.println("<table align=\"center\" style=\"background : #"
					+ HTML_REPORT_COLOR_1 + "; border : 1px solid #000000;\">");
			file.println("<tr><td>");

			// Title
			file.print("<center><h3>");
			file.print(Game.getInstance().processText(Game.getInstance().getGameDescriptor().getTitle()));
			file.print(" report");
			file.println("</h3></center>");

			// Clear table
			file.println("<br/><br/>");
			file.println("<table align=\"center\" style=\"background : #"
					+ HTML_REPORT_COLOR_2 + "; border : 1px solid #000000\">");
			file.println("<tr><td>");

			// For each processed rule
			for (ProcessedRule rule : processedRules) {
				// First check the importance
				if (rule.getImportance() >= minImportance) {
					file.println(Game.getInstance().processText(rule.getHTMLCode()));
					file.println("<br/><br/>");
				}
			}

			// Close clear table
			file.println("</td></tr>");
			file.println("</table>");

			// Close table and body
			file.println("<br/><br/>");
			file.println("</td></tr>");
			file.println("</table>");
			file.println("</body>");

			// Close HTML
			file.println("</html>");

			// Close the file
			file.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Indent the given DOM node recursively with the given depth
	 * 
	 * @param nodeDOM
	 *            DOM node to be indented
	 * @param depth
	 *            Depth of the current node
	 */
	private void indentDOM(Node nodeDOM, int depth) {
		// First of all, extract the document of the node, and the list of
		// children
		Document document = nodeDOM.getOwnerDocument();
		NodeList children = nodeDOM.getChildNodes();

		// Flag for knowing if the current node is empty of element nodes
		boolean isEmptyOfElements = true;

		int i = 0;
		// For each children node
		while (i < children.getLength()) {
			Node currentChild = children.item(i);

			// If the current child is an element node
			if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
				// Insert a indention before it, and call the recursive function
				// with the child (and a higher depth)
				nodeDOM.insertBefore(document.createTextNode("\n"
						+ getTab(depth + 1)), currentChild);
				indentDOM(currentChild, depth + 1);

				// Set empty of elements to false, and increase i (the new child
				// moves all children)
				isEmptyOfElements = false;
				i++;
			}

			// Go to next child
			i++;
		}

		// If this node has some element, add the indention for the closing tag
		if (!isEmptyOfElements)
			nodeDOM.appendChild(document.createTextNode("\n" + getTab(depth)));
	}

	/**
	 * Returns a set of tabulations, equivalent to the given numer
	 * 
	 * @param tabulations
	 *            Number of tabulations
	 */
	private String getTab(int tabulations) {
		String tab = "";
		for (int i = 0; i < tabulations; i++)
			tab += "\t";
		return tab;
	}

	public void cycleCompleted(int timerId, long elapsedTime) {
		// Do nothing
	}

	public void timerStarted(int timerId, long currentTime) {
		// Save the currentTime
		TimedAssessmentRule tRule = this.timedRules.get(new Integer(timerId));
		tRule.ruleStarted(currentTime);
		// System.out.println( "[TIMER STARTED] " + timerId + " - time:
		// "+currentTime );
	}

	public void timerStopped(int timerId, long currentTime) {
		// Get the rule
		TimedAssessmentRule tRule = this.timedRules.get(new Integer(timerId));
		tRule.ruleDone(currentTime);
		// Once the rule has been processed, remove it from the timermanager
		TimerManager.getInstance().deleteTimer(timerId);
		// System.out.println( "[TIMER DONE] " + timerId + " - time:
		// "+currentTime );
	}

	public boolean isEndOfChapterFeedbackDone() {

		if (assessmentProfile != null) {

			if (state == STATE_NONE && assessmentProfile.isShowReportAtEnd()) {

				try {
					state = STATE_STARTED;

					int i = 0;
					File reportFile = null;
					String fileName = null;

					if (!ReleaseFolders.reportsFolder().exists()) {
						ReleaseFolders.reportsFolder().mkdirs();
					}

					do {
						i++;
						fileName = "Report_" + i + ".html";
						reportFile = new File(ReleaseFolders.reportsFolder(),
								fileName);
					} while (reportFile.exists());

					reportFile.createNewFile();
					final String reportAbsoluteFile = reportFile
					.getAbsolutePath();

					generateHTMLReport(reportFile.getAbsolutePath(), -5);

					File report = new File(reportAbsoluteFile);

					try {
						FileReader fir = new FileReader(report);
						BufferedReader br = new BufferedReader(fir);
						String line = br.readLine();

						String text = "";
						while (line != null) {
							text += line + "\n\r";
							line = br.readLine();
						}

						text = checkUriEscaped(text);

						lastHTMLReport = text;

						Handler handler = GameThread.getInstance().getHandler();

						Message msg = handler.obtainMessage();

						Bundle b = new Bundle();
						b.putString("html", text);
						msg.what = ActivityHandlerMessages.ASSESSMENT;
						msg.setData(b);

						msg.sendToTarget();

					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}


				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				state = STATE_STARTED;
				return false;

			} else if (state == STATE_STARTED) {
				return false;
			} else if (state == STATE_DONE) {
				return true;
			} else
				return true;
		}
		return true;
	}

	/** 
	 * ANDROID WEBVIEW : 
	 *  The data must be URI-escaped -- '#', '%', '\', '?' 
	 *  should be replaced by %23, %25, %27, %3f respectively.
	 */

	private String checkUriEscaped(String text) {

		//	text = text.replace("#", "%23");
		text = text.replace("%", "%25");
		text = text.replace("\\", "%27");
		text = text.replace("?", "%3f");

		return text;

	}


	public AssessmentProfile getAssessmentProfile() {
		return assessmentProfile;
	}

	/**
	 * @param playerName
	 *            the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setStateDone() {

		state = STATE_DONE;

	}

	public String getLastHTMLReport() {

		return lastHTMLReport;

	}

}
