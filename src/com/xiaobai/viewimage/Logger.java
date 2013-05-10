package com.xiaobai.viewimage;

import java.lang.reflect.Field;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

public class Logger {

	public static  boolean DBG_IMPORT = false; // 开启Debug 好友导入

	public static final boolean ENABLE_UPDATE = false; // 开启升级功能

	public static final boolean DEBUG = true;

	private static final String DEFAULT_TAG = "renren";

	private static final boolean RECYCLE_DETAIL = false;

	public static void LOG(Object tag, String message) {

		if (DEBUG) {
			if (tag instanceof String) {
				Log.v((String) tag, message);
				return;
			}
			String t = tag == null ? DEFAULT_TAG : tag.getClass()
					.getSimpleName();
			Log.v(t, message);
		}

	}

	// 打印bean对象的数据，方便调试使用 
	public static String toDataStr(Object bean) {
		if (bean != null) {
			StringBuilder sb = new StringBuilder();
			Class<?> clazz = bean.getClass();
			Field[] fields = clazz.getDeclaredFields();
			sb.append(clazz.getSimpleName());
			sb.append("[");
			for (Field f : fields) {
				sb.append(f.getName());
				sb.append(" = ");
				try {
					f.setAccessible(true);
					sb.append(String.valueOf(f.get(bean)));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				sb.append(",");
			}
			if (sb.lastIndexOf(",") == sb.length() - 1) {
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append("]");
			return sb.toString();
		}
		return "";
	}

	public static void recycleDebugLoggger(Bitmap bitmap, String tag) {

		if (DEBUG) {
			Log.i("hecao_r",
					"=====" + (bitmap == null ? "null" : bitmap.toString())
							+ " tag : " + tag);
			if (RECYCLE_DETAIL) {
				try {
					throw new RuntimeException();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void d(boolean debug, String msg) {
		if (debug) {
			Log.v(DEFAULT_TAG, msg);
		}
	}

	// /////////////////////////////////////////////////

	public static final String CFTAG = "t2_cf";
	public final static String STATISTICS = "statistics";
	public final static String EMOTION = "emotion";
	public final static String IMPORT_FRIEND = "import_friend";
	public static final String SEAL = "seal";

	// public final static String SEND_PHOTO = "send_photo";
	// public final static String SEND_TEXT = "send_text";
	// public final static String SEND_VOICE = "send_voice";
	// public final static String GET_VOICE = "get_voice";
	// public final static String RECEVICE_MESSAGE = "recevice_message";
	// public final static String INPUT_BAR = "input_bar";
	// public final static String PLAY_VOICE = "play_voice";
	// public final static String RECORD = "record";

	public static void logd(String str) {
		if (DEBUG) {
			Log.i(CFTAG, getTAG() + "---" + str);
		}
	}

	public static void logd(String tag, String str) {
		if (DEBUG) {
			Log.i(tag, getTAG() + "---" + str);
		}
	}

	public static void errord(String str) {
		if (DEBUG) {
			Log.e(CFTAG, getTAG() + "---" + str);
		}
	}

	public static void errord(String tag, String str) {
		if (DEBUG) {
			Log.e(tag, getTAG() + "---" + str);
		}
	}

	public static void mark() {
		if (DEBUG) {
			Log.w(CFTAG, getTAG());
		}
	}

	public static void mark(String tag, String str) {
		if (DEBUG) {
			Log.w(tag, getTAG() + "---" + str);
		}
	}

	public static void traces() {
		if (DEBUG) {
			// StackTraceElement stack[] = (new Throwable()).getStackTrace();
			StackTraceElement stacks[] = Thread.currentThread().getStackTrace();
			StringBuilder sb = new StringBuilder();
			if (stacks != null) {
				StackTraceElement ste = stacks[3];
				sb.append(ste.getClassName() + "." + ste.getMethodName()
						+ "#line=" + ste.getLineNumber() + "的调用：\n");
				for (int i = 4; i < stacks.length && i < 15; i++) {
					ste = stacks[i];
					sb.append((i - 4) + "--" + ste.getClassName() + "."
							+ ste.getMethodName() + "(...)#line:"
							+ ste.getLineNumber() + "\n");
				}
			}
			Log.w(CFTAG, getTAG() + "--" + sb.toString());
		}

	}

	public static void traces(String tag) {
		if (DEBUG) {
			// StackTraceElement stack[] = (new Throwable()).getStackTrace();
			StackTraceElement stacks[] = Thread.currentThread().getStackTrace();
			StringBuilder sb = new StringBuilder();
			if (stacks != null) {
				StackTraceElement ste = stacks[3];
				sb.append(ste.getClassName() + "." + ste.getMethodName()
						+ "#line=" + ste.getLineNumber() + "的调用：\n");
				for (int i = 4; i < stacks.length && i < 15; i++) {
					ste = stacks[i];
					sb.append((i - 4) + "--" + ste.getClassName() + "."
							+ ste.getMethodName() + "(...)#line:"
							+ ste.getLineNumber() + "\n");
				}
			}
			Log.w(tag, getTAG() + "--" + sb.toString());
		}

	}

	public static String getTAG() {
		// XXX this not work with proguard, maybe we cannot get the line number
		// with a proguarded jar file.
		// I add a try/catch as a quick fixing.
		try {
			StackTraceElement stacks[] = Thread.currentThread().getStackTrace();
			StringBuilder sb = new StringBuilder();
			if (stacks != null) {
				StackTraceElement ste = stacks[4];
				sb.append(ste.getFileName().subSequence(0,
						ste.getFileName().length() - 5)
						+ "." + ste.getMethodName() + "#" + ste.getLineNumber());
			}
			return sb.toString();
		} catch (NullPointerException e) {
			return "PROGUARDED";
		}
	}

	public static void log(String str, byte[] bytes) {
		if (DEBUG) {
			StringBuilder sb = new StringBuilder();
			sb.append(str).append('=');
			sb.append('[');
			if (bytes != null) {
				for (int i = 0; i < bytes.length; i++) {
					sb.append(Integer.toHexString(bytes[i]));
					if (i != bytes.length - 1) {
						sb.append(',');
					}
				}
			}
			sb.append(']');
			Log.i(CFTAG, getTAG() + "---" + sb.toString());
		}
	}
	
	public static void log(String str, boolean[] bs) {
		if (DEBUG) {
			StringBuilder sb = new StringBuilder();
			sb.append(str).append('=');
			sb.append('[');
			if (bs != null) {
				for (int i = 0; i < bs.length; i++) {
					sb.append(bs[i]);
					if (i != bs.length - 1) {
						sb.append(',');
					}
				}
			}
			sb.append(']');
			Log.i(CFTAG, getTAG() + "---" + sb.toString());
		}
	}
	
	public static void errord(String str, boolean[] bs) {
		if (DEBUG) {
			StringBuilder sb = new StringBuilder();
			sb.append(str).append('=');
			sb.append('[');
			if (bs != null) {
				for (int i = 0; i < bs.length; i++) {
					sb.append(bs[i]);
					if (i != bs.length - 1) {
						sb.append(',');
					}
				}
			}
			sb.append(']');
			Log.e(CFTAG, getTAG() + "---" + sb.toString());
		}
	}

	public static void log(String str, short[] shorts) {
		if (DEBUG) {
			StackTraceElement stacks[] = Thread.currentThread().getStackTrace();
			StringBuilder sb = new StringBuilder();
			if (stacks != null) {
				StackTraceElement ste = stacks[3];
				sb.append(ste.getFileName() + "." + ste.getMethodName() + "#"
						+ ste.getLineNumber());
			}
			String tmpTAG = sb.toString();
			sb = new StringBuilder();
			sb.append(str).append('=');
			sb.append('[');
			if (shorts != null) {
				for (int i = 0; i < shorts.length; i++) {
					// sb.append(Integer.toHexString(shorts[i]));
					sb.append(shorts[i]);
					if (i != shorts.length - 1) {
						sb.append(',');
					}
				}
			}
			sb.append(']');
			Log.i(CFTAG, tmpTAG + "---" + sb.toString());
		}
	}

	public static void log(String str, int[] ints) {
		if (DEBUG) {
			StackTraceElement stacks[] = Thread.currentThread().getStackTrace();
			StringBuilder sb = new StringBuilder();
			if (stacks != null) {
				StackTraceElement ste = stacks[3];
				sb.append(ste.getFileName() + "." + ste.getMethodName() + "#"
						+ ste.getLineNumber());
			}
			String tmpTAG = sb.toString();
			sb = new StringBuilder();
			sb.append(str).append('=');
			sb.append('[');
			if (ints != null) {
				for (int i = 0; i < ints.length; i++) {
					// sb.append(Integer.toHexString(shorts[i]));
					sb.append(ints[i]);
					if (i != ints.length - 1) {
						sb.append(',');
					}
				}
			}
			sb.append(']');
			Log.i(CFTAG, tmpTAG + "---" + sb.toString());
		}
	}

	public static void log(String str, String[] strary) {
		if (DEBUG) {
			StackTraceElement stacks[] = Thread.currentThread().getStackTrace();
			StringBuilder sb = new StringBuilder();
			if (stacks != null) {
				StackTraceElement ste = stacks[3];
				sb.append(ste.getFileName() + "." + ste.getMethodName() + "#"
						+ ste.getLineNumber());
			}
			String tmpTAG = sb.toString();
			sb = new StringBuilder();
			sb.append(str).append('=');
			sb.append('[');
			if (str != null) {
				for (int i = 0; i < strary.length; i++) {
					// sb.append(Integer.toHexString(shorts[i]));
					sb.append(strary[i]);
					if (i != strary.length - 1) {
						sb.append(',');
					}
				}
			}
			sb.append(']');
			Log.i(CFTAG, tmpTAG + "---" + sb.toString());
		}
	}
	
	public static void errord(String str, String[] strary) {
		if (DEBUG) {
			StackTraceElement stacks[] = Thread.currentThread().getStackTrace();
			StringBuilder sb = new StringBuilder();
			if (stacks != null) {
				StackTraceElement ste = stacks[3];
				sb.append(ste.getFileName() + "." + ste.getMethodName() + "#"
						+ ste.getLineNumber());
			}
			String tmpTAG = sb.toString();
			sb = new StringBuilder();
			sb.append(str).append('=');
			sb.append('[');
			if (str != null) {
				for (int i = 0; i < strary.length; i++) {
					// sb.append(Integer.toHexString(shorts[i]));
					sb.append(strary[i]);
					if (i != strary.length - 1) {
						sb.append(',');
					}
				}
			}
			sb.append(']');
			Log.e(CFTAG, tmpTAG + "---" + sb.toString());
		}
	}

	public static void log(String str, List list) {
		if (DEBUG) {
			StackTraceElement stacks[] = Thread.currentThread().getStackTrace();
			StringBuilder sb = new StringBuilder();
			if (stacks != null) {
				StackTraceElement ste = stacks[3];
				sb.append(ste.getFileName() + "." + ste.getMethodName() + "#"
						+ ste.getLineNumber());
			}
			String tmpTAG = sb.toString();
			sb = new StringBuilder();
			sb.append(str).append('=');
			sb.append('[');
			if (list != null) {
				int size = list.size();
				for (int i = 0; i < size; i++) {
					sb.append(list.get(i).toString());
					if (i != size - 1) {
						sb.append(',');
					}
				}
			}
			sb.append(']');
			Log.i(CFTAG, tmpTAG + "---" + sb.toString());
		}
	}

	@SuppressLint("NewApi")
	public static void logStrictModeThread() {
		if (DEBUG && Build.VERSION.SDK_INT >= 10) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectAll().penaltyLog().build());
		}
	}

}
