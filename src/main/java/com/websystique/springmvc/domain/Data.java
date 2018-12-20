package com.websystique.springmvc.domain;

public class Data {

	private BitCoinPrice data;

	public BitCoinPrice getData() {
		return data;
	}

	public void setData(BitCoinPrice data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Data [data=" + data + "]";
	}
	
}
