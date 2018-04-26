package com.asynclife.lucene.index;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class IndexCreator {

	public void createIndex(File folder, IndexStore where) {
		IndexWriter indexWriter = null;
		try {
			// 创建Directory，指定索引存储的地方：内存或者硬盘
			Directory directory = null;
			if (IndexStore.MEMORY.equals(where)) {
				directory = new RAMDirectory();
			} else {
				File indexFolder = new File("target/index");
				indexFolder.mkdir();
				directory = FSDirectory.open(indexFolder);
			}

			// 创建IndexWriter，写索引
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35,
					analyzer);
			indexWriter = new IndexWriter(directory, config);

			// 创建Document，添加Field（标题，大小，路径，内容）
			for (File file : folder.listFiles()) {
				Document doc = new Document();
				// 添加文件内容
				doc.add(new Field("content", new FileReader(file)));
				// 添加文件名到索引并且存储，对文件名不作分词
				doc.add(new Field("filename", file.getName(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				// 添加文件路径到索引并且存储，对文件路径不作分词
				doc.add(new Field("path", file.getAbsolutePath(),
						Field.Store.YES, Field.Index.NOT_ANALYZED));
				// 使用IndexWriter将Document添加到索引中
				indexWriter.addDocument(doc);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				indexWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
