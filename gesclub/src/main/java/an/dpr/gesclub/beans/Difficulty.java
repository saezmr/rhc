package an.dpr.gesclub.beans;

import java.util.ResourceBundle;


public enum Difficulty {

    EASY((long)1), MEDIUM((long)2), HARD((long)3), VERY_HARD((long)4);

    private Long id;
    private ResourceBundle bundle;

    private Difficulty(Long id) {
	this.id = id;
	bundle = ResourceBundle.getBundle("i18n");
    }

    public static Difficulty get(Long id) {
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

    public Long getId() {
	return id;
    }

    public String getI18n() {
	String ret = null;
	switch (this) {
	case EASY:
	    ret = bundle.getString("string.difficulty_EASY");
	    break;
	case HARD:
	    ret = bundle.getString("string.difficulty_HARD");
	    break;
	case MEDIUM:
	    ret = bundle.getString("string.difficulty_MEDIUM");
	    break;
	case VERY_HARD:
	    ret = bundle.getString("string.difficulty_VERY_HARD");
	    break;
	}
	return ret;
    }
    
    public String toString(){
	return getI18n();
    }

}
