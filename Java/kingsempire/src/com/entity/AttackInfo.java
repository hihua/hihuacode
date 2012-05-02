package com.entity;

import java.util.Hashtable;

import com.events.EventArmy;
import com.worldmap.Tile;

public class AttackInfo {
	private Tile npc;
	private Long x;
	private Long y;
	private EventArmy eventArmy;
	private Hashtable<String, Long> soldiers;
	
	public Tile getNpc() {
		return npc;
	}
	
	public Long getX() {
		return x;
	}
	
	public Long getY() {
		return y;
	}
	
	public EventArmy getEventArmy() {
		return eventArmy;
	}
	
	public Hashtable<String, Long> getSoldiers() {
		return soldiers;
	}
	
	public void setNpc(Tile npc) {
		this.npc = npc;
	}
	
	public void setX(Long x) {
		this.x = x;
	}
	
	public void setY(Long y) {
		this.y = y;
	}
	
	public void setEventArmy(EventArmy eventArmy) {
		this.eventArmy = eventArmy;
	}
	
	public void setSoldiers(Hashtable<String, Long> soldiers) {
		this.soldiers = soldiers;
	}	
}
