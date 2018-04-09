package com.seeyon.apps.a8x.clocked.rule;

import java.util.Map;

import com.seeyon.apps.a8x.clocked.entity.ClockedResult;
import com.seeyon.apps.a8x.clocked.entity.MobileDaKaRecord2;

/**
 * 移动打卡规则
 * 
 */
public class MobileDaKaRule {

	public static void attachWithDaKa(ClockedResult theReslut, Map<String, MobileDaKaRecord2> theMap) {
		if (theReslut.getClockDate().toString().equals("2016-08-11") && theReslut.getWorkNum().equals("100177")) {
			System.out.println();
		}
		String key = theReslut.getMemberID() + theReslut.getClockDate().toString();
		MobileDaKaRecord2 tmpClockedRecord = theMap.get(key);
		attachWithDaKaOne(theReslut, tmpClockedRecord);
	}

	/**
	 * 某人某天的打卡情况---仅仅根据打卡记录--实际打卡情况，需要根据业务数据进一步判定
	 * 
	 * @param theReslut
	 * @param theClockedRecord
	 */
	public static void attachWithDaKaOne(ClockedResult theReslut, MobileDaKaRecord2 theClockedRecord) {
		// ================没有打卡记录=======================================
		if (theClockedRecord == null) {
			return;
		}
		// =================有打卡记录=======================================
		String daKaLocation = "";
		// 打卡开始时间
		if (theClockedRecord.getBegin() != null) {
			theReslut.setClockBeginTime(theClockedRecord.getBegin().getClockTime());
			daKaLocation = theClockedRecord.getBegin().getLocation();
		}
		// 打卡结束时间
		if (theClockedRecord.getEnd() != null) {
			theReslut.setClockEndTime(theClockedRecord.getEnd().getClockTime());
			if (daKaLocation.length() > 0) {
				daKaLocation = daKaLocation + "-" + theClockedRecord.getEnd().getLocation();
			}
		}
		theReslut.setDaKaLocation(daKaLocation);

		if (theClockedRecord.getBegin() != null && theClockedRecord.getEnd() != null) {
			double workHours = WorkHourRule.getMobileWorkHoursBySameDay(theClockedRecord.getBegin().getClockTime(), theClockedRecord.getEnd().getClockTime(), theReslut,true);
			theReslut.setDaKaTimeLength(workHours);
		}
	}

}
