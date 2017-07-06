package org.testobject.integrations;

public class Test {

	enum State {
		SUCCESS,
		FAILED,
		SKIPPED
	}

	private final String name;
	private final long startTime;

	private State state;
	private long finishTime;

	public Test(String name, long startTime) {
		this.name = name;
		this.startTime = startTime;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public long getStartTime() {
		return startTime;
	}

	public State getState() {
		return state;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}
}
