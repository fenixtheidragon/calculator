package back;

import lombok.Getter;

@Getter
public enum Regexes {
    NUMBER("-?\\d+(\\.\\d+)?"),
    VARIABLE("[a-zA-Z]+"),
    NUMBER_OR_VARIABLE("(\\d+(\\.\\d+)?|[a-zA-Z]+)"),
    //example of expression: -5.1+a/12-((-1+7)*3.2)
    EXPRESSION("-?\\(*-?(\\d+(\\.\\d+)?|[a-zA-Z]+)\\)*([-+*/]\\(*-?(\\d+(\\.\\d+)?|[a-zA-Z]+)\\)*)*"),
    UNKNOWN_COMMAND("/.*"),
    EQUATION(".*=.*"),
    VARIABLE_EQUALS_SOMETHING("[a-zA-Z]+=.*"),
    VARIABLE_EQUALS_EXPRESSION("[a-zA-Z]+=" + EXPRESSION.regex);
    private final String regex;
    Regexes(String regex) {
        this.regex = regex;
    }
}
