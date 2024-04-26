package com.softserve.edu.opencart.data;

public enum Currencies {
	DEFAULT_CURRENCY("US Dollar", "$"),
	EURO("Euro", "€"),
	POUND_STERLING("Pound Sterling", "£"),
	US_DOLLAR("US Dollar", "$");
	//
	private String name;
	private String symbol;

	private Currencies(String name, String symbol) {
		this.name = name;
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public String getSymbol() {
		return symbol;
	}

	@Override
	public String toString() {
		return name;
	}

}
