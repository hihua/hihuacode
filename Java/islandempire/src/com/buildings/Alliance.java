package com.buildings;

public class Alliance {
	private Long allianceId;
	private Long level;
	private Long membershipId;
	private String allianceName;
	
	public Long getAllianceId() {
		return allianceId;
	}
	
	public Long getLevel() {
		return level;
	}
	
	public Long getMembershipId() {
		return membershipId;
	}
	
	public String getAllianceName() {
		return allianceName;
	}
	
	public void setAllianceId(Long allianceId) {
		this.allianceId = allianceId;
	}
	
	public void setLevel(Long level) {
		this.level = level;
	}
	
	public void setMembershipId(Long membershipId) {
		this.membershipId = membershipId;
	}
	
	public void setAllianceName(String allianceName) {
		this.allianceName = allianceName;
	}	
}
