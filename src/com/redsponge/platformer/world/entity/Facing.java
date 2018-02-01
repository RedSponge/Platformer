package com.redsponge.platformer.world.entity;

public enum Facing {
	RIGHT("right"),
	LEFT("left"),
	NONE("none");
	
	private String id;
	private Facing opposite;
	
	
	private Facing(String id) {
		this.id = id;
		setupOpposite();
	}
	
	private void setupOpposite() {
		switch(id) {
		case "right":
			opposite = Facing.LEFT;
			break;
		case "left":
			opposite = Facing.RIGHT;
			break;
		default:
			opposite = Facing.NONE;
		}
	}
	
	public String getId() {
		return id;
	}

	public Facing getOpposite() {
		return opposite;
	}
}
