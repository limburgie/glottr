package be.webfactor.polyglot.service.word;

public class SpokenWord {

	private String voiceId;
	private String word;

	public SpokenWord(String voiceId, String word) {
		this.voiceId = voiceId;
		this.word = word;
	}

	public String getVoiceId() {
		return voiceId;
	}

	public String getWord() {
		return word;
	}

	public String getWordOutsideBrackets() {
		int openingBracketIndex = word.indexOf(" (");
		if (openingBracketIndex == -1) {
			return word;
		}
		int closingBracketIndex = word.indexOf(")", openingBracketIndex);
		if (closingBracketIndex == -1) {
			closingBracketIndex = word.length() - 1;
		}
		return word.replace(
				word.substring(openingBracketIndex, closingBracketIndex + 1),
				"");
	}

}
