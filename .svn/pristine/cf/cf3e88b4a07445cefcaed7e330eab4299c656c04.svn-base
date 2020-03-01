package dmdd.dmddjava.service.replenish;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.cool.common.constant.DMOConst;
import com.cool.common.logging.CoolLogger;
import com.cool.common.vo.HashVO;
import com.cool.dbaccess.CommDMO;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import dmdd.dmddjava.dataaccess.bizobject.BMetaEndingInvertory;

/**
 * 期末库存配置
 * 
 * @author zxc
 * 
 */
public class MetaTermEndService {

	private Logger logger = CoolLogger.getLogger(this.getClass());

	private CommDMO dmo = null;

	public MetaTermEndService(CommDMO dmo) {
		this.dmo = dmo;
	}

	/** memory cache */
	private LoadingCache<String, BMetaEndingInvertory> termEndCache = CacheBuilder
			.newBuilder().expireAfterAccess(6, TimeUnit.HOURS)
			.build(new CacheLoader<String, BMetaEndingInvertory>() {
				@Override
				public BMetaEndingInvertory load(String key) throws Exception {
					return getMetaEndingByCode(key);
				}
			});

	public BMetaEndingInvertory getMetaEndingInvertory(String code) {
		try {
			return termEndCache.get(code);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	public BMetaEndingInvertory getMetaEndingByCode(String code) {
		BMetaEndingInvertory metaEndingInvertory = new BMetaEndingInvertory();
		String sql = "select ID,CODE,NAME,WEIGHT_COEFFICIENT from META_ENDING_INVENTORY where CODE=?";
		try {
			HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql,
					code);
			if (vos == null || vos.length < 1 || vos.length > 1) // 查询不到或者查询结果超过1个，都视为无效
				return null;
			else if (vos.length == 1) {
				HashVO vo = vos[0];
				metaEndingInvertory
						.setId(vo.getLognValue("ID"))
						.setName(vo.getStringValue("NAME"))
						.setCode(vo.getStringValue("CODE"))
						.setWeightCoefficient(
								vo.getLongValue("WEIGHT_COEFFICIENT"));
			}
		} catch (Exception e) {
			logger.error("根据期末库存编码查询期末库存类型异常:" + e.toString());
		}
		return metaEndingInvertory;
	}

}
