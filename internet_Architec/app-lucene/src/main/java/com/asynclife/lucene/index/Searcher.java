package com.asynclife.lucene.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Searcher {

	public void search(File indexFolder, String target) {
		IndexSearcher searcher = null;
		try {
			// 搜索目标对应的索引在哪里
			Directory directory = FSDirectory.open(indexFolder);

			// 创建IndexSearcher
			IndexReader reader = getIndexReader(directory);
			searcher = new IndexSearcher(reader);

			// 创建Query
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content",
					new StandardAnalyzer(Version.LUCENE_35));
			Query query = parser.parse(target); // 目标：content中包含target内容的文件

			// 返回TopDocs
			TopDocs topDocs = searcher.search(query, 10);

			// 获取ScoreDoc对象
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;

			// 获取Document
			for (ScoreDoc sd : scoreDocs) {
				Document document = searcher.doc(sd.doc);
				System.out.println(document.get("filename") + ","
						+ document.get("path"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				searcher.close(); // reader保持打开，关闭searcher
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private IndexReader getIndexReader(Directory directory)
			throws CorruptIndexException, IOException {
		IndexReader reader = IndexReader.open(directory);
		if (reader != null) {
			// 重新打开，让索引的改变实时生效
			IndexReader newReader = IndexReader.openIfChanged(reader);
			if (newReader != null) {
				reader.close(); // 关闭旧的reader
				reader = newReader;
			}
		}
		return reader;
	}

	public void searchTerm(File indexFolder, Term term) {
		IndexSearcher searcher = null;
		try {
			// 搜索目标对应的索引在哪里
			Directory directory = FSDirectory.open(indexFolder);

			// 创建IndexSearcher
			IndexReader reader = getIndexReader(directory);
			searcher = new IndexSearcher(reader);

			TermQuery query = new TermQuery(term);

			// 返回TopDocs
			TopDocs topDocs = searcher.search(query, 10);

			// 获取ScoreDoc对象
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;

			// 获取Document
			for (ScoreDoc sd : scoreDocs) {
				Document document = searcher.doc(sd.doc);
				System.out.println(sd.score + "," + document.get("id") + ","
						+ document.get("content"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				searcher.close(); // reader保持打开，关闭searcher
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
