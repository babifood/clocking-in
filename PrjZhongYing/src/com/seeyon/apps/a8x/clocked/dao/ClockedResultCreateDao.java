package com.seeyon.apps.a8x.clocked.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.seeyon.apps.a8x.clocked.entity.ClockedResult;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.RowSetter;

/*
 * 用于ClockedDataInitService
 */
public class ClockedResultCreateDao extends JdbcDAO implements RowSetter<ClockedResult> {

	public ClockedResultCreateDao() {
		super();
	}

	public ClockedResultCreateDao(DAOContext context) {
		super(context);
	}

	public void create(List<ClockedResult> theModelList) throws DAOExcuteException {
		StringBuffer tmpInsertSQL = new StringBuffer(500);
		tmpInsertSQL.append("insert into A8XCLOCKEDRESULT(");
		tmpInsertSQL.append("ID,STATE,START_MEMBER_ID,START_DATE,APPROVE_MEMBER_ID");
		tmpInsertSQL.append(",APPROVE_DATE,FINISHEDFLAG,RATIFYFLAG,RATIFY_MEMBER_ID,RATIFY_DATE");
		tmpInsertSQL.append(",MODIFY_MEMBER_ID,MODIFY_DATE,YEAR,MONTH,MEMBERID");
		tmpInsertSQL.append(",WORKNUM,DEPT,ORGAN,CLOCKDATE,WEEK");
		tmpInsertSQL.append(",CLOCKFLAG,CHIDAO,ZAOTUI,KUANGGONG,DAKATIMELENGTH");
		tmpInsertSQL.append(",WORKHOURS,QUEQIN,YIDONG,CHUCHA,QINGJIA");
		tmpInsertSQL.append(",SHIJIA,NIANJIA,HUNJIA,SANGJIA,BINGJIA");
		tmpInsertSQL.append(",OTHERQINGJIA,PEIXUNJIA,CHANJIA,PEICHANJIA,TIAOXIU");
		tmpInsertSQL.append(",JIABAN,CANBU,BEGINTIME,ENDTIME,CLOCKBEGINTIME");
		tmpInsertSQL.append(",CLOCKENDTIME,EVENTBEGINTIME,EVENTENDTIME,DAKALOCATION,DAKATYPE,STANDWORKTIMELENGTH");
		tmpInsertSQL.append(") values (");
		tmpInsertSQL.append("?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?,?,?");
		tmpInsertSQL.append(")");
		super.create(theModelList, tmpInsertSQL.toString(), this);
	}

	public void update(List<ClockedResult> theModelList) throws DAOExcuteException {
		StringBuffer tmpUpdateByKeySQL = new StringBuffer(500);
		tmpUpdateByKeySQL.append("update A8XCLOCKEDRESULT set ");
		tmpUpdateByKeySQL.append("MODIFY_MEMBER_ID=?,MODIFY_DATE=?,YEAR=?,MONTH=?,MEMBERID=?");
		tmpUpdateByKeySQL.append(",WORKNUM=?,DEPT=?,ORGAN=?,CLOCKDATE=?,WEEK=?");
		tmpUpdateByKeySQL.append(",CLOCKFLAG=?,CHIDAO=?,ZAOTUI=?,KUANGGONG=?,DAKATIMELENGTH=?");
		tmpUpdateByKeySQL.append(",WORKHOURS=?,QUEQIN=?,YIDONG=?,CHUCHA=?,QINGJIA=?");
		tmpUpdateByKeySQL.append(",SHIJIA=?,NIANJIA=?,HUNJIA=?,SANGJIA=?,BINGJIA=?");
		tmpUpdateByKeySQL.append(",OTHERQINGJIA=?,PEIXUNJIA=?,CHANJIA=?,PEICHANJIA=?,TIAOXIU=?");
		tmpUpdateByKeySQL.append(",JIABAN=?,CANBU=?,BEGINTIME=?,ENDTIME=?,CLOCKBEGINTIME=?");
		tmpUpdateByKeySQL.append(",CLOCKENDTIME=?,EVENTBEGINTIME=?,EVENTENDTIME=?,DAKALOCATION=?,DAKATYPE=?,STANDWORKTIMELENGTH=?");
		tmpUpdateByKeySQL.append(" where id=?");
		super.update(theModelList, tmpUpdateByKeySQL.toString(), this);
	}

