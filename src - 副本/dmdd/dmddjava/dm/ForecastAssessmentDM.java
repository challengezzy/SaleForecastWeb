package dmdd.dmddjava.dm;

import java.util.ArrayList;
import java.util.List;

import com.cool.common.constant.DMOConst;
import com.cool.common.vo.HashVO;

import dmdd.dmddjava.common.utils.UtilUUID;
import dmdd.dmddjava.dataaccess.aidobject.ASumCommonData;

public class ForecastAssessmentDM extends BasicDM
{

	public List<ASumCommonData> getForecastAssessment(String detailIds,int beginPeriod,int endPeriod,List<Long> listbizdataids) throws Exception
	{
		logger.info("getForecastAssessment begin...");
		String tag = UtilUUID.uuid();

		List<ASumCommonData> rstList = new ArrayList<ASumCommonData>();
		try
		{
			insertProORg(detailIds,tag);
			StringBuffer sqlStr=new StringBuffer();
			sqlStr.append("select sum(A.VALUE) as y0_, A.PERIOD as y1_,  A.BIZDATAID as y2_  from ForecastAssessmentData A,QUERY_PRODORG B where " +
					" A.PERIOD>="+beginPeriod+" and A.PERIOD<="+endPeriod+" and A.BIZDATAID ="+listbizdataids.get(0)+" and  A.Productid = B.Productid and A.Organizationid = B.Organizationid and QUERYTAG='"+tag+"' group by A.PERIOD, A.BIZDATAID");
			for(int i=1;i<listbizdataids.size();i++)
			{
				sqlStr.append(" union all select sum(A.VALUE) as y0_, A.PERIOD as y1_,  A.BIZDATAID as y2_  from ForecastAssessmentData A,QUERY_PRODORG B where " +
					" A.PERIOD>="+beginPeriod+" and A.PERIOD<="+endPeriod+" and A.BIZDATAID ="+listbizdataids.get(i)+" and  A.Productid = B.Productid and A.Organizationid = B.Organizationid and QUERYTAG='"+tag+"' group by A.PERIOD, A.BIZDATAID");
			}
			
			
			HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sqlStr.toString()); 
	
			for(int i=0;i<vos.length;i++){
				HashVO vo = vos[i];
				
				ASumCommonData aSumData = new ASumCommonData();
				aSumData.setValue( vo.getLongValue(0) );
				aSumData.setPeriod( vo.getIntegerValue(1) );		
				aSumData.setBizDataId( vo.getLongValue(2) );
				
				rstList.add( aSumData );
				
			}		
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			deleteProOrg(tag);
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		
		logger.info("getForecastAssessment end...");
		return rstList;
	}
}
