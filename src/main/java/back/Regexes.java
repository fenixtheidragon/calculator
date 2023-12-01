package back;

public enum Regexes {
    NUMBER("-?\\d+(\\.\\d+)?"),
    VARIABLE("[a-zA-Z]+"),
    OPERATORS("[-+*/^]"),
    LEFT_BRACKETS("(\\(|\\(\\-)*"),
    NUM_OR_VAR("(\\d+(\\.\\d+)?|[a-zA-Z]+)"),
    //this "EXPRESSION" regex is any valid expression that could be entered into calculator
    //example of expression: -5.1+a/12-((-1+7)*3.2)
    //full form: EXPRESSION("-?(\\(|\\(\\-)*(\\d+(\\.\\d+)?|[a-zA-Z]+)([-+*/](\\(|\\(\\-)*(\\d+(\\.\\d+)?|[a-zA-Z]+)\\)*)*"),
    EXPRESSION("-?" + LEFT_BRACKETS.regex + NUM_OR_VAR.regex + "\\)*" + "(" + OPERATORS.regex + LEFT_BRACKETS.regex + NUM_OR_VAR.regex + "\\)*" + ")*"),
    UNKNOWN_CMD("/.*"),
    EQUATION(".*=.*"),
    VAR_EQUALS("[a-zA-Z]+=.*"),
    VAR_EQUALS_EXP("[a-zA-Z]+=" + EXPRESSION.regex);
    private final String regex;

    Regexes(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
