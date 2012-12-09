package cpw.mods.fml.relauncher;

public interface IDownloadDisplay
{
    void resetProgress(int var1);

    void setPokeThread(Thread var1);

    void updateProgress(int var1);

    boolean shouldStopIt();

    void updateProgressString(String var1, Object ... var2);

    Object makeDialog();

    void makeHeadless();
}
