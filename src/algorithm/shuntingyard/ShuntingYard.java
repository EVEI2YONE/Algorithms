package algorithm.shuntingyard;

import org.junit.jupiter.params.converter.ArgumentConversionException;

public class ShuntingYard {

    enum State {
        LOW,
        HIGH,
        CHANGE
    };

    public StringBuilder Shunt(String input){
        StringBuilder result = new StringBuilder();
        StringBuilder stack = new StringBuilder();
        State state = State.LOW;
        boolean highPrecedence;
        boolean exponentFound = false;
        int skip = 0;
        for(char c : input.toCharArray()) {
            if(skip > 0) { //this is to skip nested values '(7-4)' = 4 skips since currently point to '('
                skip--;
                continue;
            }
            if(Character.isLetter(c) || Character.isDigit(c))
                result.append((c));
            else if(IsOperand(c)) { //Compare current precedence to current operator
                if(exponentFound) {
                    result.append('^');
                    exponentFound = false;
                }
                highPrecedence = IsOperandHigherPrecedence(c);
                if (state == State.HIGH && !highPrecedence) {
                    state = State.CHANGE; //high -> low (change)
                }
                else if(state == State.LOW && highPrecedence) {
                    state = State.HIGH; //low -> high
                }

                if(state == State.CHANGE) {
                    AppendOperandStack(result, stack); //empty operand stack and start over
                    state = State.LOW; //high -> low (reset)
                }
                stack.append((c)); //push to operand stack
            }
            else if(IsNested(c)) {
                String extractNested = ExtractNestedShunt(input, c); //remove it as well
                skip = extractNested.length()+1;
                result.append(Shunt(extractNested));
            }
            else if(c == '^') {
                exponentFound = true;
            }
        }
        AppendOperandStack(result, stack);
        return result;
    }

    private String ExtractNestedShunt(String input, char open) {
        StringBuilder sbInput = new StringBuilder(input);
        int start = input.indexOf(open);
        char close = FindPair(input, open);
        int end = input.indexOf(close);
        return sbInput.substring(start+1, end);
    }

    private char FindPair(String input, char open) {
        char pair = '~';
        switch(open) {
            case '(': pair = ')'; break;
            case '[': pair = ']'; break;
            case '{': pair = '}'; break;
            default: throw new ArgumentConversionException("Cannot find nested pair");
        }
        return pair;
    }

    private void AppendOperandStack(StringBuilder result, StringBuilder stack) {
        stack.reverse();
        result.append((stack));
        stack.delete(0,stack.length());
    }

    private boolean IsOperand(char c) {
        return (c == '+' || c == '-' || c == '*' || c =='/');
    }

    private boolean IsOperandHigherPrecedence(char c) {
        return (c == '*' || c =='/');
    }

    private boolean IsNested(char c) {
        return (c == '(' || c ==')');
    }
}
