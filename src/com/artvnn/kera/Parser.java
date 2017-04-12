package com.artvnn.kera;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by ARTVNN on 4/12/2017.
 */
public class Parser {

    enum State {
        WHITESPACE, TOKEN;
    }
    State state;

    ArrayList tree;
    StringBuilder token;
    Stack<ArrayList> stack;
    char c;
    boolean allowWhitespacesInToken;
    int i;
    String input;

    public ArrayList parse (String input) throws Exception  {
        this.input = input;
        state = State.WHITESPACE;
        tree = new ArrayList();
        token = new StringBuilder();
        stack = new Stack<>();

        stack.push(tree);
        for(i=0; i<input.length(); i++) {
            c = input.charAt(i);
            switch (c) {
                case ' ':
                case '\t':
                case '\r':
                case '\n':
                    if(state == State.TOKEN) {
                        if(allowWhitespacesInToken) appendToToken(); else endToken();
                    }
                    break;
                case '(':
                    if(state == State.TOKEN) endToken();
                    beginList();
                    break;
                case ')':
                    if(state == State.TOKEN) endToken();
                    endList();
                    break;
                case '\'':
                    if(state != State.TOKEN) beginToken(true); else endToken();
                    break;
                default:
                    if(state != State.TOKEN) beginToken(false);
                    appendToToken();
                    break;
            }
        }
        if(state == State.TOKEN) endToken();
        if(stack.peek().equals(tree)) return tree; else throw new Exception("Incomplete expression.");
    }

    private void beginList () {
        ArrayList temp = stack.peek();
        stack.push(new ArrayList());
        temp.add(stack.peek());
        state = State.WHITESPACE;
    }

    private void endList () throws Exception {
        if(stack.peek().equals(tree)) throw new Exception("Invalid character ')' in expression");
        stack.pop();
        state = State.WHITESPACE;
    }

    private void beginToken (boolean allowWhitespacesInToken) {
        this.allowWhitespacesInToken = allowWhitespacesInToken;
        token.delete(0, token.length());
        state = State.TOKEN;
    }

    private void appendToToken () {
        token.append(c);
    }

    private void endToken () {
        stack.peek().add(token.toString());
        state = State.WHITESPACE;
    }

}
