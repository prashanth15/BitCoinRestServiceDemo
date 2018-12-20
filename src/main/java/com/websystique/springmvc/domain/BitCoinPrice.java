package com.websystique.springmvc.domain;

import java.util.List;
import java.util.Map;

public class BitCoinPrice {

	private String base;
	private String currency;
	private List<Prices> prices;
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public List<Prices> getPrices() {
		return prices;
	}
	public void setPrices(List<Prices> prices) {
		this.prices = prices;
	}
	@Override
	public String toString() {
		return "BitCoinPrice [base=" + base + ", currency=" + currency + ", prices=" + prices + "]";
	}
}
