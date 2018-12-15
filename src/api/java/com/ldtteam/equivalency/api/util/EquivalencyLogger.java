package com.ldtteam.equivalency.api.util;

import net.minecraftforge.fml.common.TracingPrintStream;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.FMLRelaunchLog;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.util.Locale;

public class EquivalencyLogger
{

    private static final Logger log;

    static {
        log = LogManager.getLogger(Constants.MOD_ID);
    }

    public static void bigWarning(String format, Object... data)
    {
        log.warn("****************************************");
        log.warn("* "+format, data);
        log.warn("****************************************");
    }

    public static void bigWarningWithStackTrace(String format, Object... data)
    {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        log.warn("****************************************");
        log.warn("* "+format, data);
        for (int i = 2; i < 8 && i < trace.length; i++)
        {
            log.warn("*  at {}{}", trace[i].toString(), i == 7 ? "..." : "");
        }
        log.warn("****************************************");
    }

    public static void log(String targetLog, Level level, String format, Object... data)
    {
        EquivalencyLogger.log(targetLog, level, format, data);
    }

    public static void log(Level level, String format, Object... data)
    {
        EquivalencyLogger.log(level, format, data);
    }

    public static void log(String targetLog, Level level, Throwable ex, String format, Object... data)
    {
        EquivalencyLogger.log(targetLog, level, ex, format, data);
    }

    public static void log(Level level, Throwable ex, String format, Object... data)
    {
        EquivalencyLogger.log(level, ex, format, data);
    }

    public static void severe(String format, Object... data)
    {
        log(Level.ERROR, format, data);
    }

    public static void warning(String format, Object... data)
    {
        log(Level.WARN, format, data);
    }

    public static void info(String format, Object... data)
    {
        log(Level.INFO, format, data);
    }

    public static void fine(String format, Object... data)
    {
        log(Level.DEBUG, format, data);
    }

    public static void finer(String format, Object... data)
    {
        log(Level.TRACE, format, data);
    }

    public static Logger getLogger()
    {
        return log;
    }
}
