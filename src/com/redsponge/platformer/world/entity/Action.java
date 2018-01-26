package com.redsponge.platformer.world.entity;

public enum Action {
	
	IDLE("idle"),
	DUCKING("ducking"),
	RUNNING("running"),
	WALKING("walking"),
	NONE("none");
	
	private String id;
	
	private Action(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
}
