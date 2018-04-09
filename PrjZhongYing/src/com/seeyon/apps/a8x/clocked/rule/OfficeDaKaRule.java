package com.seeyon.apps.a8x.clocked.rule;

import java.util.List;

import com.seeyon.apps.a8x.clocked.entity.OfficeDaKaRecord;
import com.seeyon.apps.a8x.clocked.entity.ClockedResult;
import com.seeyon.apps.a8x.common.util.UtilDateTime;

public class OfficeDaKaRule {

	public static void attachWithDaKa(ClockedResult theReslut, List<OfficeDaKaRecord> theList) {
		OfficeDaKaRecord tmpClockedRecord = getClockedRecordWithDaKa(theReslut, theList);
		attachWithDaKaOne(theReslut, tmpClockedRecord);
	}

	/**
	 * 某人某天的打卡情况---仅仅根据打卡记录--实际打卡情况，需要根据业务数据进一步判定
	 * 
	 * @param theReslut
	 * @param theClockedRecord
	 */
	public static void attachWithDaKaOne(ClockedResult theReslut, OfficeDaKaRecord theClockedRecord) {
		// ================没有打卡记录=======================================
		if (theClockedRecord == null) {
			theReslut.setDaKaTimeLength(-1*theReslut.getStandWorkTimeLength());
			theReslut.setKuangGong(1);
			// 退出程序
			return;
		}
		// =================有打卡记录=======================================
		// 打卡开始时间
		theReslut.setClockBeginTime(theClockedRecord.getDaKaBeginTime());
		// 打卡结束时间
		theReslut.setClockEndTime(theClockedRecord.getDaKaEndTime(theReslut.isDelayOneDay()));
		if (theClockedRecord.getDaKaBeginTime() == null || theClockedRecord.getDaKaEndTime(theReslut.isDelayOneDay()) == null) {
			theReslut.setDaKaTimeLength(-1*theReslut.getStandWorkTimeLength());
			// 退出程序
			return;
		}
		double workHours = WorkHourRule.getOfficeWorkHoursBySameDay(theClockedRecord.getDaKaBeginTime(), theClockedRecord.getDaKaEndTime(theReslut.isDelayOneDay()), theReslut);
		theReslut.setDaKaTimeLength(workHours);
		/*
		// 迟到
		long chiDaoValue = 0;
		if (theClockedRecord.getBeginTime().after(theReslut.getStandBeginTime())) {
			chiDaoValue = UtilDateTime.getMinuteBetween(theReslut.getStandBeginTime(), theClockedRecord.getBeginTime());
		}
		// 早退
		long zaoTuiValue = 0;
		if (theClockedRecord.getEndTime().before(theReslut.getStandEndTime())) {
			zaoTuiValue = UtilDateTime.getMinuteBetween(theClockedRecord.getEndTime(), theReslut.getStandEndTime());
		}
		if (chiDaoValue > 15 || zaoTuiValue > 15) {
			theReslut.setKuangGong(1);
		} else {
			if (chiDaoValue > 0) {
				theReslut.setChiDao(1);
			}
			if (zaoTuiValue > 0) {
				theReslut.setZaoTui(1);
			}
		}
		*/
	}

	private static OfficeDaKaRecord getClockedRecordWithDaKa(ClockedResult theClockedResult, List<OfficeDaKaRecord> clockedRecordList) {
		OfficeDaKaRecord temp = null;
		int size = clockedRecordList == null ? 0 : clockedRecordList.size();
		boolean flag = false;
		for (int i = 0; i < size; i++) {
			flag = false;
			temp = clockedRecordList.get(i);
			if (temp.getMemberID().equals(theClockedResult.getMemberID())) {
				if (temp.getClockDate() != null && UtilDateTime.getDaysBetween(theClockedResult.getClockDate(), temp.getClockDate()) == 0) {
					flag = true;
					break;
				}
			}
		}
		if (flag) {
			return temp;
		} else {
			return null;
		}
	}

}
