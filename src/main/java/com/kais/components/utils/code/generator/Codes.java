package com.kais.components.utils.code.generator;

import com.sun.org.apache.bcel.internal.generic.NEW;
import sun.net.TelnetOutputStream;

public enum Codes implements Code, CallableCode {

    NEW {

        @Override
        public String toString() {
            return "new";
        }
    },
    NULL {

        @Override
        public String toString() {
            return "null";
        }
    },
    SUPER {

        @Override
        public String toString() {

            return "super";
        }

        @Override
        public SingleLineCode call(Object target, Object... parameters) {

            return Code.call(this, target, parameters);
        }
    },
    THIS {

        @Override
        public String toString() {
            return "this";
        }

        @Override
        public SingleLineCode call(Object target, Object... parameters) {

            return Code.call(this, target, parameters);
        }
    },
    SIMPLE_MESSAGE_LOCAL {

        @Override
        public String toString() {
            return "SimplateMessageLocal";
        }

        @Override
        public SingleLineCode call(Object target, Object... parameters) {

            return Code.call(this, target, parameters);
        }
    };


    @Override
    public SingleLineCode call(Object target, Object... parameters) {
        throw new UnsupportedOperationException();
    }
}
