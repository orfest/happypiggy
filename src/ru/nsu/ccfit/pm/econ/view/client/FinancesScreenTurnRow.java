package ru.nsu.ccfit.pm.econ.view.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.pivot.collections.Dictionary;

public class FinancesScreenTurnRow implements Dictionary<String, String> {

	private static final long serialVersionUID = 411675888537610080L;
	
	private Map<Integer, Double> turnToData = new HashMap<Integer, Double>();
	private String rowHeader = "";
	private int lastTurn = -1;
	
	public FinancesScreenTurnRow() {
	}
	
	public FinancesScreenTurnRow(String categoryName) {
		this.rowHeader = categoryName;
	}
	
	public void addTurnValue(int turnNumber, double value) {
		turnToData.put(turnNumber, value);
		lastTurn = turnNumber;
	}
	
	public double changeLastTurnValue(double value) {
		if (turnToData.get(lastTurn) != null)
			return turnToData.put(lastTurn, value);
		return 0.0;
	}
	
	public double changeTurnValue(int turnNumber, double value) {
		if (turnToData.get(turnNumber) != null) 
			return turnToData.put(turnNumber, value);
		return 0.0;
	}
	
	private String formatData(Double data) {
		if (data == null)
			return "";
		// TODO place into IFormatter ?
		if (Math.abs(data) < 1e-6)
			return "0";
		return String.format("%+.2f", data);
	}
	
	private int turnStringToNumber(String str) {
		try {
			int turnNum = Integer.valueOf( str.substring(FinancesScreen.TURN_FIELD_PREFIX.length()) );
			return turnNum;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public boolean containsKey(String key) {
		if (FinancesScreen.ROW_HEADER_FIELD.equals(key))
			return true;
		return turnToData.containsKey(turnStringToNumber(key));
	}

	@Override
	public String get(String key) {
		if (FinancesScreen.ROW_HEADER_FIELD.equals(key))
			return rowHeader;
		return formatData( turnToData.get(turnStringToNumber(key)) );
	}

	@Override
	public String put(String key, String value) {
		if (FinancesScreen.ROW_HEADER_FIELD.equals(key)) {
			String oldRowHeader = rowHeader;
			rowHeader = value;
			return oldRowHeader;
		}
		
		try {
			int turnNumber = turnStringToNumber(key);
			return formatData( turnToData.put(turnNumber, Double.valueOf(value)) );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String remove(String key) {
		return null;
	}

}
