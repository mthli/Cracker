package io.github.mthli.Cracker.Crash;

public class CrashItem {
    private String packageName;
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    private long time;
    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }

    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public CrashItem() {
        this.packageName = null;
        this.time = 0l;
        this.content = null;
    }
}
