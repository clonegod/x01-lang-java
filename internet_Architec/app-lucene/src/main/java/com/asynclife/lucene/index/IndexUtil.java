package com.asynclife.lucene.index;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexUtil {

	private String[] ids = { "1", "2", "3", "4", "5", "6" };
	private String[] emails = { "a@a.com", "b@b.com", "c@c.com", "d@d.com",
			"e@e.com", "f@f.com" };
	private String[] contents = { "you like what", "i like swim",
			"i like movie", "i love sports", "i like movie and sports",
			"i love swim like too" };
	private String[] names = { "zhangsan", "lisi", "john", "jetty", "tomcat",
			"resin" };
	private int[] attaches = { 30, 20, 10, 50, 60, 40 }; // 数字类型
	private Date[] dates = { new Date(), new Date(), new Date(), new Date(),
			new Date(), new Date() };

	private Directory directory;// RAMDirectory();
	private IndexReader indexReader;

	public IndexUtil() {
		try {
			File indexDir = new File("target/index");
			indexDir.mkdirs();
			directory = FSDirectory.open(indexDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createIndex() {
		IndexWriter indexWriter = null;
		try {
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35,
					analyzer);
			indexWriter = new IndexWriter(directory, config);

			indexWriter.deleteAll();

			for (int i = 0; i < ids.length; i++) {
				Document doc = new Document();
				doc.add(new Field("id", ids[i], Field.Store.YES,
						Field.Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new Field("email", emails[i], Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("name", names[i], Field.Store.YES,
						Field.Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new Field("content", contents[i], Field.Store.NO,
						Field.Index.ANALYZED));
				doc.add(new NumericField("attach", Field.Store.YES, true)
						.setIntValue(attaches[i]));
				doc.add(new NumericField("date", Field.Store.YES, true)
						.setLongValue(dates[i].getTime()));
				indexWriter.addDocument(doc);
			}

			indexWriter.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				indexWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void initIndexReader() {
		try {
			if (indexReader == null) {
				indexReader = IndexReader.open(directory);
			} else {
				IndexReader newReader = IndexReader.openIfChanged(indexReader);
				if (newReader != null) {
					indexReader.close();
					indexReader = newReader;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void searchByTerm(String fieldName, String searchValue, int count) {
		try {
			IndexSearcher searcher = new IndexSearcher(indexReader);
			Query query = new TermQuery(new Term(fieldName, searchValue));
			TopDocs topDocs = searcher.search(query, count);
			System.out.println("一共查询到：" + topDocs.totalHits);

			executeQuery(count, searcher, query);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void searchByTermRange(String fieldName, String start, String end,
			int count) {
		try {
			IndexSearcher searcher = new IndexSearcher(indexReader);
			Query query = new TermRangeQuery(fieldName, start, end, true, false); // 包头不包尾
			TopDocs topDocs = searcher.search(query, count);
			System.out.println("一共查询到：" + topDocs.totalHits);

			executeQuery(count, searcher, query);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void searchByTermRangeNumeric(String fieldName, int min, int max,
			int count) {
		try {
			IndexSearcher searcher = new IndexSearcher(indexReader);
			Query query = NumericRangeQuery.newIntRange(fieldName, min, max,
					true, true);
			TopDocs topDocs = searcher.search(query, count);
			System.out.println("一共查询到：" + topDocs.totalHits);

			executeQuery(count, searcher, query);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void searchByPrefix(String fieldName, String prefix, int count) {
		try {
			IndexSearcher searcher = new IndexSearcher(indexReader);
			Query query = new PrefixQuery(new Term(fieldName, prefix));
			TopDocs topDocs = searcher.search(query, count);
			System.out.println("一共查询到：" + topDocs.totalHits);

			executeQuery(count, searcher, query);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void searchByWildcard(String fieldName, String prefix, int count) {
		try {
			IndexSearcher searcher = new IndexSearcher(indexReader);
			Query query = new WildcardQuery(new Term(fieldName, prefix));
			TopDocs topDocs = searcher.search(query, count);
			System.out.println("一共查询到：" + topDocs.totalHits);

			executeQuery(count, searcher, query);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void searchByBoolean(int count) {
		try {
			IndexSearcher searcher = new IndexSearcher(indexReader);

			// 多条件联合查询
			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(new TermQuery(new Term("content", "like")),
					Occur.MUST);
			booleanQuery.add(new TermQuery(new Term("name", "zhangsan")),
					Occur.MUST_NOT);

			executeQuery(count, searcher, booleanQuery);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void searchByPhrase(int count) {
		try {
			IndexSearcher searcher = new IndexSearcher(indexReader);

			// 短语查询
			PhraseQuery query = new PhraseQuery();
			query.setSlop(1); // number of other words permitted between words
			query.add(new Term("content", "i"));
			query.add(new Term("content", "movie"));

			executeQuery(count, searcher, query);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void searchByFuzzy(String feildName, String valueLike, int count) {
		try {
			IndexSearcher searcher = new IndexSearcher(indexReader);

			FuzzyQuery query = new FuzzyQuery(new Term(feildName, valueLike),
					0.2f);
			executeQuery(count, searcher, query);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void searchByQueryParser(Query query, int count) {
		try {
			IndexSearcher searcher = new IndexSearcher(indexReader);
			executeQuery(count, searcher, query);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param fieldName
	 *            字段域
	 * @param searchValue
	 *            查询内容
	 * @param pageIndex
	 *            页面从1开始
	 * @param pageSize
	 *            每页记录数
	 */
	public void searchByPage(String fieldName, String searchValue,
			int pageIndex, int pageSize) {
		IndexSearcher searcher = null;
		try {
			searcher = new IndexSearcher(indexReader);
			QueryParser parser = new QueryParser(Version.LUCENE_35, fieldName,
					new StandardAnalyzer(Version.LUCENE_35));

			Query query = parser.parse(searchValue);

			int MAX_RECORD = pageIndex * pageSize; // 限定每次最多查询多少条进行分页
			TopDocs topDocs = searcher.search(query, MAX_RECORD); // 先查询至多x条记录，再过滤
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;

			if (MAX_RECORD - scoreDocs.length >= pageSize) {
				return; // 防止角标越界
			}
			System.out.println("共：" + topDocs.totalHits + "条记录，每页" + pageSize
					+ "条，第" + pageIndex + "页");

			// 分页
			int preDocNum = (pageIndex - 1) * pageSize - 1; // 上一次分页的最后1条记录的索引号

			// 获取上一次分页的最后1条记录
			if (preDocNum == -1) { // 第1页
				topDocs = searcher.searchAfter(null, query, pageSize);
			} else {
				topDocs = searcher.searchAfter(scoreDocs[preDocNum], query,
						pageSize);
			}

			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				Document doc = searcher.doc(scoreDoc.doc);
				System.out.println(doc.get("id") + "--->" + doc.get("email")
						+ "--->" + doc.get("name") + "--->" + doc.get("attach")
						+ "--->" + doc.get("date")

				);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				searcher.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void executeQuery(int count, IndexSearcher searcher, Query query)
			throws IOException, CorruptIndexException {
		TopDocs topDocs = searcher.search(query, count);
		System.out.println("一共查询到：" + topDocs.totalHits);

		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			Document doc = searcher.doc(scoreDoc.doc);
			System.out.println(doc.get("id") + "--->" + doc.get("email")
					+ "--->" + doc.get("name") + "--->" + doc.get("attach")
					+ "--->" + doc.get("date")

			);
		}

		searcher.close();
	}
}
