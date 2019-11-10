
public class CrashInfo {
    private boolean crashed;
    private boolean groundCrashed;

    public CrashInfo(boolean crashed, boolean groundCrashed) {
        this.crashed = crashed;
        this.groundCrashed = groundCrashed;
    }


    public boolean isCrashed() {
        return crashed;
    }

    public boolean isGroundCrashed() {
        return groundCrashed;
    }
}
