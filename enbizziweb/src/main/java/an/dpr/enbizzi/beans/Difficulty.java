package an.dpr.enbizzi.beans;

import an.dpr.util.I18n;

public enum Difficulty {

    EASY(1), MEDIUM(2), HARD(3), VERY_HARD(4);

    private int id;

    private Difficulty(int id) {
	this.id = id;
    }

    public static Difficulty get(int id) {
	Difficulty retValue = null;
	if (EASY.getId() == id) {
	    retValue = EASY;
	} else if (MEDIUM.getId() == id) {
	    retValue = MEDIUM;
	} else if (HARD.getId() == id) {
	    retValue = HARD;
	} else if (VERY_HARD.getId() == id) {
	    retValue = VERY_HARD;
	}
	return retValue;
    }

    public int getId() {
	return id;
    }

    public String getI18n() {
	String ret = null;
	switch (this) {
	case EASY:
	    ret = I18n.getText("string.difficulty_EASY");
	    break;
	case HARD:
	    ret = I18n.getText("string.difficulty_HARD");
	    break;
	case MEDIUM:
	    ret = I18n.getText("string.difficulty_MEDIUM");
	    break;
	case VERY_HARD:
	    ret = I18n.getText("string.difficulty_VERY_HARD");
	    break;
	}
	return ret;
    }

    public an.dpr.enbizzi.domain.Difficulty getDomain(){
	an.dpr.enbizzi.domain.Difficulty ret = new an.dpr.enbizzi.domain.Difficulty();
	ret.setDifficultyId(this.getId());
	ret.setName(this.name());
	return ret;
    }
}
