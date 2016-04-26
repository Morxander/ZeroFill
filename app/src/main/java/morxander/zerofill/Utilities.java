package morxander.zerofill;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by morxander on 4/25/16.
 */
public class Utilities {

    // Get internal (data partition) free space
    // This will match what's shown in System Settings > Storage for
    // Internal Space, when you subtract Total - Used
    public static long getFreeInternalMemory() {
        return getFreeMemory(Environment.getDataDirectory());
    }

    // Get external (SDCARD) free space
    public static long getFreeExternalMemory() {
        return getFreeMemory(Environment.getExternalStorageDirectory());
    }

    // Get Android OS (system partition) free space
    public static long getFreeSystemMemory() {
        return getFreeMemory(Environment.getRootDirectory());
    }

    // Get free space for provided path
    // Note that this will throw IllegalArgumentException for invalid paths
    public static long getFreeMemory(File path) {
        StatFs stats = new StatFs(path.getAbsolutePath());
        return (long) stats.getAvailableBlocks() * stats.getBlockSize();
    }
}
