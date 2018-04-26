package com.asynclife.lucene.index;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.junit.After;
import org.junit.Test;

public class LuceneTest {

	@Test
	public void testCreateIndex() {
		IndexCreator indexer = new IndexCreator();
		indexer.createIndex(new File("src/test/resources/files"),
				IndexStore.DISK);
	}

	@Test
	public void testSearchFromIndex() {
		String target = "lucene";
		Searcher searcher = new Searcher();
		searcher.search(new File("target/index"), target);
	}

	@After
	public void reportIndex() {
		new IndexService().reportIndex();
	}

	@Test
	public void testAddIndex() {
		Map<String, String> addressMap = new HashMap<String, String>();
		addressMap.put("1", "北京市朝阳区aaa街道");
		addressMap.put("2", "上海市浦东区bbb街道");
		addressMap.put("3", "广州市越秀区ccc街道");

		IndexService service = new IndexService();
		service.addIndex(addressMap, false);

	}

	@Test
	public void testDeleteAll() {
		IndexService service = new IndexService();
		service.deleteAll();
	}

	@Test
	public void testDeleteTerm() {
		IndexService service = new IndexService();
		Term term = new Term("id", "1"); // 删除包含指定field的Document（xxx.del--回收站）
		service.delete(term);
	}

	@Test
	public void testRecoverDeletedDoc() {
		IndexService service = new IndexService();
		service.recoverDeletedDocument();
	}

	@Test
	public void testForceMerge() {
		IndexService service = new IndexService();
		service.forceMerge(2);
	}

	@Test
	public void testUpdate() {
		Term term = new Term("id", "1");

		Document newDoc = new Document();
		newDoc.add(new Field("id", "23", Field.Store.YES,
				Field.Index.NOT_ANALYZED_NO_NORMS));

		IndexService service = new IndexService();
		service.updateIndex(term, newDoc);

	}

	@Test
	public void search() {
		IndexService service = new IndexService();

		AtomicInteger ai = new AtomicInteger();

		Map<String, String> emails = new HashMap<String, String>();
		emails.put(ai.incrementAndGet() + "", "a01@163.com");
		emails.put(ai.incrementAndGet() + "", "a02@126.com");
		emails.put(ai.incrementAndGet() + "", "a03@126.com");
		emails.put(ai.incrementAndGet() + "", "a04@163.com");
		service.addIndex(emails, true);

		Searcher searcher = new Searcher();
		Term term = new Term("content", "com");
		searcher.searchTerm(new File("target/index"), term);
	}

}
