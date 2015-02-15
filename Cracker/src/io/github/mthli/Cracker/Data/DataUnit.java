package io.github.mthli.Cracker.Data;

public class DataUnit {
    public static final String TABLE = "CRASH";

    public static final String PACKAGE_NAME = "PACKAGE_NAME";
    public static final String TIME = "TIME";
    public static final String CONTENT = "CONTENT";

    public static final String CREATE_SQL = "CREATE TABLE "
            + TABLE
            + " ("
            + " " + PACKAGE_NAME + " text,"
            + " " + TIME + " integer,"
            + " " + CONTENT + " text"
            + ")";
}
