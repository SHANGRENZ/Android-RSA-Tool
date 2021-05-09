package com.zhoushangren.rsatool.vars;

public class SQLiteInfo {
    public static final class DataBase {
        public static final String NAME = "data.db";
    }

    public static final class TablePublic {
        public static final String NAME = "rsa_pairs_public";

        public static final class Items {
            public static final String NICKNAME = "nickname";
            public static final String CONTENT = "content";
        }
    }

    public static final class TablePrivate {
        public static final String NAME = "rsa_pairs_private";

        public static final class Items {
            public static final String NICKNAME = "nickname";
            public static final String CONTENT = "content";
        }
    }
}
