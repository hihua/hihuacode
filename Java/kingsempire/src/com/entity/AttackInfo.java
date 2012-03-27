package com.entity;

import java.util.Hashtable;

import com.events.EventArmy;
import com.world.WorldMap;

public class AttackInfo {
	private WorldMap npc;
	private Long x;
	private Long y;
	private EventArmy eventArmy;
	private Hashtable<String, Long> soldiers;
	
	public WorldMap getNpc() {
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
	
	public void setNpc(WorldMap npc) {
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
