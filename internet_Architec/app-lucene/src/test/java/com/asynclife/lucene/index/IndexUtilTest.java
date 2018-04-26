package com.asynclife.lucene.index;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.junit.Before;
import org.junit.Test;

public class IndexUtilTest {

	IndexUtil indexUtil;

	@Before
	public void setUp() {
		indexUtil = new IndexUtil();
		indexUtil.createIndex();
		indexUtil.initIndexReader();
	}

	/**
	 * 精确查询
	 */
	@Test
	public void testSearchByTerm() {
		// 查询content字段中存在like的记录
		String fieldName = "content";
		String searchValue = "like";
		int count = 3;
		indexUtil.searchByTerm(fieldName, searchValue, count);
	}

	/**
	 * 查询某个范围---字符串
	 */
	@Test
	public void testSearchByTermRange() {
		indexUtil.searchByTermRange("id", "1", "4", 4); // 查询id从1-6的记录，最多取4条
		indexUtil.searchByTermRange("name", "f", "k", 10); // 查询 email中以b或c开头的记录
	}

	/**
	 * 查询某个范围---数字
	 */
	@Test
	public void testSearchByTermRangeNumeric() {
		indexUtil.searchByTermRangeNumeric("attach", 1, 10, 10); // 查询数字范围
	}

	/**
	 * 基于前缀的查询
	 */
	@Test
	public void testSearchByPrefix() {
		String prefix = "j";
		indexUtil.searchByPrefix("name", prefix, 10); // 查询name以j开头的记录

		prefix = "s";
		indexUtil.searchByPrefix("content", prefix, 10); // 查询content分词后存在以s开头的记录
	}

	/**
	 * 基于通配符的查询
	 */
	@Test
	public void testSearchByWildcard() {
		// ? 匹配单个字符
		// * 匹配任意个字符
		indexUtil.searchByWildcard("name", "*s??", 10); // 查询name以j开头的记录

	}

	/**
	 * 多条件联合查询
	 */
	@Test
	public void testSearchByMultiCondition() {
		indexUtil.searchByBoolean(6);

	}

	/**
	 * 基于短语的查询--主要靠空格来分词。对中文处理无用
	 */
	@Test
	public void testSearchByPhrase() {
		indexUtil.searchByPhrase(6);

	}

	/**
	 * 模糊查询
	 */
	@Test
	public void testSearchByFuzzy() {
		indexUtil.searchByFuzzy("name", "joue", 5);

	}

	/**
	 * 基于QueryParser的查询 ---直接传入整句进行查找，匹配度最高的越靠前
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testSearchByQueryParser() throws ParseException {
		String defaultField = "content";
		QueryParser parser = new QueryParser(Version.LUCENE_35, defaultField,
				new StandardAnalyzer(Version.LUCENE_35));

		// 空格默认解析为OR条件
		Query query = parser.parse("i like movie");
		indexUtil.searchByQueryParser(query, 10);

		// 基于某个字符串的精确匹配---相当于空格解析为AND
		query = parser.parse("\"i like movie\"");
		indexUtil.searchByQueryParser(query, 10);

		// i 和 movie 之间存在1个任意单词
		query = parser.parse("\"i movie\"~1");
		indexUtil.searchByQueryParser(query, 10);

		// 模糊查询
		query = parser.parse("name:jehn~");
		indexUtil.searchByQueryParser(query, 10);

		// 改变空格的逻辑为AND条件
		parser.setDefaultOperator(Operator.AND);
		query = parser.parse("i like movie");
		indexUtil.searchByQueryParser(query, 10);

		// 指定搜索域，复合表达式
		query = parser.parse("(content:i AND movie) AND (id:3)");
		indexUtil.searchByQueryParser(query, 10);

		// 表达式中使用通配符
		query = parser.parse("name:j*");
		indexUtil.searchByQueryParser(query, 10);

		// 限定符 ： - 表示不存在 + 表示存在
		query = parser.parse("- name:resin + swim");
		indexUtil.searchByQueryParser(query, 10);

		// 区间查询 必须大小TO
		query = parser.parse("id:[2 TO 4]");
		indexUtil.searchByQueryParser(query, 10);

	}

	@Test
	public void testSearchAfter() {
		String fieldName = "content";
		String searchValue = "like";
		int pageSize = 2;
		for (int i = 1; i <= 10; i++) {
			int pageIndex = i;
			indexUtil.searchByPage(fieldName, searchValue, pageIndex, pageSize);
		}
	}

}
