package com.soldier;

import com.queue.TrainingQueue;

public class Soldier {
	private String name;
	private Long count;
	private TrainingQueue trainingQueue;

	public String getName() {
		return name;
	}

	public Long getCount() {
		return count;
	}

	public TrainingQueue getTrainingQueue() {
		return trainingQueue;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public void setTrainingQueue(TrainingQueue trainingQueue) {
		this.trainingQueue = trainingQueue;
	}
}
