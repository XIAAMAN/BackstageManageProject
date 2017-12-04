package cn.nchu.lims.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import cn.nchu.lims.domain.LifeShow;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.PageModel;
import cn.nchu.lims.util.ajax.AjaxJsonRequestDynaPageParam;
import cn.nchu.lims.util.lang.StringUtil;

public class LifeShowDynaSqlProvider {

	public String listDynaPage(final AjaxJsonRequestDynaPageParam<LifeShow> ajrdpp) {
		String sql =  new SQL() {
			{
				SELECT("*");
				FROM(Constant.LIFESHOW_TABLE);
				LifeShow lifeShow = ajrdpp.getParam();
				if(lifeShow != null) {
					if(!StringUtil.isNullOrEmpty(lifeShow.getType())) {
						WHERE("type LIKE CONCAT ('%',#{param.type},'%')");
					}
				}
				ORDER_BY("type");
			}
		}.toString();
		
		PageModel pageModel = ajrdpp.getPageModel();
		if(pageModel != null){
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		
		return sql;
	}
}
