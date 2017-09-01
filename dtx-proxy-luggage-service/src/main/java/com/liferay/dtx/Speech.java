package com.liferay.dtx;

public class Speech {

	private String speech;
	private String displayText;

	public Speech(String speech) {
		super();
		setDisplayText(speech);
		this.speech = displayText;
	}

	public String getSpeech() {
		return speech;
	}

	public String getDisplayText() {
		return displayText;
	}

	private void setDisplayText(String speech) {
		switch (speech) {
		case "balcão":
			this.displayText = "Fique tranquilo! Sua bagagem ainda está no balcão de despacho";
			break;
		case "esteira":
			this.displayText = "Sua bagagem está na esteira";
			break;
		case "porão":
			this.displayText = "Sua bagagem está no porão do avião";
			break;
		case "extraviada":
			this.displayText = "Infelizmente identificamos um problema com sua bagagem";
			break;

		default:
			break;
		}
	}
}
