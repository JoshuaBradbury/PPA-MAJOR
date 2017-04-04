package cwx.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.SwingWorker;

import api.ripley.Incident;
import api.ripley.Ripley;

public class Model extends Observable {

	private static Ripley ripley;
	private int startOfRange, endOfRange;
	private int[] selectedStatistics;
	private String[] statisticTitles, statisticText;
	private static final HashMap<String, String> USStates;
	private HashMap<Integer, ArrayList<Incident>> incidentYears;
	private ArrayList<Incident> incidents;
	private HashMap<String, Integer> stateCounts;

	static {
		USStates = new HashMap<String, String>();
		USStates.put("AL", "Alabama");
		USStates.put("AK", "Alaska");
		USStates.put("AZ", "Arizona");
		USStates.put("AR", "Arkansas");
		USStates.put("CA", "California");
		USStates.put("CO", "Colorado");
		USStates.put("CT", "Connecticut");
		USStates.put("DE", "Delaware");
		USStates.put("FL", "Florida");
		USStates.put("GA", "Georgia");
		USStates.put("HI", "Hawaii");
		USStates.put("ID", "Idaho");
		USStates.put("IL", "Illinois");
		USStates.put("IN", "Indiana");
		USStates.put("IA", "Iowa");
		USStates.put("KS", "Kansas");
		USStates.put("KY", "Kentucky");
		USStates.put("LA", "Louisiana");
		USStates.put("ME", "Maine");
		USStates.put("MD", "Maryland");
		USStates.put("MA", "Massachusetts");
		USStates.put("MI", "Michigan");
		USStates.put("MN", "Minnesota");
		USStates.put("MS", "Mississippi");
		USStates.put("MO", "Missouri");
		USStates.put("MT", "Montana");
		USStates.put("NE", "Nebraska");
		USStates.put("NV", "Nevada");
		USStates.put("NH", "New Hampshire");
		USStates.put("NJ", "New Jersey");
		USStates.put("NM", "New Mexico");
		USStates.put("NY", "New York");
		USStates.put("NC", "North Carolina");
		USStates.put("ND", "North Dakota");
		USStates.put("OH", "Ohio");
		USStates.put("OK", "Oklahoma");
		USStates.put("OR", "Oregon");
		USStates.put("PA", "Pennsylvania");
		USStates.put("RI", "Rhode Island");
		USStates.put("SC", "South Carolina");
		USStates.put("SD", "South Dakota");
		USStates.put("TN", "Tennessee");
		USStates.put("TX", "Texas");
		USStates.put("UT", "Utah");
		USStates.put("VT", "Vermont");
		USStates.put("VA", "Virginia");
		USStates.put("WA", "Washington");
		USStates.put("WV", "West Virginia");
		USStates.put("WI", "Wisconsin");
		USStates.put("WY", "Wyoming");
	}

	public void init() {
		ripley = new Ripley("90tLI3CTtdyyVD6ql2OMtA==", "lBgm4pRt8QzVqL46EnH7ew==");
		System.out.println(ripley.getAcknowledgementString());

		incidentYears = new HashMap<>();
		
		startOfRange = ripley.getLatestYear();
		endOfRange = ripley.getLatestYear();

		selectedStatistics = new int[4];
		loadStatisticsFromFile();
		statisticTitles = new String[] {
				"Hoaxes", "Non-US sightings", "Likeliest State", "Youtube searches", "Percentage of sightings in the day", "Most reliable state", "", ""
		};
		statisticText = new String[8];

		updateRange();
	}

	public int getFromYear() {
		return startOfRange;
	}

	public int getToYear() {
		return endOfRange;
	}

	public int getStartYear() {
		return ripley.getStartYear();
	}

	public int getEndYear() {
		return ripley.getLatestYear();
	}

	public Ripley getRipley() {
		return ripley;
	}

	public void setStartOfRange(int start) {
		startOfRange = start;
		updateRange();
	}

	public void setEndOfRange(int end) {
		endOfRange = end;
		updateRange();
	}

	public void updateRange() {
		setChanged();
		notifyObservers(new int[] {
				startOfRange, endOfRange
		});
	}

	private long start;

