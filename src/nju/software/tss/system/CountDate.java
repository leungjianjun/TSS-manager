package nju.software.tss.system;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 该类用于数据库日期的运算
 * @author softwware
 * @deprecated 暂时先不用，这个类获得详细的内存，cpu信息等，不符合这个软件的需求
 *
 */
public class CountDate {

	/**
	 * @param args
	 */
	private String currentMonth;
	private String nextMonth;
	private String currentYear;
	private String nextYear;
	private String currentYM;
	private String currentYMDHMS;
	private Date currentYMDHMSs;
	private String nextYMDHMS;

	/* 根据系统日期获得当月 */
	public String getCurrentMonth() {
		Calendar rightNow = Calendar.getInstance();
		if (String.valueOf(rightNow.get(Calendar.MONTH) + 1).length() > 1) {
			this.currentMonth = String
					.valueOf(rightNow.get(Calendar.MONTH) + 1);
			return currentMonth;
		}

		this.currentMonth = "0"
				+ String.valueOf(rightNow.get(Calendar.MONTH) + 1);

		return currentMonth;
	}

	/* 根据系统日期获得当年 */
	public String getCurrentYear() {
		Calendar rightNow = Calendar.getInstance();
		this.currentYear = String.valueOf(rightNow.get(Calendar.YEAR));
		return currentYear;
	}

