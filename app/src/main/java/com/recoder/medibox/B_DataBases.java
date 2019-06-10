package com.recoder.medibox;

import android.provider.BaseColumns;

public final class B_DataBases {

    public static final class CreateDB implements BaseColumns {
        public static final String PACK_IMG = "_packimg";
        public static final String NAME = "_name";
        public static final String EFFECT = "_effect";
        public static final String CHECK = "_check";
        public static final String DRUG_IMG = "_drugimg";
        public static final String CODE = "_code";
        public static final String DATE = "_date";
        public static final String MEMO = "_memo";
        public static final String D_EFFECT = "_deffect";

        public static final String _TABLENAME0 = "usertable";

        public static final String _CREATE0 = "create table if not exists " + _TABLENAME0 + "("
                + _ID + " integer primary key autoincrement, "
                + PACK_IMG + " text  , "
                + NAME + " text  , "
                + EFFECT + " text  , "
                + CHECK + " text  , "
                + DRUG_IMG + " text  , "
                + CODE + " text  , "
                + DATE + " text  , "
                + MEMO + " text  , "
                + D_EFFECT + " text );";

    }
}