	/**
	 * 适配执行参数
	 * 
	 * @param pstmt
	 *            PreparedStatement
	 * @param theModel
	 *            IValueObject
	 * @throws SQLException
	 * @return int
	 */
	public int fillStatement(PreparedStatement pstmt, ClockedResult theModel) throws SQLException {
		ClockedResult tmpModel = theModel;
		String temp = null;
		int index = 0;
		if (actionType == INSERT) {
			// id;
			index++;
			pstmt.setLong(index, tmpModel.getId());
			// 审核状态;
			index++;
			pstmt.setInt(index, tmpModel.getState());
			// 发起人;
			index++;
			pstmt.setLong(index, tmpModel.getStart_member_id());
			// 发起时间;
			index++;
			if (tmpModel.getStart_date() == null) {
				pstmt.setNull(index, java.sql.Types.NULL);
			} else {
				pstmt.setTimestamp(index, tmpModel.getStart_date());
			}
			// 审核人;
			index++;
			pstmt.setLong(index, tmpModel.getApprove_member_id());
			// 审核时间;
			index++;
			if (tmpModel.getApprove_date() == null) {
				pstmt.setNull(index, java.sql.Types.NULL);
			} else {
				pstmt.setTimestamp(index, tmpModel.getApprove_date());
			}
			// 流程状态;
			index++;
			pstmt.setInt(index, tmpModel.getFinishedflag());
			// 核定状态;
			index++;
			pstmt.setInt(index, tmpModel.getRatifyflag());
			// 核定人;
			index++;
			pstmt.setLong(index, tmpModel.getRatify_member_id());
			// 核定时间;
			index++;
			if (tmpModel.getRatify_date() == null) {
				pstmt.setNull(index, java.sql.Types.NULL);
			} else {
				pstmt.setTimestamp(index, tmpModel.getRatify_date());
			}
		}
		// 修改者标识;
		index++;
		pstmt.setLong(index, tmpModel.getModify_member_id());
		// 修改日期;
		index++;
		if (tmpModel.getModify_date() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			pstmt.setTimestamp(index, tmpModel.getModify_date());
		}
		// 年度;
		index++;
		pstmt.setInt(index, tmpModel.getYear());
		// 月份;
		index++;
		pstmt.setInt(index, tmpModel.getMonth());
		// 姓名;
		index++;
		if (tmpModel.getMemberID() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getMemberID();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 工号;
		index++;
		if (tmpModel.getWorkNum() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getWorkNum();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 部门;
		index++;
		if (tmpModel.getDept() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getDept();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 单位机构;
		index++;
		if (tmpModel.getOrgan() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getOrgan();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 日期;
		index++;
		if (tmpModel.getClockDate() == null) {
			pstmt.setNull(index, java.sql.Types.DATE);
		} else {
			pstmt.setDate(index, tmpModel.getClockDate());
		}
		// 星期;
		index++;
		if (tmpModel.getWeek() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getWeek();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 考勤标志;
		index++;
		pstmt.setInt(index, tmpModel.getClockFlag());
		// 迟到;
		index++;
		pstmt.setInt(index, tmpModel.getChiDao());
		// 早退;
		index++;
		pstmt.setInt(index, tmpModel.getZaoTui());
		// 旷工;
		index++;
		pstmt.setInt(index, tmpModel.getKuangGong());
		// 打卡原始时长;
		index++;
		pstmt.setDouble(index, tmpModel.getDaKaTimeLength());
		// 工作时长;
		index++;
		pstmt.setDouble(index, tmpModel.getWorkHours());
		// 缺勤小时数;
		index++;
		pstmt.setDouble(index, tmpModel.getQueQin());
		// 异动小时数;
		index++;
		pstmt.setDouble(index, tmpModel.getYiDong());
		// 出差小时数;
		index++;
		pstmt.setDouble(index, tmpModel.getChuCha());
		// 请假小时数;
		index++;
		pstmt.setDouble(index, tmpModel.getQingJia());
		// 事假;
		index++;
		pstmt.setDouble(index, tmpModel.getShiJia());
		// 年假;
		index++;
		pstmt.setDouble(index, tmpModel.getNianJia());
		// 婚假;
		index++;
		pstmt.setDouble(index, tmpModel.getHunJia());
		// 丧假;
		index++;
		pstmt.setDouble(index, tmpModel.getSangJia());
		// 病假;
		index++;
		pstmt.setDouble(index, tmpModel.getBingJia());
		// 其它请假;
		index++;
		pstmt.setDouble(index, tmpModel.getOtherQingJia());
		// 培训假;
		index++;
		pstmt.setDouble(index, tmpModel.getPeiXunJia());
		// 产假;
		index++;
		pstmt.setDouble(index, tmpModel.getChanJia());
		// 陪产假;
		index++;
		pstmt.setDouble(index, tmpModel.getPeiChanJia());
		// 调休;
		index++;
		pstmt.setDouble(index, tmpModel.getTiaoXiu());
		// 加班小时数;
		index++;
		pstmt.setDouble(index, tmpModel.getJiaBan());
		// 餐补个数;
		index++;
		pstmt.setInt(index, tmpModel.getCanBu());
		// 标准上班时间;
		index++;
		if (tmpModel.getBeginTime() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getBeginTime();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 标准下班时间;
		index++;
		if (tmpModel.getEndTime() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getEndTime();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 打卡起始时间;
		index++;
		if (tmpModel.getClockBeginTime() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			pstmt.setTimestamp(index, tmpModel.getClockBeginTime());
		}
		// 打卡结束时间;
		index++;
		if (tmpModel.getClockEndTime() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			pstmt.setTimestamp(index, tmpModel.getClockEndTime());
		}
		// 业务开始时间;
		index++;
		if (tmpModel.getEventBeginTime() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			pstmt.setTimestamp(index, tmpModel.getEventBeginTime());
		}
		// 业务结束时间;
		index++;
		if (tmpModel.getEventEndTime() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			pstmt.setTimestamp(index, tmpModel.getEventEndTime());
		}

		// 打卡地点;
		index++;
		if (tmpModel.getDaKaLocation() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getDaKaLocation();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 考勤方式;
		index++;
		if (tmpModel.getDaKaType() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getDaKaType();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 标准工作时长
		index++;
		pstmt.setDouble(index, tmpModel.getStandWorkTimeLength());

		if (actionType == UPDATE) {
			index++;
			// id;
			pstmt.setLong(index, tmpModel.getId());
		}
		return index;
	}

}