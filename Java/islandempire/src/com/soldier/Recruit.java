package com.soldier;

import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Recruit {
	private Long defense;
	private String name;
	private Long consumeFood;
	private HashMap<String, Long> cost;
	private Long time;
	private Long capture;
	private Long attack;

	public Long getDefense() {
		return defense;
	}

	public String getName() {
		return name;
	}

	public Long getConsumeFood() {
		return consumeFood;
	}

	public HashMap<String, Long> getCost() {
		return cost;
	}

	public Long getTime() {
		return time;
	}

	public Long getCapture() {
		return capture;
	}

	public Long getAttack() {
		return attack;
	}

	public void setDefense(Long defense) {
		this.defense = defense;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setConsumeFood(Long consumeFood) {
		this.consumeFood = consumeFood;
	}

	public void setCost(HashMap<String, Long> cost) {
		this.cost = cost;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public void setCapture(Long capture) {
		this.capture = capture;
	}

	public void setAttack(Long attack) {
		this.attack = attack;
	}
	
	public static Recruit parse(String response) {
		JSONObject json = JSONObject.fromObject(response);
		if (json == null || json.get("soldier_def") == null)
			return null;
		
		Recruit recruit = new Recruit();
		recruit.setDefense((json.get("defense") != null) ? json.getLong("defense") : null);
		recruit.setName((json.get("name") != null) ? json.getString("name") : null);
		recruit.setConsumeFood((json.get("consume_food") != null) ? json.getLong("consume_food") : null);
		
		HashMap<String, Long> cost = new HashMap<String, Long>();
		JSONArray arrays = json.getJSONArray("cost");
				
		for (int i = 0;i < arrays.size();i++) {
			JSONObject array = (JSONObject) arrays.get(i);
			if (array.get("name") == null || array.get("count") == null)
				continue;
			
			String name = array.getString("name");
			Long count = array.getLong("count");
			cost.put(name, count);
		}
		
		recruit.setCost(cost);	
		recruit.setTime((json.get("time") != null) ? json.getLong("time") : null);
		recruit.setCapture((json.get("capture") != null) ? json.getLong("capture") : null);
		recruit.setAttack((json.get("attack") != null) ? json.getLong("attack") : null);
		return recruit;
	}
}
