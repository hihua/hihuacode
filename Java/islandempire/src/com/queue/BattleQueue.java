package com.queue;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.util.DateTime;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BattleQueue extends Queue {
	private Long toX;
	private Long toLevel;
	private Date arriveTime;
	private Long toY;
	private Long fromLevel;
	private Long id;
	private Long fromX;
	private String fromTownName;
	private Long fromY;

	public Long getToX() {
		return toX;
	}

	public Long getToLevel() {
		return toLevel;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public Long getToY() {
		return toY;
	}

	public Long getFromLevel() {
		return fromLevel;
	}

	public Long getId() {
		return id;
	}

	public Long getFromX() {
		return fromX;
	}

	public String getFromTownName() {
		return fromTownName;
	}

	public Long getFromY() {
		return fromY;
	}

	public void setToX(Long toX) {
		this.toX = toX;
	}

	public void setToLevel(Long toLevel) {
		this.toLevel = toLevel;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public void setToY(Long toY) {
		this.toY = toY;
	}

	public void setFromLevel(Long fromLevel) {
		this.fromLevel = fromLevel;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFromX(Long fromX) {
		this.fromX = fromX;
	}

	public void setFromTownName(String fromTownName) {
		this.fromTownName = fromTownName;
	}

	public void setFromY(Long fromY) {
		this.fromY = fromY;
	}
	
	public static List<BattleQueue> parse(String response) {
		JSONObject json = JSONObject.fromObject(response);
		if (json == null || json.get("battle_queue") == null)
			return null;
		
		JSONArray arrays = json.getJSONArray("battle_queue");
		if (arrays != null) {
			List<BattleQueue> battleQueues = new Vector<BattleQueue>(); 
			for (int i = 0; i < arrays.size(); i++) {
				JSONObject array = (JSONObject) arrays.get(i);
				BattleQueue battleQueue = new BattleQueue();
				battleQueue.setToLevel(array.get("to_level") != null ? array.getLong("to_level") : null);
				battleQueue.setToX(array.get("to_x") != null ? array.getLong("to_x") : null);
				battleQueue.setActionOwnerTownId(array.get("action_owner_town_id") != null ? array.getLong("action_owner_town_id") : null);
				battleQueue.setTotalTime(array.get("total_time") != null ? array.getLong("total_time") : null);
				battleQueue.setArriveTime(array.get("arrive_time") != null ? DateTime.getTime(array.getLong("arrive_time")) : null);
				battleQueue.setToY(array.get("to_y") != null ? array.getLong("to_y") : null);
				battleQueue.setFromLevel(array.get("from_level") != null ? array.getLong("from_level") : null);
				battleQueue.setFromX(array.get("from_x") != null ? array.getLong("from_x") : null);
				battleQueue.setFromTownName(array.get("from_town_name") != null ? array.getString("from_town_name") : null);
				battleQueue.setFromY(array.get("from_y") != null ? array.getLong("from_y") : null);
				battleQueue.setMission(array.get("mission_type") != null ? array.getLong("mission_type") : null);
				battleQueue.setToTownId(array.get("to_town_id") != null ? array.getLong("to_town_id") : null);
				battleQueue.setBattleInfoLevel(array.get("battleInfoLevel") != null ? array.getLong("battleInfoLevel") : null);
				battleQueue.setToTownName(array.get("to_town_name") != null ? array.getString("to_town_name") : null);
				battleQueue.setId(array.get("queue_id") != null ? array.getLong("queue_id") : null);
				battleQueue.setFromTownId(array.get("from_town_id") != null ? array.getLong("from_town_id") : null);
				battleQueues.add(battleQueue);
			}
			
			return battleQueues;
		} else
			return null;
	}
}
