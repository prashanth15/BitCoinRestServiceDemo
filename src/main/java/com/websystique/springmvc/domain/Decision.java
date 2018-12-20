package com.websystique.springmvc.domain;


public class Decision {
	private String decision;
	private Double average;
	private Double min;
	private Double max;
	public String getDecision() {
		return decision;
	}
	public Double getAverage() {
		return average;
	}
	public void setAverage(Double average) {
		this.average = average;
	}
	public Double getMin() {
		return min;
	}
	public void setMin(Double min) {
		this.min = min;
	}
	public Double getMax() {
		return max;
	}
	public void setMax(Double max) {
		this.max = max;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	@Override
	public String toString() {
		return "Decision [decision=" + decision + ", average=" + average + ", min=" + min + ", max=" + max + "]";
	}
}