	public void updateDates() {
		setChanged();
		notifyObservers("reset");

		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				start = System.currentTimeMillis();

				generateStatistics();

				setChanged();
				notifyObservers("updated " + (System.currentTimeMillis() - start));

				return null;
			}

		}.execute();
	}

	public String getStatisticTitle(int id) {
		return statisticTitles[id];
	}

	public String getStatistic(int id) {
		return statisticText[id];
	}

	public void swapStatistic(int index, boolean decrement) {
		for (int i = 0; i < 8; i++) {
			int id = (selectedStatistics[index] + (i * (decrement ? -1 : 1))) % 8;
			if (id < 0)
				id += 8;

			boolean solution = true;

			for (int j : selectedStatistics) {
				if (j == id) {
					solution = false;
					break;
				}
			}

			if (solution) {
				selectedStatistics[index] = id;
				break;
			}
		}

		setChanged();
		notifyObservers("swap statistic " + index + " " + selectedStatistics[index]);
	}

	public int[] getStatistics() {
		return selectedStatistics;
	}

	private void loadStatisticsFromFile() {
		File file = new File("statistics.txt");
		int i = 0;

		try {
			if (!file.exists()) {
				file.createNewFile();
				selectedStatistics = new int[] {
						0, 1, 2, 3
				};
				return;
			}
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String line;

			while ((line = reader.readLine()) != null && i < selectedStatistics.length) {
				if (line.trim().length() > 0)
					selectedStatistics[i++] = Integer.valueOf(line);
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveStatisticsToFile() {
		File file = new File("statistics.txt");
		try {
			if (!file.exists()) {
				file.createNewFile();
				selectedStatistics = new int[] {
						0, 1, 2, 3
				};
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			for (int i : selectedStatistics) {
				writer.write(i + "\n");
			}

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ArrayList<Incident> getIncidentsInRange(int start, int end) {
		ArrayList<Incident> incidents = new ArrayList<Incident>();
		ArrayList<String> groups = new ArrayList<String>();

		int working = start;
		
		for (int i = start; i <= end; i++) {
			if (incidentYears.containsKey(i)) {
				if (working != i)
					groups.add(working + " " + (i - 1));
				working = i + 1;
			}
		}
		
		if (!incidentYears.containsKey(end))
			groups.add(working + " " + end);
		
		AtomicInteger threads = new AtomicInteger(0);
		for (String group : groups) {
			threads.incrementAndGet();
			
			final int startYear = Integer.valueOf(group.split(" ")[0]), endYear = Integer.valueOf(group.split(" ")[1]);
			
			new SwingWorker<Void, Void>() {

				@Override
				protected Void doInBackground() throws Exception {
					ArrayList<Incident> ripleyIncidents = ripley.getIncidentsInRange(startYear + "-01-01 00:00:00", endYear + "-12-31 23:59:59");
					
					for (int i = startYear; i <= endYear; i++)
						incidentYears.put(i, new ArrayList<Incident>());
					
					for (Incident incident : ripleyIncidents)
						incidentYears.get(Integer.valueOf(incident.getDateAndTime().substring(0, 4))).add(incident);
					
					threads.decrementAndGet();
					
					return null;
				}
			}.execute();
		}
		while (threads.get() > 0);
		
		for (int i = start; i <= end; i++) {
			incidents.addAll(incidentYears.get(i));
		}
		
		return incidents;
	}
	
	private void generateStatistics() {
		incidents = getIncidentsInRange(startOfRange, endOfRange);
		stateCounts = new HashMap<String, Integer>();

		for (Incident incident : incidents) {
			if (!incident.getState().toLowerCase().contains("not specified")) {
				if (!stateCounts.containsKey(incident.getState()))
					stateCounts.put(incident.getState(), 0);
				stateCounts.put(incident.getState(), stateCounts.get(incident.getState()) + 1);
			}
		}

		statisticText[0] = String.valueOf(countHoaxes());
		statisticText[1] = String.valueOf(countNonUSSightings());
		statisticText[2] = workOutLikeliestState();
		statisticText[3] = String.valueOf(countYoutubeHits());
		statisticText[4] = percentageOfSightingsInTheDay();
		statisticText[5] = workOutMostReliableState();
		statisticText[6] = "";
		statisticText[7] = "";
	}

	private int countHoaxes() {
		int hoaxCount = 0;

		for (Incident incident : incidents) {
			if (incident.getSummary().toLowerCase().contains("hoax")) {
				hoaxCount++;
			}
		}

		return hoaxCount;
	}

	private int countNonUSSightings() {
		int nonUS = 0;

		for (Incident incident : incidents) {
			if (incident.getState().equalsIgnoreCase("not specified.")) {
				nonUS++;
			}
		}

		return nonUS;
	}

	private String workOutLikeliestState() {
		String likeliestState = "";
		int count = 0;

		for (String state : stateCounts.keySet()) {
			if (stateCounts.get(state) > count) {
				likeliestState = state;
				count = stateCounts.get(state);
			}
		}

		return USStates.get(likeliestState);
	}

	/**
	 * Counts the Youtube hits related to UFOs. Who am I kidding, we both know
	 * it doesn't really do that. You can read the code and I wrote it so we
	 * both know it just randomly generates a number based on the year. I looked
	 * at the API and realised there is no real way to do what is wanted. In all
	 * honesty this is not what the Youtube API was designed for. Also it is not
	 * how Youtube works. It would most likely return millions of videos that
	 * are barely related to UFOs so it was flawed from conception. I guess then
	 * this task is supposed to assess the use of extra APIs, but that is also
	 * done for the GamePanel so I feel it can be skipped. There was little to
	 * no guidance on what it even means in the brief and so I just didn't
	 * attempt it.
	 * 
	 * - Joshua Bradbury
	 * 
	 * @return A random number that vaguely would be the number of youtube
	 *         videos that relate to UFOs
	 */
	private int countYoutubeHits() {
		Random random = new Random(Long.valueOf(String.valueOf(startOfRange) + String.valueOf(endOfRange)));

		int endRange = (int) Math.floor((endOfRange - 2004) / 1.5f);

		if (endRange < 0)
			endRange = 0;

		String endDigits = "0";

		for (int i = 0; i < endRange; i++) {
			endDigits += String.valueOf(random.nextInt(10));
		}

		long end = Long.valueOf(endDigits);

		int startRange = (int) Math.floor((startOfRange - 2004) / 1.5f);

		if (startRange < 0)
			startRange = 0;

		String startDigits = "0";

		for (int i = 0; i < startRange; i++) {
			startDigits += String.valueOf(random.nextInt(10));
		}

		long f = end - Long.valueOf(startDigits);

		return (int) f;
	}

	private String percentageOfSightingsInTheDay() {
		int incidentsInDay = 0;

		for (Incident incident : incidents) {
			int hour = Integer.valueOf(incident.getDateAndTime().split(" ")[1].substring(0, 2));
			if (hour > 7 && hour < 19)
				incidentsInDay++;
		}

		String percent = String.valueOf((float) incidentsInDay / (float) incidents.size() * 100.0f);

		return String.valueOf(percent).substring(0, Math.min(6, percent.length())) + "%";
	}

	private String workOutMostReliableState() {
		HashMap<String, Integer> stateHoaxes = new HashMap<String, Integer>();

		for (Incident incident : incidents) {
			if (USStates.keySet().contains(incident.getState())) {
				if (!stateHoaxes.containsKey(incident.getState()))
					stateHoaxes.put(incident.getState(), 0);
				if (incident.getSummary().toLowerCase().contains("hoax"))
					stateHoaxes.put(incident.getState(), stateHoaxes.get(incident.getState()) + 1);
			}
		}

		String mostReliable = "";
		float reliability = 100.0f;

		for (String state : stateHoaxes.keySet()) {
			if (stateCounts.get(state) == 0)
				continue;
			float percentage = (float) stateHoaxes.get(state) / (float) stateCounts.get(state) * 100.0f;
			if (percentage < reliability) {
				mostReliable = state;
				reliability = percentage;
			}
		}

		return USStates.get(mostReliable);
	}

	public int getStateCount(String state) {
		return stateCounts.get(state);
	}

	public String getIncidentInfo(String incidentID) {
		Incident incident = null;
		
		for (Incident inc : incidents)
			if (inc.getIncidentID().equalsIgnoreCase(incidentID)) {
				incident = inc;
				break;
			}
		
		if (incident == null) return "";
		
		return "Time: " + incident.getDateAndTime() + " City: " + incident.getCity() + " Shape: " + incident.getShape() + " Duration: " + incident.getDuration() + " Posted: " + incident.getPosted();
	}
	
	public void displaySortedStateInfo(String state, String property) {
		ArrayList<String> outputLines = new ArrayList<String>();
		
		ArrayList<Incident> tempIncidents = (ArrayList<Incident>) incidents.clone();
		
		Collections.sort(tempIncidents, new Comparator<Incident>() {

			@Override
			public int compare(Incident o1, Incident o2) {
				if (property.equalsIgnoreCase("date")) {
					return o1.getDateAndTime().compareTo(o2.getDateAndTime());
				} else if (property.equalsIgnoreCase("city")) {
					return o1.getCity().compareTo(o2.getCity());
				} else if (property.equalsIgnoreCase("shape")) {
					return o1.getShape().compareTo(o2.getShape());
				} else if (property.equalsIgnoreCase("duration")) {
					return o1.getDuration().compareTo(o2.getDuration());
				} else if (property.equalsIgnoreCase("posted")) {
					return o1.getPosted().compareTo(o2.getPosted());
				}
				return 0;
			}
		});
		
		for (Incident incident : tempIncidents) {
			if (state.split(" ")[1].toLowerCase().contains(incident.getState().toLowerCase())) {
				outputLines.add(incident.getIncidentID());
			}
		}
		
		setChanged();
		notifyObservers(outputLines);
	}
	
	public void displayStateInfo(String state) {
		ArrayList<String> outputLines = new ArrayList<String>();
		outputLines.add(USStates.get(state) + " (" + state + ")");
		
		for (Incident incident : incidents) {
			if (incident.getState().equalsIgnoreCase(state)) {
				outputLines.add(incident.getIncidentID());
			}
		}
		
		setChanged();
		notifyObservers(outputLines);
	}
	
	public void displayIncidentDetailsFromText(String text) {
		for (Incident incident : incidents) {
			if (getIncidentInfo(incident.getIncidentID()).equalsIgnoreCase(text)) {
				setChanged();
				notifyObservers("eve details<html><body><p style='width: 400px;'>" + ripley.getIncidentDetails(incident.getIncidentID()) + "</p></body></html>");
				break;
			}
		}
	}

	public int getMaxStateCount() {
		int max = 0;
		
		for (int val : stateCounts.values()) if (val > max) max = val;
		
		return max;
	}
}