	/* 根据系统日期获得数据库日期 */
	public String getCurrentYMDHMS() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp t = new Timestamp(Calendar.getInstance().getTimeInMillis());
		this.currentYMDHMS = sdf.format(t);
		return currentYMDHMS;
	}

	public Date getCurrentYMDHMSs() {
		Timestamp t = new Timestamp(Calendar.getInstance().getTimeInMillis());
		currentYMDHMSs = t;
		return currentYMDHMSs;
	}

	/* 根据系统日期获得下个月 */
	public String getNextMonth() {
		Calendar rightNow = Calendar.getInstance();
		if ((rightNow.get(Calendar.MONTH) + 1) == 12) {
			this.nextMonth = "01";
			this.nextYear = String.valueOf(rightNow.get(Calendar.YEAR) + 1);
			return nextMonth;
		}
		if (String.valueOf(rightNow.get(Calendar.MONTH) + 2).length() > 1) {
			this.nextMonth = String.valueOf(rightNow.get(Calendar.MONTH) + 2);
			return nextMonth;
		}

		this.nextMonth = "0" + String.valueOf(rightNow.get(Calendar.MONTH) + 2);

		return nextMonth;
	}

	/* 根据系统日期获得下个月的年分 */
	public String getNextYear() {
		Calendar rightNow = Calendar.getInstance();
		if ((rightNow.get(Calendar.MONTH) + 1) == 12) {
			this.nextMonth = "01";
			this.nextYear = String.valueOf(rightNow.get(Calendar.YEAR) + 1);
			return nextYear;
		}
		this.nextYear = String.valueOf(rightNow.get(Calendar.YEAR));
		return nextYear;
	}

	/* 根据系统日期获得下个月的数据库日期 */
	public String getNextYMDHMS() {
		nextYear = getNextYear();
		nextMonth = getNextMonth();
		this.nextYMDHMS = nextYear + "-" + nextMonth + "-" + "01 00:00:00";
		return nextYMDHMS;
	}

	/* 根据给定的月参数获得当月日期 */
	public String getCurrentMonth(int pcurrentMonth) {
		if (pcurrentMonth % 12 > 1 || (pcurrentMonth % 12 == 1)) {
			if (String.valueOf((pcurrentMonth % 12)).length() > 1) {
				this.currentMonth = String.valueOf((pcurrentMonth % 12));

			} else {
				this.currentMonth = "0" + String.valueOf((pcurrentMonth % 12));
				return currentMonth;
			}
		}
		if (String.valueOf(pcurrentMonth).length() > 1) {
			this.currentMonth = String.valueOf(pcurrentMonth);
		} else {
			this.currentMonth = "0" + String.valueOf(pcurrentMonth);
		}
		return currentMonth;

	}

	/* 根据给定的月参数和年参数获得当年 */
	public String getCurrentYear(int pcurrentMonth, int pcurrentYear) {
		if (pcurrentMonth % 12 > 1 || (pcurrentMonth % 12 == 1)) {
			if (String.valueOf((pcurrentMonth % 12)).length() > 1) {
				this.currentMonth = String.valueOf((pcurrentMonth % 12));

			} else {
				this.currentMonth = "0" + String.valueOf((pcurrentMonth % 12));
			}
			this.currentYear = String
					.valueOf(pcurrentMonth / 12 + pcurrentYear);
			return currentYear;
		}
		return this.currentYear = String.valueOf(pcurrentYear);
	}

	/* 根据给定的月参数和年参数获得数据库日期 */
	public String getCurrentYMDHMS(int pcurrentMonth, int pcurrentYear) {
		if ((pcurrentMonth % 12 > 1) || (pcurrentMonth % 12 == 1)) {
			if (String.valueOf((pcurrentMonth % 12)).length() > 1) {
				this.currentMonth = String.valueOf((pcurrentMonth % 12));

			} else {
				this.currentMonth = "0" + String.valueOf((pcurrentMonth % 12));
			}
			this.currentYear = String
					.valueOf(pcurrentMonth / 12 + pcurrentYear);
			this.currentYMDHMS = currentYear + "-" + currentMonth + "-"
					+ "01 00:00:00";
			return currentYMDHMS;
		}
		if (String.valueOf(pcurrentMonth).length() > 1) {
			this.currentMonth = String.valueOf(pcurrentMonth);
		} else {
			this.currentMonth = "0" + String.valueOf(pcurrentMonth);
		}
		this.currentYear = String.valueOf(pcurrentYear);
		this.currentYMDHMS = currentYear + "-" + currentMonth + "-"
				+ "01 00:00:00";
		return currentYMDHMS;
	}

	public Date getCurrentYMDHMSs(int pcurrentMonth, int pcurrentYear) {
		if (pcurrentMonth % 12 > 1 || (pcurrentMonth % 12 == 1)) {
			if (String.valueOf((pcurrentMonth % 12)).length() > 1) {
				this.currentMonth = String.valueOf((pcurrentMonth % 12));

			} else {
				this.currentMonth = "0" + String.valueOf((pcurrentMonth % 12));
			}
			this.currentYear = String
					.valueOf(pcurrentMonth / 12 + pcurrentYear);
			this.currentYMDHMSs = Timestamp.valueOf(currentYear + "-"
					+ currentMonth + "-" + "01 00:00:00.0");
			return currentYMDHMSs;
		}
		if (String.valueOf(pcurrentMonth).length() > 1) {
			this.currentMonth = String.valueOf(pcurrentMonth);
		} else {
			this.currentMonth = "0" + String.valueOf(pcurrentMonth);
		}
		this.currentYear = String.valueOf(pcurrentYear);
		this.currentYMDHMSs = Timestamp.valueOf(currentYear + "-"
				+ currentMonth + "-" + "01 00:00:00.0");
		return currentYMDHMSs;
	}

	/* 根据给定的月参数和年参数获得下个月的月份和年份 */
	public String getNextYMDHMS(int pcurrentMonth, int pcurrentYear) {
		if ((pcurrentMonth % 12 > 1) || (pcurrentMonth % 12 == 1)) {
			if (String.valueOf((pcurrentMonth % 12)).length() > 1) {
				this.currentMonth = String.valueOf((pcurrentMonth % 12));

			} else {
				this.currentMonth = "0" + String.valueOf((pcurrentMonth % 12));

			}

			this.currentYear = String
					.valueOf(pcurrentMonth / 12 + pcurrentYear);
			if (String.valueOf(Integer.parseInt(currentMonth) + 1).length() > 1) {
				this.nextMonth = String
						.valueOf(Integer.parseInt(currentMonth) + 1);
			} else {
				this.nextMonth = "0"
						+ String.valueOf(Integer.parseInt(currentMonth) + 1);
			}
			this.nextYear = currentYear;
			this.nextYMDHMS = nextYear + "-" + nextMonth + "-" + "01 00:00:00";
			return nextYMDHMS;
		}
		if (pcurrentMonth == 12) {
			this.nextMonth = "01";
			this.nextYear = String.valueOf(pcurrentYear + 1);
			this.nextYMDHMS = nextYear + "-" + nextMonth + "-" + "01 00:00:00";
			return nextYMDHMS;
		}
		if (String.valueOf(pcurrentMonth + 1).length() > 1) {
			this.nextMonth = String.valueOf(pcurrentMonth + 1);
			this.nextYear = String.valueOf(pcurrentYear);
			this.nextYMDHMS = nextYear + "-" + nextMonth + "-" + "01 00:00:00";
			return nextYMDHMS;
		}
		this.nextMonth = "0" + String.valueOf(pcurrentMonth + 1);
		this.nextYear = String.valueOf(pcurrentYear);
		this.nextYMDHMS = nextYear + "-" + nextMonth + "-" + "01 00:00:00";
		return nextYMDHMS;
	}

	/* 根据系统日期获得下个月的年份和月份 */
	public String getCurrentYM() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		Timestamp t = new Timestamp(Calendar.getInstance().getTimeInMillis());
		this.currentYM = sdf.format(t);
		return currentYM;
	}

	/* 根据系统日期获得当月的年份和月份 */
	public String getCurrentYM(int pcurrentMonth, int pcurrentYear) {
		if (pcurrentMonth % 12 > 1 || (pcurrentMonth % 12 == 1)) {
			if (String.valueOf(pcurrentMonth % 12).length() > 1) {
				this.currentMonth = String.valueOf((pcurrentMonth % 12));

			} else {
				this.currentMonth = "0" + String.valueOf((pcurrentMonth % 12));

			}

			this.currentYear = String
					.valueOf(pcurrentMonth / 12 + pcurrentYear);
			this.currentYM = currentYear + "年" + currentMonth + "月";
			return currentYM;
		}
		if (String.valueOf(pcurrentMonth).length() > 1) {
			this.currentMonth = String.valueOf(pcurrentMonth);
		} else {
			this.currentMonth = "0" + String.valueOf(pcurrentMonth);
		}
		this.currentYear = String.valueOf(pcurrentYear);
		this.currentYM = currentYear + "年" + currentMonth + "月";
		return currentYM;
	}
	/*
	 * public static void main(String args[]){ CountDate c = new CountDate();
	 * 
	 * System.out.println(c.getCurrentYMDHMSs(12, 2029)); }
	 */
}
