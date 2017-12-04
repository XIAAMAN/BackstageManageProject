package cn.nchu.lims.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import cn.nchu.lims.domain.News;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.StringUtil;

public class DocumentDynaSqlProvider {

	public String saveDyna(final News news) {
		return new SQL() {
			{
				INSERT_INTO(Constant.NEWS_TABLE);
				if(!StringUtil.isNullOrEmpty(news.getTitle())){
					VALUES("title", "#{title}");
				}
				if(!StringUtil.isNullOrEmpty(news.getAuthor())){
					VALUES("author", "#{author}");
				}
				if(!StringUtil.isNullOrEmpty(news.getContent())){
					VALUES("content", "#{content}");
				}
				if(news.getPublishTime() != null){
					VALUES("publishTime", "#{publishTime}");
				}
				if(!StringUtil.isNullOrEmpty(news.getKeyword())){
					VALUES("keyword", "#{keyword}");
				}
				if(news.getSeq() != 0){
					VALUES("seq", "#{seq}");
				}
			}
		}.toString();
	}
}
