package com.seeyon.apps.a8x.clocked.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.seeyon.apps.a8x.clocked.entity.ClockedResult;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.RowSetter;

public class ClockedResultUpdateDao extends JdbcDAO implements RowSetter<ClockedResult> {

	public ClockedResultUpdateDao() {
		super();
	}

	public ClockedResultUpdateDao(DAOContext context) {
		super(context);
	}

	public void update(List<ClockedResult> theModelList) throws DAOExcuteException {
		StringBuffer tmpUpdateByKeySQL = new StringBuffer(500);
		tmpUpdateByKeySQL.append("update A8XCLOCKEDRESULT set ");
		tmpUpdateByKeySQL.append("MODIFY_MEMBER_ID=?,MODIFY_DATE=?,CLOCKBEGINTIME=?,CLOCKENDTIME=?,CHIDAO=?");
		tmpUpdateByKeySQL.append(",ZAOTUI=?,KUANGGONG=?,DAKATIMELENGTH=?,WORKHOURS=?,QUEQIN=?");
		tmpUpdateByKeySQL.append(",YIDONG=?,CHUCHA=?,QINGJIA=?,SHIJIA=?,NIANJIA=?");
		tmpUpdateByKeySQL.append(",HUNJIA=?,SANGJIA=?,BINGJIA=?,OTHERQINGJIA=?,PEIXUNJIA=?");
		tmpUpdateByKeySQL.append(",CHANJIA=?,PEICHANJIA=?,TIAOXIU=?,JIABAN=?,CANBU=?");
		tmpUpdateByKeySQL.append(",EVENTBEGINTIME=?,EVENTENDTIME=?,DAKALOCATION=?");
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
		int index = 0;
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
		String daKaLocation = tmpModel.getDaKaLocation();
		pstmt.setString(index, daKaLocation==null ? "" : daKaLocation.trim());

		index++;
		// id;
		pstmt.setLong(index, tmpModel.getId());
		return index;
	}

}